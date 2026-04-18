#!/usr/bin/env python3
"""
计算器单元测试
"""

import unittest
import math
from calculator import Calculator, CalculatorError


class TestCalculator(unittest.TestCase):
    """计算器测试类"""
    
    def setUp(self):
        """每个测试前初始化计算器"""
        self.calc = Calculator()
    
    def test_basic_operations(self):
        """测试基本运算"""
        # 加法
        self.assertAlmostEqual(self.calc.calculate("2 + 3"), 5.0)
        self.assertAlmostEqual(self.calc.calculate("2.5 + 3.5"), 6.0)
        
        # 减法
        self.assertAlmostEqual(self.calc.calculate("5 - 3"), 2.0)
        self.assertAlmostEqual(self.calc.calculate("3.5 - 1.2"), 2.3)
        
        # 乘法
        self.assertAlmostEqual(self.calc.calculate("2 * 3"), 6.0)
        self.assertAlmostEqual(self.calc.calculate("2.5 * 4"), 10.0)
        
        # 除法
        self.assertAlmostEqual(self.calc.calculate("6 / 2"), 3.0)
        self.assertAlmostEqual(self.calc.calculate("10 / 4"), 2.5)
    
    def test_division_by_zero(self):
        """测试除以零错误处理"""
        with self.assertRaises(CalculatorError):
            self.calc.calculate("5 / 0")
        
        with self.assertRaises(CalculatorError):
            self.calc.calculate("10 / (5 - 5)")
    
    def test_parentheses(self):
        """测试括号运算"""
        self.assertAlmostEqual(self.calc.calculate("(2 + 3) * 4"), 20.0)
        self.assertAlmostEqual(self.calc.calculate("2 * (3 + 4)"), 14.0)
        self.assertAlmostEqual(self.calc.calculate("(1 + 2) * (3 + 4)"), 21.0)
        self.assertAlmostEqual(self.calc.calculate("((1 + 2) * 3) + 4"), 13.0)
    
    def test_operator_precedence(self):
        """测试运算符优先级"""
        # 乘除优先于加减
        self.assertAlmostEqual(self.calc.calculate("2 + 3 * 4"), 14.0)  # 2 + 12 = 14
        self.assertAlmostEqual(self.calc.calculate("3 * 4 + 2"), 14.0)  # 12 + 2 = 14
        self.assertAlmostEqual(self.calc.calculate("10 - 4 / 2"), 8.0)  # 10 - 2 = 8
        self.assertAlmostEqual(self.calc.calculate("10 / 2 - 3"), 2.0)  # 5 - 3 = 2
    
    def test_decimal_numbers(self):
        """测试小数运算"""
        self.assertAlmostEqual(self.calc.calculate("0.1 + 0.2"), 0.3, places=10)
        self.assertAlmostEqual(self.calc.calculate("1.5 * 2.5"), 3.75)
        self.assertAlmostEqual(self.calc.calculate("10.5 / 2.5"), 4.2)
        self.assertAlmostEqual(self.calc.calculate("3.14159 * 2"), 6.28318, places=5)
    
    def test_negative_numbers(self):
        """测试负数运算"""
        self.assertAlmostEqual(self.calc.calculate("-5 + 3"), -2.0)
        self.assertAlmostEqual(self.calc.calculate("5 + (-3)"), 2.0)
        self.assertAlmostEqual(self.calc.calculate("-2 * 3"), -6.0)
        self.assertAlmostEqual(self.calc.calculate("2 * (-3)"), -6.0)
        self.assertAlmostEqual(self.calc.calculate("-10 / 2"), -5.0)
        self.assertAlmostEqual(self.calc.calculate("10 / (-2)"), -5.0)
    
    def test_power_operation(self):
        """测试幂运算"""
        self.assertAlmostEqual(self.calc.calculate("2 ^ 3"), 8.0)
        self.assertAlmostEqual(self.calc.calculate("4 ^ 0.5"), 2.0)  # 平方根
        self.assertAlmostEqual(self.calc.calculate("10 ^ 2"), 100.0)
    
    def test_modulo_operation(self):
        """测试取模运算"""
        self.assertAlmostEqual(self.calc.calculate("10 % 3"), 1.0)
        self.assertAlmostEqual(self.calc.calculate("15 % 4"), 3.0)
        self.assertAlmostEqual(self.calc.calculate("7.5 % 2"), 1.5)
    
    def test_complex_expressions(self):
        """测试复杂表达式"""
        self.assertAlmostEqual(self.calc.calculate("2 + 3 * 4 - 5 / 2"), 11.5)
        self.assertAlmostEqual(self.calc.calculate("(2 + 3) * (4 - 1) / 2"), 7.5)
        self.assertAlmostEqual(self.calc.calculate("3 * (4 + 5) - 2 ^ 3"), 19.0)
    
    def test_memory_functions(self):
        """测试记忆功能"""
        # 存储和召回
        self.calc.memory_store(42.5)
        self.assertAlmostEqual(self.calc.memory_recall(), 42.5)
        
        # 加法记忆
        self.calc.memory_clear()
        self.calc.memory_store(10)
        self.calc.memory_add(5)
        self.assertAlmostEqual(self.calc.memory_recall(), 15.0)
        
        # 减法记忆
        self.calc.memory_store(20)
        self.calc.memory_subtract(7)
        self.assertAlmostEqual(self.calc.memory_recall(), 13.0)
        
        # 清除记忆
        self.calc.memory_clear()
        self.assertAlmostEqual(self.calc.memory_recall(), 0.0)
    
    def test_last_result(self):
        """测试上一次结果功能"""
        result1 = self.calc.calculate("2 + 3")
        self.assertAlmostEqual(self.calc.get_last_result(), 5.0)
        
        result2 = self.calc.calculate("10 * 2")
        self.assertAlmostEqual(self.calc.get_last_result(), 20.0)
    
    def test_history(self):
        """测试历史记录功能"""
        # 清除历史
        self.calc.clear_history()
        
        # 执行计算
        self.calc.calculate("1 + 1")
        self.calc.calculate("2 * 2")
        self.calc.calculate("3 ^ 2")
        
        # 检查历史
        history = self.calc.get_history()
        self.assertEqual(len(history), 3)
        
        # 检查历史内容
        self.assertAlmostEqual(history[0][1], 2.0)  # 1 + 1 = 2
        self.assertAlmostEqual(history[1][1], 4.0)  # 2 * 2 = 4
        self.assertAlmostEqual(history[2][1], 9.0)  # 3 ^ 2 = 9
        
        # 测试历史限制
        limited_history = self.calc.get_history(2)
        self.assertEqual(len(limited_history), 2)
    
    def test_continuous_evaluation(self):
        """测试连续计算"""
        expressions = ["2 + 3", "5 * 4", "10 / 2", "3 ^ 2"]
        results = self.calc.evaluate_continuous(expressions)
        
        self.assertEqual(len(results), 4)
        self.assertAlmostEqual(results[0], 5.0)
        self.assertAlmostEqual(results[1], 20.0)
        self.assertAlmostEqual(results[2], 5.0)
        self.assertAlmostEqual(results[3], 9.0)
    
    def test_scientific_functions(self):
        """测试科学计算函数"""
        # 三角函数
        self.assertAlmostEqual(self.calc.scientific_calculate("sin", math.pi/2), 1.0, places=10)
        self.assertAlmostEqual(self.calc.scientific_calculate("cos", 0), 1.0, places=10)
        self.assertAlmostEqual(self.calc.scientific_calculate("tan", math.pi/4), 1.0, places=10)
        
        # 平方根
        self.assertAlmostEqual(self.calc.scientific_calculate("sqrt", 16), 4.0)
        self.assertAlmostEqual(self.calc.scientific_calculate("sqrt", 2.25), 1.5)
        
        # 对数和指数
        self.assertAlmostEqual(self.calc.scientific_calculate("log", math.e), 1.0, places=10)
        self.assertAlmostEqual(self.calc.scientific_calculate("log10", 100), 2.0)
        self.assertAlmostEqual(self.calc.scientific_calculate("exp", 1), math.e, places=10)
        
        # 绝对值
        self.assertAlmostEqual(self.calc.scientific_calculate("abs", -5.5), 5.5)
        self.assertAlmostEqual(self.calc.scientific_calculate("abs", 3.2), 3.2)
        
        # 取整函数
        self.assertAlmostEqual(self.calc.scientific_calculate("ceil", 3.2), 4.0)
        self.assertAlmostEqual(self.calc.scientific_calculate("floor", 3.8), 3.0)
        self.assertAlmostEqual(self.calc.scientific_calculate("round", 3.6), 4.0)
        
        # 常数
        self.assertAlmostEqual(self.calc.scientific_calculate("pi", 0), math.pi)
        self.assertAlmostEqual(self.calc.scientific_calculate("e", 0), math.e)
    
    def test_invalid_expressions(self):
        """测试无效表达式"""
        # 括号不匹配
        with self.assertRaises(CalculatorError):
            self.calc.calculate("(2 + 3")
        
        with self.assertRaises(CalculatorError):
            self.calc.calculate("2 + 3)")
        
        # 无效字符
        with self.assertRaises(CalculatorError):
            self.calc.calculate("2 @ 3")
        
        # 空表达式
        with self.assertRaises(CalculatorError):
            self.calc.calculate("")
        
        # 只有运算符
        with self.assertRaises(CalculatorError):
            self.calc.calculate("+")
        
        # 不支持的函数
        with self.assertRaises(CalculatorError):
            self.calc.scientific_calculate("unknown_func", 5)
    
    def test_expression_with_spaces(self):
        """测试带空格的表达式"""
        self.assertAlmostEqual(self.calc.calculate(" 2 + 3 "), 5.0)
        self.assertAlmostEqual(self.calc.calculate("2   +   3"), 5.0)
        self.assertAlmostEqual(self.calc.calculate("( 2 + 3 ) * 4"), 20.0)
    
    def test_implicit_multiplication(self):
        """测试隐式乘法（可选功能）"""
        # 注意：当前实现不支持隐式乘法，但可以添加
        # 例如：2(3+4) 应该等于 2*(3+4) = 14
        # 这里我们测试显式乘法
        self.assertAlmostEqual(self.calc.calculate("2*(3+4)"), 14.0)


class TestCalculatorEdgeCases(unittest.TestCase):
    """测试边界情况"""
    
    def setUp(self):
        self.calc = Calculator()
    
    def test_very_large_numbers(self):
        """测试非常大的数字"""
        result = self.calc.calculate("1000000 + 2000000")
        self.assertAlmostEqual(result, 3000000)
    
    def test_very_small_numbers(self):
        """测试非常小的数字"""
        result = self.calc.calculate("0.000001 + 0.000002")
        self.assertAlmostEqual(result, 0.000003, places=10)
    
    def test_nested_parentheses(self):
        """测试嵌套括号"""
        result = self.calc.calculate("((((1+2)*3)+4)*5)")
        self.assertAlmostEqual(result, 65.0)  # ((3*3+4)*5) = (13*5) = 65
    
    def test_multiple_negatives(self):
        """测试多个负号"""
        self.assertAlmostEqual(self.calc.calculate("--5"), 5.0)  # 负负得正
        self.assertAlmostEqual(self.calc.calculate("---5"), -5.0)  # 负负负得负
    
    def test_decimal_point_only(self):
        """测试只有小数点的数字"""
        self.assertAlmostEqual(self.calc.calculate("0.5 + 0.5"), 1.0)
        self.assertAlmostEqual(self.calc.calculate(".5 + .5"), 1.0)


def run_tests():
    """运行所有测试"""
    # 创建测试套件
    loader = unittest.TestLoader()
    suite = unittest.TestSuite()
    
    # 添加测试类
    suite.addTests(loader.loadTestsFromTestCase(TestCalculator))
    suite.addTests(loader.loadTestsFromTestCase(TestCalculatorEdgeCases))
    
    # 运行测试
    runner = unittest.TextTestRunner(verbosity=2)
    result = runner.run(suite)
    
    return result


if __name__ == "__main__":
    print("运行计算器单元测试...")
    result = run_tests()
    
    # 输出测试统计
    print(f"\n测试结果:")
    print(f"运行测试数: {result.testsRun}")
    print(f"失败数: {len(result.failures)}")
    print(f"错误数: {len(result.errors)}")
    
    if result.wasSuccessful():
        print("所有测试通过!")
    else:
        print("部分测试失败:")
        for test, traceback in result.failures + result.errors:
            print(f"\n{test}:")
            print(traceback)