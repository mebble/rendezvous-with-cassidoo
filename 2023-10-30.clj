;; https://buttondown.email/cassidoo/archive/if-you-spend-too-much-time-looking-in-the/
;; https://twitter.com/cassidoo/status/1718817082865844523

(defn- char->int [c]
  ;; #util
  (Integer/parseInt (str c)))

(defn- digit-seq [n]
  ;; #util
  (->> n
       (str)
       (map char->int)))

(defn- digits->num [digits]
  ;; #util
  (->> digits
       (map str)
       (apply str)
       (parse-long)))

(defn- split-idx [digits]
  (->> digits
       (map-indexed vector)
       (reverse)
       (partition 2 1)
       (filter (fn [[[_ f] [_ s]]] (> f s)))
       (first)
       (second)
       (first)))

(defn- split [digits]
  (when-let [i (split-idx digits)]
    (split-at i digits)))

(defn- next-larger-idx [n xs]
  (->> xs
       (map-indexed vector)
       (reduce (fn [[ai a] [xi x]]
                 (cond
                   (<= x n) [ai a]
                   (< x a)  [xi x]
                   :else    [ai a])))
       (first)))

(defn- permutate [digits]
  (let [[f & xs] digits
        i (next-larger-idx f xs)
        [fs [x & ys]] (split-at i xs)]
    (cons x (sort (cons f (concat fs ys))))))

(defn lexo-next [n]
  (when-let [[preserved-seq permutate-seq] (split (digit-seq n))]
    (digits->num (concat preserved-seq (permutate permutate-seq)))))

(assert (nil? (lexo-next 1))) ;; no other permutation
(assert (nil? (lexo-next 11))) ;; stays same
(assert (nil? (lexo-next 21))) ;; gets smaller
(assert (= 132 (lexo-next 123))) ;; permutate last two
(assert (= 314195 (lexo-next 314159))) ;; permutate last two
(assert (= 315124 (lexo-next 314521))) ;; permutate many, swap adjacent
(assert (= 2412335 (lexo-next 2354321))) ;; permutate many, swap non-adjacent
