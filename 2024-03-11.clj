;; https://buttondown.email/cassidoo/archive/if-you-dont-go-towards-the-thing-you-fear-you/
;; https://twitter.com/cassidoo/status/1767084127541449018

(defn- seq-or [x xs]
  (if (seq xs)
    xs
    x))

(defn max-gap [xs]
  (let [sorted (sort xs)]
    (->> (rest sorted)
         (map vector sorted)
         (map (fn [[x y]] (- y x)))
         (seq-or [0])
         (apply max))))

(assert (= 3 (max-gap [3 6 9 1 2])))
(assert (= 0 (max-gap [1])))
(assert (= 0 (max-gap [])))
