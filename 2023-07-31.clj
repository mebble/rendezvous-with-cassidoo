;; https://buttondown.email/cassidoo/archive/science-is-organized-knowledge-wisdom-is/
;; https://twitter.com/cassidoo/status/1685848986421264384

(require '[clojure.string :refer [lower-case]])

(defn is-anagram [s t]
  (->> [s t]
       (map (comp frequencies lower-case))
       (apply =)))

(assert (not (is-anagram "barbie" "oppenheimer")))
(assert (is-anagram "race" "care"))
(assert (is-anagram "fooBar" "barFoo"))
