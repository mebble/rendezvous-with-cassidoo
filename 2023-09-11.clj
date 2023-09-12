;; https://buttondown.email/cassidoo/archive/people-who-think-they-know-everything-are-a-great/
;; https://twitter.com/cassidoo/status/1701099833560379659

(defn splitter [[evens odds] n]
  (if (even? n)
    [(conj evens n) odds]
    [evens (conj odds n)]))

(defn separate-and-sort [xs]
  (->> xs
       (sort)
       (reduce splitter [[] []])))

(assert (= [[2 4 8]  [1 3 5 7 9]]
           (separate-and-sort [4 3 2 1 5 7 8 9])))
(assert (= [[]  [1 1 1 1]]
           (separate-and-sort [1 1 1 1])))
