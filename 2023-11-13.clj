;; https://buttondown.email/cassidoo/archive/mellow-doesnt-always-make-for-a-good-story-but-it/
;; https://twitter.com/cassidoo/status/1723954160058462637

(def tasks [{:name "Task 1" :duration 4}
            {:name "Task 2" :duration 2}
            {:name "Task 3" :duration 7}
            {:name "Task 4" :duration 5}
            {:name "Task 5" :duration 1}
            {:name "Task 6" :duration 3}])

(def duration (comp :duration second))

(defn do-tasks [tasks time-to-work]
  (let [time-available (atom time-to-work)]
    (->> tasks
         (map-indexed vector)
         (sort-by duration)
         (take-while (fn [x] (let [t @time-available]
                               (when (<= (duration x) t)
                                 (swap! time-available dec)))))
         (sort-by first)
         (map (comp :name second)))))

(assert (= ["Task 2", "Task 5", "Task 6"] (do-tasks tasks 6)))
