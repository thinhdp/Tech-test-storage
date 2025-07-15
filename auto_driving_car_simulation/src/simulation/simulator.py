from src.objects.car import Car
from src.objects.field import Field
from typing import Dict, Tuple, List, Any


class Simulator:
    """
    Simulator class for running an autonomous car simulation on a rectangular field.
    Supports multiple cars, step-by-step execution, collision detection, and out-of-bound handling.
    """

    def __init__(self, field: Field) -> None:
        self.field: Field = field
        self.cars: List[Car] = []
        self.collisions: Dict[str, Any] = {}
        self.stuck: Dict[str, Any] = {}

    def add_car(self, car: Car) -> None:
        self.cars.append(car)

    def simulate(self) -> Dict[str, str]:
        """
        Runs the simulation for all cars, step-by-step, processing commands and handling collisions.
        :return: Dict[str, str]: A dictionary of car names and their final status/result string.
        """
        max_steps = max(len(car.commands) for car in self.cars)
        for step in range(max_steps):
            proposed_moves = self._propose_moves(step)
            self._detect_collisions(proposed_moves, step)
            self._apply_moves(proposed_moves)

        return self._compile_results()

    def _propose_moves(self, step: int) -> Dict[str, Tuple[int, int]]:
        """
        Proposes moves for each car based on their current state and commands.
        :param step: The current step in the simulation.
        :return: Dict[str, Tuple[int, int]]: A dictionary mapping car names to their proposed new positions.
        """
        proposed_moves: Dict[str, Tuple[int, int]] = {}
        for car in self.cars:
            if not car.active:
                continue

            current_pos = car.position()
            step_result = car.step(self.field)

            if step_result == "ROTATE":
                proposed_moves[car.name] = current_pos
            elif step_result == "OUT_OF_BOUNDS":
                proposed_moves[car.name] = current_pos
                self.stuck[car.name] = (current_pos, step + 1)
            elif isinstance(step_result, tuple):
                proposed_moves[car.name] = step_result
            else:
                proposed_moves[car.name] = current_pos

        return proposed_moves

    def _detect_collisions(
        self, proposed_moves: Dict[str, Tuple[int, int]], step: int
    ) -> None:
        """
        Detects collisions based on proposed moves and updates the collision dictionary.
        :param proposed_moves: A dictionary mapping car names to their proposed new positions.
        :param step: The current step in the simulation.
        :return: None
        """
        position_to_names: Dict[Tuple[int, int], List[str]] = {}
        for name, pos in proposed_moves.items():
            position_to_names.setdefault(pos, []).append(name)

        for pos, names in position_to_names.items():
            if len(names) > 1:
                for name in names:
                    self.collisions[name] = (
                        pos,
                        step + 1,
                        [n for n in names if n != name],
                    )

    def _apply_moves(self, proposed_moves: Dict[str, Tuple[int, int]]) -> None:
        """
        Applies the proposed moves to the cars, updating their positions and handling collisions.
        :param proposed_moves: A dictionary mapping car names to their proposed new positions.
        :return: None
        """
        for car in self.cars:
            if not car.active:
                continue
            if car.name in self.collisions:
                car.active = False
                continue
            new_pos = proposed_moves[car.name]
            car.x, car.y = new_pos

    def _compile_results(self) -> Dict[str, str]:
        results: Dict[str, str] = {}
        for car in self.cars:
            name = car.name
            if name in self.collisions:
                pos, step, others = self.collisions[name]
                others_str = ", ".join(others)
                results[name] = f"collides with {others_str} at {pos} at step {step}"
            elif name in self.stuck:
                pos, step = self.stuck[name]
                results[name] = f"stuck at {pos} at step {step}"
            else:
                results[name] = f"({car.x},{car.y}) {car.direction.name}"
        return results
