# SkyPulse 🌤️

**SkyPulse** is a robust, feature-rich modern Android weather application designed to provide users with real-time weather tracking, detailed multi-day forecasts, offline support, and interactive map-based location management. Built with a solid architecture and modern technology stack, SkyPulse ensures a seamless and responsive experience for staying prepared for changing conditions.

Developed by **Abdelrahman Waheed**.

## 📸 App Screenshots

| Splash | Onboarding 1 | Onboarding 2 | Onboarding 3 |
|--------|--------------|--------------|--------------|
| <img src="https://github.com/user-attachments/assets/0b2396c5-44e7-493e-a1e8-1afcabf53efc" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/4c04f953-c4a8-4004-ae85-e6b7a87b4d55" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/bf490e2c-fe54-4e96-b893-1aa6b6bad060" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/63e7ea7f-a783-4f94-ba90-89a947ab3b24" width="220" height="480"/> |

| Location Picker | Map | Home | Forecast |
|-------|----------|------|----------|
| <img src="https://github.com/user-attachments/assets/1153b5d7-4f71-4d45-aa71-5a3e31846c02" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/d1edf079-d141-49ae-84f8-e9e2e6585880" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/59d9f479-577e-4c9d-a550-0eb0df0946f0" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/6c248375-8a37-462b-8d04-71e968420ffd" width="220" height="480"/> |

| Favorites | Favorite Details | Alerts | Add Alert |
|-------|----------|------|----------|
| <img src="https://github.com/user-attachments/assets/d9e14540-b642-48fb-b850-6a7273088e18" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/9d59125f-1286-41f3-87c7-755c1c6d0f2f" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/668187ac-3712-4ca7-b68d-a797c584481a" width="220" height="480"/> | <img src="https://github.com/user-attachments/assets/5a22f553-c1b8-456e-874c-024ca4450bf0" width="220" height="480"/> |

| Settings |
|----------|
| <img src="https://github.com/user-attachments/assets/542f716e-6e7d-4874-94d3-35b096354365" width="220" height="480"/> |


## ✨ Features

### 🌤️ Weather Monitoring
- **Real-time Updates**: Get instant access to current weather conditions based on your pinpointed location.
- **Comprehensive Forecasts**: Dive into detailed daily and hourly forecasts to plan your week ahead with accurate temperature, humidity, and wind speed data.
- **Interactive Mapping**: Search and pinpoint exact locations seamlessly using Google Maps integration. Switch locations dynamically across the globe.

### ❤️ Personalization & Offline Support
- **Favorites Management**: Save your most searched weather destinations with an intuitive swipe-to-dismiss and undo capability for quick access later.
- **Robust Offline Access**: Heavy integration with Room database caching ensures your saved favorites and last fetched weather data are accessible even when completely offline.
- **Theme & Units Customization**: Tailor your experience with fully reactive Light/Dark modes, alongside customizable metric/imperial format settings that apply globally across the app.
- **Dynamic Localization**: Robust multi-language support (English, Arabic).

### 🔔 Smart Background Alerts
- **Weather Alarms**: A sophisticated background scheduling system via `WorkManager` & `AlarmManager` allowing you to configure weather notifications constraints.
- **Precision Scheduling**: Set exact custom duration bounds (specific start and end times) for your alerts. E.g., Notify me if there is Rain between 7:00 AM and 9:00 AM.
- **Custom Event Triggers**: Choose from specific weather events to monitor, such as Rain, Snow, Clouds, Check Clear Sky conditions, etc.

### 📍 Advanced Location Services
- **GPS State Monitoring**: Advanced location-aware UI that immediately detects, reacts to, and prompts for GPS status enablement dynamically if the service is turned off.
- **Smart Initial Location**: The app asks for location permission once on first launch and automatically centers your forecast logic to where you are.


---

## 🏗️ Project Architecture

SkyPulse is built following **Clean Architecture** principles, utilizing the **Model-View-ViewModel (MVVM)** pattern for the UI layer and the **Repository Pattern** for data management.

### 🧩 UI Layer (MVVM & Compose)
The UI is decoupled from the business logic, making it highly reactive and maintainable.
- **View (Jetpack Compose)**: UI is built entirely utilizing declarative Jetpack Compose.
- **ViewModel**: Contains the presentation logic. It reacts to user actions, manages StateFlows, fetches data from the Model (Repository), and exposes immutable state to the View.

### 📂 Data Layer (Repository Pattern)
The repository acts as a single source of truth for data, abstracting the source (Network vs. Local).
- **Remote Data Source**: Fetches weather data from the API using Retrofit.
- **Local Data Source**: Manages persistent storage using Room Database for caching and DataStore Preferences for session and settings management.
- **Dependency Injection**: Relies on a clean, scalable **Manual Dependency Injection (Service locator)** approach to keep the application lightweight.

---

## 📂 Folder Structure

```text
com.iti.skypulse
├── core/           # Core cross-cutting utilities (Network, Schedulers, Notifications)
├── data/           # Data layer infrastructure
│   ├── local/      # Room Databases, DAOs, DataStore Preferences
│   ├── model/      # Data entities and DTOs
│   ├── remote/     # Retrofit API clients and service logic
│   └── repository/ # Repository implementations (Single source of truth)
├── di/             # Manual Dependency Injection (ServiceLocator)
├── ui/             # Presentation layer grouped by feature domains
│   ├── alerts/     # Weather alarms & notifications UI
│   ├── favorites/  # Favorite locations administration
│   ├── forecast/   # Multi-day forecast charting & UI
│   ├── home/       # Main dashboard layout
│   ├── location/   # Smart location acquisition logic
│   ├── map/        # Google Maps integration & marker selections
│   ├── navigation/ # Compose Navigation routes and graph logic
│   ├── settings/   # Customization & localization screens
│   └── theme/      # Material 3 Design System elements
└── worker/         # WorkManager background sync logic
```

---

## 🛠️ Technology Stack

SkyPulse leverages a variety of modern Android development tools and libraries:

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel) & Clean Architecture
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) with Material Design 3.
- **Reactive Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [StateFlow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) for handling asynchronous data streams and UI state.
- **Networking**: [Retrofit 2](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/) (Logging Interceptor) for efficient API communication.
- **Local Persistence**: 
  - [Room](https://developer.android.com/training/data-storage/room) for caching and complex data management.
  - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for type-safe preference storage.
- **Background Tasks**: [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) and `AlarmManager`.
- **Location & Mapping**: Google Maps Compose, Google Play Services Location.
- **Image Loading**: [Coil Compose](https://coil-kt.github.io/coil/compose/) and [Shimmer](https://facebook.github.io/shimmer-android/).
- **Testing Tools**: JUnit 4, MockK, Turbine, Compose UI Testing.

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (Koala or newer).
- JDK 17 (or compatible version).
- An API Key from OpenWeatherMap (or the configured weather provider).

### Setup Instructions
1. **Clone the project**:
   ```bash 
   git clone https://github.com/AbdoWa7eed/SkyPulse.git
   ```
2. **Open in Android Studio**:
   Import the project and wait for Gradle sync to unpack dependencies.
3. **API Keys Setup**:
   Open the repository root directory and create a file named `local.properties` (if it does not natively exist). Insert your required API keys for weather and mapping services:
   ```properties
   MAPS_API_KEY="your_google_maps_api_key_here"
   WEATHER_API_KEY="your_openweather_api_key_here"
   ```
4. **Build and Run**:
   Compile the app via Build > Make Project and run the app on an Android Emulator or physical device.

---

## 👤 Author

**Abdelrahman Waheed**
- **LinkedIn**: [Abdelrahman Waheed](https://www.linkedin.com/in/abdelrahmanwa7eed-dev/)
- **GitHub**: [@AbdoWa7eed](https://github.com/AbdoWa7eed)

---

## 📄 License

This project was developed as part of the Android Development Track, Intake 46, at ITI (Information Technology Institute).
