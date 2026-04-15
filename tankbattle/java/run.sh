#!/bin/bash

# Java 坦克大战游戏运行脚本
# 作者: StevenWang6
# 版本: 1.0.0

echo "🎮 Java 坦克大战游戏启动器"
echo "=========================="

# 检查 Java 是否安装
if ! command -v java &> /dev/null; then
    echo "❌ 错误: Java 未安装"
    echo "请安装 Java 11 或更高版本"
    exit 1
fi

# 检查 Maven 是否安装
if ! command -v mvn &> /dev/null; then
    echo "⚠️  警告: Maven 未安装，尝试直接运行..."
    
    # 检查是否已编译
    if [ ! -f "target/tank-battle-java-1.0.0.jar" ]; then
        echo "❌ 错误: 未找到编译的 JAR 文件"
        echo "请先运行: mvn clean package"
        exit 1
    fi
    
    # 直接运行 JAR
    echo "🚀 启动游戏..."
    java -jar target/tank-battle-java-1.0.0.jar
    exit 0
fi

# 显示菜单
echo ""
echo "请选择操作:"
echo "1. 编译并运行游戏"
echo "2. 仅运行游戏（如果已编译）"
echo "3. 运行测试"
echo "4. 清理并重新编译"
echo "5. 显示帮助"
echo "6. 退出"
echo ""

read -p "请输入选择 (1-6): " choice

case $choice in
    1)
        echo "🔨 编译项目..."
        mvn clean compile
        if [ $? -eq 0 ]; then
            echo "✅ 编译成功"
            echo "🚀 启动游戏..."
            mvn exec:java
        else
            echo "❌ 编译失败"
            exit 1
        fi
        ;;
    2)
        echo "🚀 启动游戏..."
        mvn exec:java
        ;;
    3)
        echo "🧪 运行测试..."
        mvn test
        ;;
    4)
        echo "🧹 清理项目..."
        mvn clean
        echo "🔨 重新编译..."
        mvn compile
        if [ $? -eq 0 ]; then
            echo "✅ 编译成功"
        else
            echo "❌ 编译失败"
        fi
        ;;
    5)
        echo "=== 帮助信息 ==="
        echo ""
        echo "游戏控制键:"
        echo "  W/A/S/D - 移动坦克"
        echo "  空格键   - 发射炮弹"
        echo "  P       - 暂停/继续游戏"
        echo "  T       - 开关设置菜单"
        echo "  ESC     - 退出游戏"
        echo ""
        echo "使能开关:"
        echo "  在游戏中按 T 进入开关菜单"
        echo "  使用数字键 1-5 切换开关状态"
        echo "  开关包括: 音效、碰撞检测、AI、道具系统、调试模式"
        echo ""
        echo "项目结构:"
        echo "  src/main/java/ - Java 源代码"
        echo "  src/test/java/ - 测试代码"
        echo "  pom.xml       - Maven 配置文件"
        echo ""
        echo "常用命令:"
        echo "  ./run.sh      - 运行此脚本"
        echo "  mvn compile   - 编译项目"
        echo "  mvn test      - 运行测试"
        echo "  mvn package   - 打包为 JAR"
        echo "  mvn clean     - 清理项目"
        ;;
    6)
        echo "👋 再见！"
        exit 0
        ;;
    *)
        echo "❌ 无效的选择"
        exit 1
        ;;
esac
