# Hermes 在线投票系统

一个基于 Spring Boot 的在线投票系统，作为 Hermes Experiment 的一部分。

## 功能特性

- ✅ 创建投票（标题、描述、多个选项）
- ✅ 参与投票（防止重复投票）
- ✅ 实时查看投票结果（图表展示）
- ✅ 投票管理（激活/停用、删除）
- ✅ 响应式设计（移动端友好）
- ✅ 防止重复投票（基于IP地址）
- ✅ 示例数据初始化

## 技术栈

- **后端**: Spring Boot 3.2.4, Java 17
- **前端**: Thymeleaf, HTML5, CSS3, JavaScript
- **数据库**: H2 Database（嵌入式）
- **构建工具**: Maven
- **其他**: Lombok, Spring Data JPA, Spring Validation

## 快速开始

### 1. 环境要求

- Java 17 或更高版本
- Maven 3.6 或更高版本

### 2. 克隆项目

```bash
git clone <repository-url>
cd poll-system
```

### 3. 构建项目

```bash
mvn clean package
```

### 4. 运行应用

```bash
java -jar target/poll-system-1.0.0.jar
```

或者使用 Maven 直接运行：

```bash
mvn spring-boot:run
```

### 5. 访问应用

- 主页面: http://localhost:8080/
- H2 数据库控制台: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/poll_db`
  - 用户名: `sa`
  - 密码: (空)

## 项目结构

```
src/main/java/com/hermes/poll/
├── PollSystemApplication.java      # 主应用类
├── controller/
│   └── PollController.java         # 控制器
├── model/
│   ├── Poll.java                   # 投票实体
│   ├── PollOption.java             # 投票选项实体
│   └── Vote.java                   # 投票记录实体
├── repository/
│   ├── PollRepository.java         # 投票数据访问
│   ├── PollOptionRepository.java   # 选项数据访问
│   └── VoteRepository.java         # 投票记录数据访问
├── service/
│   └── PollService.java            # 业务逻辑服务
└── dto/
    ├── CreatePollRequest.java      # 创建投票请求DTO
    ├── VoteRequest.java            # 投票请求DTO
    ├── ApiResponse.java            # API响应DTO
    └── PollViewModel.java          # 视图模型DTO

src/main/resources/
├── templates/                      # Thymeleaf模板
│   ├── base.html                   # 基础模板
│   ├── index.html                  # 首页
│   ├── poll.html                   # 投票详情页
│   ├── create.html                 # 创建投票页
│   └── admin.html                  # 管理页面
├── static/
│   ├── css/
│   │   └── style.css               # 样式表
│   └── js/
│       └── main.js                 # JavaScript
├── application.properties          # 配置文件
└── data/                           # 数据库文件目录
```

## API 接口

### 投票相关

| 方法 | 路径 | 描述 | 参数 |
|------|------|------|------|
| GET | `/` | 首页（投票列表） | - |
| GET | `/poll/{id}` | 投票详情页 | `id`: 投票ID |
| GET | `/create` | 创建投票表单 | - |
| POST | `/create` | 创建投票 | `title`, `description`, `options` |
| POST | `/poll/{id}/vote` | 提交投票 | `optionId` |
| GET | `/admin` | 管理页面 | - |
| POST | `/admin/poll/{id}/toggle` | 切换投票状态 | - |
| POST | `/admin/poll/{id}/delete` | 删除投票 | - |
| GET | `/api/poll/{id}/stats` | 获取投票统计 | - |

### JSON API 响应格式

```json
{
  "success": true,
  "message": "操作成功",
  "data": {...}
}
```

## 数据库设计

### polls 表
- `id`: 主键
- `title`: 投票标题
- `description`: 投票描述
- `created_at`: 创建时间
- `is_active`: 是否激活

### poll_options 表
- `id`: 主键
- `poll_id`: 外键（关联投票）
- `text`: 选项文本

### votes 表
- `id`: 主键
- `poll_id`: 外键（关联投票）
- `option_id`: 外键（关联选项）
- `voter_ip`: 投票者IP地址
- `voted_at`: 投票时间

## 功能截图

### 首页
![首页截图](docs/screenshots/home.png)

### 创建投票
![创建投票截图](docs/screenshots/create.png)

### 投票详情
![投票详情截图](docs/screenshots/poll.png)

### 管理页面
![管理页面截图](docs/screenshots/admin.png)

## 开发指南

### 添加新功能

1. 在 `model` 包中添加实体类
2. 在 `repository` 包中添加数据访问接口
3. 在 `service` 包中添加业务逻辑
4. 在 `controller` 包中添加控制器方法
5. 在 `templates` 包中添加HTML模板
6. 更新CSS和JavaScript文件

### 运行测试

```bash
mvn test
```

### 打包部署

```bash
mvn clean package -DskipTests
```

生成的JAR文件位于 `target/poll-system-1.0.0.jar`

## 配置说明

### 应用配置 (`application.properties`)

```properties
# 服务器端口
server.port=8080

# 数据库配置
spring.datasource.url=jdbc:h2:file:./data/poll_db
spring.datasource.username=sa
spring.datasource.password=

# H2控制台
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Thymeleaf配置
spring.thymeleaf.cache=false
```

### 修改数据库

要使用其他数据库（如MySQL、PostgreSQL）：

1. 在 `pom.xml` 中添加对应的数据库驱动依赖
2. 修改 `application.properties` 中的数据库配置
3. 更新数据库连接信息

## 故障排除

### 常见问题

1. **端口被占用**
   - 修改 `server.port` 为其他端口

2. **数据库连接失败**
   - 检查数据库服务是否运行
   - 验证数据库连接配置

3. **样式或脚本不加载**
   - 清除浏览器缓存
   - 检查静态资源路径配置

### 日志查看

应用日志默认输出到控制台，可以通过以下配置调整日志级别：

```properties
logging.level.com.hermes.poll=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate=WARN
```

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目基于 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 联系方式

- 项目主页: [GitHub Repository](https://github.com/StevenWang6/hermestest)
- 问题反馈: [Issues](https://github.com/StevenWang6/hermestest/issues)

---

**Hermes Experiment** - 探索AI辅助开发的新可能