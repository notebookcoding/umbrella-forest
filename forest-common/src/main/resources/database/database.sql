
-- 创建区分字母大小写的数据库
CREATE DATABASE IF NOT EXISTS umbrelladb default charset utf8mb4 COLLATE utf8mb4_bin;

-- 创建任意IP均可以登录的用户
CREATE USER 'umbrellauser'@'%' IDENTIFIED BY '123456';

-- 给用户赋予oceandb数据库的所有的权限
GRANT ALL ON umbrelladb.* TO 'umbrellauser'@'%'  WITH GRANT OPTION;

-- 修改用户的认证规则 针对于高版本的mysql
ALTER USER 'umbrellauser'@'%' IDENTIFIED WITH mysql_native_password BY '123456';
-- 刷新权限
flush privileges;

-- 创建区分大小写的表
CREATE TABLE forest_animal
(
id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '森林动物表主键id',
name_cn VARCHAR(100) NOT NULL COMMENT '中文名称',
alias VARCHAR(100) DEFAULT NULL COMMENT '别称',
name_en VARCHAR(200) NOT NULL COMMENT '英文名称',
introduction VARCHAR(2000) DEFAULT NULL COMMENT '简单介绍',
image_path VARCHAR (200) DEFAULT NULL COMMENT '图片地址',
animal_morphology tinyint(2)  DEFAULT NULL COMMENT '动物形态:1.脊椎动物、2.无脊椎动物',
animal_morphology_name VARCHAR(50)  DEFAULT NULL COMMENT '动物形态名称:脊椎动物，无脊椎动物',
animal_category tinyint(2)  DEFAULT NULL COMMENT '动物类别:1.哺乳类、2.鸟类、3.爬行类、4.两栖类、5.鱼类、6.原生动物、7.扁形动物、8.腔肠动物、9.线形动物、10.环节动物、11.软体动物、12.棘皮动物、13.节肢动物',
animal_category_name VARCHAR(50)  DEFAULT NULL COMMENT '动物类别名称:哺乳类、鸟类、爬行类、两栖类、鱼类、原生动物、扁形动物、腔肠动物、线形动物、环节动物、软体动物、棘皮动物、节肢动物',
is_deleted tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
create_by bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '创建人',
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_by bigint(20) unsigned DEFAULT NULL COMMENT '更新人',
update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (id),
KEY index_name_cn (name_cn) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET = utf8mb4  COLLATE = utf8mb4_bin COMMENT '森林动物表';

-- 创建测试数据

INSERT INTO forest_animal (id, name_cn, alias, name_en, introduction, image_path, animal_morphology, animal_morphology_name, animal_category, animal_category_name, is_deleted, create_by, create_time, update_by, update_time) VALUES ('1', '狮子', '狮、狻猊', 'Lion', '食肉目、猫科、豹属的大型猛兽。简称狮，中国古称狻猊。是一种生存在非洲与亚洲的大型猫科动物，是现存平均体重最大的猫科动物，也是在世界上唯一一种雌雄两态的猫科动物。', '', '1', '脊椎动物', '1', '哺乳类', '0', '0', '2022-03-10 17:54:39', NULL, '2022-03-10 17:54:39');
INSERT INTO forest_animal (id, name_cn, alias, name_en, introduction, image_path, animal_morphology, animal_morphology_name, animal_category, animal_category_name, is_deleted, create_by, create_time, update_by, update_time) VALUES ('2', '虎', '老虎', 'Tiger', '哺乳纲的大型猫科动物；毛色浅黄或棕黄色，满身黑色横纹；头圆、耳短，耳背面黑色，中央有一白斑甚显著；四肢健壮有力；尾粗长，具黑色环纹，尾端黑色。共有九个亚种，各亚种之间的体型和形态差异很大。西伯利亚虎最大，雄性体长可达3.7米，体重423千克。苏门答腊虎是最小的活亚种，雄虎体长2.34米，体重136千克。', NULL, '1', '脊椎动物', '1', '哺乳类', '0', '0', '2022-03-10 17:56:31', NULL, '2022-03-10 17:57:36');
INSERT INTO forest_animal (id, name_cn, alias, name_en, introduction, image_path, animal_morphology, animal_morphology_name, animal_category, animal_category_name, is_deleted, create_by, create_time, update_by, update_time) VALUES ('3', '孔雀', '孔雀', 'Peafowl', '仅2属3种。孔雀属包括2种，全长达2米以上，其中尾屏约1.5米，为鸡形目体型最大者。头顶翠绿，羽冠蓝绿而呈尖形；尾上覆羽特别长，形成尾屏，鲜艳美丽；真正的尾羽很短，呈黑褐色。雌鸟无尾屏，羽色暗褐而多杂斑。', NULL, '1', '脊椎动物', '2', '鸟类', '0', '0', '2022-03-10 17:59:29', NULL, '2022-03-10 17:59:29');
INSERT INTO forest_animal (id, name_cn, alias, name_en, introduction, image_path, animal_morphology, animal_morphology_name, animal_category, animal_category_name, is_deleted, create_by, create_time, update_by, update_time) VALUES ('4', '红腹锦鸡', '金鸡', 'Chrysolophus pictus', '中型鸡类，体长59-110厘米。尾特长，约38-42厘米。雄鸟羽色华丽，头具金黄色丝状羽冠，上体除上背浓绿色外，其余为金黄色，后颈被有橙棕色而缀有黑边的扇状羽，形成披肩状。下体深红色，尾羽黑褐色，满缀以桂黄色斑点。雌鸟头顶和后颈黑褐色，其余体羽棕黄色，满缀以黑褐色虫蠢状斑和横斑。脚黄色。野外特征极明显，全身羽毛颜色互相衬托，赤橙黄绿青蓝紫具全，光彩夺目，是驰名中外的观赏鸟类。', NULL, '1', '脊椎动物', '2', '鸟类', '0', '0', '2022-03-10 18:06:12', NULL, '2022-03-10 18:06:27');
INSERT INTO forest_animal (id, name_cn, alias, name_en, introduction, image_path, animal_morphology, animal_morphology_name, animal_category, animal_category_name, is_deleted, create_by, create_time, update_by, update_time) VALUES ('5', '蝴蝶', '蝴蝶', 'butterfly', '昆虫纲（Insecta），类脉总目（Amphiesmenoptera），鳞翅目（Lepidoptera）中一类昆虫的统称，全世界已记载近2万种，中国的蝴蝶资源较为丰富，已记录2000多种。大部分蝴蝶触角为棒状或锤状，细长，底部略有加粗白天活动，两翅连锁器为翅抱，身体相对纤细。\r\n蝴蝶被誉为“会飞的花朵”，是一类非常美丽的昆虫。蝴蝶大多数体型属于中型至大型，翅展在15~260毫米之间，有2对膜质的翅。体躯长圆柱形，分为头、胸、腹三部分。体及翅膜上覆有鳞片及毛，形成各种色彩斑纹。', NULL, '2', '无脊椎动物', '13', '节肢动物', '0', '0', '2022-03-10 18:09:25', NULL, '2022-03-10 18:13:21');

