# 🎮 Java 坦克大战游戏 - 运行指南

## 📋 环境要求

### 必需环境
1. **Java 11+** - 运行 Java 程序
2. **Maven 3.6+** - 项目构建工具

### 可选环境
- Git - 版本控制
- IDE (IntelliJ IDEA, Eclipse, VS Code) - 开发环境

## 🚀 快速开始

### 方法1: 使用预编译版本（如果可用）
```bash
cd tankbattle/java
java -jar target/tank-battle-java-1.0.0.jar
```

### 方法2: 从源码编译运行

#### Linux/macOS:
```bash
cd tankbattle/java

# 编译项目
mvn clean compile

# 运行游戏
mvn exec:java
```

#### Windows:
```cmd
cd tankbattle\java

# 编译项目
mvn clean compile

# 运行游戏
mvn exec:java
```

### 方法3: 使用运行脚本 (Linux/macOS)
```bash
cd tankbattle/java
chmod +x run.sh  # 首次运行需要添加执行权限
./run.sh
```

## 🛠️ 安装开发环境

### Ubuntu/Debian:
```bash
# 安装 Java
sudo apt update
sudo apt install openjdk-11-jdk

# 安装 Maven
sudo apt install maven

# 验证安装
java -version
mvn --version
```

### macOS (使用 Homebrew):
```bash
# 安装 Java
brew install openjdk@11

# 安装 Maven
brew install maven

# 设置 Java 环境
sudo ln -sfn /usr/local/opt/openjdk@11/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-11.jdk
```

### Windows:
1. 下载并安装 [Java JDK 11](https://adoptium.net/temurin/releases/)
2. 下载并安装 [Maven](https://maven.apache.org/download.cgi)
3. 设置环境变量:
   - `JAVA_HOME`: JDK 安装路径
   - `MAVEN_HOME`: Maven 安装路径
   - 将 `%JAVA_HOME%\bin` 和 `%MAVEN_HOME%\bin` 添加到 PATH

## 🎮 游戏控制

### 基本控制
- **W/A/S/D**: 移动坦克
- **空格键**: 发射炮弹
- **P**: 暂停/继续游戏
- **T**: 进入使能开关菜单
- **ESC**: 退出游戏

### 使能开关控制
在游戏中按 **T** 进入开关菜单:
- **数字键 1-5**: 切换对应开关状态
- **Enter**: 确认并返回游戏
- **ESC**: 取消并返回游戏

### 使能开关说明
1. **音效开关** - 控制游戏音效（控制台提示音）
2. **碰撞检测开关** - 控制炮弹碰撞检测
3. **AI开关** - 控制敌人AI行为
4. **道具系统开关** - 控制道具生成（预留功能）
5. **调试模式开关** - 显示调试信息

## 🧪 运行测试

```bash
cd tankbattle/java
mvn test
```

测试覆盖:
- 坦克类测试 (TankTest.java)
- 游戏配置测试 (GameConfigTest.java)
- 炮弹类测试 (BulletTest.java)

## 📦 项目构建

### 编译项目:
```bash
mvn clean compile
```

### 打包为 JAR:
```bash
mvn clean package
```

生成的 JAR 文件在 `target/tank-battle-java-1.0.0.jar`

### 运行打包的 JAR:
```bash
java -jar target/tank-battle-java-1.0.0.jar
```

## 🔧 项目结构

```
tankbattle/java/
├── src/main/java/com/tankbattle/
│   ├── TankBattleGame.java      # 游戏主类
│   ├── model/                   # 数据模型
│   │   ├── GameConfig.java     # 游戏配置（包含使能开关）
│   │   ├── Tank.java          # 坦克类
│   │   └── Bullet.java        # 炮弹类
│   ├── view/                   # 视图层
│   │   └── ConsoleRenderer.java # 控制台渲染器
│   ├── controller/             # 控制器层
│   │   ├── GameController.java # 游戏控制器
│   │   └── InputHandler.java   # 输入处理器
│   └── utils/                  # 工具类
│       ├── GameUtils.java     # 游戏工具
│       └── SwitchManager.java # 开关管理器
├── src/main/resources/         # 资源文件
│   └── config.properties      # 配置文件
├── src/test/java/              # 测试文件
├── pom.xml                    # Maven 配置文件
├── run.sh                     # 运行脚本
└── README.md                  # 项目说明
```

## ⚙️ 配置说明

配置文件: `src/main/resources/config.properties`

主要配置项:
```properties
# 游戏速度（毫秒）
game.speed=100

# 最大炮弹数
game.maxBullets=5

# 地图尺寸
game.mapWidth=40
game.mapHeight=20

# 使能开关默认值
switch.enableSound=true
switch.enableCollision=true
switch.enableAI=true
switch.enablePowerups=false
switch.enableDebug=false
```

## 🐛 故障排除

### 常见问题

1. **"java: command not found"**
   - 解决方案: 安装 Java JDK 并设置环境变量

2. **"mvn: command not found"**
   - 解决方案: 安装 Maven 并设置环境变量

3. **编译错误: "package does not exist"**
   - 解决方案: 运行 `mvn clean compile` 重新编译

4. **游戏运行缓慢**
   - 调整 `config.properties` 中的 `game.speed` 值
   - 关闭调试模式: 在游戏中按 T，然后按 5

5. **控制台显示乱码**
   - 确保终端支持 UTF-8 编码
   - Linux/macOS: `export LANG=en_US.UTF-8`
   - Windows: 使用 Windows Terminal 或设置代码页: `chcp 65001`

### 日志文件
游戏运行日志会输出到控制台，如果开启调试模式会显示更多信息。

## 📞 支持与反馈

如果遇到问题:
1. 检查本指南的故障排除部分
2. 查看 GitHub 仓库的 Issues
3. 联系项目维护者

## 📄 许可证

本项目使用 MIT 许可证 - 详见 [LICENSE](LICENSE) 文件

---

**祝您游戏愉快！** 🎮
