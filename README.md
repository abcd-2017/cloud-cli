# Cloud-cli

## 简介

cloud-cli只为提供分布式项目提供脚手架，提供最基本的环境。通过引入starter或注解，即可将依赖包即拉即用，尽可能减少配置文件的编写。

架构：

``` lua
Cloud-cli
├── cloud-common -- 工具库及通用代码
    ├── cloud-common-core -- 通用代码和工具包
    ├── cloud-common-knife4j -- api文档生成包
    ├── cloud-common-modules -- 导入nacos、sentinel、openfeign
    ├── cloud-common-mybatis -- 数据库操作包，以及代码生成器
    ├── cloud-common-redis -- redis操作包，以及redisson
    ├── cloud-common-logs -- 收集日志，整合logstash，将日志收集到elasticsearch
├── cloud-gateway -- 网关
```

## 使用

### 一、cloud-common

1.cloud-common-core

作为项目模块公用包，只需要在每个模块的pom文件中加入即可,包括cloud-common-modules、cloud-common-knife4j

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-core</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

2.cloud-common-redis

redis 操作包，在需要使用reids的模块的pom文件中导入包

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-redis</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

3.cloud-common-mybatis

数据库操作包，在需要操控数据库的模块的pom文件中导入包

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-mybatis</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

> mybatis-plus代码生成

修改 **com.mybatis.generator**
包下的MyBatisGenerator的fastCodeGenerator方法中的作者、包名、数据库用户名等信息，以及需生成代码的模块名的绝对地址，然后运行该类的方法，可以自动生成该数据库下所有表的代码，并且自动加到模块下

4.cloud-common-modules

包含nacos、sentinel、openfeign，使用只需要修改该模块配置文件——bootstrap.yml中的nacos和sentinel的地址

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-modules</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

5.cloud-common-knife4j

knife4j api模块，与gateway整合成多模块文档
使用步骤：

* 1.导包

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-knife4j</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

* 2.在模块配置文件中加上以下配置

```yaml
spring:
  application:
    name: 模块名
  profiles:
    active: env
  #解决springboot 6.x以上不兼容swagger3
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```

* 3.在启动类上加上 **@EnableKnife4jApi** 注解
* 4.在网关的路由配置中加上对应配置

```yaml
- id: 模块id
  uri: lb://模块名
  predicates:
    - Path=/模块名/**
  filters:
    - RewritePath=/模块名/(?<segment>.*), /$\{segment}
```

6.cloud-common-logs
整合logstash，将日志收集到elasticsearch中，使用时只需要导包即可，搭建elk可以看下这篇博客 [点击跳转](https://blog.csdn.net/qq_57474766/article/details/127311188#4ELKes_868)

```pom
<dependency>
    <groupId>com.cli</groupId>
    <artifactId>cloud-common-logs</artifactId>
    <version>${Cloud-cli.version}</version>
</dependency>
```

## 二、Cloud-Gateway

cloud网关模块，同时也整合了api文档，只需要其他服务模块导入了knife4j的包，并且在网关路由配置中对于的路由，就可以访问 **http://网关
的ip:网关的端口/doc.html** 可以看到所有模块的api接口文档
