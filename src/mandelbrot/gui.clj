(ns mandelbrot.gui
  (:use seesaw.core)
  (:use seesaw.graphics)
  (:use seesaw.color)
  (:require [mandelbrot.core :as m])
  (:import (java.awt.image ImageObserver)))

(def mandie (m/mandelbrot-seq -2 -1 0.01 0.01 1 1 ))
(def mandelbrot-image (buffered-image 800 800))

(def g (.getGraphics mandelbrot-image))

(def stoopid-image-observer
  (proxy [ImageObserver]
      []
    (imageUpdate []
      (proxy-super imageUpdate))))

(defn draw-mandie []
  (future (doseq [[y row] (map-indexed vector mandie)]
             (doseq [[x cell] (map-indexed vector row)]
               (.setColor g (color cell cell cell))
               (.fillRect g x y 1 1)))))

(def paint-canvas  (fn [c g]
                     (let [w (.getWidth c)
                           h (.getHeight c)
                           
                           ]
                       (.drawImage g mandelbrot-image 0 0 stoopid-image-observer))))


(comment
  (invoke-later
   (-> (frame :title "Hello",
              :content (canvas
                        :id :canvas
                        :background "#BBBBDD"
                        :paint paint-canvas

                        )
              :on-close :hide)
       pack!
       show!))

  (draw-mandie)
  )
