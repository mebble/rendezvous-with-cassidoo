;; https://buttondown.email/cassidoo/archive/everybody-gets-so-much-information-all-day-long/

(defn print-tree [n]
  "")

(def expected-1
  "/\\")
(def expected-2
  " /\\
/\\ \\")

(assert (= expected-1 (print-tree 2)))
(assert (= expected-2 (print-tree 3)))
