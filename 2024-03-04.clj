;; https://buttondown.email/cassidoo/archive/time-can-turn-a-scab-into-a-beauty-mark-nia/
;; https://twitter.com/cassidoo/status/1764557684617924768

(require '[clojure.core.async :refer [chan go >! <! <!! timeout]])

(defn timer [work-duration rest-duration rounds]
  (let [out (chan)]
    (go
      (dotimes [_ rounds]
        (loop [secs (dec work-duration)
               state :work]
          (<! (timeout 1000))
          (>! out [state secs])
          (cond
            (> secs 0)      (recur (dec secs) state)
            (= state :work) (recur (dec rest-duration) :rest)))))
    out))

(let [timer-chan (timer 3 2 2)]
  (assert (= [:work 2] (<!! timer-chan)))
  (assert (= [:work 1] (<!! timer-chan)))
  (assert (= [:work 0] (<!! timer-chan)))
  (assert (= [:rest 1] (<!! timer-chan)))
  (assert (= [:rest 0] (<!! timer-chan)))
  (assert (= [:work 2] (<!! timer-chan)))
  (assert (= [:work 1] (<!! timer-chan)))
  (assert (= [:work 0] (<!! timer-chan)))
  (assert (= [:rest 1] (<!! timer-chan)))
  (assert (= [:rest 0] (<!! timer-chan)))
  (println "Tests complete"))

(defn run-timer [timer]
  (go
    (while true
      (let [[state secs] (<! timer)]
        (println
         (case state
           :work (str "working... " (str secs) " seconds left.")
           :rest (str "resting... " (str secs) " seconds left.")))))))

(run-timer (timer 3 2 2))
