#!/bin/bash

# 坦克大战游戏 - 直接编译脚本
# 当 Maven 不可用时使用此脚本

echo "🎮 编译 Java 坦克大战游戏..."

# 设置 Java 路径
JAVA_HOME_WIN="/mnt/c/Program Files/Java/jdk-26"
JAVAC="$JAVA_HOME_WIN/bin/javac.exe"
JAVA="$JAVA_HOME_WIN/bin/java.exe"
JAR="$JAVA_HOME_WIN/bin/jar.exe"

# 检查 Java
if [ ! -f "$JAVAC" ]; then
    echo "❌ 错误: 找不到 javac.exe"
    echo "请确保 Java JDK 已安装在 Windows 中"
    exit 1
fi

# 创建输出目录
mkdir -p target/classes
mkdir -p target/META-INF

# 编译所有 Java 文件
echo "🔨 编译源代码..."
find src/main/java -name "*.java" > sources.txt 2>/dev/null

"$JAVAC" -d target/classes -cp "target/classes" @sources.txt

if [ $? -eq 0 ]; then
    echo "✅ 编译成功!"
    
    # 创建可运行的 JAR
    echo "📦 创建 JAR 文件..."
    
    # 创建 manifest
    echo "Main-Class: com.tankbattle.TankBattleGame" > target/META-INF/MANIFEST.MF
    echo "Class-Path: ." >> target/META-INF/MANIFEST.MF
    
    # 创建 JAR
    cd target/classes
    "$JAR" cfm ../tank-battle-java.jar ../META-INF/MANIFEST.MF com/
    cd ../..
    
    if [ -f "target/tank-battle-java.jar" ]; then
        echo "✅ JAR 文件创建成功: target/tank-battle-java.jar"
        echo ""
        echo "🚀 运行游戏:"
        echo "  ./run-game.sh"
        echo "  或"
        echo "  $JAVA -jar target/tank-battle-java.jar"
    else
        echo "❌ JAR 文件创建失败"
    fi
else
    echo "❌ 编译失败"
    exit 1
fi
