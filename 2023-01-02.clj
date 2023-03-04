;; https://buttondown.email/cassidoo/archive/from-a-small-seed-a-mighty-trunk-may-grow/

(defn- sum [xs]
  (apply + xs))

(defn max-subarray [xs n]
  (apply max-key sum (partition n 1 xs)))

(assert (= [1 2 3 6] (max-subarray [-4 2 -5 1 2 3 6 -5 1] 4)))
(assert (= [0 5] (max-subarray [1 2 0 5] 2)))
