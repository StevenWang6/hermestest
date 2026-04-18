#!/usr/bin/env python3
"""
Caculate项目安装脚本
"""

from setuptools import setup, find_packages

with open("README.md", "r", encoding="utf-8") as fh:
    long_description = fh.read()

setup(
    name="caculate-project",
    version="1.0.0",
    author="StevenWang6",
    author_email="lhwl6@163.com",
    description="一个完整的计算器项目，包含后端算法和前端GUI界面",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="https://github.com/StevenWang6/hermestest/tree/main/Caculate",
    packages=find_packages(),
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
        "Topic :: Scientific/Engineering :: Mathematics",
        "Topic :: Software Development :: Libraries :: Python Modules",
        "Topic :: Utilities",
    ],
    python_requires=">=3.8",
    install_requires=[
        # 基础依赖 - 大多数Python标准库已包含
    ],
    extras_require={
        "gui": [
            # GUI依赖通常通过系统包管理器安装
        ],
        "dev": [
            "pytest>=7.0.0",
            "pytest-cov>=4.0.0",
            "black>=22.0.0",
            "flake8>=5.0.0",
            "mypy>=0.991",
        ],
        "test": [
            "pytest>=7.0.0",
            "pytest-cov>=4.0.0",
        ],
    },
    entry_points={
        "console_scripts": [
            "caculate-demo=backend.calculator_demo:main",
            "caculate-simple=frontend.simple_calculator:main",
            "caculate-advanced=frontend.calculator_final:main",
        ],
    },
    package_data={
        "": ["*.md", "*.txt"],
    },
)