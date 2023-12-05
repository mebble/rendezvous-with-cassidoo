;; https://buttondown.email/cassidoo/archive/the-best-preparation-for-tomorrow-is-doing-your-9764/
;; https://twitter.com/cassidoo/status/1731574854019666130

(defn rotated-num [nums]
  (->> (rest nums)
       (map vector nums)
       (take-while (fn [[l r]] (<= l r)))
       (count)
       (inc)
       ((fn [rotations] (if (= rotations (count nums))
                          0
                          rotations)))))

(assert (= 1 (rotated-num [4 0 1 2 3])))
(assert (= 0 (rotated-num [7 9 20])))
(assert (= 4 (rotated-num [7 7 314 1337 7])))
