#!/usr/bin/env python3
"""
高级计算器后端算法
支持基本运算、括号、优先级、小数、错误处理、连续运算和记忆功能
"""

import re
import math
from typing import List, Union, Optional, Dict, Tuple


class CalculatorError(Exception):
    """计算器异常基类"""
    pass


class Calculator:
    """
    高级计算器类
    支持表达式解析、基本运算、括号、优先级、小数运算、错误处理、记忆功能
    """
    
    def __init__(self):
        """初始化计算器"""
        self.memory = 0.0  # 记忆值
        self.last_result = 0.0  # 上一次计算结果
        self.history = []  # 计算历史
        
        # 运算符优先级字典
        self.operators = {
            '+': (1, lambda x, y: x + y),
            '-': (1, lambda x, y: x - y),
            '*': (2, lambda x, y: x * y),
            '/': (2, lambda x, y: x / y if y != 0 else self._handle_division_by_zero()),
            '^': (3, lambda x, y: x ** y),  # 幂运算
            '%': (2, lambda x, y: x % y if y != 0 else self._handle_division_by_zero()),  # 取模
        }
        
        # 科学计算函数
        self.functions = {
            'sin': math.sin,
            'cos': math.cos,
            'tan': math.tan,
            'asin': math.asin,
            'acos': math.acos,
            'atan': math.atan,
            'sqrt': math.sqrt,
            'log': math.log,
            'log10': math.log10,
            'exp': math.exp,
            'abs': abs,
            'ceil': math.ceil,
            'floor': math.floor,
            'round': round,
            'pi': lambda: math.pi,
            'e': lambda: math.e,
        }
    
    def _handle_division_by_zero(self) -> None:
        """处理除以零错误"""
        raise CalculatorError("错误：除以零")
    
    def _tokenize(self, expression: str) -> List[str]:
        """
        将表达式分词为标记列表
        
        Args:
            expression: 数学表达式字符串
            
        Returns:
            标记列表
        """
        # 移除空格
        expression = expression.replace(' ', '')
        
        # 使用正则表达式匹配数字、运算符、括号、函数名
        # 改进的正则表达式，支持科学计数法、单独的小数点和函数调用
        token_pattern = r'''
            \d+\.?\d*(?:[eE][+-]?\d+)?|  # 数字（整数、小数、科学计数法）
            \.\d+(?:[eE][+-]?\d+)?|      # 以小数点开头的数字（如 .5）
            [a-zA-Z_][a-zA-Z0-9_]*|      # 函数名或变量名
            [+\-*/^%()]                  # 运算符和括号
        '''
        
        tokens = re.findall(token_pattern, expression, re.VERBOSE)
        # 过滤掉空字符串
        tokens = [token for token in tokens if token]
        
        # 处理负号和减号的歧义
        processed_tokens = []
        for i, token in enumerate(tokens):
            if token == '-':
                # 如果减号是第一个标记，或者前一个标记是运算符或左括号，那么它是负号
                if i == 0 or tokens[i-1] in '+-*/^%(':
                    processed_tokens.append('~')  # 使用 ~ 表示负号
                else:
                    processed_tokens.append('-')
            else:
                processed_tokens.append(token)
        
        return processed_tokens
    
    def _is_number(self, token: str) -> bool:
        """检查标记是否为数字"""
        try:
            float(token)
            return True
        except ValueError:
            return False
    
    def _get_operator_precedence(self, operator: str) -> int:
        """获取运算符优先级"""
        if operator == '~':
            return 4  # 负号的优先级最高
        elif operator in self.operators:
            return self.operators[operator][0]
        else:
            return 0
    
    def _shunting_yard(self, tokens: List[str]) -> List[str]:
        """
        使用调度场算法将中缀表达式转换为后缀表达式（逆波兰表示法）
        
        Args:
            tokens: 中缀标记列表
            
        Returns:
            后缀表达式标记列表
        """
        output = []
        stack = []
        
        for token in tokens:
            if self._is_number(token):
                output.append(token)
            elif token in self.functions:
                stack.append(token)
            elif token == '(':
                stack.append(token)
            elif token == ')':
                while stack and stack[-1] != '(':
                    output.append(stack.pop())
                if not stack:
                    raise CalculatorError("错误：括号不匹配")
                stack.pop()  # 弹出左括号
                if stack and stack[-1] in self.functions:
                    output.append(stack.pop())  # 弹出函数
            elif token in self.operators or token == '~':
                # 处理负号运算符
                if token == '~':
                    token_precedence = 4  # 负号的优先级最高
                else:
                    token_precedence = self.operators[token][0]
                
                while (stack and stack[-1] != '(' and 
                       (stack[-1] in self.operators or stack[-1] == '~') and
                       self._get_operator_precedence(stack[-1]) >= token_precedence):
                    output.append(stack.pop())
                stack.append(token)
            else:
                raise CalculatorError(f"错误：无法识别的标记 '{token}'")
        
        # 将栈中剩余的运算符弹出
        while stack:
            if stack[-1] == '(':
                raise CalculatorError("错误：括号不匹配")
            output.append(stack.pop())
        
        return output
    
    def _evaluate_rpn(self, rpn_tokens: List[str]) -> float:
        """
        计算后缀表达式（逆波兰表示法）
        
        Args:
            rpn_tokens: 后缀表达式标记列表
            
        Returns:
            计算结果
        """
        stack = []
        
        for token in rpn_tokens:
            if self._is_number(token):
                stack.append(float(token))
            elif token == '~':  # 负号
                if not stack:
                    raise CalculatorError("错误：表达式无效")
                operand = stack.pop()
                stack.append(-operand)
            elif token in self.operators:
                if len(stack) < 2:
                    raise CalculatorError("错误：表达式无效")
                b = stack.pop()
                a = stack.pop()
                try:
                    result = self.operators[token][1](a, b)
                    stack.append(result)
                except ZeroDivisionError:
                    raise CalculatorError("错误：除以零")
                except Exception as e:
                    raise CalculatorError(f"计算错误: {str(e)}")
            elif token in self.functions:
                # 处理函数调用
                if token.endswith('('):
                    func_name = token[:-1]
                else:
                    func_name = token
                
                if func_name in ['pi', 'e']:
                    # 常数函数
                    result = self.functions[func_name]()
                    stack.append(result)
                else:
                    # 需要参数的单参数函数
                    if not stack:
                        raise CalculatorError(f"错误：函数 {func_name} 缺少参数")
                    operand = stack.pop()
                    try:
                        result = self.functions[func_name](operand)
                        stack.append(result)
                    except ValueError as e:
                        raise CalculatorError(f"数学错误: {str(e)}")
                    except Exception as e:
                        raise CalculatorError(f"函数计算错误: {str(e)}")
            else:
                raise CalculatorError(f"错误：无法识别的标记 '{token}'")
        
        if len(stack) != 1:
            raise CalculatorError("错误：表达式无效")
        
        return stack[0]
    
    def _preprocess_expression(self, expression: str) -> str:
        """
        预处理表达式，处理函数调用等
        
        Args:
            expression: 原始表达式
            
        Returns:
            预处理后的表达式
        """
        # 移除空格
        expression = expression.replace(' ', '')
        
        # 处理函数调用：将 func(arg) 转换为 func(arg)
        # 这里我们只需要确保函数名后面有括号
        # 实际的分词和解析会在后续步骤中处理
        
        return expression
    
    def calculate(self, expression: str) -> float:
        """
        计算数学表达式
        
        Args:
            expression: 数学表达式字符串
            
        Returns:
            计算结果
            
        Raises:
            CalculatorError: 如果表达式无效或计算出错
        """
        try:
            # 预处理：将函数调用转换为标准格式
            expression = self._preprocess_expression(expression)
            
            # 分词
            tokens = self._tokenize(expression)
            
            # 转换为后缀表达式
            rpn_tokens = self._shunting_yard(tokens)
            
            # 计算后缀表达式
            result = self._evaluate_rpn(rpn_tokens)
            
            # 保存结果和历史
            self.last_result = result
            self.history.append((expression, result))
            
            # 限制历史记录长度
            if len(self.history) > 100:
                self.history = self.history[-100:]
            
            return result
            
        except CalculatorError as e:
            raise e
        except Exception as e:
            raise CalculatorError(f"计算错误: {str(e)}")
    
    def memory_store(self, value: float) -> None:
        """存储值到记忆"""
        self.memory = value
    
    def memory_recall(self) -> float:
        """从记忆召回值"""
        return self.memory
    
    def memory_add(self, value: float) -> None:
        """将值加到记忆"""
        self.memory += value
    
    def memory_subtract(self, value: float) -> None:
        """从记忆减去值"""
        self.memory -= value
    
    def memory_clear(self) -> None:
        """清除记忆"""
        self.memory = 0.0
    
    def get_last_result(self) -> float:
        """获取上一次计算结果"""
        return self.last_result
    
    def get_history(self, limit: int = 10) -> List[Tuple[str, float]]:
        """
        获取计算历史
        
        Args:
            limit: 返回的历史记录数量限制
            
        Returns:
            计算历史列表，每个元素为 (表达式, 结果) 元组
        """
        return self.history[-limit:] if limit > 0 else self.history
    
    def clear_history(self) -> None:
        """清除计算历史"""
        self.history = []
    
    def evaluate_continuous(self, expressions: List[str]) -> List[float]:
        """
        连续计算多个表达式
        
        Args:
            expressions: 表达式字符串列表
            
        Returns:
            计算结果列表
        """
        results = []
        for expr in expressions:
            try:
                result = self.calculate(expr)
                results.append(result)
            except CalculatorError as e:
                results.append(float('nan'))  # 使用 NaN 表示错误
                print(f"表达式 '{expr}' 计算失败: {str(e)}")
        return results
    
    def scientific_calculate(self, func_name: str, value: float) -> float:
        """
        执行科学计算
        
        Args:
            func_name: 函数名
            value: 参数值
            
        Returns:
            计算结果
        """
        if func_name not in self.functions:
            raise CalculatorError(f"错误：不支持的科学函数 '{func_name}'")
        
        try:
            if func_name in ['pi', 'e']:
                return self.functions[func_name]()
            else:
                return self.functions[func_name](value)
        except ValueError as e:
            raise CalculatorError(f"数学错误: {str(e)}")
        except Exception as e:
            raise CalculatorError(f"科学计算错误: {str(e)}")


def main():
    """主函数，用于演示计算器功能"""
    calc = Calculator()
    
    print("=== 高级计算器演示 ===")
    print("支持：加减乘除、括号、优先级、小数、科学函数")
    print("科学函数：sin, cos, tan, sqrt, log, exp, abs 等")
    print("常数：pi, e")
    print("输入 'quit' 退出")
    print("输入 'history' 查看历史")
    print("输入 'memory' 查看记忆操作")
    print("=" * 30)
    
    while True:
        try:
            user_input = input("\n输入表达式: ").strip()
            
            if user_input.lower() == 'quit':
                break
            elif user_input.lower() == 'history':
                history = calc.get_history()
                if history:
                    print("计算历史:")
                    for i, (expr, result) in enumerate(history, 1):
                        print(f"{i:3d}. {expr} = {result}")
                else:
                    print("历史记录为空")
                continue
            elif user_input.lower() == 'memory':
                print(f"当前记忆值: {calc.memory_recall()}")
                print("可用命令: M+ (加), M- (减), MC (清除), MR (召回), MS (存储)")
                cmd = input("记忆操作: ").strip().upper()
                if cmd == 'M+':
                    value = float(input("输入要加的值: "))
                    calc.memory_add(value)
                    print(f"已加到记忆: {calc.memory_recall()}")
                elif cmd == 'M-':
                    value = float(input("输入要减的值: "))
                    calc.memory_subtract(value)
                    print(f"已从记忆减去: {calc.memory_recall()}")
                elif cmd == 'MC':
                    calc.memory_clear()
                    print("记忆已清除")
                elif cmd == 'MR':
                    print(f"记忆值: {calc.memory_recall()}")
                elif cmd == 'MS':
                    value = float(input("输入要存储的值: "))
                    calc.memory_store(value)
                    print(f"值已存储到记忆: {calc.memory_recall()}")
                continue
            
            # 计算表达式
            result = calc.calculate(user_input)
            print(f"结果: {result}")
            
        except CalculatorError as e:
            print(f"错误: {str(e)}")
        except KeyboardInterrupt:
            print("\n\n退出计算器")
            break
        except Exception as e:
            print(f"未知错误: {str(e)}")


if __name__ == "__main__":
    main()