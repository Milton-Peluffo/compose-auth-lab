# Habit Tracker App 🚀

> **Note:** This is a **temporary README**. The application and its documentation are currently under development.

A modern, **Offline-First** Habit Tracker built with a focus on **Clean Architecture** and high-quality software engineering standards.

---

## Tech Stack
* **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI).
* **Architecture:** Clean Architecture (Feature-based) + MVVM.
* **Dependency Injection:** [Dagger Hilt](https://dagger.dev/hilt/).
* **Local Data & Cache:** [Room Database](https://developer.android.com/training/data-storage/room) (Single source of truth).
* **Background Tasks:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) (Offline-to-Cloud synchronization).
* **Backend Integration:** * **Authentication:** [Supabase SDK](https://supabase.com/docs/reference/kotlin/introduction).
    * **Core API & Networking:** [Retrofit 2](https://square.github.io/retrofit/) + [OkHttp](https://square.github.io/okhttp/).
* **Async Programming:** Kotlin Coroutines & Flow.

---

## Key Features & Patterns
* **Offline-First Approach:** High-performance local caching with Room; the app remains fully functional without an internet connection.
* **Background Sync:** Seamless data synchronization between local cache and Supabase using WorkManager.
* **Clean Architecture:** Strict separation of layers (Domain, Data, and Presentation).
* **SOLID & Clean Code:** High adherence to industry best practices and naming conventions.
* **Feature-Oriented:** Modular package organization (Feature-based slicing) for high scalability.
