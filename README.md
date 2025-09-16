# Demres
A simple demo for sharing resources natively

# Launching the KMP Application (res-sample)

This document provides instructions on how to set up your environment, build, and run the `res-sample` Kotlin Multiplatform (KMP) application, which features native UIs for each platform and consumes libraries from a parent project structure, with some dependencies hosted on GitHub Packages.

## 1. Prerequisites

*   **Android Studio:** Latest stable version recommended
*   **Xcode:** Latest stable version recommended
*   **JDK:** Java Development Kit, version 21
*   **Kotlin Multiplatform Mobile Plugin:** Ensure it's installed in Android Studio.
*   **CocoaPods:** Install via `brew` or `gem`.

## 2. Environment Setup for GitHub Dependencies

The project fetches some dependencies from this GitHub Packages repository.To allow Gradle to download these dependencies, you **must** set the following environment variables:

*   `GITHUB_USER`: Your GitHub username.
*   `GITHUB_API_KEY`: A GitHub Personal Access Token (PAT) with at least the `read:packages` scope.

### How to Set Environment Variables:

The `System.getenv()` call in the build script requires these to be actual environment variables accessible by the Gradle process.

1.  **For macOS/Linux:**
*   Open your shell configuration file (e.g., `~/.zshrc`, `~/.bashrc`, or `~/.bash_profile`).
*   Add the following lines, replacing placeholders with your actual credentials:
    
```bash
export GITHUB_USER="your_github_username"
export GITHUB_API_KEY="your_github_personal_access_token"
```
*   Save the file and apply the changes by running source ~/.zshrc (or your respective file) or by restarting your terminal and Android Studio


2. For Windows:
*   Search for "environment variables" in the Start menu to open the System Properties dialog
*   Click on "Environment Variables..."
*   Under "User variables" or "System variables", click "New..." to add GITHUB_USER and GITHUB_API_KEY with their respective values
*   Click OK to save. You'll likely need to restart Android Studio and any command prompt windows for the changes to take effect

   Important Notes:
*   Ensure your PAT is valid and has the correct read:packages permission.
*   After setting these variables, restart Android Studio.
*   Perform a Gradle Sync in Android Studio (File > Sync Project with Gradle Files, or the elephant icon) to ensure dependencies can be resolved.


## 3. Launching app

The res-sample project (`sample` folder) is a KMP application. Key modules include:
* :shared: Contains the common Kotlin logic shared across platforms (Android, iOS)
* :composeApp: Android application module. This module would implement the Android UI (in Compose Multiplatform) and utilize the logic from the :shared module
* iOS Project: This is an Xcode project that implements the native iOS UI (SwiftUI) and consumes the :shared module as a framework

To launch the app open project (sample folder) in Android Studio and iosApp folder in XCode. Then use build-in tools to launch the app
***As project for iOS is not signed, you can launch it only on emulator***


