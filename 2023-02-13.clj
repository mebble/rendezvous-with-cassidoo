;; https://buttondown.email/cassidoo/archive/if-you-dont-live-your-life-then-who-will-rihanna/

(defn under-hundred? [num]
  (let [[cx cy] (reverse (str num))]
    (not (= [\0 \0] [cx cy]))))

(defn print-nums []
  (let [nums (->> (iterate inc 0)
                  (take-while under-hundred?))]
    (doseq [n nums]
      (println n))
    (println (inc (last nums)))))

(print-nums)
