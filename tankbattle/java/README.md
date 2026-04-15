# 🎮 Java 坦克大战游戏

这是一个使用 Java 实现的简易坦克大战游戏，包含使能开关功能。

## 🚀 功能特性

- 🎯 双人对战模式（玩家 vs 电脑）
- ⚙️ **使能开关系统**：可动态启用/禁用游戏功能
- 🏗️ 可破坏的障碍物
- 💥 炮弹碰撞检测
- 🎨 控制台图形界面
- 📊 游戏状态实时显示

## 🔧 使能开关功能

游戏包含以下可开关的功能：

| 开关名称 | 默认状态 | 功能描述 |
|---------|---------|---------|
| `ENABLE_SOUND` | 开启 | 游戏音效 |
| `ENABLE_COLLISION` | 开启 | 碰撞检测 |
| `ENABLE_AI` | 开启 | 电脑AI |
| `ENABLE_POWERUPS` | 关闭 | 道具系统 |
| `ENABLE_DEBUG` | 关闭 | 调试模式 |

## 📁 项目结构

```
java/
├── src/main/java/com/tankbattle/
│   ├── TankBattleGame.java      # 游戏主类
│   ├── model/                   # 数据模型
│   │   ├── Tank.java           # 坦克类
│   │   ├── Bullet.java         # 炮弹类
│   │   ├── Map.java            # 地图类
│   │   └── GameConfig.java     # 游戏配置（包含使能开关）
│   ├── view/                   # 视图层
│   │   ├── GameView.java       # 游戏视图
│   │   └── ConsoleRenderer.java # 控制台渲染器
│   ├── controller/             # 控制器层
│   │   ├── GameController.java # 游戏控制器
│   │   └── InputHandler.java   # 输入处理器
│   └── utils/                  # 工具类
│       ├── GameUtils.java      # 游戏工具
│       └── SwitchManager.java  # 开关管理器
├── src/main/resources/         # 资源文件
│   └── config.properties       # 配置文件
├── src/test/java/              # 测试文件
└── pom.xml                    # Maven 配置文件
```

## 🎮 游戏控制

### 玩家控制
- **W/A/S/D**: 移动坦克
- **空格键**: 发射炮弹
- **ESC**: 退出游戏
- **P**: 暂停/继续游戏
- **T**: 切换使能开关菜单

### 使能开关控制
在开关菜单中：
- **数字键 1-5**: 切换对应开关状态
- **Enter**: 确认并返回游戏
- **ESC**: 取消并返回游戏

## 🛠️ 开发指南

### 环境要求
- Java 11+
- Maven 3.6+

### 编译项目
```bash
mvn clean compile
```

### 运行游戏
```bash
mvn exec:java
```

或者：
```bash
java -jar target/tank-battle-java-1.0.0.jar
```

### 运行测试
```bash
mvn test
```

### 打包项目
```bash
mvn clean package
```

## 📄 配置文件

游戏配置位于 `src/main/resources/config.properties`：

```properties
# 游戏配置
game.speed=100
game.maxBullets=5
game.mapWidth=40
game.mapHeight=20

# 使能开关配置
switch.enableSound=true
switch.enableCollision=true
switch.enableAI=true
switch.enablePowerups=false
switch.enableDebug=false
```

## 🧪 测试覆盖

项目包含以下测试：
- 坦克移动测试
- 炮弹发射测试
- 碰撞检测测试
- 使能开关测试
- 游戏逻辑测试

## 📄 许可证

MIT License - 详见 LICENSE 文件

## 🖥️ WSL (Windows Subsystem for Linux) 运行指南

如果你在 WSL 中运行此项目，Windows 中已安装 Java，请使用以下方法：

### 方法1: 使用提供的脚本（推荐）
```bash
# 编译游戏
./compile.sh

# 运行游戏（英文模式，避免编码问题）
./run-game-english.sh
```

### 方法2: 直接命令
```bash
# 编译
"/mnt/c/Program Files/Java/jdk-26/bin/javac.exe" -d target/classes -cp "target/classes" src/main/java/com/tankbattle/*.java src/main/java/com/tankbattle/**/*.java

# 运行
"/mnt/c/Program Files/Java/jdk-26/bin/java.exe" -jar target/tank-battle-java.jar
```

### 编码问题解决
如果遇到中文字符显示乱码，请使用英文模式运行：
```bash
./run-game-english.sh
```

或者设置环境变量：
```bash
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8
```

## 🎮 游戏控制说明（英文）

### Basic Controls
- **W/A/S/D**: Move tank
- **Spacebar**: Fire bullet
- **P**: Pause/Resume game
- **T**: Enable switch menu
- **ESC**: Exit game

### Enable Switches (Press T in game)
- **Key 1-5**: Toggle corresponding switch
- **Enter**: Confirm and return to game
- **ESC**: Cancel and return to game

### Switch Functions
1. **Sound Switch** - Control game sound effects
2. **Collision Detection** - Control bullet collision
3. **AI Switch** - Control enemy AI behavior
4. **Powerup System** - Control powerup generation
5. **Debug Mode** - Show debug information
