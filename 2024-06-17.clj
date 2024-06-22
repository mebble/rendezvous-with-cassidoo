;; https://buttondown.email/cassidoo/archive/life-is-trying-things-to-see-if-they-work-ray/
;; https://x.com/cassidoo/status/1802593214366372080

(def vowels (set "aeiouAEIOU"))

(defn num-vowels [name]
  (->> name
       (filter vowels)
       (count)))

(defn compare-num-vowels [name1 name2]
  (let [n1 (num-vowels name1)
        n2 (num-vowels name2)]
    (- n1 n2)))

(defn compare-names [name1 name2]
  (let [x (compare-num-vowels name1 name2)]
    (if-not (zero? x)
      x
      (- (compare name1 name2)))))

(defn sort-names [names]
  (reverse (sort-by identity compare-names names)))

(assert (= (sort-names ["Goku", "Vegeta", "Go"]) ["Vegeta", "Goku", "Go"]))
(assert (= (sort-names ["Goku", "Vegeta", "Piccolo", "Gohan"]) ["Piccolo", "Vegeta", "Gohan", "Goku"]))
(assert (= (sort-names ["Edward", "Alphonse", "Roy", "Winry"]) ["Alphonse", "Edward", "Roy", "Winry"]))
