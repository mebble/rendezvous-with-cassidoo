(defn round
  "Rounds 'x' to 'places' decimal places
   Sources:
   - https://stackoverflow.com/a/4826827/5811761
   - https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html#setScale-int-java.math.RoundingMode-
   - https://docs.oracle.com/javase/8/docs/api/java/math/RoundingMode.html"
  [places, x]
  (->> x
       bigdec
       (#(.setScale % places java.math.RoundingMode/HALF_UP))
       .doubleValue))

(def grade-map {"A"  4
                "A-" 3.7
                "B+" 3.3
                "B"  3
                "B-" 2.7
                "C+" 2.3
                "C"  2
                "C-" 1.7
                "D+" 1.3
                "D"  1
                "D-" 0.7
                "F"  0})

(defn calculateGPA [grades]
    (->> grades
         (map grade-map)
         (apply +)
         (#(/ % (count grades)))
         (round 1)))

(assert (= 4.0 (calculateGPA ["A"])))
(assert (= 0.0 (calculateGPA ["F" "F" "F"])))
(assert (= 3.3 (calculateGPA ["A" "A-" "B+" "B" "B-"])))
(assert (= 3.3 (calculateGPA ["A" "B+" "C-" "A"])))
