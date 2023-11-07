;; https://buttondown.email/cassidoo/archive/quality-is-not-an-act-it-is-a-habit-aristotle/
;; https://twitter.com/cassidoo/status/1721420074618073124

(def letter-scores
  (->> (range 1 27)
       (map (fn [n] [(char (+ 96 n)) n]))
       (into {})))

(defn- word-score [scores word]
  (* (count word)
     (reduce + (map scores word))))

(defn- max-key-multiple [k xs]
  (reduce (fn [maxes x]
            (if-let [f (first maxes)]
              (let [kx (k x)
                    kf (k f)]
                (cond
                  (> kx kf) [x]
                  (= kx kf) (cons x maxes)
                  :else     maxes))
              [x]))
          []
          xs))

(defn score-word-game [words scores]
  (->> words
       (max-key-multiple (partial word-score scores))
       (sort)
       (first)))

(assert (= "cherry" (score-word-game ["apple" "banana" "cherry" "date" "fig"] letter-scores)))
(assert (= "evil" (score-word-game ["live" "evil" "vile"] letter-scores)))
