;; https://buttondown.email/cassidoo/archive/5528/

(defn remove-zeroes [xs]
  (->> xs
       (drop-while zero?)
       (reverse)
       (drop-while zero?)
       (reverse)))

(assert (= [3 1 4 1 5 9]
           (remove-zeroes [0 0 0 3 1 4 1 5 9 0 0 0 0])))
(assert (= []
           (remove-zeroes [0 0 0])))
(assert (= [8]
           (remove-zeroes [8])))
