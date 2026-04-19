#!/bin/bash

# Hermes Poll System 启动脚本
# 用法: ./run.sh [start|stop|restart|status|build]

APP_NAME="Hermes Poll System"
APP_JAR="target/poll-system-1.0.0.jar"
PID_FILE="poll-system.pid"
LOG_FILE="poll-system.log"

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_status() {
    echo -e "${BLUE}[${APP_NAME}]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[${APP_NAME}]${NC} $1"
}

print_error() {
    echo -e "${RED}[${APP_NAME}]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[${APP_NAME}]${NC} $1"
}

check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java 未安装，请先安装 Java 17 或更高版本"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 17 ]; then
        print_error "需要 Java 17 或更高版本，当前版本: $JAVA_VERSION"
        exit 1
    fi
}

check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven 未安装，请先安装 Maven 3.6 或更高版本"
        exit 1
    fi
}

build_app() {
    print_status "正在构建应用..."
    mvn clean package -DskipTests
    if [ $? -eq 0 ]; then
        print_success "应用构建成功"
    else
        print_error "应用构建失败"
        exit 1
    fi
}

start_app() {
    if [ -f "$PID_FILE" ]; then
        PID=$(cat "$PID_FILE")
        if ps -p "$PID" > /dev/null 2>&1; then
            print_warning "应用已在运行 (PID: $PID)"
            return
        else
            rm -f "$PID_FILE"
        fi
    fi
    
    print_status "正在启动应用..."
    nohup java -jar "$APP_JAR" > "$LOG_FILE" 2>&1 &
    PID=$!
    echo $PID > "$PID_FILE"
    
    sleep 2
    if ps -p "$PID" > /dev/null 2>&1; then
        print_success "应用启动成功 (PID: $PID)"
        print_status "日志文件: $LOG_FILE"
        print_status "访问地址: http://localhost:8080"
        print_status "H2控制台: http://localhost:8080/h2-console"
    else
        print_error "应用启动失败"
        rm -f "$PID_FILE"
        exit 1
    fi
}

stop_app() {
    if [ ! -f "$PID_FILE" ]; then
        print_warning "应用未运行"
        return
    fi
    
    PID=$(cat "$PID_FILE")
    if ps -p "$PID" > /dev/null 2>&1; then
        print_status "正在停止应用 (PID: $PID)..."
        kill "$PID"
        
        # 等待进程结束
        for i in {1..30}; do
            if ! ps -p "$PID" > /dev/null 2>&1; then
                rm -f "$PID_FILE"
                print_success "应用已停止"
                return
            fi
            sleep 1
        done
        
        print_warning "应用未正常停止，强制终止..."
        kill -9 "$PID" 2>/dev/null
        rm -f "$PID_FILE"
        print_success "应用已强制终止"
    else
        print_warning "应用未运行"
        rm -f "$PID_FILE"
    fi
}

restart_app() {
    stop_app
    sleep 2
    start_app
}

status_app() {
    if [ -f "$PID_FILE" ]; then
        PID=$(cat "$PID_FILE")
        if ps -p "$PID" > /dev/null 2>&1; then
            print_success "应用正在运行 (PID: $PID)"
            
            # 显示内存使用情况
            MEM_USAGE=$(ps -o rss= -p "$PID" | awk '{printf "%.1f MB", $1/1024}')
            print_status "内存使用: $MEM_USAGE"
            
            # 显示运行时间
            START_TIME=$(ps -o lstart= -p "$PID")
            print_status "启动时间: $START_TIME"
            
            # 显示最后几行日志
            if [ -f "$LOG_FILE" ]; then
                print_status "最近日志:"
                tail -5 "$LOG_FILE"
            fi
        else
            print_error "PID文件存在但应用未运行"
            rm -f "$PID_FILE"
        fi
    else
        print_warning "应用未运行"
    fi
}

show_help() {
    echo "Hermes Poll System 管理脚本"
    echo ""
    echo "用法: $0 [命令]"
    echo ""
    echo "命令:"
    echo "  start    启动应用"
    echo "  stop     停止应用"
    echo "  restart  重启应用"
    echo "  status   查看应用状态"
    echo "  build    构建应用"
    echo "  help     显示帮助信息"
    echo ""
    echo "示例:"
    echo "  $0 start     # 启动应用"
    echo "  $0 status    # 查看状态"
    echo "  $0 stop      # 停止应用"
}

# 主程序
case "$1" in
    start)
        check_java
        if [ ! -f "$APP_JAR" ]; then
            print_warning "JAR文件不存在，正在构建..."
            check_maven
            build_app
        fi
        start_app
        ;;
    stop)
        stop_app
        ;;
    restart)
        check_java
        if [ ! -f "$APP_JAR" ]; then
            print_warning "JAR文件不存在，正在构建..."
            check_maven
            build_app
        fi
        restart_app
        ;;
    status)
        status_app
        ;;
    build)
        check_maven
        build_app
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        if [ -z "$1" ]; then
            print_error "请指定命令"
            echo ""
            show_help
        else
            print_error "未知命令: $1"
            echo ""
            show_help
        fi
        exit 1
        ;;
esac