#!/bin/bash

# 坦克大战游戏 - 英文版运行脚本
# 避免中文字符编码问题

JAVA_HOME_WIN="/mnt/c/Program Files/Java/jdk-26"
JAVA="$JAVA_HOME_WIN/bin/java.exe"

if [ ! -f "$JAVA" ]; then
    echo "ERROR: java.exe not found"
    exit 1
fi

if [ ! -f "target/tank-battle-java.jar" ]; then
    echo "WARNING: JAR file not found, trying to compile..."
    ./compile.sh
fi

echo "Starting Tank Battle Game..."
echo "Note: Running in English mode to avoid encoding issues"

# 设置控制台编码为 UTF-8
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

"$JAVA" -Dfile.encoding=UTF-8 -jar target/tank-battle-java.jar
