(defn fib-like-1
  "Using recursion, not tail-call optimised"
  [x1 x2 n]
  (if (>= 0 n)
    []
    (into [x1] (fib-like-1 x2 (+ x1 x2) (dec n)))))

(let [n 5]
  (assert (= [10, 20, 30, 50, 80] (fib-like-1 10 20 n)))
  (assert (= [3, 7, 10, 17, 27] (fib-like-1 3 7 n))))

(defn fib-like-2
  "Using recursion, tail-call optimised"
  ([x1 x2 n] (fib-like-2 x1 x2 n []))
  ([x1 x2 n xs] (if (>= 0 n)
                  xs
                  (recur x2 (+ x1 x2) (dec n) (conj xs x1)))))

(let [n 5]
  (assert (= [10, 20, 30, 50, 80] (fib-like-2 10 20 n)))
  (assert (= [3, 7, 10, 17, 27] (fib-like-2 3 7 n))))
