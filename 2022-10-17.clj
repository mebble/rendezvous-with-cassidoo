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

(defn pass-doors
  ([num-doors num-passes]
   (pass-doors num-doors
               num-passes
               1
               (->> num-doors
                    (range)
                    (map (constantly :closed)))))
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
  (assert (= 4 (pass-doors n 3)))
  (assert (= 5 (pass-doors n 4))))
