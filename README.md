### wenblog
毕设作品 一个简洁的博客
预览：https://www.endxu.cn/article/getall

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
2. 执行：gradlew clean build后进入war包所在位置，修改war包名为ROOT.war,放到tomcatwebapp目录下启动tomcat
3. 导入ide启动

### 好好学习
本系统为毕设作品，开发过程中体会到了自己的不足    
1. 前台页面的编写，details模板里面写了几百行js代码用于动态控制，可能是因为写惯规范的JAVA代码，感觉这些代码写的有点混乱，或者说他们真的就写的混乱    
2. 一些功能的实现其实不难，但是因为要编写页面所以放弃了    
3. 在使用layui的前端模板渲染的时候感受到了这种服务端提供数据，在前端页面进行渲染相较于使用jsp、thymeleaf渲染的舒适感，但是layui不是MVVM框架没有双向绑定，在这种没有使用MVVM框架的情况下感受到了MVVM的好处，希望有时间的时候可以去进一步学习下VUE、Angular，现在对他们的认知也就是做了官方教程。    
3. 对于返回的JSON数据的处理，也不是合理，系统中直接返回一个装载POJO类的LIST或直接返回POJO的方式，如果是真的大型项目，这种做法带来的不一致性并不合理。    
4. 本想在搜索功能引入elasticsearch，由于毕设的时间比较紧张放弃了。因为我不懂elasticsearch的语法只是对这个技术有了解，虽然通过JPA可以直接使用，但是这对我也没有好处。

所以，这个博客在满足毕设的情况下就暂停开发了，去多多学习，学无止境，厚积薄发。 
