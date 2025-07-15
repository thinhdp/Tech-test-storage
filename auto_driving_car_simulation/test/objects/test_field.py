import unittest
from src.objects.field import Field


class TestField(unittest.TestCase):

    def setUp(self) -> None:
        self.field = Field(width=5, height=5)

    def test_in_bounds_true(self):
        self.assertTrue(self.field.in_bounds(3, 3))

    def test_in_bounds_false_negative(self) -> None:
        self.assertFalse(self.field.in_bounds(-1, 0))

    def test_in_bounds_false_too_large(self) -> None:
        self.assertFalse(self.field.in_bounds(5, 5))  # max is 4,4