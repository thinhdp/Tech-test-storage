# Sudoku Game - Quick Start Guide

## 🔧 Environment Requirements

- **OS:** Windows, Linux, or macOS
- **Java:** 17 or higher
- **Maven:** 3.6 or higher

## Get Started in 2 Steps

### Build the Project
```bash
cd gic-game
mvn clean package
```

### Run the Game
```bash
cd target
java -jar sudoku-game.jar
```

### Run the test
```bash
cd gic-game
mvn test
```

### Run Specific Test Class
```bash
# Test the validator
mvn test -Dtest=SudokuValidatorTest

# Test the command parser
mvn test -Dtest=CommandParserTest

# Test the game integration (full flow)
mvn test -Dtest=SudokuGameIntegrationTest
```


## Design & Architecture

### Core Architecture

The application follows a **layered architecture** with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│         SudokuGame (Game Loop)          │
│   ├─ Reads commands via Scanner         │
│   ├─ Orchestrates game flow             │
│   └─ Displays output                    │
└────────────┬────────────────────────────┘
             │
    ┌────────┴─────────┬──────────┬──────────┐
    │                  │          │          │
    ▼                  ▼          ▼          ▼
┌─────────┐    ┌────────────┐  ┌──────┐  ┌──────────┐
│ Command │    │SudokuBoard │  │Check │  │GridPrinter
│ Parser  │    │   (State)  │  │Valid │  │(Display)
└─────────┘    └────────────┘  └──────┘  └──────────┘
                      │
           ┌──────────┼──────────┐
           │          │          │
           ▼          ▼          ▼
      ┌─────────┐ ┌──────────┐ ┌──────────┐
      │Generator│ │Solver    │ │Validator │
      │ (Create)│ │(Logic)   │ │(Rules)   │
      └─────────┘ └──────────┘ └──────────┘
```

## Key Assumptions

### Design Decisions

1. **Puzzle Difficulty**
    - Exactly 30 pre-filled cells per puzzle (75% empty)
    - Puzzles are guaranteed valid and have exactly one solution

2. **Cell References**
    - Rows labeled A–I (displayed), internally stored as 0–8
    - Columns labeled 1–9 (displayed), internally stored as 0–8
    - Example: "B3" = row B, column 3 = index [1][2]

3. **Move Validation**
    - Pre-filled cells cannot be modified or cleared
    - User-entered cells cannot be overwritten without clearing first
    - Numbers must be 1–9
    - Move is accepted immediately without checking Sudoku rules (player can create violations and check them with "check" command)

4. **Hint Behavior**
    - Hint reveals exactly one empty cell with the correct value from the solution
    - Hints are not limited (player can request unlimited hints)

5. **Board Completion**
    - Puzzle is "won" only when all 81 cells are filled AND no rule violations exist
    - Incomplete puzzles cannot be won

6. **Input & Output**
    - All I/O is text-based via stdin/stdout
    - Grid printed in a compact, readable format with row/column labels
    - Commands are trimmed and case-insensitive


## Project Structure

```
game/
├── README.md                                 ← This file
├── pom.xml                                   ← Maven configuration
├── SudokuProblemStatement.md                 ← Original specification
│
└── src/
    ├── main/java/org/gic/
    │   ├── Main.java                         ← Entry point
    │   ├── board/
    │   │   ├── SudokuBoard.java              ← Game state
    │   │   ├── SudokuGenerator.java          ← Puzzle creation
    │   │   ├── SudokuSolver.java             ← Backtracking algorithm
    │   │   └── SudokuValidator.java          ← Rule validation
    │   ├── command/
    │   │   ├── Command.java                  ← Command value object
    │   │   └── CommandParser.java            ← Input parsing
    │   ├── display/
    │   │   └── GridPrinter.java              ← Console output
    │   └── game/
    │       └── SudokuGame.java               ← Game loop orchestration
    │
    └── test/java/org/gic/
        ├── board/
        │   ├── SudokuBoardTest.java          ← Board state tests
        │   ├── SudokuGeneratorTest.java      ← Generation tests
        │   ├── SudokuSolverTest.java         ← Solver tests
        │   └── SudokuValidatorTest.java      ← Validation tests
        ├── command/
        │   └── CommandParserTest.java        ← Parser tests
        └── game/
            └── SudokuGameIntegrationTest.java ← Full game flow tests
```


