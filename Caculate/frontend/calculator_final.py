#!/usr/bin/env python3
"""
完整计算器GUI应用 - 支持高级数学函数和表达式计算
"""

import tkinter as tk
from tkinter import ttk, messagebox, scrolledtext
import sys
import os
import math
import re

# 添加后端路径
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'backend'))
try:
    from calculator import Calculator
    HAS_BACKEND = True
except ImportError:
    HAS_BACKEND = False
    # 创建一个本地的计算器类
    class Calculator:
        def __init__(self):
            self.functions = {
                'sin': math.sin,
                'cos': math.cos,
                'tan': math.tan,
                'sqrt': math.sqrt,
                'log': math.log10,
                'ln': math.log,
                'exp': math.exp,
                'abs': abs,
                'pi': math.pi,
                'e': math.e
            }
            
        def calculate(self, expression):
            try:
                # 预处理表达式
                expr = expression.lower().replace('^', '**')
                
                # 替换函数调用
                for func_name, func in self.functions.items():
                    if func_name in ['pi', 'e']:
                        # 替换常量
                        expr = expr.replace(func_name, str(func))
                    else:
                        # 替换函数调用
                        pattern = rf'{func_name}\(([^)]+)\)'
                        while True:
                            match = re.search(pattern, expr)
                            if not match:
                                break
                            arg = match.group(1)
                            try:
                                # 先计算参数
                                arg_value = eval(arg, {"__builtins__": {}}, self.functions)
                                result = func(arg_value)
                                expr = expr.replace(match.group(0), str(result))
                            except:
                                # 如果参数计算失败，保留原表达式
                                break
                
                # 计算表达式
                result = eval(expr, {"__builtins__": {}}, self.functions)
                return str(result)
                
            except Exception as e:
                raise ValueError(f"计算错误: {str(e)}")


class CalculatorApp:
    def __init__(self, root):
        self.root = root
        self.root.title("高级计算器")
        self.root.geometry("600x700")
        
        # 设置样式
        self.setup_styles()
        
        # 初始化计算器
        self.calculator = Calculator()
        
        # 创建界面
        self.create_widgets()
        
        # 初始化状态
        self.history = []
        
    def setup_styles(self):
        style = ttk.Style()
        style.configure('Title.TLabel', font=('Arial', 16, 'bold'))
        style.configure('Result.TEntry', font=('Arial', 18))
        style.configure('Function.TButton', font=('Arial', 10))
        
    def create_widgets(self):
        # 主框架
        main_frame = ttk.Frame(self.root, padding="10")
        main_frame.pack(fill=tk.BOTH, expand=True)
        
        # 标题
        title_label = ttk.Label(main_frame, text="高级计算器", style='Title.TLabel')
        title_label.pack(pady=10)
        
        # 输入区域
        input_frame = ttk.LabelFrame(main_frame, text="输入表达式", padding="10")
        input_frame.pack(fill=tk.X, pady=10)
        
        self.expression_var = tk.StringVar()
        expression_entry = ttk.Entry(input_frame, textvariable=self.expression_var, 
                                    font=('Arial', 14), width=50)
        expression_entry.pack(fill=tk.X, pady=5)
        expression_entry.bind('<Return>', lambda e: self.calculate())
        
        # 函数按钮区域
        functions_frame = ttk.LabelFrame(main_frame, text="数学函数", padding="10")
        functions_frame.pack(fill=tk.X, pady=10)
        
        functions = [
            ('sin()', 'sin'), ('cos()', 'cos'), ('tan()', 'tan'),
            ('sqrt()', 'sqrt'), ('log()', 'log'), ('ln()', 'ln'),
            ('exp()', 'exp'), ('abs()', 'abs'), ('π', 'pi'),
            ('e', 'e'), ('^', '^'), ('(', '('), (')', ')')
        ]
        
        func_buttons_frame = ttk.Frame(functions_frame)
        func_buttons_frame.pack()
        
        for i, (text, func) in enumerate(functions):
            row = i // 6
            col = i % 6
            btn = ttk.Button(func_buttons_frame, text=text, width=8,
                            command=lambda f=func: self.insert_function(f),
                            style='Function.TButton')
            btn.grid(row=row, column=col, padx=2, pady=2)
        
        # 数字按钮区域
        numbers_frame = ttk.LabelFrame(main_frame, text="数字和运算符", padding="10")
        numbers_frame.pack(fill=tk.X, pady=10)
        
        numbers = [
            '7', '8', '9', '/',
            '4', '5', '6', '*',
            '1', '2', '3', '-',
            '0', '.', '=', '+'
        ]
        
        num_buttons_frame = ttk.Frame(numbers_frame)
        num_buttons_frame.pack()
        
        for i, num in enumerate(numbers):
            row = i // 4
            col = i % 4
            if num == '=':
                btn = ttk.Button(num_buttons_frame, text=num, width=8,
                                command=self.calculate)
            else:
                btn = ttk.Button(num_buttons_frame, text=num, width=8,
                                command=lambda n=num: self.insert_text(n))
            btn.grid(row=row, column=col, padx=2, pady=2)
        
        # 控制按钮
        control_frame = ttk.Frame(main_frame)
        control_frame.pack(fill=tk.X, pady=10)
        
        calc_button = ttk.Button(control_frame, text="计算", command=self.calculate)
        calc_button.pack(side=tk.LEFT, padx=5)
        
        clear_button = ttk.Button(control_frame, text="清空", command=self.clear)
        clear_button.pack(side=tk.LEFT, padx=5)
        
        backspace_button = ttk.Button(control_frame, text="退格", command=self.backspace)
        backspace_button.pack(side=tk.LEFT, padx=5)
        
        # 结果显示区域
        result_frame = ttk.LabelFrame(main_frame, text="计算结果", padding="10")
        result_frame.pack(fill=tk.X, pady=10)
        
        self.result_var = tk.StringVar()
        result_entry = ttk.Entry(result_frame, textvariable=self.result_var,
                                font=('Arial', 16), state='readonly', style='Result.TEntry')
        result_entry.pack(fill=tk.X)
        
        # 历史记录区域
        history_frame = ttk.LabelFrame(main_frame, text="计算历史", padding="10")
        history_frame.pack(fill=tk.BOTH, expand=True, pady=10)
        
        self.history_text = scrolledtext.ScrolledText(history_frame, height=8,
                                                     font=('Arial', 10))
        self.history_text.pack(fill=tk.BOTH, expand=True)
        
        # 状态栏
        status_frame = ttk.Frame(main_frame)
        status_frame.pack(fill=tk.X, pady=5)
        
        self.status_var = tk.StringVar(value="就绪")
        status_label = ttk.Label(status_frame, textvariable=self.status_var)
        status_label.pack(side=tk.LEFT)
        
        backend_status = "后端连接: " + ("正常" if HAS_BACKEND else "使用本地计算")
        backend_label = ttk.Label(status_frame, text=backend_status)
        backend_label.pack(side=tk.RIGHT)
        
    def insert_text(self, text):
        current = self.expression_var.get()
        self.expression_var.set(current + text)
        
    def insert_function(self, func):
        current = self.expression_var.get()
        if func in ['sin', 'cos', 'tan', 'sqrt', 'log', 'ln', 'exp', 'abs']:
            self.expression_var.set(current + func + '()')
            # 将光标移动到括号内
            entry = self.root.focus_get()
            if isinstance(entry, tk.Entry):
                entry.icursor(tk.END)
                entry.xview(tk.END)
        elif func == '^':
            self.expression_var.set(current + '^')
        elif func == '(':
            self.expression_var.set(current + '(')
        elif func == ')':
            self.expression_var.set(current + ')')
        elif func in ['pi', 'e']:
            self.expression_var.set(current + func)
            
    def calculate(self):
        expression = self.expression_var.get().strip()
        if not expression:
            messagebox.showwarning("警告", "请输入表达式")
            return
            
        try:
            result = self.calculator.calculate(expression)
            self.result_var.set(result)
            
            # 添加到历史记录
            history_entry = f"{expression} = {result}\n"
            self.history_text.insert(tk.END, history_entry)
            self.history_text.see(tk.END)
            
            self.history.append((expression, result))
            
            self.status_var.set(f"计算成功: {expression} = {result}")
            
        except Exception as e:
            messagebox.showerror("错误", f"计算失败: {str(e)}")
            self.status_var.set(f"计算失败: {str(e)}")
            
    def clear(self):
        self.expression_var.set("")
        self.result_var.set("")
        self.status_var.set("已清空")
        
    def backspace(self):
        current = self.expression_var.get()
        if current:
            self.expression_var.set(current[:-1])
            self.status_var.set("已删除最后一个字符")


def main():
    root = tk.Tk()
    app = CalculatorApp(root)
    root.mainloop()


if __name__ == "__main__":
    main()