# 📚 IMAU 图书管理系统

一个基于 Spring Boot 3 + Sa-Token + MyBatis Plus 实现的轻量级图书管理系统，具备完整的用户认证、角色授权和借阅管理功能。

## ✨ 项目简介

本项目是一个完整的图书管理后端系统，采用前后端分离架构设计，实现了：
- 用户注册、登录功能
- 基于 Sa-Token 的权限认证体系
- 自定义 AOP 注解实现细粒度权限控制
- 图书信息管理（CRUD）
- 借阅记录管理
- 基于角色的访问控制（RBAC）

## 🛠 技术栈

### 后端框架
- **Spring Boot 3.5.3** - 核心框架
- **Spring AOP** - 面向切面编程，实现权限拦截
- **MyBatis Plus 3.5.12** - 持久层框架，简化 CRUD 操作
- **Sa-Token 1.44.0** - 轻量级权限认证框架

### 工具库
- **Hutool 5.8.16** - Java 工具库
- **Lombok 1.18.30** - 简化实体类编写

### 数据库
- **MySQL 5.7+** - 关系型数据库

### 运行环境
- **JDK 17+**
- **Maven 3.6+**

## 📁 项目结构

```
imau-bookmanager/
├── src/main/java/com/pan/
│   ├── ImauBookmanagerApplication.java    # 启动类
│   ├── annotation/
│   │   └── NeeAuth.java                   # 自定义权限注解
│   ├── config/
│   │   └── NeeAuthConfig.java             # AOP 权限拦截配置
│   ├── controller/
│   │   └── UserController.java            # 用户控制器
│   ├── dao/
│   │   ├── BookDao.java                   # 图书数据访问层
│   │   ├── RecordDao.java                 # 借阅记录数据访问层
│   │   ├── RoleDao.java                   # 角色数据访问层
│   │   └── UserDao.java                   # 用户数据访问层
│   ├── pojo/
│   │   ├── BookPojo.java                  # 图书实体类
│   │   ├── RecordPojo.java                # 借阅记录实体类
│   │   ├── RolePojo.java                  # 角色实体类
│   │   └── UserPojo.java                  # 用户实体类
│   └── service/
│       ├── BookServiceImpl.java           # 图书业务逻辑
│       ├── RecordServiceImpl.java         # 借阅记录业务逻辑
│       ├── RoleServiceImpl.java           # 角色业务逻辑
│       ├── StpInterfaceImpl.java          # Sa-Token 权限接口实现
│       └── UserServiceImpl.java           # 用户业务逻辑
├── src/main/resources/
│   └── application.yml                     # 配置文件
├── imau_learn.sql                          # 数据库脚本
└── pom.xml                                 # Maven 依赖配置
```

## 🚀 快速开始

### 1. 环境准备

确保你的开发环境已安装：
- JDK 17 或更高版本
- MySQL 5.7 或更高版本
- Maven 3.6 或更高版本

### 2. 数据库配置

创建数据库：
```sql
CREATE DATABASE imau_learn CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

根据项目需求创建以下数据表：

**用户表 (user)**
```sql
CREATE TABLE `user` (
  `uid` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` VARCHAR(50) NOT NULL COMMENT '用户名',
  `pass_word` VARCHAR(100) NOT NULL COMMENT '密码',
  `nick_name` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uk_user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

**角色表 (role)**
```sql
CREATE TABLE `role` (
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_user_id` INT(11) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`role_id`),
  KEY `idx_user_id` (`role_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
```

**图书表 (book)**
```sql
CREATE TABLE `book` (
  `book_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '图书ID',
  `book_name` VARCHAR(100) NOT NULL COMMENT '图书名称',
  `book_isbn` VARCHAR(50) DEFAULT NULL COMMENT 'ISBN号',
  `book_price` DECIMAL(10,2) DEFAULT NULL COMMENT '价格',
  `book_writer` VARCHAR(50) DEFAULT NULL COMMENT '作者',
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书表';
```

**借阅记录表 (record)**
```sql
CREATE TABLE `record` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `book_id` INT(11) NOT NULL COMMENT '图书ID',
  `user_id` INT(11) NOT NULL COMMENT '用户ID',
  `time` VARCHAR(50) DEFAULT NULL COMMENT '借阅时间',
  PRIMARY KEY (`id`),
  KEY `idx_book_id` (`book_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='借阅记录表';
```

### 3. 修改配置文件

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/imau_learn
    username: root          # 修改为你的数据库用户名
    password: root          # 修改为你的数据库密码
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 4. 编译运行

```bash
# 进入项目目录
cd imau-bookmanager

# 使用 Maven 编译打包
mvn clean package

# 运行项目
java -jar target/imau-bookmanager-0.0.1-SNAPSHOT.jar
```

或者在 IDE 中直接运行 `ImauBookmanagerApplication.java`

### 5. 访问项目

- 服务地址：`http://localhost:8081`
- 登录接口：`POST http://localhost:8081/Login`

## 📖 核心功能说明

### 1. 用户认证 (Sa-Token)

项目使用 Sa-Token 实现轻量级的用户认证：

```java
// 登录
StpUtil.login(userId);

// 获取当前登录用户ID
Object loginId = StpUtil.getLoginId();

// 检查是否登录
StpUtil.checkLogin();

// 注销登录
StpUtil.logout();
```

**Sa-Token 配置特点：**
- Token 有效期：30天 (2592000秒)
- 允许同一账号多地登录
- 每次登录生成新 Token
- Token 风格：UUID

### 2. 自定义权限注解 (@NeeAuth)

通过自定义注解实现方法级别的权限控制：

```java
@NeeAuth(neeAuth = true, needRole = {"admin"})
@RequestMapping(value = "/Heollo", method = RequestMethod.GET)
public String doHeollo() {
    return "hello";
}
```

**注解参数说明：**
- `neeAuth`：是否需要权限认证（true/false）
- `needRole`：需要的角色列表（数组形式）

### 3. AOP 权限拦截

使用 Spring AOP 实现自动权限拦截：

```java
@Aspect
@Component
public class NeeAuthConfig {
    @Pointcut("@annotation(com.pan.annotation.NeeAuth)")
    public void NeeAuth() {}
    
    @Before("NeeAuth()")
    public void beforeRequestInDirect(JoinPoint Point) {
        // 获取注解信息
        // 检查登录状态
        // 验证角色权限
        // 不满足条件则拦截并返回
    }
}
```

### 4. 角色权限管理

实现 `StpInterface` 接口，自定义角色获取逻辑：

```java
@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 根据用户ID查询角色列表
        // 返回角色名称集合
    }
}
```

## 🔌 API 接口示例

### 用户登录
```http
POST /Login
Content-Type: application/x-www-form-urlencoded

username=admin&password=123456
```

**成功响应：**
```json
{
  "code": 200,
  "msg": "登录成功",
  "data": null
}
```

### 权限保护接口
```http
GET /Heollo
Cookie: satoken=xxxx-xxxx-xxxx-xxxx
```

**需要满足：**
1. 用户已登录（携带有效 Token）
2. 用户拥有 `admin` 角色

## 🔧 开发建议

### 补充完整的 Controller

当前项目只有 `UserController`，建议补充：
- `BookController` - 图书管理接口
- `RecordController` - 借阅记录接口
- `RoleController` - 角色管理接口

### 添加统一异常处理

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    public SaResult handleNotLoginException(NotLoginException e) {
        return SaResult.error("未登录，请先登录");
    }
}
```

### 完善数据校验

使用 `@Valid` 和 `@Validated` 注解进行参数校验：
```java
public String register(@Valid @RequestBody UserDTO userDTO) {
    // ...
}
```

## 📚 学习资源

### 相关教程
- 出处1：[Spring Boot 教程](https://www.bilibili.com/video/BV1Uo4y1V7Wa/)
- 出处2：[权限认证教程](https://www.bilibili.com/video/BV1zV4y1B7fq/)

### 官方文档
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot)
- [Sa-Token 官方文档](https://sa-token.cc)
- [MyBatis Plus 官方文档](https://baomidou.com/)
- [Hutool 官方文档](https://hutool.cn/docs/)

### 学习重点
- ✅ AOP 面向切面编程
- ✅ Token 认证机制
- ✅ 自定义注解的实现与应用
- ✅ Spring Boot 拦截器与过滤器
- ✅ RESTful API 设计规范
