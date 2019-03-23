create database IF NOT EXISTS blog_1_0;
USE blog_1_0;

-----------
-- 1.blog_user 用户表
-----------
CREATE TABLE IF NOT EXISTS blog_user (
 -- 登录依据，以下其中之一
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '用户ID', 
 user_phone VARCHAR(16) NOT NULL DEFAULT "NULL" COMMENT '用户手机号码',
 user_email VARCHAR(64) NOT NULL DEFAULT "NULL" COMMENT '用户EMAIL地址',
 user_qq int(10) NOT NULL DEFAULT 0 COMMENT '用户QQ号码',
 user_wechat VARCHAR(64) NOT NULL DEFAULT "NULL" COMMENT '用户微信',
 -- 用户所拥有的role
 user_role TEXT NOT NULL COMMENT '用户权限',
 -- 基础信息
 user_name VARCHAR(32) NOT NULL COMMENT '用户名',
 user_pwd VARCHAR(32) NOT NULL COMMENT '用户密码',
 user_image_url VARCHAR(255) NOT NULL DEFAULT "NULL" COMMENT '用户头像存储路径',
 -- 暂时没什么用
 user_mark mediumint(9) NOT NULL DEFAULT 0 COMMENT '用户积分',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=10000000;
drop table  blog_user;
-----------
-- 2.blog_login_history 
-- 用户历史登录记录
-----------
CREATE TABLE IF NOT EXISTS blog_login_history(
id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
login_user_id mediumint(8) NOT NULL,
login_time TIMESTAMP not NULL DEFAULT CURRENT_TIMESTAMP COMMENT '历史登录的时间',
login_ip VARCHAR(15) NOT NULL DEFAULT 'UNKNOW' COMMENT '用户历史登录时IP地址',
PRIMARY KEY (id)
)ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-----------
-- blog_user_change_history 
-- 用户历史修改记录
---------
CREATE TABLE IF NOT EXISTS blog_user_change_history(
id int NOT NULL AUTO_INCREMENT COMMENT '自增ID',
user_id mediumint(8) NOT NULL COMMENT '用户ID',
change_field VARCHAR(64) NOT NULL COMMENT '修改的字段' ,
change_value VARCHAR(255) NOT NULL COMMENT '修改为',
before_value VARCHAR(255) NOT NULL COMMENT '修改前',
change_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改执行时间',
PRIMARY KEY (id)
)ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-----------
-- 3.blog_user_data
-- 用户详细资料
-----------
create TABLE IF NOT EXISTS blog_user_data(
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 user_sex VARCHAR(6) NOT NULL DEFAULT 'unknow' COMMENT '用户性别',
 user_birthday date NOT NULL DEFAULT '1999-12-12' COMMENT '用户生日',
 user_description VARCHAR(255) NOT NULL DEFAULT '该用户很懒，没有自我描述' COMMENT '自我描述',
 user_school VARCHAR(255) NOT NULL DEFAULT 'unknow' COMMENT '毕业学校',
 user_address VARCHAR(255) NOT NULL DEFAULT 'unknow' COMMENT '用户地址',
 user_register_time timestamp NOT NULL default current_timestamp COMMENT '用户注册时间',
 user_register_ip VARCHAR(128) NOT NULL DEFAULT 'unknow' COMMENT '用户注册时IP地址',
 user_last_update_time TIMESTAMP NOT NULL default current_timestamp COMMENT '用户上次更新博客时间',
 user_blood_type VARCHAR(6) NOT NULL DEFAULT 'unknow' COMMENT '用户血型',
 user_signatures VARCHAR(255) NOT NULL  DEFAULT '该用户很懒，没有留下语录' COMMENT '用户语录',
 blog_description VARCHAR(255) NOT NULL COMMENT '博客简介',
 user_has_change_loginname boolean NOT NULL DEFAULT false COMMENT '是否修改默认登录名';
 PRIMARY KEY (user_id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-------------------
-- 4.blog_attention 用户关注表 
-------------------
CREATE TABLE IF NOT EXISTS blog_user_attention(
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 attention_id mediumint(8) NOT NULL COMMENT '被关注者ID',
 mutual_attention BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否互相关注',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-----------------------
-- 5.blog_secret_message 用户私信表
-- 系统通知与之共用
-----------------------
CREATE TABLE IF NOT EXISTS blog_secret_message (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '自增私信ID',
 send_id mediumint(8) NOT NULL COMMENT '发信者ID',
 receive_id mediumint(8) NOT NULL COMMENT '收信者ID',
 message_title VARCHAR(64) NOT NULL COMMENT '私信标题',
 message_content VARCHAR(255) NOT NULL COMMENT '私信内容',
 had_read BOOLEAN NOT NULL DEFAULT FALSE COMMENT '消息是否已读',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-------------------
-- 6.blog_stay_message 用户留言表
--------------------
CREATE TABLE IF NOT EXISTS blog_stay_message (
 id INT NOT NULL AUTO_INCREMENT COMMENT '留言表自增ID',
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 stay_user_id mediumint(8) NOT NULL COMMENT '留言者ID',
 message_content VARCHAR(255) NOT NULL COMMENT '留言内容',
 message_stay_time TIMESTAMP NOT NULL default current_timestamp COMMENT '留言时间',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;


---------------------------
-- 7.blog_article_sort 文章分类表
---------------------------
CREATE TABLE IF NOT EXISTS blog_article_sort (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '文章分类自增ID',
 user_id mediumint(8) NOT NULL COMMENT '该分类所属用户ID',
 sort_article_name VARCHAR(60) NOT NULL COMMENT '分类名称',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

 

----------------------------
-- 8.blog_article 文章表
----------------------------
CREATE TABLE IF NOT EXISTS blog_article (
 id int NOT NULL AUTO_INCREMENT COMMENT '日志自增ID号',
 article_title VARCHAR(128) NOT NULL COMMENT '文章标题',
 article_keyword TEXT NOT NULL COMMENT '文章关键字',
 article_summary TEXT NOT NULL COMMENT '文章概要',
 article_content MEDIUMTEXT NOT NULL COMMENT '文章内容',
 article_time TIMESTAMP NOT NULL default CURRENT_TIMESTAMP COMMENT '发布时间',
 last_update_time TIMESTAMP NOT NULL default CURRENT_TIMESTAMP COMMENT '最后时间',
 article_click int(10) NOT NULL default 0 COMMENT '点击数',
 article_like int(10) NOT NULL default 0 COMMENT '点赞数',
 sort_article_id mediumint(8) NOT NULL COMMENT '所属分类',
 user_id mediumint(8) NOT NULL COMMENT '所属用户ID',
 article_mode int(13) NOT NULL DEFAULT 1 COMMENT '文章的模式:1为私有，2为公开，3为仅好友查看',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;


----------------------------
-- 9.blog_comment 用户评论表
----------------------------
CREATE TABLE IF NOT EXISTS blog_article_comment (
 id int NOT NULL AUTO_INCREMENT COMMENT '评论自增ID号',
 comment_article_id int NOT NULL  COMMENT '评论的文章的ID',
 comment_content VARCHAR(255) NOT NULL COMMENT '评论内容',
 comment_user_id mediumint(8) NOT NULL COMMENT '评论者ID',
 be_comment_user_id mediumint(8) NOT NULL COMMENT '被评论者ID',
 comment_time TIMESTAMP NOT NULL default current_timestamp COMMENT '评论时间',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

------------------------------
-- 15.blog_email 邮件记录表
------------------------------
CREATE TABLE IF NOT EXISTS blog_email_log (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '自增ID号',
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 to_address  VARCHAR(64) NOT NULL DEFAULT "NULL" COMMENT '目标邮件地址',
 contents VARCHAR(255) NOT NULL COMMENT '发送内容',
 send_time TIMESTAMP NOT NULL default current_timestamp COMMENT '发送时间',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;



-- 下面的逐步完善

------------------------
-- 10.visitor 最近访客表
------------------------
CREATE TABLE IF NOT EXISTS blog_visitor (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '访客记录ID',
 visitor_id mediumint(8) NOT NULL COMMENT '访客ID',
 visitor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '来访时间',
 user_id mediumint(8) NOT NULL COMMENT '被访用户ID',
 visitor_ip VARCHAR(15) NOT NULL COMMENT '访客IP地址',
 type_id int(3) NOT NULL COMMENT '访问板块ID',
 where_id mediumint(8) NOT NULL COMMENT '查看某板块的某个子项目，如查看相册板块的第3个相册，该ID对应该相册的ID号',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;


-------------------------
-- 11.blog_mirco_blog 用户心情说说表
-------------------------
CREATE TABLE IF NOT EXISTS blog_miro_blog (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '说说记录ID',
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 micro_blog_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
 micro_blog_ip VARCHAR(15) NOT NULL COMMENT '说说发布时的IP地址',
 micro_blog_content VARCHAR(255) NOT NULL COMMENT '说说内容',
 type_id tinyint(3) NOT NULL DEFAULT 3 COMMENT '栏目ID,默认为3',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-------------------------
-- 12.blog_album 相册表
-------------------------
CREATE TABLE IF NOT EXISTS blog_album (
 id mediumint(8) NOT NULL  AUTO_INCREMENT COMMENT '相册ID',
 album_name VARCHAR(20) NOT NULL COMMENT '相册名',
 album_type VARCHAR(20) NOT NULL COMMENT '展示方式 0->仅主人可见,1->输入密码即可查看,2->仅好友能查看,3->回答问题即可查看',
 album_password VARCHAR(32) NOT NULL COMMENT '查看密码',
 user_id mediumint(8) NOT NULL COMMENT '所属用户ID',
 album_question VARCHAR(255) NOT NULL COMMENT '访问问题',
 album_answer VARCHAR(128) NOT NULL COMMENT '访问问题的答案',
 type_id int(3) NOT NULL DEFAULT 1 COMMENT '默认1表示相册板块',
 top_pic_id mediumint(8) NOT NULL COMMENT '封面图片的ID',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

-------------------------
-- 13.blog_photo 相片表
-------------------------
CREATE TABLE IF NOT EXISTS blog_photo (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '相片ID',
 photo_name VARCHAR(255) NOT NULL COMMENT '相片名称',
 photo_src VARCHAR(255) NOT NULL COMMENT '图片路径',
 photo_description VARCHAR(255) NOT NULL COMMENT '图片描述',
 album_id mediumint(8) NOT NULL COMMENT '所属相册ID',
 upload_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '图片上传时间',
 upload_ip VARCHAR(15) NOT NULL COMMENT '图片操作上传IP地址',
 PRIMARY KEY (id) 
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;

------------------------------
-- 14.blog_phone_message 短信记录表
------------------------------
CREATE TABLE IF NOT EXISTS blog_phone_message (
 id mediumint(8) NOT NULL AUTO_INCREMENT COMMENT '自增ID号',
 phone_num VARCHAR(12) NOT NULL COMMENT '用户手机号码',
 contents VARCHAR(255) NOT NULL COMMENT '发送内容',
 send_time TIMESTAMP NOT NULL default current_timestamp COMMENT '发送时间',
 user_id mediumint(8) NOT NULL COMMENT '用户ID',
 PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 ;
drop table phone_message;


--  省级 Provincial
--  城市 City
create table Provincial(pid int,Provincial varchar(50),primary key (pid));
insert into Provincial values(1,'北京市');
insert into Provincial values(2,'天津市');
insert into Provincial values(3,'上海市');
insert into Provincial values(4,'重庆市');
insert into Provincial values(5,'河北省');
insert into Provincial values(6,'山西省');
insert into Provincial values(7,'台湾省');
insert into Provincial values(8,'辽宁省');
insert into Provincial values(9,'吉林省');
insert into Provincial values(10,'黑龙江省');
insert into Provincial values(11,'江苏省');
insert into Provincial values(12,'浙江省');
insert into Provincial values(13,'安徽省');
insert into Provincial values(14,'福建省');
insert into Provincial values(15,'江西省');
insert into Provincial values(16,'山东省');
insert into Provincial values(17,'河南省');
insert into Provincial values(18,'湖北省');
insert into Provincial values(19,'湖南省');
insert into Provincial values(20,'广东省');
insert into Provincial values(21,'甘肃省');
insert into Provincial values(22,'四川省');
insert into Provincial values(23,'贵州省');
insert into Provincial values(24,'海南省');
insert into Provincial values(25,'云南省');
insert into Provincial values(26,'青海省');
insert into Provincial values(27,'陕西省');
insert into Provincial values(28,'广西壮族自治区');
insert into Provincial values(29,'西藏自治区');
insert into Provincial values(30,'宁夏回族自治区');
insert into Provincial values(31,'新疆维吾尔自治区');
insert into Provincial values(32,'内蒙古自治区');
insert into Provincial values(33,'澳门特别行政区');
insert into Provincial values(34,'香港特别行政区');
--  select pid,Provincial from Provincial

create table City(cid int not null,city varchar(50) primary key,pid int ,foreign key (pid) references Provincial(pid));
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -
--  插入各个省的城市数据
--  4个直辖市
insert into City values(1,'北京市',1);
insert into City values(1,'天津市',2);
insert into City values(1,'上海市',3);
insert into City values(1,'重庆市',4);
--  select * from City where pid=4
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  5河北省 11个地级市
insert into City values(1,'石家庄市',5);
insert into City values(2,'唐山市',5);
insert into City values(3,'秦皇岛市',5);
insert into City values(4,'邯郸市',5);
insert into City values(5,'邢台市',5);
insert into City values(6,'保定市',5);
insert into City values(7,'张家口市',5);
insert into City values(8,'承德市',5);
insert into City values(9,'沧州市',5);
insert into City values(10,'廊坊市',5);
insert into City values(11,'衡水市',5);
--  select * from City where pid=5 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  6山西省 11个城市
insert into City values(1,'太原市',6);
insert into City values(2,'大同市',6);
insert into City values(3,'阳泉市',6);
insert into City values(4,'长治市',6);
insert into City values(5,'晋城市',6);
insert into City values(6,'朔州市',6);
insert into City values(7,'晋中市',6);
insert into City values(8,'运城市',6);
insert into City values(9,'忻州市',6);
insert into City values(10,'临汾市',6);
insert into City values(11,'吕梁市',6);
--  select * from City where pid=6 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  7台湾省(台湾本岛和澎湖共设7市、16县，其中台北市和高雄市为“院辖市”，直属“行政院”，其余属台湾省；市下设区，县下设市（县辖市）、镇、乡，合称区市镇乡。);
insert into City values(1,'台北市',7);
insert into City values(2,'高雄市',7);
insert into City values(3,'基隆市',7);
insert into City values(4,'台中市',7);
insert into City values(5,'台南市',7);
insert into City values(6,'新竹市',7);
insert into City values(7,'嘉义市',7);
insert into City values(8,'台北县',7);
insert into City values(9,'宜兰县',7);
insert into City values(10,'桃园县',7);
insert into City values(11,'新竹县',7);
insert into City values(12,'苗栗县',7);
insert into City values(13,'台中县',7);
insert into City values(14,'彰化县',7);
insert into City values(15,'南投县',7);
insert into City values(16,'云林县',7);
insert into City values(17,'嘉义县',7);
insert into City values(18,'台南县',7);
insert into City values(19,'高雄县',7);
insert into City values(20,'屏东县',7);
insert into City values(21,'澎湖县',7);
insert into City values(22,'台东县',7);
insert into City values(23,'花莲县',7);
--  select * from City where pid=7 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  8辽宁省 14个地级市
insert into City values(1,'沈阳市',8);
insert into City values(2,'大连市',8);
insert into City values(3,'鞍山市',8);
insert into City values(4,'抚顺市',8);
insert into City values(5,'本溪市',8);
insert into City values(6,'丹东市',8);
insert into City values(7,'锦州市',8);
insert into City values(8,'营口市',8);
insert into City values(9,'阜新市',8);
insert into City values(10,'辽阳市',8);
insert into City values(11,'盘锦市',8);
insert into City values(12,'铁岭市',8);
insert into City values(13,'朝阳市',8);
insert into City values(14,'葫芦岛市',8);
--  select * from City where pid=8 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  9吉林省(2006年，辖：8个地级市、1个自治州；20个市辖区、20个县级市、17个县、3个自治县。);
insert into City values(1,'长春市',9);
insert into City values(2,'吉林市',9);
insert into City values(3,'四平市',9);
insert into City values(4,'辽源市',9);
insert into City values(5,'通化市',9);
insert into City values(6,'白山市',9);
insert into City values(7,'松原市',9);
insert into City values(8,'白城市',9);
insert into City values(9,'延边朝鲜族自治州',9);
--  select * from City where pid=9 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  10黑龙江省(2006年，辖：12地级市、1地区；64市辖区、18县级市、45县、1自治县);
insert into City values(1,'哈尔滨市',10);
insert into City values(2,'齐齐哈尔市',10);
insert into City values(3,'鹤 岗 市',10);
insert into City values(4,'双鸭山市',10);
insert into City values(5,'鸡 西 市',10);
insert into City values(6,'大 庆 市',10);
insert into City values(7,'伊 春 市',10);
insert into City values(8,'牡丹江市',10);
insert into City values(9,'佳木斯市',10);
insert into City values(10,'七台河市',10);
insert into City values(11,'黑 河 市',10);
insert into City values(12,'绥 化 市',10);
insert into City values(13,'大兴安岭地区',10);
--  select * from City where pid=10 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  11江苏省(2005年辖：13个地级市；54个市辖区、27个县级市、25个县);
insert into City values(1,'南京市',11);
insert into City values(2,'无锡市',11);
insert into City values(3,'徐州市',11);
insert into City values(4,'常州市',11);
insert into City values(5,'苏州市',11);
insert into City values(6,'南通市',11);
insert into City values(7,'连云港市',11);
insert into City values(8,'淮安市',11);
insert into City values(9,'盐城市',11);
insert into City values(10,'扬州市',11);
insert into City values(11,'镇江市',11);
insert into City values(12,'泰州市',11);
insert into City values(13,'宿迁市',11);
--  select * from City where pid=11 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  12浙江省(2006年，辖：11个地级市；32个市辖区、22个县级市、35个县、1个自治县。);
insert into City values(1,'杭州市',12);
insert into City values(2,'宁波市',12);
insert into City values(3,'温州市',12);
insert into City values(4,'嘉兴市',12);
insert into City values(5,'湖州市',12);
insert into City values(6,'绍兴市',12);
insert into City values(7,'金华市',12);
insert into City values(8,'衢州市',12);
insert into City values(9,'舟山市',12);
insert into City values(10,'台州市',12);
insert into City values(11,'丽水市',12);
--  select * from City where pid=12 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  13安徽省(2005年辖：17个地级市；44个市辖区、5县个级市、56个县。);
insert into City values(1,'合肥市',13);
insert into City values(2,'芜湖市',13);
insert into City values(3,'蚌埠市',13);
insert into City values(4,'淮南市',13);
insert into City values(5,'马鞍山市',13);
insert into City values(6,'淮北市',13);
insert into City values(7,'铜陵市',13);
insert into City values(8,'安庆市',13);
insert into City values(9,'黄山市',13);
insert into City values(10,'滁州市',13);
insert into City values(11,'阜阳市',13);
insert into City values(12,'宿州市',13);
insert into City values(13,'巢湖市',13);
insert into City values(14,'六安市',13);
insert into City values(15,'亳州市',13);
insert into City values(16,'池州市',13);
insert into City values(17,'宣城市',13);
--  select * from City where pid=13 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  14福建省(2006年辖：9个地级市；26个市辖区、14个县级市、45个县。);
insert into City values(1,'福州市',14);
insert into City values(2,'厦门市',14);
insert into City values(3,'莆田市',14);
insert into City values(4,'三明市',14);
insert into City values(5,'泉州市',14);
insert into City values(6,'漳州市',14);
insert into City values(7,'南平市',14);
insert into City values(8,'龙岩市',14);
insert into City values(9,'宁德市',14);
--  select * from City where pid=14 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  15江西省(2006年全省辖：11个地级市；19个市辖区、10个县级市、70个县。);
insert into City values(1,'南昌市',15);
insert into City values(2,'景德镇市',15);
insert into City values(3,'萍乡市',15);
insert into City values(4,'九江市',15);
insert into City values(5,'新余市',15);
insert into City values(6,'鹰潭市',15);
insert into City values(7,'赣州市',15);
insert into City values(8,'吉安市',15);
insert into City values(9,'宜春市',15);
insert into City values(10,'抚州市',15);
insert into City values(11,'上饶市',15);
--  select * from City where pid=15 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  16山东省(2005年，辖：17个地级市；49个市辖区、31个县级市、60个县。);
insert into City values(1,'济南市',16);
insert into City values(2,'青岛市',16);
insert into City values(3,'淄博市',16);
insert into City values(4,'枣庄市',16);
insert into City values(5,'东营市',16);
insert into City values(6,'烟台市',16);
insert into City values(7,'潍坊市',16);
insert into City values(8,'济宁市',16);
insert into City values(9,'泰安市',16);
insert into City values(10,'威海市',16);
insert into City values(11,'日照市',16);
insert into City values(12,'莱芜市',16);
insert into City values(13,'临沂市',16);
insert into City values(14,'德州市',16);
insert into City values(15,'聊城市',16);
insert into City values(16,'滨州市',16);
insert into City values(17,'菏泽市',16);
--  select * from City where pid=16 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  17河南省 17个地级市
insert into City values(1,'郑州市',17);
insert into City values(2,'开封市',17);
insert into City values(3,'洛阳市',17);
insert into City values(4,'平顶山市',17);
insert into City values(5,'安阳市',17);
insert into City values(6,'鹤壁市',17);
insert into City values(7,'新乡市',17);
insert into City values(8,'焦作市',17);
insert into City values(9,'濮阳市',17);
insert into City values(10,'许昌市',17);
insert into City values(11,'漯河市',17);
insert into City values(12,'三门峡市',17);
insert into City values(13,'南阳市',17);
insert into City values(14,'商丘市',17);
insert into City values(15,'信阳市',17);
insert into City values(16,'周口市',17);
insert into City values(17,'驻马店市',17);
insert into City values(18,'济源市',17);
--  select * from City where pid=17 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  18湖北省（截至2005年12月31日，全省辖13个地级单位（12个地级市、1个自治州）；102县级单位（38个市辖区、24个县级市、37个县、2个自治县、1个林区），共有1220个乡级单位（277个街道、733个镇、210个乡）。）
insert into City values(1,'武汉市',18);
insert into City values(2,'黄石市',18);
insert into City values(3,'十堰市',18);
insert into City values(4,'荆州市',18);
insert into City values(5,'宜昌市',18);
insert into City values(6,'襄樊市',18);
insert into City values(7,'鄂州市',18);
insert into City values(8,'荆门市',18);
insert into City values(9,'孝感市',18);
insert into City values(10,'黄冈市',18);
insert into City values(11,'咸宁市',18);
insert into City values(12,'随州市',18);
insert into City values(13,'仙桃市',18);
insert into City values(14,'天门市',18);
insert into City values(15,'潜江市',18);
insert into City values(16,'神农架林区',18);
insert into City values(17,'恩施土家族苗族自治州',18);
--  select * from City where pid=18 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  19湖南省（2005年辖：13个地级市、1个自治州；34个市辖区、16个县级市、65个县、7个自治县。）
insert into City values(1,'长沙市',19);
insert into City values(2,'株洲市',19);
insert into City values(3,'湘潭市',19);
insert into City values(4,'衡阳市',19);
insert into City values(5,'邵阳市',19);
insert into City values(6,'岳阳市',19);
insert into City values(7,'常德市',19);
insert into City values(8,'张家界市',19);
insert into City values(9,'益阳市',19);
insert into City values(10,'郴州市',19);
insert into City values(11,'永州市',19);
insert into City values(12,'怀化市',19);
insert into City values(13,'娄底市',19);
insert into City values(14,'湘西土家族苗族自治州',19);
--  select * from City where pid=19 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  20广东省（截至2005年12月31日，广东省辖：21个地级市，54个市辖区、23个县级市、41个县、3个自治县，429个街道办事处、1145个镇、4个乡、7个民族乡。）
insert into City values(1,'广州市',20);
insert into City values(2,'深圳市',20);
insert into City values(3,'珠海市',20);
insert into City values(4,'汕头市',20);
insert into City values(5,'韶关市',20);
insert into City values(6,'佛山市',20);
insert into City values(7,'江门市',20);
insert into City values(8,'湛江市',20);
insert into City values(9,'茂名市',20);
insert into City values(10,'肇庆市',20);
insert into City values(11,'惠州市',20);
insert into City values(12,'梅州市',20);
insert into City values(13,'汕尾市',20);
insert into City values(14,'河源市',20);
insert into City values(15,'阳江市',20);
insert into City values(16,'清远市',20);
insert into City values(17,'东莞市',20);
insert into City values(18,'中山市',20);
insert into City values(19,'潮州市',20);
insert into City values(20,'揭阳市',20);
insert into City values(21,'云浮市',20);
--  select * from City where pid=20 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  21甘肃省 12个地级市、2个自治州
insert into City values(1,'兰州市',21);
insert into City values(2,'金昌市',21);
insert into City values(3,'白银市',21);
insert into City values(4,'天水市',21);
insert into City values(5,'嘉峪关市',21);
insert into City values(6,'武威市',21);
insert into City values(7,'张掖市',21);
insert into City values(8,'平凉市',21);
insert into City values(9,'酒泉市',21);
insert into City values(10,'庆阳市',21);
insert into City values(11,'定西市',21);
insert into City values(12,'陇南市',21);
insert into City values(13,'临夏回族自治州',21);
insert into City values(14,'甘南藏族自治州',21);
--  select * from City where pid=21 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  22四川省18个地级市、3个自治州
insert into City values(1,'成都市',22);
insert into City values(2,'自贡市',22);
insert into City values(3,'攀枝花市',22);
insert into City values(4,'泸州市',22);
insert into City values(5,'德阳市',22);
insert into City values(6,'绵阳市',22);
insert into City values(7,'广元市',22);
insert into City values(8,'遂宁市',22);
insert into City values(9,'内江市',22);
insert into City values(10,'乐山市',22);
insert into City values(11,'南充市',22);
insert into City values(12,'眉山市',22);
insert into City values(13,'宜宾市',22);
insert into City values(14,'广安市',22);
insert into City values(15,'达州市',22);
insert into City values(16,'雅安市',22);
insert into City values(17,'巴中市',22);
insert into City values(18,'资阳市',22);
insert into City values(19,'阿坝藏族羌族自治州',22);
insert into City values(20,'甘孜藏族自治州',22);
insert into City values(21,'凉山彝族自治州',22);
--  select * from City where pid=22 order by cid
--  -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
--  23贵州省(2006年辖：4个地级市、2个地区、3个自治州；10个市辖区、9个县级市、56个县、11个自治县、2个特区。);
insert into City values(1,'贵阳市',23);
insert into City values(2,'六盘水市',23);
insert into City values(3,'遵义市',23);
insert into City values(4,'安顺市',23);
insert into City values(5,'铜仁地区',23);
insert into City values(6,'毕节地区',23);
insert into City values(7,'黔西南布依族苗族自治州',23);
insert into City values(8,'黔东南苗族侗族自治州',23);
insert into City values(9,'黔南布依族苗族自治州',23);
-- select * from City where pid=23 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 24海南省全省有2个地级市，6个县级市，4个县，6个民族自治县，4个市辖区，1个办事处（西南中沙群岛办事处 ，县级）。);
insert into City values(1,'海口市',24);
insert into City values(2,'三亚市',24);
insert into City values(3,'五指山市',24);
insert into City values(4,'琼海市',24);
insert into City values(5,'儋州市',24);
insert into City values(6,'文昌市',24);
insert into City values(7,'万宁市',24);
insert into City values(8,'东方市',24);
insert into City values(9,'澄迈县',24);
insert into City values(10,'定安县',24);
insert into City values(11,'屯昌县',24);
insert into City values(12,'临高县',24);
insert into City values(13,'白沙黎族自治县',24);
insert into City values(14,'昌江黎族自治县',24);
insert into City values(15,'乐东黎族自治县',24);
insert into City values(16,'陵水黎族自治县',24);
insert into City values(17,'保亭黎族苗族自治县',24);
insert into City values(18,'琼中黎族苗族自治县',24);
-- select * from City where pid=24 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 25云南省(2006年辖：8个地级市、8个自治州；12个市辖区、9个县级市、79个县、29个自治县。);
insert into City values(1,'昆明市',25);
insert into City values(2,'曲靖市',25);
insert into City values(3,'玉溪市',25);
insert into City values(4,'保山市',25);
insert into City values(5,'昭通市',25);
insert into City values(6,'丽江市',25);
insert into City values(7,'思茅市',25);
insert into City values(8,'临沧市',25);
insert into City values(9,'文山壮族苗族自治州',25);
insert into City values(10,'红河哈尼族彝族自治州',25);
insert into City values(11,'西双版纳傣族自治州',25);
insert into City values(12,'楚雄彝族自治州',25);
insert into City values(13,'大理白族自治州',25);
insert into City values(14,'德宏傣族景颇族自治州',25);
insert into City values(15,'怒江傈傈族自治州',25);
insert into City values(16,'迪庆藏族自治州',25);
-- select * from City where pid=25 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 26青海省(2006年辖：1个地级市、1个地区、6个自治州；4个市辖区、2个县级市、30个县、7个自治县。);
insert into City values(1,'西宁市',26);
insert into City values(2,'海东地区',26);
insert into City values(3,'海北藏族自治州',26);
insert into City values(4,'黄南藏族自治州',26);
insert into City values(5,'海南藏族自治州',26);
insert into City values(6,'果洛藏族自治州',26);
insert into City values(7,'玉树藏族自治州',26);
insert into City values(8,'海西蒙古族藏族自治州',26);
-- select * from City where pid=26 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 27陕西省(2006年辖：10个地级市；24个市辖区、3个县级市、80个县。);
insert into City values(1,'西安市',27);
insert into City values(2,'铜川市',27);
insert into City values(3,'宝鸡市',27);
insert into City values(4,'咸阳市',27);
insert into City values(5,'渭南市',27);
insert into City values(6,'延安市',27);
insert into City values(7,'汉中市',27);
insert into City values(8,'榆林市',27);
insert into City values(9,'安康市',27);
insert into City values(10,'商洛市',27);
-- select * from City where pid=27 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 28广西壮族自治区(2005年辖：14个地级市；34个市辖区、7个县级市、56个县、12个自治县。);
insert into City values(1,'南宁市',28);
insert into City values(2,'柳州市',28);
insert into City values(3,'桂林市',28);
insert into City values(4,'梧州市',28);
insert into City values(5,'北海市',28);
insert into City values(6,'防城港市',28);
insert into City values(7,'钦州市',28);
insert into City values(8,'贵港市',28);
insert into City values(9,'玉林市',28);
insert into City values(10,'百色市',28);
insert into City values(11,'贺州市',28);
insert into City values(12,'河池市',28);
insert into City values(13,'来宾市',28);
insert into City values(14,'崇左市',28);
-- select * from City where pid=28 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 29西藏自治区(2005年辖：1个地级市、6个地区；1个市辖区、1个县级市、71个县。);
insert into City values(1,'拉萨市',29);
insert into City values(2,'那曲地区',29);
insert into City values(3,'昌都地区',29);
insert into City values(4,'山南地区',29);
insert into City values(5,'日喀则地区',29);
insert into City values(6,'阿里地区',29);
insert into City values(7,'林芝地区',29);
-- select * from City where pid=29 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 30宁夏回族自治区
insert into City values(1,'银川市',30);
insert into City values(2,'石嘴山市',30);
insert into City values(3,'吴忠市',30);
insert into City values(4,'固原市',30);
insert into City values(5,'中卫市',30);
-- select * from City where pid=30 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 31新疆维吾尔自治区(2005年辖：2个地级市、7个地区、5个自治州；11个市辖区、20个县级市、62个县、6个自治县);
insert into City values(1,'乌鲁木齐市',31);
insert into City values(2,'克拉玛依市',31);
insert into City values(3,'石河子市　',31);
insert into City values(4,'阿拉尔市',31);
insert into City values(5,'图木舒克市',31);
insert into City values(6,'五家渠市',31);
insert into City values(7,'吐鲁番市',31);
insert into City values(8,'阿克苏市',31);
insert into City values(9,'喀什市',31);
insert into City values(10,'哈密市',31);
insert into City values(11,'和田市',31);
insert into City values(12,'阿图什市',31);
insert into City values(13,'库尔勒市',31);
insert into City values(14,'昌吉市　',31);
insert into City values(15,'阜康市',31);
insert into City values(16,'米泉市',31);
insert into City values(17,'博乐市',31);
insert into City values(18,'伊宁市',31);
insert into City values(19,'奎屯市',31);
insert into City values(20,'塔城市',31);
insert into City values(21,'乌苏市',31);
insert into City values(22,'阿勒泰市',31);
-- select * from City where pid=31 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 32内蒙古自治区(2006年，辖：9个地级市、3个盟；21个市辖区、11个县级市、17个县、49个旗、3个自治旗。);
insert into City values(1,'呼和浩特市',32);
insert into City values(2,'包头市',32);
insert into City values(3,'乌海市',32);
insert into City values(4,'赤峰市',32);
insert into City values(5,'通辽市',32);
insert into City values(6,'鄂尔多斯市',32);
insert into City values(7,'呼伦贝尔市',32);
insert into City values(8,'巴彦淖尔市',32);
insert into City values(9,'乌兰察布市',32);
insert into City values(10,'锡林郭勒盟',32);
insert into City values(11,'兴安盟',32);
insert into City values(12,'阿拉善盟',32);
-- select * from City where pid=32 order by
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 33澳门特别行政区
insert into City values(1,'澳门特别行政区',33);
-- select * from City where pid=33 order by cid
-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- --
-- 34香港特别行政区
insert into City values(1,'香港特别行政区',34);
-- select * from City where pid=34 order by cid