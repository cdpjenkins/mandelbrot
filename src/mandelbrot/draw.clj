(ns mandelbrot.draw
  (:import (java.util Arrays)
           (javax.swing JFrame
                        JPanel)
           (java.awt Dimension
                     Color)))

(def backing-array (atom nil))
(def panel (atom nil))
(def colours-used (atom {}))

(defn init-draw [width height]
  (reset! backing-array (object-array (* width height)))

  (let [f (JFrame. "Mandelbrot")
        p (proxy [JPanel] []
            (paintComponent [g]
              ;(println "paint")
              (proxy-super paintComponent g)
              (loop [idx 0
                     arr @backing-array
                     len (alength arr)]
                (when (< idx len)
                  (let [pixel (get arr idx)
                        x (quot idx height)
                        y (mod idx height)]
                    (when pixel
                      (do
                        (.setColor g pixel)
                        (.drawLine g x y x y))))
                  (recur (inc idx)
                         arr
                         len)))))]
    (doto p
      (.setBackground Color/BLACK)
      (.setPreferredSize (Dimension. width height))
      (.setOpaque true))
    (doto f
      (.setDefaultCloseOperation javax.swing.JFrame/EXIT_ON_CLOSE)
      (.setContentPane p)
      (.setVisible true)
      (.pack)
      (.show))
    (reset! panel p)))

(defn set-pixel!
  "Set the pixel at x y to the given integer rgb value colour"
  [x y rgb]
  (let [c (or (get @colours-used rgb)
              (get (swap! colours-used assoc rgb (Color. rgb)) rgb))
        width (.getWidth @panel)
        height (.getHeight @panel)
        idx (+ (* x height) y)]
    (aset @backing-array
          idx
          c)
    (when (zero? x) ; repaint whole lines
      (.repaint @panel 0 y width 1))))

(defn mandelbrot-pixel
  [iterations x y]
  (let [color (if (< iterations 99)
                0xFFFFFF
                0x0)]
    (set-pixel! x y color)))
