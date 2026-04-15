#!/bin/bash

# 坦克大战游戏 - 直接运行脚本

JAVA_HOME_WIN="/mnt/c/Program Files/Java/jdk-26"
JAVA="$JAVA_HOME_WIN/bin/java.exe"

if [ ! -f "$JAVA" ]; then
    echo "❌ 错误: 找不到 java.exe"
    exit 1
fi

if [ ! -f "target/tank-battle-java.jar" ]; then
    echo "⚠️  警告: JAR 文件不存在，尝试编译..."
    ./compile.sh
fi

echo "🎮 启动坦克大战游戏..."
"$JAVA" -jar target/tank-battle-java.jar
