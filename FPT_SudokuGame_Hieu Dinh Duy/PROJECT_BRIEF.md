
# Sudoku Game – Project Brief

## 1. Project Overview
The **Sudoku Game** is a Java-based application that allows users to play, validate, and solve Sudoku puzzles.  
The project focuses on clean architecture, testability, and maintainability using modern Java testing practices while remaining compatible with **Java 8**.

---

## 2. Objectives
- Implement a functional Sudoku game engine
- Provide puzzle validation and solving logic
- Ensure high code quality with automated tests
- Support command-line execution via a runnable JAR
- Maintain clear separation between production and test code

---

## 3. Scope

### In Scope
- Sudoku grid representation (9x9)
- Rule validation (rows, columns, sub-grids)
- Puzzle solving logic
- Console-based user interaction  
  (example commands: `A1 1`, `A1 clear`, `check`, `hint`, `resolve`, `generate`, `quit`)
- Unit and integration testing
- Code coverage enforcement

### Out of Scope
- Graphical user interface (GUI)
- Online multiplayer features
- Puzzle generation using advanced heuristics
- Mobile or web deployment

---

## 4. Technology Stack

### Language & Runtime
- Java 8

### Build & Dependency Management
- Apache Maven

### Testing
- JUnit 5.10 (JUnit Jupiter & Platform Suite)
- Mockito 4.11.0
- Maven Surefire Plugin

### Quality & Coverage
- JaCoCo Maven Plugin

---
## 5. Application Entry Point

The application is executed via a single entry point class responsible for starting the Sudoku game.

- Main class: `MainProgram`
- Entry method: `public static void main(String[] args)`
- The application is designed to run from the command line.

### Execution

Build the project using Maven:

`mvn clean package`

After building the project, the application can be started using:

`java -jar sudokugame-<version>.jar`

## 6. Project Structure

```text
sudokugame/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── game/
│   │           ├── MainProgram.java
│   │           ├── application/
│   │           ├── domain/
│   │           ├── implementors/
│   │           └── ports/
│   └── test/
│       └── java/
│           └── game/
│               ├── TestUtility.java
│               ├── suite/
│               │   └── TestsSuite.java
│               ├── integration/
│               │   └── GameRunnerIntegrationTest.java
│               ├── application/
│               ├── domain/
│               ├── implementors/
│               └── ports/
├── pom.xml
└── PROJECT_BRIEF.md