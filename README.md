### wenblog
毕设作品 一个简洁的博客

### 如何启动
#### 请修改application.properties以下属性：
```
spring.datasource.driver-class-name //5.7以下请修改为com.mysql.jdbc.Driver
//以下三条请修改为你自己的数据库连接信息
spring.datasource.url
spring.datasource.username
spring.datasource.password

//以下三条跟文件上传相关若使用gradle、ide直接启动无需修改
//文件上传位置，设置为绝对目录，linux如："/home/username/",windows下如："D:/photo/"
//注意路径以斜杆结尾
wenblog.user.default.upload.dir
//部署到服务器、不以8080启动、以80、443启动需修改，设置为
//部署到服务器请将host设置为服务器公网ip或域名
//不以8080启动修改port为对应接口
//以80或443启动无需设置port
wenblog.user.protocol.host.port
//这里如果使用外置tomcat部署，请将文件放置到wenblog.user.default.upload.dir设置的上传目录+/upload/目录下
//并修改该属性为wenblog.user.protocol.host.port+/upload/文件名
wenblog.user.default.headportrait
```
#### 启动
1. 在根目录执行：gradlew bootRun
2 .执行：gradlew clean build后进入war包所在位置，修改war包名为ROOT.war,放到tomcatwebapp目录下启动tomcat
3 .导入ide启动
