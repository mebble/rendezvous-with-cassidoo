(defn- sq [x] (* x x))

(defn squares [n]
  (let [xs (range 1 (inc n))]
    (apply + (map sq xs))))

(assert (= 55 (squares 5)))
(assert (= 385 (squares 10)))
(assert (= 5525 (squares 25)))
(assert (= 338350 (squares 100)))
