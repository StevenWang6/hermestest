#!/usr/bin/env python3
"""
Test file for Hermes Test Project
"""

import unittest

def add(a, b):
    """Simple addition function for testing."""
    return a + b

def multiply(a, b):
    """Simple multiplication function for testing."""
    return a * b

class TestMathFunctions(unittest.TestCase):
    """Test cases for math functions."""
    
    def test_add(self):
        self.assertEqual(add(2, 3), 5)
        self.assertEqual(add(-1, 1), 0)
        self.assertEqual(add(0, 0), 0)
    
    def test_multiply(self):
        self.assertEqual(multiply(2, 3), 6)
        self.assertEqual(multiply(-1, 5), -5)
        self.assertEqual(multiply(0, 100), 0)
    
    def test_combined(self):
        result = multiply(add(2, 3), 4)
        self.assertEqual(result, 20)

if __name__ == "__main__":
    unittest.main()
