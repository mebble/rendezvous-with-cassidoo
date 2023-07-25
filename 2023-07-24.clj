;; https://buttondown.email/cassidoo/archive/the-thermometer-of-success-is-merely-the-jealousy/

(defn pairing [n xs]
  (map #(vector n %) xs))

(defn zip-forward [xs]
  (if (empty? xs)
    xs
    (let [x (first xs)
          ys (rest xs)]
      (concat (pairing x ys) (zip-forward ys)))))

(defn maximum-profit [prices]
  (->> prices
       (zip-forward)
       (map #(- (second %) (first %)))
       (apply max)
       (max 0)))

(assert (= (maximum-profit [7 1 5 3 6 4])
           5))
(assert (= (maximum-profit [9 7 5 3 1])
           0))
