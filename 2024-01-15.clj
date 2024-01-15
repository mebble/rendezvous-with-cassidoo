;; https://buttondown.email/cassidoo/archive/try-and-fail-but-dont-fail-to-try-john-quincy/
;; https://twitter.com/cassidoo/status/1746797451774758934

(def array [[1 2 3]
            [4 5 6]
            [7 8 9]])

(defn flip [xs direction]
  (case direction
    "horizontal" (map reverse xs)
    "vertical"   (reverse xs)
    :else xs))

(assert (= [[3 2 1] [6 5 4] [9 8 7]] (flip array "horizontal")))
(assert (= [[7 8 9] [4 5 6] [1 2 3]] (flip array "vertical")))
