;; https://buttondown.email/cassidoo/archive/forgiveness-is-giving-up-the-hope-that-the-past/

(defn- blank? [c]
  (= \space c))

(defn explode-string [s]
  (->> s
       (sort)
       (filter (complement blank?))
       (partition-by identity)
       (map (partial apply str))))

(assert (= (explode-string "Ahh, abracadabra!")
           ["!" "," "A" "aaaaa" "bb" "c" "d" "hh" "rr"]))
(assert (= (explode-string "\\o/\\o/")
           ["//","\\\\","oo"]))

