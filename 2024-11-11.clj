;; https://buttondown.com/cassidoo/archive/stand-for-something-or-you-will-fall-for-anything/
;; https://x.com/cassidoo/status/1855860165985046867

(defn- accumulate [acc height]
  (if (> height (:highest acc))
    (-> acc
        (update :count inc)
        (assoc :highest height))
    acc))

(defn see-buildings-left [buildings]
  (:count (reduce accumulate
                  {:count 0 :highest 0}
                  buildings)))

(assert (= 5 (see-buildings-left [1 2 3 4 5])))
(assert (= 1 (see-buildings-left [5 4 3 2 1])))
(assert (= 3 (see-buildings-left [3 7 8 3 6 1])))
(assert (= 4 (see-buildings-left [3 7 8 3 6 9])))
