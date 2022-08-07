启动 Sentinel 控制台
1. 下载 [Sentinel-dashboard](https://github.com/alibaba/Sentinel/releases)
2. java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar ${jarFile} 启动 sentinel dashboard
    * java 版本可能需要使用 JDK8，过高版本可能出现 mod 访问问题
    * 默认用户名密码都是 sentinel
3. 引入 **spring-cloud-starter-alibaba-sentinel** 依赖
4. *application.yml* 配置 **spring.application.name** 和 **spring.cloud.sentinel.dashboard** 为 Sentinel-dashboard 启动时指定的 host:port
6. 在对应的限流方法添加 **@SentinelResource** 注解，指定 value 为资源名
7. 启动应用，进入 Sentinel-dashboard 并登录，可以看到应用已经成功注册