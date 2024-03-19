;; https://buttondown.email/cassidoo/archive/you-can-put-things-off-until-tomorrow-but/
;; https://twitter.com/cassidoo/status/1769626762525175899

(defn hills [xs]
   (->> xs
        (dedupe)
        (partition 3 1)
        (filter (fn [[a b c]] (and (> b a)
                                   (> b c))))
        (count)))

(defn valleys [xs]
   (->> xs
        (dedupe)
        (partition 3 1)
        (filter (fn [[a b c]] (and (< b a)
                                   (< b c))))
        (count)))

(let [arr [1 2 1]]
  (assert (= 1 (hills arr)))
  (assert (= 0 (valleys arr))))

(let [arr [1 0 1]]
  (assert (= 0 (hills arr)))
  (assert (= 1 (valleys arr))))

(let [arr [[7,6,5,5,4,1]]]
  (assert (= 0 (hills arr)))
  (assert (= 0 (valleys arr))))

(let [arr [3,4,1,1,6,5]]
  (assert (= 2 (hills arr)))
  (assert (= 1 (valleys arr))))
