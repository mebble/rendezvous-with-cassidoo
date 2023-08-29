;; https://buttondown.email/cassidoo/archive/a-ship-in-port-is-safe-but-thats-not-what-ships/
;; https://twitter.com/cassidoo/status/1696054008387883479

(require '[clojure.string :refer [join]])

(defn- char->int [c]
  ;; #util
  (Integer/parseInt (str c)))

(defn- digit-seq [n]
  ;; #util
  (->> n
       (str)
       (map char->int)))

(def num->words
  {1 "one"
   2 "two"
   3 "three"
   4 "four"
   5 "five"
   6 "six"
   7 "seven"
   8 "eight"
   9 "nine"
   10 "ten"})

(defn count-and-say [n]
  (->> n
       (digit-seq)
       (partition-by identity)
       (map (fn [group] (vector (num->words (count group))
                                (first group))))
       (map (fn [[word n]] (str word " " n (if (= word "one") "" "s"))))
       (join ", then ")))

(assert (= "two 1s, then four 2s, then three 5s"
           (count-and-say 112222555)))
(assert (= "two 1s, then one 2, then three 5s"
           (count-and-say 112555)))
(assert (= "ten 3s"
           (count-and-say 3333333333)))
