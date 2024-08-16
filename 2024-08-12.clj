;; https://buttondown.com/cassidoo/archive/work-smart-get-things-done/
;; https://x.com/cassidoo/status/1822863723855323419

(require '[clojure.string :as s])

(defn- parse-assignment [statement]
  (let [[lhs rhs] (map s/trim (s/split statement #"="))]
    [:assignment {:lhs lhs :rhs rhs}]))

(defn- parse-log [statement]
  (let [[_ variable] (re-find #"log\((.*)\)" statement)]
    [:log {:var variable}]))

(defn- parse [statement]
  (cond
    (s/includes? statement "=") (parse-assignment statement)
    :else                       (parse-log statement)))

(defn- collect-vars [vars statement]
  (let [[statement-type statement] statement]
    (case statement-type
      :log        (assoc vars (:var statement) true)
      :assignment (assoc vars
                         (:lhs statement) false
                         (:rhs statement) true))))

(defn find-unused [statements]
  (->> (map parse statements)
       (reduce collect-vars {})
       (filter (fn [[_ used?]] (not used?)))
       (map first)))

(assert (= ["a", "c"] (find-unused ["a = 1" "b = 3" "c = 2" "log(b)"])))
(assert (= ["c"] (find-unused ["a = 1" "b = a" "c = 2" "log(b)"])))
