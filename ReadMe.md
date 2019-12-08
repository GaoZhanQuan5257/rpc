### 项目简介
自定义RPC框架，实现思想基于标准三角思想。
### 功能
实现服务的远程调用，负载均衡（目前只实现了随机），隐式传参，可自行补充实现

### 运行
* 运本项目需要zk环境，建议docker部署zk环境，简洁方便。或可自行下载zk部署，安装完毕，设置zk地址，默认:localhost:2181
* 启动ProviderServer,后运行ConsumerServer,观察结果、debug。在服务停止一段时间后，注册信息自动清除

### 项目待改进点
* 缺少可视化控制台界面，用于功能管控，如：负载、轮训策略、超时时间、服务上下线、服务测试等
* 服务消费端的调用方式不是很友好，和Spring整合需要优化下
* 序列化方式不够高效，后续抽空改为hession

### 老大提出的问题(12/4号)，待解决补充：
* 生产环境服务发布时，如何保证发布过程中线上服务正常可用？
* 自己的项目和现有阿里的dubbo、HSF项目差距在哪里，怎么改进优化自己的项目？

**注：** 
* 有zk环境后，需将quick-start模块下/src/main/resources/context.properties中设置对应的zk地址，默认地址localHost:2181
* netty通信端口 9999,可自行更改，更改位置同上
* 各模块含义，顾名思义
* 自己本地调试改动代码，若改动不生效，可执行mvn clean install -Dmaven.skip.tests使改动生效。

## 相关：
* [docker](https://www.runoob.com/docker/docker-tutorial.html)
* [zookeeper](https://zookeeper.apache.org/doc/current/zookeeperStarted.html)

* 项目目录结构：
```
.
├── ReadMe.md
├── common
│   ├── pom.xml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── muyu
│                       ├── App.java
│                       └── meta
│                           ├── MethodInvokeMeta.java
│                           ├── MethodInvokeMetaWrap.java
│                           ├── ObjectDecoder.java
│                           ├── ObjectEncoder.java
│                           ├── Result.java
│                           ├── ResultWrap.java
│                           └── SerializationUtils.java
├── frame.txt
├── pom.xml
├── proxy
│   ├── pom.xml
│   ├── proxy.iml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── muyu
│                       ├── App.java
│                       ├── impl
│                       │   ├── FailFastCluster.java
│                       │   └── JDKDynamicProxyFactoryBean.java
│                       └── service
│                           ├── BeanProxy.java
│                           └── Cluster.java
├── quick-start
│   ├── pom.xml
│   ├── quick-start.iml
│   └── src
│       └── main
│           ├── java
│           │   └── com
│           │       └── muyu
│           │           ├── ConsumerServer.java
│           │           ├── ProviderServer.java
│           │           ├── entity
│           │           │   └── User.java
│           │           ├── impl
│           │           │   └── UserServiceImpl.java
│           │           └── service
│           │               └── UserService.java
│           └── resources
│               ├── context.properties
│               ├── log4j.properties
│               ├── spring-consumer-context.xml
│               └── spring-context.xml
├── registry
│   ├── pom.xml
│   ├── registry.iml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── muyu
│                       ├── App.java
│                       ├── domain
│                       │   └── HostAndPort.java
│                       ├── impl
│                       │   ├── RandomLoadBalancer.java
│                       │   └── ZookeeperRegistry.java
│                       └── service
│                           ├── LoadBalancer.java
│                           └── Registry.java
├── remote
│   ├── pom.xml
│   ├── remote.iml
│   └── src
│       └── main
│           └── java
│               └── com
│                   └── muyu
│                       ├── App.java
│                       ├── server
│                       │   ├── NettyRPCClient.java
│                       │   └── ServerProvider.java
│                       └── service
│                           └── RPCClient.java
└── rpc.iml

42 directories, 45 files
```
* provider启动后zk中的目录结构：
```
[zk: localhost:2181(CONNECTED) 4] ls /
[RPC, zookeeper]
[zk: localhost:2181(CONNECTED) 5] ls /RPC
[com.muyu.service.UserService]
[zk: localhost:2181(CONNECTED) 6] ls /RPC/com.muyu.service.UserService
[providers]
[zk: localhost:2181(CONNECTED) 7] ls /RPC/com.muyu.service.UserService/providers
[192.168.199.174:9999]
[zk: localhost:2181(CONNECTED) 8] 
```

* 以上代码如有错误，或有好的改进意见，可随时联系我,最好可以一起开发。   

&copy; 闲着没事，就敲代码玩玩。
    
    