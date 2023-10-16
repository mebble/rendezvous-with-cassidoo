;; https://buttondown.email/cassidoo/archive/no-matter-what-people-tell-you-words-and-ideas/
;; https://twitter.com/cassidoo/status/1713761564459704560

(defn is-isomorphic [s t]
  (if (not= (count s) (count t))
    false
    (loop [m {}
           pairs (map vector s t)]
      (let [[si ti] (first pairs)]
        (cond
          (empty? pairs)             true
          (and (contains? m si)
               (not= (get m si) ti)) false
          :else                      (recur (assoc m si ti)
                                            (rest pairs)))))))

(assert (is-isomorphic "abb" "cdd"))
(assert (is-isomorphic "cass" "1233"))
(assert (is-isomorphic "1234567" "cassidy"))
(assert (not (is-isomorphic "cassidy" "1234567")))
(assert (not (is-isomorphic "cass" "1")))
(assert (not (is-isomorphic "c" "123")))
