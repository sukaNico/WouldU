# WouldU - Interactive Dilemmas App

🔗 Backend Link

The app communicates with a backend API to manage user data and dilemmas. You can find the backend repository here.
Make sure the backend server is running and configured correctly before using the app.
URL: https://github.com/Jesusda11/WouldU-backend

**WouldU** is an interactive Android application that allows users to explore ethical dilemmas, make decisions, and reflect on their choices. The app provides a complete navigation system, user management, dilemma creation and editing, and decision analysis.

---

## 🎯 Features

* **Interactive dilemmas**: Explore ethical and everyday situations.
* **User management**: Registration, login, and user profile.
* **Dilemma administration**: Create, edit, and delete dilemmas (admin screens).
* **Feed and detail views**: Browse recent dilemmas and view individual details.
* **App configuration**: User-customizable settings.
* **Modern interface**: Built with Jetpack Compose and Material3.
* **Persistence**: User preferences stored using DataStore.
* **Server communication**: Retrofit for API calls and JWT handling.

---

## 📂 Project Structure

```
app/
│
├─ manifests/
│
├─ java/com/example/WouldU/
│   ├─ data/
│   │   ├─ ApiService.kt
│   │   ├─ RetrofitClient.kt
│   │   └─ UserPreferences.kt
│   ├─ model/
│   │   ├─ Dilema.kt
│   │   └─ ProfileViewModel.kt
│   ├─ ui/
│   │   ├─ components/
│   │   │   └─ BottomNavigationBar.kt
│   │   ├─ screens/
│   │   │   ├─ AdminDilemasScreen.kt
│   │   │   ├─ ConfigScreen.kt
│   │   │   ├─ CreateAccountScreen.kt
│   │   │   ├─ CreateDilemmaScreen.kt
│   │   │   ├─ DilemaPagerScreen.kt
│   │   │   ├─ DilemaScreenSingle.kt
│   │   │   ├─ EditDilemasScreen.kt
│   │   │   ├─ LoginScreen.kt
│   │   │   ├─ ProfileScreen.kt
│   │   │   └─ SplashScreen.kt
│   │   └─ theme/
│   │       └─ AppNavigation.kt
│   ├─ util/
│   └─ MainActivity.kt
```
## 💻 Technologies and Dependencies

* **Language:** Kotlin
* **UI:** Jetpack Compose, Material3
* **Navigation:** Navigation Compose
* **Persistence:** DataStore Preferences
* **Network:** Retrofit, OkHttp Logging Interceptor
* **Compatibility:** Min SDK 26, Target SDK 34, Compile SDK 36
* **Other libraries:** Foundation, Activity Compose, UI Tooling Preview

**Key dependencies:**

```kotlin
implementation(platform("androidx.compose:compose-bom:2024.10.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.datastore:datastore-preferences:1.1.1")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("androidx.navigation:navigation-compose:2.8.3")
implementation("androidx.compose.material3:material3:1.2.0")
implementation("androidx.activity:activity-compose:1.9.3")
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
```

---

## 🚀 Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/WouldU.git
```

2. Open the project in **Android Studio**.
3. Set up SDK 36 and Kotlin 1.9+.
4. Sync Gradle dependencies.
5. Run the app on an emulator or physical device.

---

## 🕹️ Usage

1. Launch the app.
2. Register or log in with an existing account.
3. Explore dilemmas in the **feed** or via the **pager**.
4. Select a dilemma to view details and make decisions.
5. If you are an admin, you can create or edit dilemmas from the admin screen.


