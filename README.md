# Kadvice 🐻
*An Android application that gives you advice. Kotlin everywhere.*

This app is merely a sample and has no real use out there. It fetches a random advice from [AdviceSlip API](https://api.adviceslip.com/) and presents it alongside a "matching" beautiful image from [Unsplash API](https://unsplash.com/developers) (the query is the advice itself and all kinds of non-related images might appear 🙈).

My main goal with this project is to explore some emerging technologies, keep tuned for future commits!

## The Stack 🧠

Kadvice is a multi-module Android application that strives for a *CLEAN* architecture. It uses *MVVM* on the presentation layer, the *UseCase* pattern for domain access and the *Repository* pattern for data access.

**Some cool libraries it uses** 😎  :
 - **Kotlin Coroutines & Flow** *for concurrency and reactivity*
 - **Hilt** *for dependency injection*
 - **Ktor (client)** *for HTTP calls*
 - **Motion Layout** *for crazy coordinated animations*
 - **View Binding** *for view manipulation*
 - **Coil** *for image loading*

## Setup 🔨

 1. Download the repo
 2. Register for a free [Unsplash API Key](https://unsplash.com/developers)
 3. Go to `UNSPLASH_API_KEY.kt` and replace the TODO with your own key
 4. You're done! Run the project with Android Studio and get yourself some random advice 🤗

**Some examples of the app:**

<p float="left">
  <img src="/kadvice-1.png" width="180" />
  <img src="/kadvice-1_1.png" width="180" />
  <img src="/kadvice-2.png" width="180" /> 
  <img src="/kadvice-3.png" width="180" />
</p>
