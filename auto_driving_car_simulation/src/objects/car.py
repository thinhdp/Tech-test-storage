from src.objects.direction import Direction
from src.objects.field import Field

from typing import Optional, Union, Tuple


class Car:
    def __init__(self, name, x, y, direction, commands):
        self.name = name
        self.x = x
        self.y = y
        self.direction = Direction[direction]
        self.commands = list(commands)
        self.active = True
        self.command_idx = 0

    def position(self):
        return (self.x, self.y)

    def step(self, field: Field) -> Union[Optional[Tuple[int, int]], str]:
        """
        Executes the next command for the car.
        :param field: The field on which the car operates.
        :return: A tuple with the new position if the move is valid,
                 'ROTATE' if the command is a rotation, or 'OUT_OF_BOUNDS' if the move goes out of bounds.
        """
        if not self.active or self.command_idx >= len(self.commands):
            return None

        cmd = self.commands[self.command_idx]
        self.command_idx += 1

        if cmd == 'L':
            self.direction = self.direction.left()
            return 'ROTATE'
        elif cmd == 'R':
            self.direction = self.direction.right()
            return 'ROTATE'
        elif cmd == 'F':
            dx, dy = self.direction.value
            new_x, new_y = self.x + dx, self.y + dy
            if field.in_bounds(new_x, new_y):
                return (new_x, new_y)
            else:
                return 'OUT_OF_BOUNDS'