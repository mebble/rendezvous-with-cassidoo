;; https://buttondown.email/cassidoo/archive/beware-of-missing-chances-otherwise-it-may-be/
;; https://twitter.com/cassidoo/status/1716332701454614576

(defn- safe-drop [s drop-fn]
  (if (<= (count s) 1)
    ""
    (drop-fn s)))
(defn- drop-ends [s] (safe-drop s (fn [_s] (subs _s 1 (dec (count _s))))))
(defn- drop-left [s] (safe-drop s (fn [_s] (subs _s 1))))
(defn- drop-right [s] (safe-drop s (fn [_s] (subs _s 0 (dec (count _s))))))

(defn k-pal [s k]
  (loop [s s]
    (cond
      (empty? s)             true
      (= (first s) (last s)) (recur (drop-ends s))
      (zero? k)              false
      :else                  (or (k-pal (drop-left s) (dec k))
                                 (k-pal (drop-right s) (dec k))))))

(assert (k-pal "" 0))
(assert (k-pal "abcweca" 2))
(assert (k-pal "acwecba" 2))
(assert (not (k-pal "acxcb" 1)))
