;; https://buttondown.email/cassidoo/archive/mellow-doesnt-always-make-for-a-good-story-but-it/
;; https://twitter.com/cassidoo/status/1723954160058462637

(def tasks [{:name "Task 1" :duration 4}
            {:name "Task 2" :duration 2}
            {:name "Task 3" :duration 7}
            {:name "Task 4" :duration 5}
            {:name "Task 5" :duration 1}
            {:name "Task 6" :duration 3}])

(defn- -do-tasks [tasks time-to-work]
  (let [task (first tasks)
        dur (:duration task)
        remaining (rest tasks)]
    (cond
      (empty? tasks)       []
      (> dur time-to-work) (-do-tasks remaining time-to-work)
      :else                (cons task (-do-tasks remaining (- time-to-work dur))))))

(defn- do-from [tasks time-to-work]
  (cond
    (empty? tasks)                             []
    (> (:duration (first tasks)) time-to-work) []
    :else                                      (-do-tasks tasks time-to-work)))

(defn- count-or-duration [tasks1 tasks2]
  (let [f (compare (count tasks1)
                   (count tasks2))]
    (if-not (zero? f)
      (* -1 f)
      (* -1 (compare (reduce + (map :duration tasks1))
                     (reduce + (map :duration tasks2)))))))

(defn do-tasks [tasks time-to-work]
  (->> tasks
       (iterate (fn [ts] (rest ts)))
       (take (count tasks))
       (map (fn [ts] (do-from ts time-to-work)))
       (sort count-or-duration)
       (first)
       (map :name)))

(assert (= ["Task 2", "Task 5", "Task 6"] (do-tasks tasks 6)))
(assert (= ["Task 5", "Task 6"] (do-tasks tasks 4)))
(assert (= ["Task 2"] (do-tasks tasks 2)))
