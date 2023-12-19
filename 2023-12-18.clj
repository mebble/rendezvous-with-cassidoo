;; https://buttondown.email/cassidoo/archive/everything-has-beauty-but-not-everyone-sees-it/
;; https://twitter.com/cassidoo/status/1736653884397912094

(defn- get-parts [xs]
  (->> (rest xs)
       (map vector xs)
       (partition-by (fn [[x y]] (>= y x)))
       (filter seq)))

(defn is-bitonic [xs]
  (let [parts (get-parts xs)
        yes? (= (count parts) 2)]
    (when yes?
      (-> parts first last second println))
    yes?))

(assert (is-bitonic [1 2 3 2]))
(assert (not (is-bitonic [1 2 3])))
(assert (is-bitonic [3 4 5 5 5 2 1]))
(assert (not (is-bitonic [1 2 3 2 1 2 3])))
