;; https://buttondown.email/cassidoo/archive/dont-be-afraid-of-hard-work-nothing-worthwhile/

(defn is-vowel? [c]
  (let [c-lower (clojure.string/lower-case c)]
    (boolean (some #{c-lower} ["a" "e" "i" "o" "u"]))))

(def is-consonant? (complement is-vowel?))

(defn capital-after-vowel [string]
  (->> (cons "" string)  ;; #util #pattern - look behind 
       (map vector string)
       (map (fn [[c c-prev]]
              (if (and (is-consonant? c)
                       (is-vowel? c-prev))
                (clojure.string/upper-case c)
                c)))
       (clojure.string/join)))

(capital-after-vowel "hello world")
(assert (= "heLlo WoRld" (capital-after-vowel "hello world")))
(assert (= "xaaBeueKaDii" (capital-after-vowel "xaabeuekadii")))
