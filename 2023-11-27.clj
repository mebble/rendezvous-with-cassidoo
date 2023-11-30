;; https://buttondown.email/cassidoo/archive/when-i-let-go-of-what-i-am-i-become-what-i-might/
;; https://twitter.com/cassidoo/status/1729035353829630183

;; Run this with:
;; bb --classpath "$(clojure -Spath -Sdeps '{:deps {org.clojure/math.combinatorics {:mvn/version "0.2.0"}}}')" 2023-11-27.clj

;; Alternative algorithm to generate subsets:
; src: https://codepen.io/Tzyinc/pen/GRzBbYz?editors=0012
; const xs = [200, 400, 600, 800]
;
; const possibleSets = [];
; for (let i = 0; i < xs.length; i++) {
;     possibleSets.forEach(set => {
;         possibleSets.push([...set, i]);
;     });
;
;     possibleSets.push([i]);
; }
; Another algo: https://codereview.stackexchange.com/questions/8930/enumerate-k-combinations-in-clojure-p26-from-99-problems

(require '[clojure.math.combinatorics :as combo])

(defn- combo-price [items-combo]
  (->> items-combo
       (map :price)
       (reduce +)))

(defn- at-least [x xs]
  (if (seq xs)
    xs
    [x]))

(defn min-cost-for-calories [calories prices daily-goal]
  (let [items (map (fn [c p] {:calories c :price p})
                   calories
                   prices)]
    (->> items
         (combo/subsets)
         (filter (fn [combo] (>= (reduce + (map :calories combo))
                                 daily-goal)))
         (map combo-price)
         (at-least -1)
         (apply min))))

(assert (= 160 (min-cost-for-calories [200, 400, 600, 800] [50, 60, 80, 100] 1200)))
(assert (= -1 (min-cost-for-calories [200, 400, 600, 800] [50, 60, 80, 100] 1000000)))
