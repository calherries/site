[:html
  [:head
   [:meta {:charset "UTF-8"}]
   [:title "Callum Herries"]
   [:meta {:name "description" :content "My personal site."}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   [:link {:rel "stylesheet" :href "resources/public/css/app-components.css"}]
   [:link {:rel "stylesheet" :href "resources/public/css/app-utilities.css"}]
   ]
 [:body {:class "p-10 leading-7 text-gray-700"}
  [:h1 {:class "text-blue-500"} "Hey."]
  [:div {:class "my-3"}
   (markdown "intro.md")]]]
