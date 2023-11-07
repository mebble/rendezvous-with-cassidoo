;; https://buttondown.email/cassidoo/archive/quality-is-not-an-act-it-is-a-habit-aristotle/
;; https://twitter.com/cassidoo/status/1721420074618073124

(def letter-scores
  (->> (range 1 27)
       (map (fn [n] [(char (+ 96 n)) n]))
       (into {})))

(defn- word-score [scores word]
  (* (count word)
     (reduce + (map scores word))))

(defn score-word-game [words scores]
  (->> words
       (sort)
       (reverse)
       (apply max-key (partial word-score scores))))

(assert (= "cherry" (score-word-game ["apple" "banana" "cherry" "date" "fig"] letter-scores)))
(assert (= "evil" (score-word-game ["live" "evil" "vile"] letter-scores)))
