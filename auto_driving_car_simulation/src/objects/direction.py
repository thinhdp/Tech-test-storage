from enum import Enum

class Direction(Enum):
    N: tuple = (0, 1)
    E: tuple = (1, 0)
    S: tuple = (0, -1)
    W: tuple = (-1, 0)

    def left(self) -> "Direction":
        anti_clockwise_order: list[str] = ["N", "W", "S", "E"]
        new_index: int = (anti_clockwise_order.index(self.name) + 1) % 4
        return Direction[anti_clockwise_order[new_index]]

    def right(self) -> "Direction":
        clockwise_order: list[str] = ["N", "E", "S", "W"]
        new_index: int = (clockwise_order.index(self.name) + 1) % 4
        return Direction[clockwise_order[new_index]]