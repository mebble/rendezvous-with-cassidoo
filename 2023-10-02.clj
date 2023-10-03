;; https://buttondown.email/cassidoo/archive/perseverance-is-failing-19-times-and-succeeding/
;; https://twitter.com/cassidoo/status/1708697320827547762

(defn char->int [c]
  (read-string (str c)))

(defn digit-seq [n]
  (->> n
       (str)
       (map char->int)))

(defn var->str [v]
  (last (str v)))

(defn try-op [op digits]
  (try
    (apply op digits)
    (catch Exception _ nil)))

(def operators ["+" "*" "-" "/"])

(defn add-operators [source target]
  (let [digits (digit-seq source)]
    (->> operators
         (map (comp resolve read-string))
         (map (fn [op] (vector (try-op op digits) op)))
         (filter (fn [[res]] (= res target)))
         (map (comp var->str second))
         (map #(interpose % digits))
         (map (partial apply str)))))

(assert (= ["1+2+3" "1*2*3"] (add-operators 123 6)))
(assert (= [] (add-operators 3456237490 9191)))
