(ns mascarand.core
  (:require
    [reagent.core :as r]
    [mascarand.logic]
    [clojure.string :as string]))

;; -------------------------
;; Views

(defonce state (r/atom {:players 6
                        :expansion true}))

(defn home-page []
  [:div [:h2 "Mascarand"]
   "Number of players: " [:input {:type      :number
                                  :min       2
                                  :max       13
                                  :on-change (fn [event] (swap! state assoc :players (js/parseInt (-> event .-target .-value))))
                                  :value     (:players @state)}]
   [:div "Include expansion" [:input {:type     :checkbox
                                      :checked  (:expansion @state)
                                      :on-click (fn [event] (swap! state assoc :expansion (-> event .-target .-checked)))}]]
   [:div [:button {:on-click (fn [] (swap! state assoc :cards (mascarand.logic/randomize (:players @state)
                                                                                         (when-not (:expansion @state) :exclude-expansion ))))}
          "Randomize"]]
   [:div {} (->> (:cards @state)
                 (map (fn [cardname]
                        [:div (string/capitalize (name cardname))])))]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
