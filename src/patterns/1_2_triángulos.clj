(ns patterns.1_2_triangulos
  (:require [quil.core :as q]
            [quil.helpers.drawing :as d]
            [quil.helpers.seqs :as s]
            [quil.helpers.calc :as c]))



(defn key-pressed []
  ;(println (q/key-code))
  (cond
   (= 49 (q/key-code)) (q/save-frame "000_triangulos-####.png"))) ;1

(defn dibuja-triangulo-equilatero [x y l]
  (let [h (* l (q/sin (q/radians 60)))
        x1 (- x (/ l 2))
        y1 (- y (/ h 3))
        x2 x
        y2 (+ y (/ (* 2 h) 3))
        x3 (+ x (/ l  2))
        y3 (- y (/ h 3))
        step (/ l 72)
        n (+ 1 (/ l step))
        xs (s/range-incl 0 l step)
        rads (map #(* 5 (q/radians %)) (range n))
        ys (map q/sin rads)
        ;yss (map #(Math/pow % 3) ys)
        scaled-ys (map #(* 10 %) ys)
        line-args (d/line-join-points xs scaled-ys)]

    (q/with-translation [x1 y1]
      (q/rotate (q/radians 60))
      (dorun (map #(apply q/line %) line-args)))
    (q/with-translation [x2 y2]
      (q/rotate (q/radians 300))
      (dorun (map #(apply q/line %) line-args)))
    (q/with-translation [x3 y3]
      (q/rotate (q/radians 180))
      (dorun (map #(apply q/line %) line-args)))))

(defn setup []
  (q/color-mode :hsb 360 100 100)
  (q/smooth))

(defn draw []
  (q/background 360)
  (q/stroke 180 72 74)
  (q/stroke-weight 1)
  (q/no-fill)

  (let [w (q/width)
        h (q/height)
        nx 75
        ny (* nx (q/sin (/ q/PI 3)))
        l (if (= 0 (q/mouse-x)) 0.01 (q/map-range (q/mouse-x) 0 w 0 300))
        a (* l (q/sin (q/radians 60)))]
    (doseq [x (s/range-incl 0 w  nx)
            [index y] (s/indexed-range-incl 0 h ny)]
        (cond
         (odd? index)  (do
                         (q/with-translation [x y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           (dibuja-triangulo-equilatero 0 0 l)))
         (even? index)  (do
                          (q/with-translation [(+ x (/ nx 2)) y]
                           (q/rotate (q/map-range (q/mouse-y) 0 h 0 q/TWO-PI))
                           (dibuja-triangulo-equilatero 0 0 l))))))
  ;(q/fill 0)
  ;(q/text "1.2.triángulos" 10 20)
  ;(q/text "mouse-x" 10 40)
  ;(q/text-num (q/mouse-x) 80 40)
  ;(q/text "mouse-y" 10 60)
  ;(q/text-num (q/mouse-y) 80 60)
  )



(q/defsketch triangulos
  :size [1400 1000]
  :setup setup
  :draw draw
  :key-pressed key-pressed)
