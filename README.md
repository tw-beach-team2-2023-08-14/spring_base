# spring_base

## 开发指南

### 技术栈

- Java 17
- Spring Boot 3.1.2
- Gradle 8.2.1

### 本地依赖

#### 安装Docker
- Option 1: 安装colima代替Docker desktop

安装 colima
```
brew install docker colima
```

启动colima

```
colima start
```

完成docker安装后，检查docker daemon是否启动成功

```
docker ps
```
 - Option 2: Docker desktop only

#### 安装&运行mysql

拉取mysql镜像

```
docker pull mysql:8.0.22
```

检查mysql镜像

```
docker images
```

初始化本地环境和依赖，启动容器。

```
docker-compose up
```

### 本地构建

```
./gradlew clean build 
```

### 本地运行

```
./gradlew bootRun
```

### Swagger

Local environment: http://localhost:9090/swagger-ui.html

### 格式代码

```
./gradlew spotlessApply
```
