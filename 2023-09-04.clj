;; https://buttondown.email/cassidoo/archive/your-self-worth-is-determined-by-you-beyonce/
;; https://twitter.com/cassidoo/status/1698590979874259084

(defn min-subs [xs k]
  (->> xs
       (partition k 1)
       (apply min-key (fn [part] (apply + part)))))

(assert (= [4 8 9] (min-subs [1 3 20 4 8 9 11] 3)))
(assert (= [4 4] (min-subs [4 4 4 4 8] 2)))
