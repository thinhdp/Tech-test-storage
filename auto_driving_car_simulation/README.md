#  Auto Driving Car Simulation

A command-line simulation that models multiple autonomous cars navigating a rectangular grid field. Cars follow user-defined command sequences (`L`, `R`, `F`) and respect field boundaries. The simulator supports multi-car collisions and reports stuck or crashed cars accurately.

---

##  Features

-  **Interactive CLI**: Guide users to define field size, add cars with name, position, direction (`N`, `S`, `E`, `W`), and command list.
-  **Supports multiple cars**: Each with its own command list (`L`, `R`, `F`), running simultaneously.
-  **Command Types**:
  - `L`: Rotate 90° to the left (counter-clockwise)
  - `R`: Rotate 90° to the right (clockwise)
  - `F`: Move forward by one unit in the current direction
-  **Simulation Logic**:
  - Out-of-bound detection (`stuck`)
  - Collision detection (2+ cars)
  - Step-by-step sequential execution (1 command per car per step)
-  **Clean, modular OOP architecture**
-  **Fully unit tested** (`Car`, `Field`, `Direction`, `Simulator`)

---

##  How to Run the Program

From the project root directory, run:

```bash
python main.py
```

You will be prompted to:

1. Enter the field size (`width height`)
2. Add cars (name, position, direction, and command sequence)
3. Run the simulation and view results
4. Start over or exit

---

##  How to Run Tests

Unit tests are located in the `tests/` folder and cover every core component.

Run all tests:

```bash
python -m unittest discover -s tests
```

Run a specific test file:

```bash
python -m unittest tests/simulation/test_simulator.py
```
---

##  Simulation Scenarios

###  1. Normal Movement (no collisions or boundaries)

```
A: (2, 2) facing N, commands = FFRF

Step-by-step:
- F → (2, 3)
- F → (2, 4)
- R → facing E
- F → (3, 4)

Result:
- A, (3, 4) E
```

---

###  2. Out-of-Bound Movement (stuck)

```
A: (0, 0) facing N, commands = FLF

Step-by-step:
- F → (0, 1)
- L → facing W
- F → attempts (-1, 1) →  out-of-bounds

Result:
- A, stuck at (0, 1) at step 3
```

---

###  3. Two-Car Collision

```
A: (0, 0) N, commands = FRF
B: (1, 0) N, commands = LRF

Step-by-step:
- A moves → (0, 1), turns R → E, then tries to move to (1, 1)
- B moves → (1, 1), turns R → E, then stays

At step 3, both attempt (1, 1)

Result:
- A, collides with B at (1, 1) at step 3
- B, collides with A at (1, 1) at step 3
```

---

###  4. Multi-Car Collision (3+ cars)

```
A: (0, 0) N → FRF
B: (1, 0) N → LRF
C: (2, 0) N → FLF

At step 3, all try to move to (1, 1)

Result:
- A, collides with B, C at (1, 1) at step 3
- B, collides with A, C at (1, 1) at step 3
- C, collides with A, B at (1, 1) at step 3
```

---

###  5. Sequential Step Execution

Simulation proceeds one step at a time, per car.

```
Step 1:
- A: F
- B: F

Step 2:
- A: L
- B: R

Step 3:
- A: F
- B: F

Ensures fairness across cars.
```

---

##  Project Structure

```
auto_driving_car_simulation/
│
├── src/
│   ├── simulation/
│   │   ├── simulator.py
│   │   └── display.py
│   └── objects/
│       ├── car.py
│       ├── field.py
│       └── direction.py
│
├── tests/
│   ├── simulation/
│   │   └── test_simulator.py
│   └── objects/
│       ├── test_car.py
│       ├── test_field.py
│       └── test_direction.py
│
├── run.py
├── requirements.txt
└── README.md
```

---

##  Requirements

- Python 3.8+
- No external libraries required (except `pytest` optionally for testing)

---