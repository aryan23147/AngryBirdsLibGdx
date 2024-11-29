# Angry Birds Clone - Advanced Programming Project

## Overview

This project is a **Java-based Angry Birds Clone**, developed as part of the Advanced Programming course. It combines game development concepts using **libGDX** and **Box2D** for physics simulation, focusing on a slingshot mechanic, destructible objects, and various bird types with unique abilities.

### Key Features
1. **Gameplay Mechanics**:
    - Slingshot birds to destroy pigs and structures.
    - Three bird types with special powers:
        - **Red Bird**: Speed boost.
        - **Black Bird**: Radial push.
        - **Blue Bird**: Splits into three smaller birds.
    - Multiple levels with increasing difficulty.

2. **Game Components**:
    - Birds, Pigs, Blocks, Slingshot, and Ground.
    - Level completion based on objectives (destroy all pigs).
    - Number of stars given at winning based on the amount of destruction caused
    - Serialization for game saving/loading.

3. **Technical Features**:
    - Developed using **libGDX** for game rendering.
    - **Box2D** for realistic physics.

4. **UI/UX**:
    - Static GUI with screens for:
        - Loading Screen, Main Menu, Level Selection, Game Screen.
        - Save, Load, Pause, Win, and Lose Windows.
    - Interactive buttons and visual feedback for user actions.
    - Basic sound controls.
    - SFX for clicks and gameplay.

## Project Structure

### Core Classes
- **`Main`**: Main game launcher, extends `Game`.
- **`MainMenu`**: Starting the game.
- **`LevelSelectionScreen`**: Can select one of the three levels to play.
- **`GameScreen`**: Handles game logic, physics, and rendering.
- **`Bird` and Subclasses (`RedBird`, `BlackBird`, `BlueBird`)**: Implements bird behavior and powers.
- **`Slingshot`**: Controls bird loading and launching.
- **`Pig`** and **`Block`**: Targets and obstacles with destructible properties.
- **`Power` Interface**: Defines special bird abilities.

### Utilities
- **Serialization**: Saves and loads game state.

## Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone "https://github.com/aryan23147/AngryBirdsLibGdx.git"
   ```

2. **Import the Project**:
    - Use IntelliJ IDEA with Gradle support.

3. **Run the Game**:
    - Launch ``Lwjgl3Launcher.java`` from
      lwjgl3\src\main\java\io\github\some_example_name\lwjgl3\Lwjgl3Launcher.java.

## Dependencies

- **libGDX**: Game development framework.
- **Box2D**: Physics engine.
- **Gradle**: Build automation tool.

## How to Play
1. Select a level from the Level Selection screen.
2. Use the slingshot to launch birds at pigs and structures.
3. Complete the level by defeating all pigs.
4. Use special bird powers by pressing SpaceBar.

## Contributors
- Aryan Singhal 2023147
- Aditya Verma 2023051

---
