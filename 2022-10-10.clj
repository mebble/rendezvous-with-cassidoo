;; TODO
;; Use Character/isLetter

(defn- alpha? [c]
  ;; #util
  (let [x (int c)]
    (or (and (>= x 97)
             (<= x 122))
        (and (>= x 65)
             (<= x 90)))))

(defn- word? [w] (every? alpha? w))
(defn- trunc [s n]
  (subs s 0 (min (count s) n)))
(defn- alpha-end? [v]
  (-> v
      last
      last
      alpha?))
(defn- poke [x] (doto x prn))

(defn truncate [sentence n]
  (->> sentence
       (reduce (fn [v c] (cond
                           (empty? v)           [(str c)]
                           (and (alpha? c)
                                (alpha-end? v)) (conj (pop v) (str (last v) c))
                           :else                (conj v (str c))))
               [])
       (map #(if (word? %) (trunc % n) %))
       (poke)
       (clojure.string/join)))

(let [n 3]
  (assert (= "nev gon giv you up"
             (truncate "never gonna give you up" n)))
  (assert (= "*hel* dar, my ~old_fri"
             (truncate "*hello* darkness, my ~old_friend" n))))
