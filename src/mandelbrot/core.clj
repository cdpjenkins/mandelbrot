(ns mandelbrot.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def max-iterations 100)

(defn square-complex [[x y]]
  [(- (* x x) (* y y))  (* 2 x y) ])

(defn add-complex [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ x2 y2)])


(defn mandelbrot-step [x c]
  (let [xn+1 (add-complex (square-complex x) c)]
    xn+1))

(defn magnitude-squared [[x y]]
  (+  (* x x) (* y y)))


(defn within-bounds [z]
  (< (magnitude-squared z) 4))

(defn mandelbot [c]
  (let [iterations (count
          (take max-iterations
                (take-while within-bounds
                            (iterate #(mandelbrot-step % [0 0]) c))))]))
