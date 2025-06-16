
![Screenshot_20250616_150905_ExamTsira](https://github.com/user-attachments/assets/1b807b41-577d-403b-a7fe-97f4c3532ef7)
![Screenshot_20250616_150859_ExamTsira](https://github.com/user-attachments/assets/833aecdf-74be-404d-9a57-19a2986df0ec)




**Description**

This is a simple Android application written in Kotlin that displays a list of the most popular works by famous artists. Upon launching the app, users are presented with a list of artists, including the following details:

* Photo
* First name
* Last name
* Year of birth
* Year of death

Users can tap on any artist to navigate to a second screen displaying a list of their 10 most famous artworks along with the years they were created.

Additionally, users can remove artists from the list by swiping left or right — the artist will be automatically deleted from the list.

---

**Features**

* Fetches the list of artists using **Retrofit**
* Displays each artist with:

  * Full name
  * Lifespan period
  * Photo (loaded with **Picasso**)
* Clicking an artist opens a new screen showing their top 10 famous artworks and the year of creation
* Swipe to delete functionality for removing artists from the list

---

**Technologies Used**

* **Kotlin**
* **Android Jetpack Components**:

  * RecyclerView
  * ConstraintLayout
  * Toolbar
* **Retrofit + Gson**
* **OkHttp3**
* **Picasso**

---

**Data Source**

The artist data is retrieved from a JSON file hosted on GitHub:
🔗 [https://github.com/tsira00/middterm/blob/main/artists](https://github.com/tsira00/middterm/blob/main/artists)

---
