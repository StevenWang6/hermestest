# Caculate 项目

一个完整的计算器项目，包含后端算法和前端GUI界面。

## 项目结构

```
Caculate/
├── backend/                 # 后端计算引擎
│   ├── calculator.py       # 主计算器模块（使用Shunting-yard算法）
│   └── calculator_demo.py  # 演示脚本
├── frontend/               # 前端GUI界面
│   ├── calculator_final.py # 高级计算器GUI
│   └── simple_calculator.py # 简单计算器GUI
├── tests/                  # 测试文件
│   ├── calculator_final_test.py    # 后端集成测试
│   ├── test_calculator_app.py      # GUI应用测试
│   └── test_calculator_unit.py     # 后端单元测试
└── docs/                   # 文档
    └── README_calculator.md # 详细文档
```

## 功能特性

### 后端计算引擎
- **表达式解析**: 支持复杂数学表达式
- **运算符优先级**: 正确处理运算符优先级
- **函数支持**: sin, cos, tan, sqrt, log, ln, exp, abs
- **常量支持**: π (pi), e
- **错误处理**: 完善的错误检测和报告

### 前端GUI界面
1. **简单计算器** (`simple_calculator.py`)
   - 基本表达式输入
   - 历史记录功能
   - 示例表达式快速输入

2. **高级计算器** (`calculator_final.py`)
   - 数学函数按钮
   - 数字键盘
   - 实时历史记录
   - 状态显示

## 快速开始

### 安装依赖
```bash
# 安装Python Tkinter（GUI依赖）
sudo apt-get install python3-tk

# 安装Python依赖（如果需要）
pip install -r requirements.txt
```

### 运行简单计算器
```bash
cd Caculate/frontend
python3 simple_calculator.py
```

### 运行高级计算器
```bash
cd Caculate/frontend
python3 calculator_final.py
```

### 运行测试
```bash
cd Caculate/tests

# 运行单元测试
python3 test_calculator_unit.py

# 运行集成测试
python3 calculator_final_test.py

# 运行GUI测试（需要显示环境）
python3 test_calculator_app.py
```

## 使用示例

### 后端使用
```python
from backend.calculator import Calculator

calc = Calculator()
result = calc.calculate("3 + 4 * 2")  # 返回 "11"
result = calc.calculate("sin(30)")    # 返回正弦值
result = calc.calculate("sqrt(16)")   # 返回 "4"
```

### 支持的表达式
- 基本运算: `3 + 4 * 2 / (1 - 5)`
- 幂运算: `2 ^ 3` 或 `2 ** 3`
- 函数调用: `sin(30)`, `cos(45)`, `sqrt(16)`
- 常量: `pi`, `e`
- 复杂表达式: `sin(pi/2) + cos(0) * sqrt(9)`

## 算法说明

### Shunting-yard算法
后端计算器使用经典的Shunting-yard算法处理表达式：
1. 将中缀表达式转换为后缀表达式（逆波兰表示法）
2. 使用栈计算后缀表达式
3. 支持运算符优先级和括号

### 错误处理
- 语法错误检测
- 除零错误处理
- 无效函数参数检测
- 括号匹配检查

## 开发指南

### 添加新函数
1. 在 `backend/calculator.py` 的 `functions` 字典中添加函数
2. 在 `frontend/calculator_final.py` 的函数按钮区域添加按钮
3. 更新测试用例

### 扩展GUI功能
1. 修改 `frontend/calculator_final.py` 的界面布局
2. 添加新的功能按钮
3. 实现相应的事件处理函数

## 测试覆盖

- **单元测试**: 测试后端计算器的各个函数
- **集成测试**: 测试整个计算流程
- **GUI测试**: 测试用户界面功能

## 许可证

本项目使用 MIT 许可证。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目。

## 作者

StevenWang6 - 项目创建者和维护者