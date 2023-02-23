;; https://buttondown.email/cassidoo/archive/normal-is-nothing-more-than-a-cycle-on-a-washing/

(defn combine-strings [strings n]
  (->> strings
       (interpose " ")
       (reduce (fn [combo x]
                 (let [new-str     (str (last combo) x)
                       new-str-len (count new-str)]
                   (cond
                     (<= new-str-len n) (conj (pop combo) new-str)
                     (= x " ")          combo
                     :else              (conj combo x))))
               [""])
       (map (partial clojure.string/trim))
       (vec)))

(assert (= ["a b c" "d e f" "g"]
           (combine-strings ["a" "b" "c" "d" "e" "f" "g"] 5)))
(assert (= ["a b c d e f" "g"]
           (combine-strings ["a" "b" "c" "d" "e" "f" "g"] 12)))
(assert (= ["alpha beta gamma" "delta epsilon"]
           (combine-strings ["alpha" "beta" "gamma" "delta" "epsilon"] 20)))
