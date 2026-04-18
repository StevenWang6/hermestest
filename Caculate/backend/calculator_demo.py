#!/usr/bin/env python3
"""
计算器功能演示
"""

from calculator import Calculator, CalculatorError

def main():
    """主演示函数"""
    print("=" * 60)
    print("高级计算器后端算法演示")
    print("=" * 60)
    
    calc = Calculator()
    
    # 演示1: 基本运算
    print("\n1. 基本运算演示:")
    basic_tests = [
        ("2 + 3", 5),
        ("10 - 4", 6),
        ("3 * 5", 15),
        ("20 / 4", 5),
        ("2 + 3 * 4", 14),
        ("(2 + 3) * 4", 20),
    ]
    
    for expr, expected in basic_tests:
        try:
            result = calc.calculate(expr)
            status = "✓" if abs(result - expected) < 0.0001 else "✗"
            print(f"  {status} {expr} = {result} (期望: {expected})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 演示2: 小数和负数
    print("\n2. 小数和负数演示:")
    decimal_tests = [
        ("0.1 + 0.2", 0.3),
        ("-5 + 3", -2),
        ("2.5 * 3.2", 8.0),
        ("10 / (-2)", -5),
    ]
    
    for expr, expected in decimal_tests:
        try:
            result = calc.calculate(expr)
            status = "✓" if abs(result - expected) < 0.0001 else "✗"
            print(f"  {status} {expr} = {result:.4f} (期望: {expected})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 演示3: 科学计算
    print("\n3. 科学计算演示:")
    import math
    scientific_tests = [
        ("sin(pi/2)", 1.0),
        ("sqrt(25)", 5.0),
        ("log(e)", 1.0),
        ("exp(1)", math.e),
    ]
    
    for expr, expected in scientific_tests:
        try:
            result = calc.calculate(expr)
            status = "✓" if abs(result - expected) < 0.0001 else "✗"
            print(f"  {status} {expr} = {result:.4f} (期望: {expected:.4f})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 演示4: 记忆功能
    print("\n4. 记忆功能演示:")
    calc.memory_clear()
    calc.memory_store(100)
    print(f"  ✓ 存储到记忆: {calc.memory_recall()}")
    
    calc.memory_add(50)
    print(f"  ✓ 加50到记忆: {calc.memory_recall()}")
    
    calc.memory_subtract(30)
    print(f"  ✓ 减30从记忆: {calc.memory_recall()}")
    
    # 演示5: 错误处理
    print("\n5. 错误处理演示:")
    error_tests = [
        "5 / 0",
        "(2 + 3",
        "2 @ 3",
        "sqrt(-1)",
    ]
    
    for expr in error_tests:
        try:
            result = calc.calculate(expr)
            print(f"  ✗ {expr} = {result} (应该出错但没有)")
        except CalculatorError as e:
            print(f"  ✓ {expr} -> 正确捕获错误: {e}")
    
    # 演示6: 历史记录
    print("\n6. 历史记录演示:")
    history = calc.get_history()
    print(f"  总共执行了 {len(history)} 次计算")
    print("  最近5次计算:")
    for i, (expr, result) in enumerate(history[-5:], 1):
        print(f"    {i}. {expr} = {result:.4f}")
    
    print("\n" + "=" * 60)
    print("演示完成!")
    print("=" * 60)

if __name__ == "__main__":
    main()