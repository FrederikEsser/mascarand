(ns mascarand-test
  (:require [clojure.test :refer :all]
            [mascarand.logic :as mascarand]))

(deftest check-gold-test
  (is (not (mascarand/check-gold (concat (repeat 1 {:gold true}) (repeat 6 {:gold false})))))
  (is (mascarand/check-gold (concat (repeat 2 {:gold true}) (repeat 5 {:gold false}))))
  (is (not (mascarand/check-gold (concat (repeat 2 {:gold true}) (repeat 6 {:gold false})))))
  (is (mascarand/check-gold (concat (repeat 3 {:gold true}) (repeat 5 {:gold false}))))
  (is (not (mascarand/check-gold (concat (repeat 2 {:gold true}) (repeat 8 {:gold false})))))
  (is (mascarand/check-gold (concat (repeat 3 {:gold true}) (repeat 7 {:gold false})))))

(deftest check-courtesan-test
  (is (mascarand/check-courtesan (repeat 6 {:gender :male})))
  (is (not (mascarand/check-courtesan (concat [{:name :courtesan :gender :female}] (repeat 5 {:gender :male})))))
  (is (mascarand/check-courtesan (concat [{:name :courtesan :gender :female} {:gender :female}] (repeat 4 {:gender :male}))))
  (is (not (mascarand/check-courtesan (concat [{:name :courtesan :gender :female} {:gender :female}] (repeat 5 {:gender :male})))))
  (is (mascarand/check-courtesan (concat [{:name :courtesan :gender :female}] (repeat 2 {:gender :female}) (repeat 4 {:gender :male})))))

(deftest count-test
  (is (every? true? (map (fn [n] (= n (count (mascarand/randomize n)))) (range 6 14)))))
