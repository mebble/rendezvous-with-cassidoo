;; https://buttondown.email/cassidoo/archive/dont-let-doubt-stop-you-from-getting-where-you/
;; https://twitter.com/cassidoo/status/1759461497439146057

(require '[clojure.set :as s])

(defn- pairings [xs]
  ;; #util
  (let [x (first xs)
        ys (rest xs)]
    (if (seq xs)
      (concat
       (map (fn [y] [x y]) ys)
       (pairings ys))
      [])))

(defn- to-data [word]
  {:length (count word)
   :charset (set word)})

(defn- exclusive-letters [[data1 data2]]
  (let [charset1 (:charset data1)
        charset2 (:charset data2)]
    (not (seq (s/intersection charset1 charset2)))))

(defn- length-product [[data1 data2]]
  (let [l1 (:length data1)
        l2 (:length data2)]
    (* l1 l2)))

(defn- seq-or [x xs]
  ;; #util
  (if (seq xs)
    xs
    x))

(defn word-length-product [xs]
  (->> xs
       (map to-data)
       (pairings)
       (filter exclusive-letters)
       (map length-product)
       (seq-or [0])
       (apply max)))

(assert (= 16 (word-length-product ["fish" "fear" "boo" "egg" "cake" "abcdef"])))
(assert (= 0 (word-length-product ["a" "aa" "aaa" "aaaa"])))
