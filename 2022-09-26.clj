(defn ordinal [x]
  (str x
       (let [last-two (->> x
                           Math/abs
                           (format "%02d"))
             last-one (last last-two)]
         (case last-two
           "11" "th"
           "12" "th"
           "13" "th"
           (case last-one
             \1 "st"
             \2 "nd"
             \3 "rd"
             "th")))))

(assert (= "0th" (ordinal 0)))
(assert (= "1st" (ordinal 1)))
(assert (= "2nd" (ordinal 2)))
(assert (= "3rd" (ordinal 3)))
(assert (= "4th" (ordinal 4)))
(assert (= "9th" (ordinal 9)))
(assert (= "10th" (ordinal 10)))
(assert (= "11th" (ordinal 11)))
(assert (= "12th" (ordinal 12)))
(assert (= "21st" (ordinal 21)))
(assert (= "22nd" (ordinal 22)))
(assert (= "23rd" (ordinal 23)))
(assert (= "24th" (ordinal 24)))
(assert (= "31st" (ordinal 31)))
