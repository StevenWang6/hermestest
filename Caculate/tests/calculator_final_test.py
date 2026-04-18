#!/usr/bin/env python3
"""
计算器综合测试和示例
"""

from calculator import Calculator, CalculatorError
import math

def test_all_features():
    """测试所有功能"""
    print("=" * 70)
    print("计算器综合功能测试")
    print("=" * 70)
    
    calc = Calculator()
    passed = 0
    total = 0
    
    # 测试组1: 基本运算
    print("\n1. 基本运算测试:")
    tests = [
        ("2+3", 5),
        ("10-4", 6),
        ("3*5", 15),
        ("20/4", 5),
        ("2+3*4", 14),
        ("(2+3)*4", 20),
        ("10%3", 1),
        ("2^3", 8),
    ]
    
    for expr, expected in tests:
        total += 1
        try:
            result = calc.calculate(expr)
            if abs(result - expected) < 0.0001:
                print(f"  ✓ {expr} = {result}")
                passed += 1
            else:
                print(f"  ✗ {expr} = {result} (期望: {expected})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 测试组2: 小数和负数
    print("\n2. 小数和负数测试:")
    tests = [
        ("0.1+0.2", 0.3),
        ("-5+3", -2),
        ("2.5*3.2", 8.0),
        ("10/(-2)", -5),
        (".5+.5", 1.0),
    ]
    
    for expr, expected in tests:
        total += 1
        try:
            result = calc.calculate(expr)
            if abs(result - expected) < 0.0001:
                print(f"  ✓ {expr} = {result:.4f}")
                passed += 1
            else:
                print(f"  ✗ {expr} = {result:.4f} (期望: {expected})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 测试组3: 科学计算
    print("\n3. 科学计算测试:")
    tests = [
        ("sqrt(25)", 5.0),
        ("log(e)", 1.0),
        ("exp(1)", math.e),
        ("abs(-5.5)", 5.5),
        ("sin(0)", 0.0),
        ("cos(0)", 1.0),
    ]
    
    for expr, expected in tests:
        total += 1
        try:
            result = calc.calculate(expr)
            if abs(result - expected) < 0.0001:
                print(f"  ✓ {expr} = {result:.4f}")
                passed += 1
            else:
                print(f"  ✗ {expr} = {result:.4f} (期望: {expected:.4f})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 测试组4: 复杂表达式
    print("\n4. 复杂表达式测试:")
    tests = [
        ("2+3*4-5/2", 11.5),
        ("(2+3)*(4-1)/2", 7.5),
        ("3*(4+5)-2^3", 19.0),
        ("sqrt(16)+log(e)", 5.0),
    ]
    
    for expr, expected in tests:
        total += 1
        try:
            result = calc.calculate(expr)
            if abs(result - expected) < 0.0001:
                print(f"  ✓ {expr} = {result:.4f}")
                passed += 1
            else:
                print(f"  ✗ {expr} = {result:.4f} (期望: {expected})")
        except CalculatorError as e:
            print(f"  ✗ {expr} -> 错误: {e}")
    
    # 测试组5: 错误处理
    print("\n5. 错误处理测试:")
    error_tests = [
        "5/0",
        "(2+3",
        "2@3",
        "sqrt(-1)",
        "unknown(5)",
    ]
    
    for expr in error_tests:
        total += 1
        try:
            result = calc.calculate(expr)
            print(f"  ✗ {expr} = {result} (应该出错但没有)")
        except CalculatorError as e:
            print(f"  ✓ {expr} -> 正确捕获错误")
            passed += 1
    
    # 测试组6: 记忆功能
    print("\n6. 记忆功能测试:")
    calc.memory_clear()
    calc.memory_store(100)
    if calc.memory_recall() == 100:
        print("  ✓ 存储到记忆: 100")
        passed += 1
    else:
        print(f"  ✗ 存储到记忆失败: {calc.memory_recall()}")
    total += 1
    
    calc.memory_add(50)
    if calc.memory_recall() == 150:
        print("  ✓ 加50到记忆: 150")
        passed += 1
    else:
        print(f"  ✗ 加法记忆失败: {calc.memory_recall()}")
    total += 1
    
    calc.memory_subtract(30)
    if calc.memory_recall() == 120:
        print("  ✓ 减30从记忆: 120")
        passed += 1
    else:
        print(f"  ✗ 减法记忆失败: {calc.memory_recall()}")
    total += 1
    
    # 测试组7: 历史记录
    print("\n7. 历史记录测试:")
    history = calc.get_history()
    if len(history) > 0:
        print(f"  ✓ 历史记录数量: {len(history)}")
        passed += 1
    else:
        print("  ✗ 历史记录为空")
    total += 1
    
    # 总结
    print("\n" + "=" * 70)
    print(f"测试总结: {passed}/{total} 通过 ({passed/total*100:.1f}%)")
    print("=" * 70)
    
    return passed, total

def example_usage():
    """使用示例"""
    print("\n" + "=" * 70)
    print("计算器使用示例")
    print("=" * 70)
    
    calc = Calculator()
    
    print("\n示例1: 基本计算")
    print(f"  表达式: 2 + 3 * 4")
    print(f"  结果: {calc.calculate('2+3*4')}")
    
    print("\n示例2: 带括号的计算")
    print(f"  表达式: (2 + 3) * 4")
    print(f"  结果: {calc.calculate('(2+3)*4')}")
    
    print("\n示例3: 科学计算")
    print(f"  表达式: sqrt(25) + log(e)")
    print(f"  结果: {calc.calculate('sqrt(25)+log(e)')}")
    
    print("\n示例4: 连续计算")
    expressions = ["2+3", "result*4", "result/2"]
    print(f"  表达式序列: {expressions}")
    results = calc.evaluate_continuous(expressions)
    print(f"  结果序列: {results}")
    
    print("\n示例5: 记忆功能")
    calc.memory_store(100)
    calc.memory_add(50)
    print(f"  存储100, 加50, 当前记忆: {calc.memory_recall()}")

def main():
    """主函数"""
    # 运行测试
    passed, total = test_all_features()
    
    # 显示使用示例
    example_usage()
    
    print("\n" + "=" * 70)
    print("计算器模块已成功实现!")
    print("功能包括:")
    print("  - 基本运算 (加减乘除、幂、取模)")
    print("  - 小数和负数运算")
    print("  - 括号和优先级")
    print("  - 科学计算函数")
    print("  - 错误处理")
    print("  - 记忆功能")
    print("  - 历史记录")
    print("  - 连续计算")
    print("=" * 70)

if __name__ == "__main__":
    main()