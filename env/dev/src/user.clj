(ns user
  (:require
   [shadow.cljs.devtools.api :as shadow]
   [shadow.cljs.devtools.server]))

(defn start-shadow [target]
  (shadow.cljs.devtools.server/start!)
  (shadow/watch target))

(comment
  (start-shadow :app)
  (shadow.cljs.devtools.server/stop!))

;; see components/designer-frontend/env/dev/src/nc_platform/shadow.clj
