(ns index
  (:require [babashka.pods :as pods]))

(pods/load-pod "bootleg")
(require '[pod.retrogradeorbit.bootleg.utils :as utils]
         '[pod.retrogradeorbit.bootleg.markdown :as markdown]
         '[clojure.string :as string]
         '[babashka.fs :as fs])

(defn capitalize-words
  "Capitalize every word in a string"
  [s]
  (->> (string/split (str s) #"\b")
       (map string/capitalize)
       string/join))

(def posts (for [post-path (fs/glob "." "posts/*.md")]
             (let [title (-> post-path
                             fs/file-name
                             (->> (re-find #"^\d{4}-\d{2}-\d{2}-(.*).md$"))
                             second
                             (string/replace #"-" " ")
                             capitalize-words)
                   link-path (string/replace post-path #".md$" ".html")]
               {:title title
                :link-path link-path
                :post-path post-path
                :body (markdown/markdown (str post-path))})))

(def index-page
  [:html
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Cal Herries"]
    [:meta {:name "description" :content "My personal site."}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-utilities.css"}]

    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Roboto+Slab:700"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Crimson+Pro"}]
    [:link {:rel "stylesheet" :href "https://use.typekit.net/kxb8fhj.css"}]]
   [:body {:class "pt-24 p-8"}
    [:h1 {:style {:font-size "6em"
                  :margin "0"}} "Cal Herries"]
    [:pre [:code]] ; Hack for postcss to not purge these elements code
    [:div {:class "my-3"}
     (markdown/markdown "intro.md")]
    [:ul
     (for [{:keys [title link-path]} posts]
       [:li
        [:a {:href (str link-path)} title]])]]])

(spit "index.html" (utils/convert-to index-page :html))

;; Posts

(prn "Updated index")

(defn post-page [body]
  [:html
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Cal Herries"]
    [:meta {:name "description" :content "My personal site."}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-utilities.css"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/github.css"}]
    [:link {:rel "stylesheet" :href "https://use.typekit.net/kxb8fhj.css"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Crimson+Pro"}]
    [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.6.0/highlight.min.js"}]
    [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.6.0/languages/clojure.min.js"}]
    [:script "hljs.highlightAll()"]]
   [:body {:class "p-8"}
    [:div {:class "flex gap-5 mb-4"}
     [:h2 {:class "font-bold"}
      [:a {:href "/"} "Home"]]
     #_[:h2 {:class "font-bold"}
      [:a {:href "/posts.html"} "Posts"]]]
    [:div {:class "my-3"}
     body]]])

(for [{:keys [link-path post-path body]} posts]
  (spit link-path (utils/convert-to (post-page body) :html)))

(prn "Updated posts")
(doall (map (comp prn str) (fs/glob "." "posts/*.md")))