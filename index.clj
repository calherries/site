(ns index
  (:require [babashka.pods :as pods]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [java.io]
           [java.time]))


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

(def site-map
  (edn/read (java.io.PushbackReader. (io/reader "site-map.edn"))))

(def posts
  (->> site-map
       (map (fn [page]
              (assoc page :body (markdown/markdown (:source page)))))
       (sort-by :date #(compare %2 %1))))

(def index-page
  [:html
   [:head
    [:meta {:charset "UTF-8"}]
    [:title "Cal Herries"]
    [:meta {:name "description" :content "My personal site."}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-utilities.css"}]
    [:link {:rel "icon" :type "image/x-icon" :href "resources/images/favicon.ico"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Roboto+Slab:700"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Crimson+Pro"}]
    [:link {:rel "stylesheet" :href "https://use.typekit.net/kxb8fhj.css"}]]
   [:body {:class "p-8"}
    [:h1 {:style {:margin "0"}} "Cal Herries"]
    [:pre [:code]] ; Hack for postcss to not purge these elements code
    [:div {:class "mt-5"}
     [:b "Some things you should know about me:"]
     [:ul
      [:li "I know very little about anything (but I'm working on it)."]
      [:li "I spend most of my time writing bits of text to solve problems."]
      [:li "Most of these problems are caused by other bits of text, usually written by me."]]]
    [:div {:class "mt-5"}
     [:b "Some things I've worked on recently:"]
     [:ul
      [:li [:a {:href "https://github.com/calherries/rich"} "Rich"] ", a rich text editor for ClojureScript"]
      [:li [:a {:href "https://github.com/calherries/graft"} "Graft"] ", a <100 LOC graph data structure for Clojure"]
      [:li [:a {:href "https://github.com/calherries/fuelsurcharges.com"} "fuelsurcharges.com"] ", a full-stack web app in Clojure to track fuel surcharges on shipments"]
      [:li [:a {:href "https://github.com/calherries/clj-data-cookbook"} "clj-data-cookbook"] ", recipes for using clojure.core for data exploration"]]]
    [:div {:class "mt-5"}
     [:b "Some things you can click on:"]
     [:ul
      [:li [:a {:href "https://github.com/calherries"} "Github"] ", a place where I solve some problems and create others"]
      [:li [:a {:href "https://twitter.com/calherries"} "Twitter"] ", a place for random bursts of coherence"]]]
    [:div {:class "mt-5"}
     [:b "Some thoughts on programming:"]
     [:ul
      (for [{:keys [title path]} (filter #(= (:section %) "Programming") posts)]
        [:li
         [:a {:href path} title]])]]
    [:div {:class "mt-5"}
     [:b "Other ramblings:"]
     [:ul
      (for [{:keys [title path]} (filter #(= (:section %) "General") posts)]
        [:li
         [:a {:href path} title]])]]
    [:div {:class "my-5"}
     [:div [:a {:href "/papers.html"} "Papers I love"]]
     [:div [:a {:href "/books.html"} "Book shelf"]]]]])

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
    [:link {:rel "icon" :type "image/x-icon" :href "../resources/images/favicon.ico"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-utilities.css"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/github.css"}]
    [:link {:rel "stylesheet" :href "https://use.typekit.net/kxb8fhj.css"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap"}]
    [:link {:rel "stylesheet" :href "https://fonts.googleapis.com/css?family=Crimson+Pro"}]
    [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.6.0/highlight.min.js"}]
    [:script {:src "https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.6.0/languages/clojure.min.js"}]
    [:script "hljs.highlightAll()"]]
   [:body {:class "p-8"}
    [:div {:class "flex gap-5 mb-4"}
     [:h2 {:class "font-bold"}
      [:a {:style {:color "#6679CC" :border-color "#6679CC"} :href "/"} "Home"]]]
    [:div {:class "my-3"}
     body]]])

(doseq [{:keys [path body]} posts]
  (spit path (utils/convert-to (post-page body) :html)))

(prn "Updated posts")
(doall (map (comp prn str) (fs/glob "." "posts/*.md")))

;; Special pages

(def page-names ["papers" "books"])

(let [pages (for [page page-names]
              {:link-path (str page ".html")
               :body      (markdown/markdown (str page ".md"))})]
  (doseq [{:keys [link-path body]} pages]
    (spit link-path (utils/convert-to (post-page body) :html))))

(prn "Updated pages")
(doall (map (comp prn) page-names))
