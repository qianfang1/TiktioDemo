# Project Overview

This is a native Android application developed in Java, seemingly a clone of TikTok. It displays a list of videos in a staggered grid layout, and users can click on a video to view it in detail. The video data is currently loaded from a local `data.json` file.

## Main Technologies

*   **Language:** Java
*   **Platform:** Android
*   **UI:** AndroidX libraries (AppCompat, ConstraintLayout, RecyclerView)
*   **Image Loading:** Glide
*   **JSON Parsing:** Gson
*   **UI Components:** SmartRefreshLayout for pull-to-refresh

## Project Structure

*   `app/src/main/java/com/example/faketiktok/`: Main source code directory.
    *   `MainActivity.java`: The main entry point of the application, displaying the video list.
    *   `VideoDetailActivity.java`: Activity for playing a single video.
    *   `VideoAdapter.java`: Adapter for the RecyclerView in `MainActivity`.
    *   `VideoPagerAdapter.java`: Adapter for the ViewPager2 in `VideoDetailActivity`.
    *   `model/VideoBean.java`: Data model for a video.
*   `app/src/main/assets/data.json`: Local JSON file containing the video data.
*   `app/build.gradle.kts`: Gradle build script, defining dependencies and project settings.

# Building and Running

## Building

To build the project, you can use Gradle. Open a terminal in the project's root directory and run the following command:

```bash
./gradlew build
```

## Running

You can run the application on an Android emulator or a physical device using Android Studio or by running the following Gradle command:

```bash
./gradlew installDebug
```

# Development Conventions

## Code Style

The code follows standard Java and Android conventions.

## Testing

The project includes basic unit and instrumentation tests, which can be run with the following Gradle commands:

*   Unit tests: `./gradlew test`
*   Instrumentation tests: `./gradlew connectedAndroidTest`

## TODOs

*   The video and cover URLs in `data.json` are placeholders. These need to be replaced with actual URLs.
*   The video playback functionality in `VideoDetailActivity` is not fully implemented.
*   Implement a proper backend to serve video data instead of using a local JSON file.
