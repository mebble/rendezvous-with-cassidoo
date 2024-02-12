;; https://buttondown.email/cassidoo/archive/youve-got-to-love-whats-yours-alicia-keys/
;; https://twitter.com/cassidoo/status/1756941571352613116

(defn from-to [a b]
  (let [range-vec (vec (range a b))
        current (atom 0)]
    (fn []
      (let [i @current]
        (swap! current inc)
        (get range-vec i)))))

(let [range* (from-to 0 3)]
  (assert (= 0 (range*)))
  (assert (= 1 (range*)))
  (assert (= 2 (range*)))
  (assert (= nil (range*)))
  (assert (= nil (range*))))
