;; https://buttondown.email/cassidoo/archive/the-spirit-of-envy-can-destroy-it-can-never-build/
;; https://twitter.com/cassidoo/status/1726515013458993237

(defn- constrained [l h xs]
  ;; #util
  (->> xs
       (drop-while (fn [x] (< x l)))
       (take-while (fn [x] (< x h)))))

(defn- gen-evens [l h]
  (if (even? l)
    (range (+ l 2) h 2)
    (range (+ l 1) h 2)))

(defn- gen-odds [l h]
  (if (odd? l)
    (range (+ l 2) h 2)
    (range (+ l 1) h 2)))

(defn- prime? [n]
  (cond
    (< n 1) false
    (= n 2) true
    :else   (->> (range 2 (inc (Math/sqrt n)))
                 (filter (fn [i] (zero? (mod n i))))
                 (empty?))))

(defn- gen-primes [l h]
  (->> (range l h)
       (constrained 2 h)
       (filter prime?)))

(defn between-nums [a b s]
  (let [[a b] (sort [a b])]
    (case s
      "even"  (gen-evens a b)
      "odd"   (gen-odds a b)
      "prime" (gen-primes a b)
      [])))

(assert (= [4 6 8 10] (between-nums 3 11 "even")))
(assert (= [5 7 9] (between-nums 3 11 "odd")))
(assert (= [2 3 5 7 11 13] (between-nums 15 1 "prime")))
