(defn- div-by?
  ;; #util
  "Is num divisible by div?"
  [div num]
  (zero? (mod num div)))

(defn- enumerate
  ;; #util
  ([xs] (enumerate 0 xs))
  ([start xs] (->> xs
                   (map-indexed vector)
                   (map (fn [[i x]] [(+ start i) x])))))

(defn- check-door [pass [i door-state]]
  (if (div-by? pass i)
    (door-state {:closed :open
                 :open   :closed})
    door-state))

(defn pass-doors-1
  ([num-doors num-passes]
   (pass-doors-1 num-doors
               num-passes
               1
               (->> (repeat :closed)
                    (take num-doors))))
  ([_ num-passes pass doors]
   (if (> pass num-passes)
     (->> doors
          (filter #{:open})
          (count))
     (->> doors
          (enumerate 1)
          (map (partial check-door pass))
          (recur _ num-passes (inc pass))))))

(let [n 7]
  (assert (= 4 (pass-doors-1 n 3)))
  (assert (= 5 (pass-doors-1 n 4))))

;; -------------------------------

(defn- check-door-2 [pass [i door-state]]
  (let [new-door-state (if (div-by? pass i)
                         (door-state {:closed :open
                                      :open   :closed})
                         door-state)]
    (vector i new-door-state)))

(defn pass-doors-2 [num-doors num-passes]
  (->> (repeat :closed)
       (take num-doors)
       (enumerate 1)
       (assoc { :pass 1 } :doors)
       (iterate (fn [app-state] {:pass  (->> app-state :pass inc)
                                 :doors (->> app-state :doors (map (partial check-door-2 (:pass app-state))))}))
       (take (inc num-passes))
       (last)
       (:doors)
       (filter (fn [[_ door-state]] (= :open door-state)))
       (count)))

(let [n 7]
  (assert (= 4 (pass-doors-2 n 3)))
  (assert (= 5 (pass-doors-2 n 4))))
