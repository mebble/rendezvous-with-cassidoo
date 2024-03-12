;; https://buttondown.email/cassidoo/archive/the-potential-for-greatness-lives-within-each-of/
;; https://twitter.com/cassidoo/status/1762023522241573185

(require '[clojure.string :refer [index-of]])

(defn- all-indexes [string value]
  (loop [indexes (vector)
         from 0]
    (if-let [i (index-of string value from)]
      (recur (conj indexes i)
             (inc i))
      indexes)))

(defn remove-digit [n d]
  (let [s   (str n)
        indexes (all-indexes s (str d))]
    (if (seq indexes)
      (->> indexes
           (map (fn [i] (str (subs s 0 i)
                             (subs s (inc i)))))
           (map parse-long)
           (apply max))
      n)))

(assert (= 3415926 (remove-digit 31415926 1)))
(assert (= 231 (remove-digit 1231 1)))
(assert (= 1211 (remove-digit 12121 2)))
(assert (= 111 (remove-digit 111 2)))
