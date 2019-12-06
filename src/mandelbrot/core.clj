(ns mandelbrot.core
  (:require [clojure.string :as cs]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def max-iterations 255)
(def step 0.1)

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

(defn mandelbrizzle [c max-iterations]
  (let [iterations (count
                    (take max-iterations
                          (take-while within-bounds
                                      (iterate #(mandelbrot-step % c) [0 0]))))]
    iterations)
  )

(defn mandelbot [c]
  (mandelbrizzle c max-iterations))

(defn text-mandelbrot-lazy-seq []
  (for [y (range -1 1 step)]
    (cs/join ""
     (for  [x (range -2 1 step)]
       (let [iterations (mandelbot [x y])]
         (if (< iterations 99)
           "."
           "X"))))))

(defn text-mandelbrot []
  (doseq [row (text-mandelbrot-lazy-seq)]
    (println row)))

(defn mandelbrot-seq [minx miny stepx stepy maxx maxy]
  (for [y (range miny maxy stepy)]
    (for  [x (range minx maxx stepx)] 
             (let [iterations (mandelbot [x y])]
               iterations))))


(defn -main [& args]
  (text-mandelbrot))
