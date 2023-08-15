(defn vowel? [c]
  (contains? #{\a \e \i \o \u \y} c))

(defn faulty-keeb [s]
  (let [append? (atom true)
        num-vowels (atom 0)]
    (->> (loop [result ""
                s s]
           (if (empty? s)
             result
             (let [c (first s)
                   xs (rest s)]
               (cond
                 (vowel? c) (do (swap! append? not)
                                (swap! num-vowels inc)
                                (recur result xs))
                 @append?   (recur (str result c) xs)
                 :else      (recur (str c result) xs)))))
         (#(if (odd? @num-vowels)
             (reverse %)
             %))
         (apply str))))

(assert (= "rtsng" (faulty-keeb "string")))
(assert (= "w hllrld!" (faulty-keeb "hello world!")))
(assert (= "qpbd" (faulty-keeb "bdepqe")))
