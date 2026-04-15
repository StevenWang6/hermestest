#!/bin/bash

# 快速测试脚本
echo "=== Java Tank Battle Game - Quick Test ==="
echo ""
echo "1. Checking Java..."
"/mnt/c/Program Files/Java/jdk-26/bin/java.exe" -version 2>&1 | head -1
echo ""
echo "2. Checking compilation..."
if [ -f "target/tank-battle-java.jar" ]; then
    echo "✅ JAR file exists: target/tank-battle-java.jar"
    echo "   Size: $(du -h target/tank-battle-java.jar | cut -f1)"
else
    echo "❌ JAR file not found"
    echo "   Run: ./compile.sh"
fi
echo ""
echo "3. Available scripts:"
echo "   ./compile.sh          - Compile the game"
echo "   ./run-game.sh         - Run the game (Chinese)"
echo "   ./run-game-english.sh - Run the game (English mode)"
echo "   ./quick-test.sh       - This test script"
echo ""
echo "4. To play the game:"
echo "   ./compile.sh && ./run-game-english.sh"
echo ""
echo "=== Test Complete ==="
