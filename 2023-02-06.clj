;; https://buttondown.email/cassidoo/archive/talent-without-working-hard-is-nothing-cristiano/

(defn- letter->int [letter]
  (- (int letter) 64))

(defn column-num [column-name]
  (->> column-name
       (map letter->int)
       (reverse)
       (map-indexed vector)
       (map (fn [[i n]] (int (* n (clojure.math/pow 26 i)))))
       (reduce +)))

(assert (= 1 (column-num "A")))
(assert (= 2 (column-num "B")))
(assert (= 3 (column-num "C")))
(assert (= 26 (column-num "Z")))
(assert (= 27 (column-num "AA")))
(assert (= 28 (column-num "AB")))
(assert (= 703 (column-num "AAA")))
