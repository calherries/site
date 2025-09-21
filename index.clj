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
         '[babashka.fs :as fs])

(defn capitalize-words
  "Capitalize every word in a string"
  [s]
  (->> (str/split (str s) #"\b")
       (map str/capitalize)
       str/join))

(def posts
  (let [site-map (edn/read (java.io.PushbackReader. (io/reader "site-map.edn")))]
    (->> site-map
         (map (fn [page]
                (assoc page :body (markdown/markdown (:source page)))))
         (sort-by :date #(compare %2 %1)))))

(def index-page
  [:html
   [:head
    [:title "Cal Herries"]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "description" :content "My personal site."}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "resources/public/css/app-utilities.css"}]]
   [:body {:class "p-8"}
    [:pre [:code]] ; Hack for postcss to not purge these elements code
    [:div {:class "mb-5"}
     [:b "About me"]
     [:ul
      [:li "I'm a software engineer specializing in building robust, scalable applications."]
      [:li "I help businesses solve complex technical challenges through custom software solutions."]
      [:li "My expertise spans full-stack development with professional experience in C, Clojure, JavaScript, and Python."]]]
    [:div {:class "mb-5"}
     [:b "Projects"]
     [:ul
      [:li [:a {:href "https://github.com/calherries/rich"} "Rich"] ", a rich text editor for ClojureScript"]
      [:li [:a {:href "https://github.com/calherries/graft"} "Graft"] ", a 30 LOC graph database implementation for Clojure"]]]
    [:div {:class "mb-5"}
     [:b "On programming"]
     [:ul
      (for [{:keys [title path]} (filter #(= (:section %) "Programming") posts)]
        [:li
         [:a {:href path} title]])]]
    [:div {:class "mb-5"}
     [:b "On life"]
     [:ul
      (for [{:keys [title path]} (filter #(= (:section %) "General") posts)]
        [:li
         [:a {:href path} title]])]]
    [:div {:class "mb-5"}
     [:b "Linked lists"]
     [:ul
      [:li [:a {:href "/papers.html"} "Papers I love"]]
      [:li [:a {:href "/books.html"} "Book shelf"]]]]
    [:div {:class "mb-5"}
     [:b "Elsewhere"]
     [:ul
      [:li [:a {:href "https://github.com/calherries"} "Github"]]
      [:li [:a {:href "https://twitter.com/calherries"} "Twitter"]]]]
    [:div {:class "mb-5 mt-8 pt-4"}
     [:b "Contact"]
     [:p "calherries[at]gmail.com"]
     [:p "Tallinn, Estonia"]]]])


(prn "Updated index")
(spit "index.html" (utils/convert-to index-page :html))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Posts

(defn post-page [body]
  [:html
   [:head
    [:title "Cal Herries"]
    [:meta {:charset "UTF-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-components.css"}]
    [:link {:rel "stylesheet" :href "../resources/public/css/app-utilities.css"}]]
   [:body {:class "p-8"}
    [:a {:style {:color "blue"
                 :font-weight "bold"
                 :text-decoration "none"} :href "/"} "←"]
    [:div
     body]]])

(doseq [{:keys [path body]} posts]
  (spit path (utils/convert-to (post-page body) :html)))

(prn "Updated posts")
(doall (map (comp prn str) (fs/glob "." "posts/*.md")))

;; Special pages

(def page-names ["papers" "books"])

(doseq [page page-names]
  (let [link-path (str page ".html")
        body      (markdown/markdown (str page ".md"))]
    (spit link-path (utils/convert-to (post-page body) :html))))

(prn "Updated pages")
(doall (map (comp prn) page-names))
