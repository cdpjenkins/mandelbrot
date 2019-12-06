(ns mandelbrot.core-test
  (:use mandelbrot.core)
  (:require [clojure.test :refer :all]
            [mandelbrot.core :refer :all]))

(deftest simple-mandelbrot
  (testing "Create a simple textual representation of a Mandelbrot set"
    (let [expected-mandie (list ".............................."
                                ".............................."
                                "..................XX.........."
                                "..................XX.........."
                                "...............X.XXXXX........"
                                "...............XXXXXXXXX......"
                                "..............XXXXXXXXXX......"
                                "..............XXXXXXXXXX......"
                                ".........XXX.XXXXXXXXXXX......"
                                "........XXXXXXXXXXXXXXXX......"
                                ".....XXXXXXXXXXXXXXXXXX......."
                                "........XXXXXXXXXXXXXXXX......"
                                ".........XXX.XXXXXXXXXXX......"
                                "..............XXXXXXXXXX......"
                                "..............XXXXXXXXXX......"
                                "...............XXXXXXXXX......"
                                "...............X.XXXXX........"
                                "..................XX.........."
                                "..................XX.........."
                                ".............................."
                                "..............................")]
      (is (= expected-mandie (text-mandelbrot-lazy-seq))))))
