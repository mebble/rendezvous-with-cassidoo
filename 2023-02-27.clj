;; https://buttondown.email/cassidoo/archive/like-what-you-do-and-then-you-will-do-your-best/

(defn repeated-groups [xs]
  (->> xs
       (partition-by identity)
       (filter #(> (count %) 1))))

(assert (= [[2, 2]]
           (repeated-groups [1, 2, 2, 4, 5])))
(assert (= [[1, 1], [0, 0], [4, 4, 4], [9, 9]]
           (repeated-groups [1, 1, 0, 0, 8, 4, 4, 4, 3, 2, 1, 9, 9])))
