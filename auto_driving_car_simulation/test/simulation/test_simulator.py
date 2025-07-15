import unittest
from src.objects.field import Field
from src.objects.car import Car
from src.simulation.simulator import Simulator

from typing import Dict, Any


class TestSimulator(unittest.TestCase):

    def setUp(self) -> None:
        self.field = Field(width=10, height=10)

    def test_single_car_simple_move(self) -> None:
        car = Car(name="A", x=1, y=1, direction="N", commands="FFRFF")
        sim = Simulator(field=self.field)
        sim.add_car(car=car)
        results: Dict = sim.simulate()
        self.assertEqual(results["A"], "(3,3) E")

    def test_single_car_stuck_out_of_bounds(self):
        car = Car(name="A", x=0, y=0, direction="N", commands="FLF")
        sim = Simulator(field=self.field)
        sim.add_car(car=car)
        results: Dict = sim.simulate()
        self.assertEqual(results["A"], "stuck at (0, 1) at step 3")

    def test_two_cars_collide(self) -> None:
        car1 = Car(name="A", x=0, y=0, direction="N", commands="FRF")
        car2 = Car(name="B", x=1, y=0, direction="N", commands="LRF")
        sim = Simulator(field=self.field)
        sim.add_car(car=car1)
        sim.add_car(car=car2)
        results = sim.simulate()
        self.assertEqual(first=results["A"], second="collides with B at (1, 1) at step 3")
        self.assertEqual(first=results["B"], second="collides with A at (1, 1) at step 3")

    def test_multiple_car_collision(self) -> None:
        car1 = Car(name="A", x=0, y=0, direction="N", commands="FRF")
        car2 = Car(name="B", x=1, y=0, direction="N", commands="LRF")
        car3 = Car(name="C", x=2, y=0, direction="N", commands="FLF")
        sim = Simulator(self.field)
        sim.add_car(car=car1)
        sim.add_car(car=car2)
        sim.add_car(car=car3)
        results: Dict = sim.simulate()
        self.assertEqual(results["A"], "collides with B, C at (1, 1) at step 3")
        self.assertEqual(results["B"], "collides with A, C at (1, 1) at step 3")
        self.assertEqual(results["C"], "collides with A, B at (1, 1) at step 3")

    def test_no_collision_all_safe(self) -> None:
        car1 = Car(name="A", x=0, y=0, direction="N", commands="F")
        car2 = Car(name="B", x=2, y=2, direction="S", commands="F")
        sim = Simulator(self.field)
        sim.add_car(car=car1)
        sim.add_car(car=car2)
        results: Dict = sim.simulate()
        self.assertEqual(results["A"], "(0,1) N")
        self.assertEqual(results["B"], "(2,1) S")