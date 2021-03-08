[:html
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "Callum Herries"]
   [:meta {:name "description" :content "My personal site."}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:link {:rel "stylesheet" :href "resources/public/css/main.css"}]
   [:script {:type "text/javascript" :src "https://livejs.com/live.js"}]
   ;; [:link {:rel "stylesheet" :href "http://ozviz.io/fonts/lmroman12-regular.woff"}]
   ;; [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Open+Sans"}]
   ]
 [:body
  [:h1.font-bold "Hey."]
  [:div (markdown "intro.md")]]]
