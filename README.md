# SnakeFx

A simple and classic Snake game built with Java and JavaFX.

## Overview

SnakeFx is a desktop application that recreates the classic Snake game experience. The game is implemented in Java and uses JavaFX for its user interface, providing smooth graphics and responsive controls.

## Features

- Classic Snake gameplay
- Keyboard controls
- Food spawning and score tracking
- Game over detection (collision with walls or self)
- Clean and simple graphical interface

## Requirements

- Java 11 or higher
- JavaFX SDK

## How to Run

1. Clone this repository:
   ```
   git clone https://github.com/<your-username>/SnakeFx.git
   ```
2. Make sure you have Java and JavaFX set up on your machine.
3. Compile and run the application:
   ```
   cd SnakeFx
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls src/*.java
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp src Main
   ```
   Replace `/path/to/javafx-sdk/lib` with the path to your JavaFX SDK.

## Project Structure

- `src/` - Source code files
- `README.md` - Project documentation

## License

This project is licensed under the MIT License.