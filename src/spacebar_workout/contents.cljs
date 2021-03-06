(ns spacebar-workout.contents
  (:require [reagent.core :as r]
            [spacebar-workout.exercises :as exercises]))

(def content-data {"Wall extensions"           {:youtube "d6V2Exzb324"}
                   "Band dislocates"           {:youtube "WyW5jGGxoZk"}
                   "Cat-camels"                {:youtube "K9bK0BwKFjs"}
                   "Scapular shrugs"           {:youtube "akgQbxhrhOc"}
                   "Full body circles"         {:youtube "ABfOXAuJ0WE"}
                   "Front and side leg swings" {:youtube "4aoUZEZFJF8"}
                   "Wrist mobility"            {:youtube "mSZWSQSSEjE"}
                   "Plank"                     {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Side plank - right"        {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Side plank - left"         {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Reverse plank"             {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Hollow hold"               {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Arch hold"                 {:desc "60s hold"
                                                :youtube "44ScXWFaVBs"}
                   "Handstand"                 {:desc "5 minutes including rest"
                                                :youtube "N3K9SMNKL7Y"}
                   "Support"                   {:desc "3 minutes including rest"
                                                :youtube "8gmyhBScTLk"}
                   "Rest from skill work"      {:desc "90s"}
                   "Pullup"                    {:desc "5-8 pullups + 90s rest"
                                                :youtube "RsnbDcsZbpk"}
                   "Dipping"                   {:desc "5-8 dips + 90s rest"
                                                :youtube "ddEeZY_ii2c"}
                   "Squat"                     {:desc "5-8 squats + 90s rest"
                                                :youtube "487aR3A7HvM"}
                   "L-sit"                     {:desc "60s hold"
                                                :youtube "IUZJoSP66HI"}
                   "Rest from L-sit"           {:desc "90s"}
                   "Pushup"                    {:desc "5-8 pushups + 90s rest"
                                                :youtube "4dF1DOWzf20"}
                   "Row"                       {:desc "5-8 rows + 90s rest"
                                                :youtube "dvkIaarnf0g"}})

(defonce current-content (r/atom :contents))
(defonce player          (r/atom nil))

(defn instructions []
  (let [control (fn [icon desc]
                  [:p.row
                   [:span.col-sm-offset-3.col-sm-2.text-right
                    [:button.btn.btn-primary [:span.glyphicon {:class icon}]]]
                   [:span.col-sm-7 desc]])]
    [:div.jumbotron
     [:h1.text-center "How to use this website?"]
     [:p.text-center
      "Just hit spacebar to go through the routine. The spacebar will also start the timer. Use the arrow keys if you need to skip some exercise."]
     [:p.text-center
      "Alternatively, you can use the buttons at the timer."]
     [:h2.text-center "Controls:"]
     [:p.row
      [:span.col-sm-offset-3.col-sm-2.text-right [:button.btn.btn-primary "Spacebar"]]
      [:span.col-sm-7 "Pretty much do everything"]]
     [:p.row
      [:span.col-sm-offset-3.col-sm-2.text-right [:button.btn.btn-primary "Enter"]]
      [:span.col-sm-7 "Play video"]]
     [control "glyphicon-arrow-up"    "Back"]
     [control "glyphicon-arrow-down"  "Skip"]]))

(defn progressions []
  (let [url "https://www.reddit.com/r/bodyweightfitness/wiki/exercises"]
    [:div.jumbotron.text-center
     [:h1 "Progressions"]
     [:p [:a {:href (str url "/handstand")} "Handstand progression"]]
     [:p [:a {:href (str url "/support")}   "Support practice progression"]]
     [:p [:a {:href (str url "/pullup")}    "Pullup progression"]]
     [:p [:a {:href (str url "/dip")}       "Dipping progression"]]
     [:p [:a {:href (str url "/squat")}     "Squat progression"]]
     [:p [:a {:href (str url "/l-sit")}     "L-sit progression"]]
     [:p [:a {:href (str url "/pushup")}    "Pushup progression"]]
     [:p [:a {:href (str url "/row")}       "Row progression"]]]))

(defn resources []
  [:div.jumbotron.text-center
   [:h1 "Resources"]
   [:h2 "Websites"]
   [:p
    [:a {:href "https://www.reddit.com/r/bodyweightfitness/wiki/kb/recommended_routine"} "Recommended Routine"]
    " - Reddit's r/bodyweightfitness recommended routine"]
   [:h2 "Alternatives"]
   [:p [:a {:href "https://fitloop.co/"} "Fitloop"]
    " - Website with similar goals"]
   [:p [:a {:href "http://www.timer-tab.com/"} "Timer Tab"]
    " - Online timer"]])

(defn about []
  [:div.jumbotron.text-justify
   [:h1.text-center "About"]
   [:p "Hi!"]
   [:p "This website is for anyone looking for a timer to do the workout routine recommended by " [:a {:href "https://www.reddit.com/r/bodyweightfitness/"} "r/bodyweightfitness"] "."]
   [:p "Most of the other similar websites only provide a timer that you have to manually configure. With all the sweats produced during the workout, I can't just touch my keyboard to configure the timer. Therefore, I made this website that only requires user to just hit the spacebar."]
   [:p "The source code for this website is available in my " [:a {:href "https://github.com/burhanloey/spacebar-workout"} "GitHub repo"] ". Feel free to star or fork this project."]])

(defn privacy []
  [:div.lead
   [:h1 "Privacy Policy"]
   [:p "This website uses Google Analytics to track visitors."]
   [:p [:b "What does Google Analytics track?"]]
   [:ul
    [:li "Which website you came from"]
    [:li "Time spent on pages"]
    [:li "Visitor browser, operating system, and approximate city"]
    [:li "Etc."]]
   [:p "Google Analytics does NOT track any personal information such as names, email addresses, etc."]
   [:p [:b "What do the data used for?"]]
   [:p "The tracking information helps to improve the website and to keep track of the visitor count."]
   [:p [:b "How to opt out of tracking?"]]
   [:p "Use something like " [:a {:href "https://tools.google.com/dlpage/gaoptout"} "Google's opt-out browser plugin"] " or " [:a {:href "https://disconnect.me"} "Disconnect"] "."]])

(defn play-video []
  (when (and (= @current-content :contents)
             (get-in content-data [@exercises/current-exercise :youtube]))
    (.playVideo @player)))

(defn youtube-render [id]
  [:div.col-sm-offset-1.col-sm-10
   [:div.embed-responsive.embed-responsive-16by9
    [:iframe#player.embed-responsive-item
     {:src (str "https://www.youtube.com/embed/" id "?enablejsapi=1&origin=http://www.burhanloey.com")}]]])

(defn youtube-did-mount []
  (let [Player (.-Player js/YT)]
    (reset! player (Player. "player"))))

(defn youtube [id]
  (r/create-class {:reagent-render (fn [id]
                                     [youtube-render id])
                   :component-did-mount youtube-did-mount}))

(defn exercise-info [title]
  (let [id (get-in content-data [title :youtube])]
    [:div.text-center
     [:h1 title " " [exercises/rep]]
     [:h2 (get-in content-data [title :desc])]
     (when-not (nil? id)
       [youtube id])]))

(defn contents [title]
  (if (nil? title)
    [instructions]
    [exercise-info title]))

(defn set-content [page]
  (reset! current-content page))

(defn content-component []
  (case @current-content
    :progressions [progressions]
    :resources    [resources]
    :about        [about]
    :privacy      [privacy]
    :contents     [contents @exercises/current-exercise]))
