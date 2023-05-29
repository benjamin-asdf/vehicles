(ns ftlm.vehicles.main
  (:require
     [reagent.core :as r]
     [re-frame.core :as rf]
     [reagent.dom :as rdom]
     [quil.core :as q]
     [quil.middleware :as m]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color (mod (+ (:color state) 0.7) 255)
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      ; Draw the circle.
      (q/ellipse x y 100 100))))


(defn my-art [host-canvas]
  (q/sketch
   :host host-canvas
   :size [500 500]
   :setup setup
   ;; :update update-state
   :draw draw-state
   :features [:keep-on-top]
   :middleware [m/fun-mode]))

(defn view []
  (let [ref (r/atom nil)]
    (r/create-class
     {:component-did-mount
      (fn [] (my-art "my-art"))
      :reagent-render
      (fn []
        [:div
         {:width "500px"
          :height "500px"
          :id "my-art"
          :ref (fn [v] (reset! ref v))}])})))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (rdom/render
   [:div "hi"
    [view]]
   (.getElementById js/document "app")))

(defn init!
  "This initializes the entire frontend application using `Mount`."
  []
  (mount-root)
  ;; (rf/dispatch-sync [:initialize])
  )

(comment

  )
