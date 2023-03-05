;; https://buttondown.email/cassidoo/archive/to-think-that-everybodys-like-you-is-silly/

(defn num-balanced [parens]
  (->> parens
       (reduce (fn [count paren]
            (if (= \( paren)
              (inc count)
              (dec count)))
          0)
       (abs)))
  

(assert (= 0 (num-balanced "()")))
(assert (= 1 (num-balanced "(()")))
(assert (= 6 (num-balanced "))()))))()")))
(assert (= 5 (num-balanced ")))))")))
