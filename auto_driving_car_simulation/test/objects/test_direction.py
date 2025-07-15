import unittest
from src.objects.direction import Direction


class TestDirection(unittest.TestCase):

    def test_left_rotation(self) -> None:
        self.assertEqual(Direction.N.left(), Direction.W)
        self.assertEqual(Direction.W.left(), Direction.S)

    def test_right_rotation(self) -> None:
        self.assertEqual(Direction.N.right(), Direction.E)
        self.assertEqual(Direction.E.right(), Direction.S)

    def test_movement_value(self) -> None:
        self.assertEqual(Direction.N.value, (0, 1))
        self.assertEqual(Direction.S.value, (0, -1))
        self.assertEqual(Direction.E.value, (1, 0))
        self.assertEqual(Direction.W.value, (-1, 0))