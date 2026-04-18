#!/usr/bin/env python3
"""
计算器GUI应用测试
"""

import unittest
import tkinter as tk
from tkinter import ttk
import sys
import os

# 添加路径以便导入
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'frontend'))

try:
    from simple_calculator import SimpleCalculator
    from calculator_final import CalculatorApp
    IMPORT_SUCCESS = True
except ImportError as e:
    print(f"导入失败: {e}")
    IMPORT_SUCCESS = False


class TestCalculatorGUI(unittest.TestCase):
    """测试计算器GUI应用"""
    
    @classmethod
    def setUpClass(cls):
        """设置测试环境"""
        if not IMPORT_SUCCESS:
            raise unittest.SkipTest("无法导入GUI模块")
            
        cls.root = tk.Tk()
        cls.root.withdraw()  # 隐藏主窗口
        
    @classmethod
    def tearDownClass(cls):
        """清理测试环境"""
        if hasattr(cls, 'root'):
            cls.root.destroy()
    
    def test_simple_calculator_creation(self):
        """测试简单计算器创建"""
        app = SimpleCalculator(self.root)
        self.assertIsNotNone(app)
        self.assertIsInstance(app, SimpleCalculator)
        
        # 检查必要的组件
        self.assertTrue(hasattr(app, 'input_entry'))
        self.assertTrue(hasattr(app, 'result_var'))
        self.assertTrue(hasattr(app, 'calculator'))
        
    def test_calculator_app_creation(self):
        """测试高级计算器创建"""
        app = CalculatorApp(self.root)
        self.assertIsNotNone(app)
        self.assertIsInstance(app, CalculatorApp)
        
        # 检查必要的组件
        self.assertTrue(hasattr(app, 'expression_var'))
        self.assertTrue(hasattr(app, 'result_var'))
        self.assertTrue(hasattr(app, 'calculator'))
        
    def test_widget_types(self):
        """测试组件类型"""
        app = SimpleCalculator(self.root)
        
        # 检查组件类型
        self.assertIsInstance(app.input_entry, ttk.Entry)
        self.assertIsInstance(app.result_var, tk.StringVar)
        
    def test_clear_function(self):
        """测试清空功能"""
        app = SimpleCalculator(self.root)
        
        # 设置一些文本
        app.input_entry.insert(0, "3 + 4")
        app.result_var.set("7")
        
        # 执行清空
        app.clear()
        
        # 验证清空结果
        self.assertEqual(app.input_entry.get(), "")
        self.assertEqual(app.result_var.get(), "")
        
    def test_insert_function(self):
        """测试插入函数功能（高级计算器）"""
        app = CalculatorApp(self.root)
        
        # 测试插入数字
        app.insert_text("5")
        self.assertEqual(app.expression_var.get(), "5")
        
        # 测试插入运算符
        app.insert_text("+")
        self.assertEqual(app.expression_var.get(), "5+")
        
        # 测试插入函数
        app.insert_function("sin")
        self.assertEqual(app.expression_var.get(), "5+sin()")


class TestCalculatorIntegration(unittest.TestCase):
    """测试计算器集成功能"""
    
    def test_backend_import(self):
        """测试后端导入"""
        try:
            sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'backend'))
            from calculator import Calculator
            calc = Calculator()
            self.assertIsNotNone(calc)
            self.assertTrue(hasattr(calc, 'calculate'))
        except ImportError:
            self.skipTest("后端计算器模块不可用")
    
    def test_calculation_flow(self):
        """测试计算流程"""
        # 这里可以添加更复杂的集成测试
        pass


def run_gui_tests():
    """运行GUI测试"""
    print("=" * 60)
    print("计算器GUI应用测试")
    print("=" * 60)
    
    # 创建测试套件
    loader = unittest.TestLoader()
    suite = unittest.TestSuite()
    
    # 添加测试类
    suite.addTests(loader.loadTestsFromTestCase(TestCalculatorGUI))
    suite.addTests(loader.loadTestsFromTestCase(TestCalculatorIntegration))
    
    # 运行测试
    runner = unittest.TextTestRunner(verbosity=2)
    result = runner.run(suite)
    
    return result.wasSuccessful()


if __name__ == "__main__":
    success = run_gui_tests()
    sys.exit(0 if success else 1)