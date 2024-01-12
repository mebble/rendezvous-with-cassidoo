;; https://buttondown.email/cassidoo/archive/the-most-effective-way-to-do-it-is-to-do-it-5924/
;; https://twitter.com/cassidoo/status/1744263422873678112

;; Run this with:
;; bb --classpath "$(clojure -Spath -Sdeps '{:deps {org.clojure/math.combinatorics {:mvn/version "0.2.0"}}}')" 2024-01-08.clj

(require '[clojure.math.combinatorics :as combo]
         '[clojure.string :as s])

(defn- letters [xs]
  (->> (combo/subsets xs)
       (map combo/permutations)
       (filter (comp seq first))
       (apply concat)
       (map s/join)
       (#(doto % prn))
       (count)))

(assert (= 1 (letters ["X"]))) ; => ("X")
(assert (= 8 (letters ["A", "A", "B"])))  ; => ("A" "B" "AA" "AB" "BA" "AAB" "ABA" "BAA")
