from src.objects.field import Field
from src.simulation.simulator import Simulator
from src.objects.car import Car


def run_cli():
    print("Welcome to Auto Driving Car Simulation!")

    # Field input
    while True:
        try:
            width_height = input("Please enter the width and height of the simulation field in x y format:\n").split()
            assert len(width_height) == 2, "You must enter exactly two integers."
            width, height = map(int, width_height)
            assert width > 0 and height > 0, "Width and height must be positive integers."
            break
        except Exception as e:
            print(f"Invalid input: {e}. Please try again.")

    field = Field(width, height)
    simulator = Simulator(field)

    while True:
        print("\nPlease choose from the following options:\n[1] Add a car to field\n[2] Run simulation")
        choice = input().strip()

        if choice == '1':
            # Car name
            while True:
                name = input("Please enter the name of the car:\n").strip()
                if name and not any(c.name == name for c in simulator.cars):
                    break
                print("Invalid or duplicate car name. Please try again.")

            # Position & direction
            while True:
                try:
                    parts = input(f"Please enter initial position of car {name} in x y Direction format:\n").split()
                    assert len(parts) == 3, "Expected format: x y D"
                    x, y = int(parts[0]), int(parts[1])
                    d = parts[2].upper()
                    assert d in ['N', 'S', 'E', 'W'], "Direction must be one of N, S, E, W"
                    assert field.in_bounds(x, y), "Position out of bounds"
                    assert not any(c.position() == (x, y) for c in simulator.cars), f"Position ({x},{y}) already occupied"
                    break
                except Exception as e:
                    print(f"Invalid input: {e}. Please try again.")

            # Commands
            while True:
                commands = input(f"Please enter the commands for car {name}:\n").strip().upper()
                if commands and all(c in ['F', 'L', 'R'] for c in commands):
                    break
                print("Commands must be a non-empty string containing only F, L, or R. Please try again.")

            # Add car
            try:
                simulator.add_car(Car(name, x, y, d, commands))
                print("Your current list of cars are:")
                for car in simulator.cars:
                    print(f"- {car.name}, ({car.x},{car.y}) {car.direction.name}, {''.join(car.commands)}")
            except ValueError as e:
                print(f"Could not add car: {e}")

        elif choice == '2':
            if not simulator.cars:
                print("No cars to simulate. Please add at least one car first.")
                continue
            print("After simulation, the result is:")
            results = simulator.simulate()
            for car in simulator.cars:
                print(f"- {car.name}, {results[car.name]}")
            print("\nPlease choose from the following options:\n[1] Start over\n[2] Exit")
            while True:
                end_choice = input().strip()
                if end_choice == '1':
                    return run_cli()
                elif end_choice == '2':
                    print("Thank you for running the simulation. Goodbye!")
                    return
                else:
                    print("Invalid choice. Please enter 1 or 2.")
        else:
            print("Invalid option. Please enter 1 or 2.")