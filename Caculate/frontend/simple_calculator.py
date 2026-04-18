#!/usr/bin/env python3
"""
简单计算器GUI - 使用Tkinter
"""

import tkinter as tk
from tkinter import ttk, messagebox
import sys
import os

# 添加后端路径
sys.path.append(os.path.join(os.path.dirname(__file__), '..', 'backend'))
try:
    from calculator import Calculator
except ImportError:
    # 如果导入失败，创建一个简单的计算器类
    class Calculator:
        def calculate(self, expression):
            try:
                # 简单的表达式计算
                return str(eval(expression))
            except Exception as e:
                raise ValueError(f"计算错误: {e}")


class SimpleCalculator:
    def __init__(self, root):
        self.root = root
        self.root.title("简单计算器")
        self.root.geometry("400x500")
        
        self.calculator = Calculator()
        
        # 创建界面
        self.create_widgets()
        
    def create_widgets(self):
        # 结果显示框
        self.result_var = tk.StringVar()
        result_frame = ttk.Frame(self.root, padding="10")
        result_frame.pack(fill=tk.X, padx=10, pady=10)
        
        result_label = ttk.Label(result_frame, text="结果:")
        result_label.pack(side=tk.LEFT)
        
        result_entry = ttk.Entry(result_frame, textvariable=self.result_var, 
                                font=('Arial', 14), state='readonly', width=30)
        result_entry.pack(side=tk.LEFT, padx=10)
        
        # 表达式输入框
        input_frame = ttk.Frame(self.root, padding="10")
        input_frame.pack(fill=tk.X, padx=10, pady=10)
        
        input_label = ttk.Label(input_frame, text="表达式:")
        input_label.pack(side=tk.LEFT)
        
        self.input_entry = ttk.Entry(input_frame, font=('Arial', 12), width=30)
        self.input_entry.pack(side=tk.LEFT, padx=10)
        self.input_entry.bind('<Return>', lambda e: self.calculate())
        
        # 计算按钮
        button_frame = ttk.Frame(self.root, padding="10")
        button_frame.pack(fill=tk.X, padx=10, pady=10)
        
        calc_button = ttk.Button(button_frame, text="计算", command=self.calculate)
        calc_button.pack(side=tk.LEFT, padx=5)
        
        clear_button = ttk.Button(button_frame, text="清空", command=self.clear)
        clear_button.pack(side=tk.LEFT, padx=5)
        
        # 示例表达式
        example_frame = ttk.LabelFrame(self.root, text="示例表达式", padding="10")
        example_frame.pack(fill=tk.X, padx=10, pady=10)
        
        examples = [
            "3 + 4 * 2",
            "(3 + 4) * 2",
            "10 / 2",
            "2 ^ 3",  # 幂运算
            "sqrt(16)"  # 平方根
        ]
        
        for example in examples:
            example_btn = ttk.Button(example_frame, text=example, 
                                    command=lambda e=example: self.set_expression(e))
            example_btn.pack(side=tk.LEFT, padx=5, pady=5)
        
        # 历史记录
        history_frame = ttk.LabelFrame(self.root, text="历史记录", padding="10")
        history_frame.pack(fill=tk.BOTH, expand=True, padx=10, pady=10)
        
        self.history_listbox = tk.Listbox(history_frame, height=8)
        self.history_listbox.pack(fill=tk.BOTH, expand=True)
        
        # 状态栏
        status_frame = ttk.Frame(self.root)
        status_frame.pack(fill=tk.X, padx=10, pady=5)
        
        self.status_var = tk.StringVar(value="就绪")
        status_label = ttk.Label(status_frame, textvariable=self.status_var)
        status_label.pack(side=tk.LEFT)
        
    def calculate(self):
        expression = self.input_entry.get().strip()
        if not expression:
            messagebox.showwarning("警告", "请输入表达式")
            return
            
        try:
            result = self.calculator.calculate(expression)
            self.result_var.set(result)
            
            # 添加到历史记录
            history_item = f"{expression} = {result}"
            self.history_listbox.insert(0, history_item)
            
            self.status_var.set(f"计算成功: {expression} = {result}")
            
        except Exception as e:
            messagebox.showerror("错误", f"计算失败: {str(e)}")
            self.status_var.set(f"计算失败: {str(e)}")
            
    def clear(self):
        self.input_entry.delete(0, tk.END)
        self.result_var.set("")
        self.status_var.set("已清空")
        
    def set_expression(self, expression):
        self.input_entry.delete(0, tk.END)
        self.input_entry.insert(0, expression)
        self.status_var.set(f"已设置表达式: {expression}")


def main():
    root = tk.Tk()
    app = SimpleCalculator(root)
    root.mainloop()


if __name__ == "__main__":
    main()