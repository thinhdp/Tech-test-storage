import unittest
from src.objects.car import Car
from src.objects.field import Field


class TestCar(unittest.TestCase):

    def setUp(self) -> None:
        self.field = Field(width=5, height=5)

    def test_initial_position(self) -> None:
        car = Car(name="A", x=2, y=3, direction="N", commands="")
        self.assertEqual(car.position(), (2, 3))

    def test_rotation_left(self) -> None:
        car = Car(name="A", x=0, y=0, direction="N", commands="L")
        car.step(field=self.field)
        self.assertEqual(car.direction.name, "W")

    def test_rotation_right(self) -> None:
        car = Car(name="A", x=0, y=0, direction="N", commands="R")
        car.step(field=self.field)
        self.assertEqual(car.direction.name, "E")

    def test_forward_move_in_bounds(self) -> None:
        car = Car(name="A", x=1, y=1, direction="N", commands="F")
        result = car.step(field=self.field)
        self.assertEqual(result, (1, 2))

    def test_forward_move_out_of_bounds(self) -> None:
        car = Car(name="A", x=0, y=4, direction="N", commands="F")
        result = car.step(field=self.field)
        self.assertEqual(result, 'OUT_OF_BOUNDS')

