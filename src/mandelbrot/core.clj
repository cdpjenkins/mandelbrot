(ns mandelbrot.core
  (:require [mandelbrot.draw :as d]))


(def max-iterations 255)
(def step 0.002)
(def bound 4)

(defn square-complex [[x y]]
  [(- (* x x) (* y y))  (* 2 x y) ])

(defn add-complex [[x1 y1] [x2 y2]]
  [(+ x1 x2) (+ y1 y2)])

(defn mandelbrot-step [x c]
  "Return the next term in the Mandelbrot series"
  (let [xn+1 (add-complex (square-complex x) c)]
    xn+1))

(defn magnitude-squared [[x y]]
  (+  (* x x) (* y y)))


(defn within-bounds [z]
  (< (magnitude-squared z) 4))

(defn mandelbrot [c]
  (let [iterations (count
          (take max-iterations
                (take-while within-bounds
                            (iterate #(mandelbrot-step % c) [0 0]))))]
    iterations))

(defn text-mandelbrot []
  (doseq [y (range -1 1 step)]
    (doseq  [x (range -2 1 step)] 
             (let [iterations (mandelbrot [x y])]
               (if (< iterations 99)
                 (print ".")
                 (print "X"))))
    (println)))

(defn- mandelbrot-set
  "Evaluate whether the imaginary numbers in the given range lie
  within the Mandelbrot set "
  [[x-start x-end x-step]
   [y-start y-end y-step]
   f]
  (doseq [[y-idx y] (map-indexed #(vector %1 %2) (range y-start y-end y-step))]
    (doseq [[x-idx x] (map-indexed #(vector %1 %2) (range x-start x-end x-step))]
      (let [iterations (mandelbrot [x y])]
        (f iterations x-idx y-idx)))))

(defn- steps-count
  [[start end step]]
  (-> end
      (- start)
      (/ step)
      ;(inc)
      (int)))

(defn gui-mandelbrot
  []
  (let [y [-1 1 step]
        x [-2 1 step]
        width (steps-count x)
        height (steps-count y)]
    (d/init-draw width height)
    (mandelbrot-set x y d/mandelbrot-pixel)))

(defn mandelbrot-seq [minx miny stepx stepy maxx maxy]
  (for [y (range miny maxy stepy)]
    (for  [x (range minx maxx stepx)]
             (let [iterations (mandelbrot [x y])]
               iterations))
    ))


(defn -main [& args]
  ;(text-mandelbrot)
  (gui-mandelbrot)
  )

