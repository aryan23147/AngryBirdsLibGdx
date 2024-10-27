# AngryBirds Libgdx

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws the libGDX logo.

## Contributors
- Aryan Singhal (2023147)
- Aditya Verma (2023051)

### Contribution
We worked together for most of the project and contributed equally.

## UML Diagram
For the updated UML, please visit: [Miro UML Diagram](https://miro.com/welcomeonboard/bDMyeHV3Z0dUQ2tZR0J5aUdJZzg1YjFsOHBZSFczODFUNjkxenN5cUJ1MVE3SjZNNGVnQlpBOVAwbTExbGRyeXwzNDU4NzY0NTYyOTQ0Nzc0MTQ4fDI=?share_link_id=910498626900)

## Platforms

- **core**: Main module with shared application logic across platforms.
- **lwjgl3**: Primary desktop platform using LWJGL3; previously known as 'desktop'.

## Requirements

- **Java JDK 8 or higher** (required for running and compiling the code).
- **Gradle** (included with the project via Gradle wrapper).

## Getting Started

1. Clone the repository.
2. Open a terminal and navigate to the project directory.
3. Use Gradle wrapper commands below to build and run the project.

## Gradle Tasks

This project uses [Gradle](https://gradle.org/) for dependency management. You can run Gradle tasks using `gradlew.bat` on Windows or `./gradlew` on macOS/Linux. Key Gradle tasks:

- **Build & Run**
  - `build`: Compiles and assembles the project into a runnable application.
  - `lwjgl3:run`: Launches the application on the desktop platform.

- **Project Cleanup**
  - `clean`: Deletes the `build` folders that store compiled classes and archives.
  - `cleanEclipse`: Removes Eclipse project metadata.
  - `cleanIdea`: Removes IntelliJ project metadata.

- **IDE Integration**
  - `eclipse`: Generates Eclipse project data.
  - `idea`: Generates IntelliJ IDEA project data.

- **Advanced Flags**
  - `--continue`: Prevents errors from stopping the task sequence.
  - `--offline`: Uses cached dependencies when offline.
  - `--refresh-dependencies`: Forces Gradle to refresh dependencies.

## Running the Application

1. To build and run the application:
   ```sh
   ./gradlew lwjgl3:run
