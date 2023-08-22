;; https://buttondown.email/cassidoo/archive/not-everything-that-is-faced-can-be-changed-but-1047/
;; https://twitter.com/cassidoo/status/1693451276934078935

(defn check [guess target]
  (cond
    (= guess target) :ok
    (< guess target) :higher
    (> guess target) :lower))

(let [target (rand-int 100)]
  (println "Guess the number!")
  (loop [num-guesses 1]
    (let [guess (Integer/parseInt (read-line))
          status (check guess target)]
      (if (= :ok status)
        (println (str "Correct! You won in " num-guesses " guesses!"))
        (do (println (name status))
            (recur (inc num-guesses)))))))
