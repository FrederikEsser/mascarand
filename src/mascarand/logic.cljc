(ns mascarand.logic)

(def judge {:name :judge :players 2 :gold false :gender :male})
(def peasant {:name :peasant, :players 8, :gold true, :gender :male})

(def base-cards [{:name :bishop, :players 2, :gold false, :gender :male}
                 {:name :cheat, :players 2, :gold false, :gender :male}
                 {:name :fool, :players 2, :gold true, :gender :male}
                 {:name :king, :players 2, :gold true, :gender :male}
                 {:name :queen, :players 2, :gold true, :gender :female}
                 {:name :spy, :players 2, :gold false, :gender :female}
                 {:name :thief, :players 2, :gold false, :gender :male}
                 {:name :widow, :players 2, :gold true, :gender :female}
                 {:name :witch, :players 2, :gold false, :gender :female}
                 {:name :inquisitor, :players 8, :gold false, :gender :male}
                 peasant])

(def expansion-cards [{:name :alchemist, :players 2, :gold false, :gender :male}
                      {:name :beggar, :players 2, :gold false, :gender :female}
                      {:name :gambler, :players 2, :gold true, :gender :female}
                      {:name :necromancer, :players 2, :gold true, :gender :male}
                      {:name :patron, :players 2, :gold true, :gender :male}
                      {:name :princess, :players 6, :gold true, :gender :female}
                      {:name :puppet-master, :players 6, :gold false, :gender :female}
                      {:name :actress, :players 8, :gold true, :gender :female}
                      {:name :courtesan, :players 8, :gold false, :gender :female}
                      {:name :damned, :players 8, :gold false, :gender :male}
                      {:name :sage, :players 8, :gold true, :gender :male}
                      {:name :usurper, :players 8, :gold true, :gender :male}])

(defn check-gold [cards]
  (->> cards
       (filter :gold)
       count
       (<= (/ (dec (count cards)) 3))))

(defn check-courtesan [cards]
  (or
    (not ((set (map :name cards)) :courtesan))
    (->> cards
         (filter (comp #{:female} :gender))
         count
         (<= (/ (count cards) 3)))))

(defn randomize [num-players & args]
  (let [num-cards (max num-players 6)
        valid-cards (->> base-cards
                         (concat (when-not (-> args set :exclude-expansion) expansion-cards))
                         (filter (comp (partial >= num-players) :players))
                         shuffle
                         (concat [judge]))
        peasant-idx (->> valid-cards
                         (keep-indexed (fn [idx {:keys [:name]}]
                                         (when (= name :peasant) idx)))
                         first)
        cards (cond
                (and peasant-idx (< peasant-idx (dec num-cards))) (->> valid-cards
                                                                       (take (dec num-cards))
                                                                       (concat [peasant]))
                (= peasant-idx (dec num-cards)) (concat (take (dec num-cards) valid-cards)
                                                        (take 1 (drop num-cards valid-cards)))
                :else (take num-cards valid-cards))]
    (if (and (check-gold cards)
             (check-courtesan cards))
      (->> cards (map :name) sort)
      (apply randomize num-players args))))

