;; https://buttondown.email/cassidoo/archive/look-for-the-ridiculous-in-everything-and-you/
;; https://twitter.com/cassidoo/status/1734102578483355969

(defn- freq [counts x]
  (cond-> counts
    true      (update-in [:nums x] (fn [xc] (if xc (inc xc) 1)))
    (even? x) (update :evens inc)
    (odd? x)  (update :odds inc)))

(defn- uniq-max-key [m]
  (->> m
       (reduce (fn [current [k v]]
                 (let [[ck cv c] current]
                   (cond
                     (nil? current) [k v 1]
                     (< cv v)       [k v 1]
                     (= cv v)       [k v (inc c)]
                     :else          [ck cv c])))
               nil)
       ((fn [[k _ c]] (if (= c 1) k nil)))))

(defn- to-str [x]
  (case x
    nil "No majority"
    :evens "Majority evens"
    :odds  "Majority odds"
    (str x)))

(defn majority [nums]
  (let [f (reduce
            freq
            {:evens 0 :odds 0 :nums {}}
            nums)]
    (to-str (or
             (uniq-max-key (:nums f))
             (uniq-max-key (select-keys f [:evens :odds]))))))

(assert (= "1" (majority [3 1 4 1])))
(assert (= "Majority odds" (majority [33 44 55 66 77])))
(assert (= "No majority" (majority [1 2 3 4])))
