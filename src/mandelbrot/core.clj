(ns mandelbrot.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def max-iterations 100)
(def step 0.002)

(defn square-complex [[x y]]
  [(- (* x x) (* y y))  (* 2 x y) ])

(defn add-complex [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

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
                            (iterate #(mandelbrot-step % c) [0 0]))))]
    iterations))

(defn text-mandelbrot []
  (doseq [y (range -1 1 step)]
    (doseq  [x (range -2 1 step)] 
             (let [iterations (mandelbot [x y])]
               (if (< iterations 99)
                 (print ".")
                 (print "X"))))
    (println)))


(defn -main [& args]
  (text-mandelbrot))

