;; https://buttondown.email/cassidoo/archive/let-us-remember-that-our-voice-is-a-precious-gift/
;; https://twitter.com/cassidoo/status/1703653007013896654

(defn possibilities []
  (->> (range)
       (map inc)
       (map (fn [n] (repeat (inc n) n)))
       (apply concat)))

(defn build-staircase [n]
  (if (zero? n) 
    0
    (let [xs (possibilities)]
      (nth xs (dec n)))))

(assert (= 0 (build-staircase 0)))
(assert (= 1 (build-staircase 1)))
(assert (= 1 (build-staircase 2)))
(assert (= 2 (build-staircase 3)))
(assert (= 2 (build-staircase 4)))
(assert (= 2 (build-staircase 5)))
(assert (= 3 (build-staircase 6)))
(assert (= 3 (build-staircase 7)))
(assert (= 3 (build-staircase 8)))
(assert (= 3 (build-staircase 9)))
(assert (= 4 (build-staircase 10)))
(assert (= 4 (build-staircase 11)))
(assert (= 4 (build-staircase 12)))
(assert (= 4 (build-staircase 13)))
(assert (= 4 (build-staircase 14)))
(assert (= 5 (build-staircase 15)))
(assert (= 5 (build-staircase 16)))
(assert (= 5 (build-staircase 17)))
(assert (= 5 (build-staircase 18)))
(assert (= 5 (build-staircase 19)))
(assert (= 5 (build-staircase 20)))
(assert (= 6 (build-staircase 21)))
