;; https://buttondown.email/cassidoo/archive/if-you-want-to-feel-good-you-have-to-go-out-and/

(defn generate-arrays [n]
  (->> (inc n)
       (range 1)
       (map (fn [m] (->> (inc m)
                         (range 1))))))

(assert (= [[1], [1, 2], [1, 2, 3], [1, 2, 3, 4]] (generate-arrays 4)))
(assert (= [[1]] (generate-arrays 1)))
