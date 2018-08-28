(ns ^:figwheel-no-load mascarand.dev
  (:require
    [mascarand.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
