#!/usr/bin/env python3
"""
Hermes Test Project - Python Example
This file demonstrates basic Python functionality.
"""

def main():
    print("🐍 Python Example for Hermes Test Project")
    print("=" * 50)
    
    # Basic operations
    numbers = [1, 2, 3, 4, 5]
    total = sum(numbers)
    print(f"Sum of numbers {numbers}: {total}")
    
    # String manipulation
    message = "Hermes Agent is awesome!"
    print(f"Message: {message}")
    print(f"Uppercase: {message.upper()}")
    print(f"Word count: {len(message.split())}")
    
    # Dictionary example
    config = {
        "project": "hermestest",
        "author": "StevenWang6",
        "language": "Python",
        "version": "1.0.0"
    }
    
    print("\nConfiguration:")
    for key, value in config.items():
        print(f"  {key}: {value}")

if __name__ == "__main__":
    main()
