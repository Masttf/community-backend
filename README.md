## TODO

- [ ] 发布评论，多张图片
- [ ] markdown上传图片，图片替换，测试

## Mysql数据库

建表

```sql
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for email_code
-- ----------------------------
DROP TABLE IF EXISTS `email_code`;
CREATE TABLE `email_code` (
  `email` varchar(150) NOT NULL COMMENT '邮箱',
  `code` varchar(5) NOT NULL COMMENT '编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(1) DEFAULT NULL COMMENT '0:未使用  1:已使用',
  PRIMARY KEY (`email`,`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮箱验证码';


-- ----------------------------
-- Table structure for forum_article
-- ----------------------------
DROP TABLE IF EXISTS `forum_article`;
CREATE TABLE `forum_article` (
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `board_id` int(11) DEFAULT NULL COMMENT '板块ID',
  `board_name` varchar(50) DEFAULT NULL COMMENT '板块名称',
  `p_board_id` int(11) DEFAULT NULL COMMENT '父级板块ID',
  `p_board_name` varchar(50) DEFAULT NULL COMMENT '父板块名称',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `user_ip_address` varchar(100) DEFAULT NULL COMMENT '最后登录ip地址',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `cover` varchar(100) DEFAULT NULL COMMENT '封面',
  `content` text COMMENT '内容',
  `markdown_content` text COMMENT 'markdown内容',
  `editor_type` tinyint(4) NOT NULL COMMENT '0:富文本编辑器 1:markdown编辑器',
  `summary` varchar(200) DEFAULT NULL COMMENT '摘要',
  `post_time` datetime NOT NULL COMMENT '发布时间',
  `last_update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `read_count` int(11) DEFAULT '0' COMMENT '阅读数量',
  `good_count` int(11) DEFAULT '0' COMMENT '点赞数',
  `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
  `top_type` tinyint(4) DEFAULT '0' COMMENT '0未置顶  1:已置顶',
  `attachment_type` tinyint(4) DEFAULT NULL COMMENT '0:没有附件  1:有附件',
  `status` tinyint(4) DEFAULT NULL COMMENT '-1已删除 0:待审核  1:已审核 ',
  PRIMARY KEY (`article_id`),
  KEY `idx_board_id` (`board_id`),
  KEY `idx_pboard_id` (`p_board_id`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_top_type` (`top_type`),
  KEY `idx_title` (`title`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章信息';


-- ----------------------------
-- Table structure for forum_article_attachment
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_attachment`;
CREATE TABLE `forum_article_attachment` (
  `file_id` varchar(15) NOT NULL COMMENT '文件ID',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `user_id` varchar(15) DEFAULT NULL COMMENT '用户id',
  `file_size` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `file_name` varchar(200) DEFAULT NULL COMMENT '文件名称',
  `download_count` int(11) DEFAULT NULL COMMENT '下载次数',
  `file_path` varchar(100) DEFAULT NULL COMMENT '文件路径',
  `file_type` tinyint(4) DEFAULT NULL COMMENT '文件类型',
  `integral` int(11) DEFAULT NULL COMMENT '下载所需积分',
  PRIMARY KEY (`file_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息';


-- ----------------------------
-- Table structure for forum_article_attachment_download
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_attachment_download`;
CREATE TABLE `forum_article_attachment_download` (
  `file_id` varchar(15) NOT NULL COMMENT '文件ID',
  `user_id` varchar(15) NOT NULL COMMENT '用户id',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `download_count` int(11) DEFAULT '1' COMMENT '文件下载次数',
  PRIMARY KEY (`file_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户附件下载';


-- ----------------------------
-- Table structure for forum_board
-- ----------------------------
DROP TABLE IF EXISTS `forum_board`;
CREATE TABLE `forum_board` (
  `board_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '板块ID',
  `p_board_id` int(11) DEFAULT NULL COMMENT '父级板块ID',
  `board_name` varchar(50) DEFAULT NULL COMMENT '板块名',
  `cover` varchar(50) DEFAULT NULL COMMENT '封面',
  `board_desc` varchar(150) DEFAULT NULL COMMENT '描述',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `post_type` tinyint(1) DEFAULT '1' COMMENT '0:只允许管理员发帖 1:任何人可以发帖',
  PRIMARY KEY (`board_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8mb4 COMMENT='文章板块信息';


-- ----------------------------
-- Table structure for forum_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_comment`;
CREATE TABLE `forum_comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `p_comment_id` int(11) DEFAULT NULL COMMENT '父级评论ID',
  `article_id` varchar(15) NOT NULL COMMENT '文章ID',
  `content` varchar(800) DEFAULT NULL COMMENT '回复内容',
  `img_path` varchar(150) DEFAULT NULL COMMENT '图片',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `user_ip_address` varchar(100) DEFAULT NULL COMMENT '用户ip地址',
  `reply_user_id` varchar(15) DEFAULT NULL COMMENT '回复人ID',
  `reply_nick_name` varchar(20) DEFAULT NULL COMMENT '回复人昵称',
  `top_type` tinyint(4) DEFAULT '0' COMMENT '0:未置顶  1:置顶',
  `post_time` datetime DEFAULT NULL COMMENT '发布时间',
  `good_count` int(11) DEFAULT '0' COMMENT 'good数量',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:待审核  1:已审核',
  PRIMARY KEY (`comment_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_post_time` (`post_time`),
  KEY `idx_top` (`top_type`),
  KEY `idx_p_id` (`p_comment_id`),
  KEY `idx_status` (`status`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10004 DEFAULT CHARSET=utf8mb4 COMMENT='评论';


-- ----------------------------
-- Table structure for like_record
-- ----------------------------
DROP TABLE IF EXISTS `like_record`;
CREATE TABLE `like_record` (
  `op_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `op_type` tinyint(4) DEFAULT NULL COMMENT '操作类型0:文章点赞 1:评论点赞',
  `object_id` varchar(15) NOT NULL COMMENT '主体ID',
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `author_user_id` varchar(15) DEFAULT NULL COMMENT '主体作者ID',
  PRIMARY KEY (`op_id`),
  UNIQUE KEY `idx_key` (`object_id`,`user_id`,`op_type`) USING BTREE,
  KEY `idx_user_id` (`user_id`,`op_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COMMENT='点赞记录';


-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `code` varchar(10) NOT NULL COMMENT '编号',
  `json_content` varchar(500) DEFAULT NULL COMMENT '设置信息',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置信息';


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` varchar(15) NOT NULL COMMENT '用户ID',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `email` varchar(150) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT NULL COMMENT '0:女 1:男',
  `person_description` varchar(200) DEFAULT NULL COMMENT '个人描述',
  `join_time` datetime DEFAULT NULL COMMENT '加入时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(15) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_ip_address` varchar(100) DEFAULT NULL COMMENT '最后登录ip地址',
  `total_integral` int(11) DEFAULT NULL COMMENT '积分',
  `current_integral` int(11) DEFAULT NULL COMMENT '当前积分',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:禁用 1:正常',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `key_email` (`email`),
  UNIQUE KEY `key_nick_name` (`nick_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';


-- ----------------------------
-- Table structure for user_integral_record
-- ----------------------------
DROP TABLE IF EXISTS `user_integral_record`;
CREATE TABLE `user_integral_record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` varchar(15) DEFAULT NULL COMMENT '用户ID',
  `oper_type` tinyint(4) DEFAULT NULL COMMENT '操作类型',
  `integral` int(11) DEFAULT NULL COMMENT '积分',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10026 DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `received_user_id` varchar(15) DEFAULT NULL COMMENT '接收人用户ID',
  `article_id` varchar(15) DEFAULT NULL COMMENT '文章ID',
  `article_title` varchar(150) DEFAULT NULL COMMENT '文章标题',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论ID',
  `send_user_id` varchar(15) DEFAULT NULL COMMENT '发送人用户ID',
  `send_nick_name` varchar(20) DEFAULT NULL COMMENT '发送人昵称',
  `message_type` tinyint(4) DEFAULT NULL COMMENT '0:系统消息 1:评论 2:文章点赞  3:评论点赞 4:附件下载',
  `message_content` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '1:未读 2:已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`message_id`),
  UNIQUE KEY `idx_key` (`article_id`,`comment_id`,`send_user_id`,`message_type`) USING BTREE,
  KEY `idx_received_user_id` (`received_user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_type` (`message_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10005 DEFAULT CHARSET=utf8mb4 COMMENT='用户消息';

```



## 接口文档

### 状态码

| ode状态码 | 说明                         |
| --------- | ---------------------------- |
| 200       | 请求成功                     |
| 404       | 请求地址不存在               |
| 600       | 请求参数错误                 |
| 601       | 信息已经存在，重复提交       |
| 900       | http请求超时                 |
| 901       | 登录超时                     |
| 500       | 服务器返回错误，请联系管理员 |

> [!NOTE]
>
> 考虑返回长度影响观感，这里列表的只截取一部分
>
> 没特殊说明的，都是一页15条

### 访客端

#### 获取所有板块

- 接口地址 /board/loadBoard

- 是否需要登录

  否

- 请求参数
  无

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": [
  		{
  			"boardId": 10002,
  			"pBoardId": 0,
  			"boardName": "摸鱼",
  			"cover": null,
  			"boardDesc": "摸鱼",
  			"sort": 3,
  			"postType": 1,
  			"children": [],
  			"pboardId": 0
  		},
  		{
  			"boardId": 10006,
  			"pBoardId": 0,
  			"boardName": "社区管理",
  			"cover": null,
  			"boardDesc": "社区管理",
  			"sort": 4,
  			"postType": 0,
  			"children": [
  				{
  					"boardId": 10007,
  					"pBoardId": 10006,
  					"boardName": "规章制度",
  					"cover": null,
  					"boardDesc": "规章制度",
  					"sort": 1,
  					"postType": 0,
  					"children": [],
  					"pboardId": 10006
  				}
  			],
  			"pboardId": 0
  		}
  	]
  }
  ```


#### 获取验证码

- 接口地址 `/api/checkCode`

- 是否需要登录

  否

- 请求参数

  | 参数名 | 说明                                                         | 是否必填 |
  | ------ | ------------------------------------------------------------ | -------- |
  | type   | 类型 0:登录/注册/重置密码验证码 1:发送邮箱验证码 默认是类型0 | 是       |

- 返回
  返回图片文件流，直接在浏览器中打开，或者在页面 < img src="/api/checkCode?type=0">这样就可以显示图片切换图片可以这样 < img src="/api/checkCode?type=0&time=123"> 在用户点击切换图片的时候，更改time为当前时间。


#### 发送邮箱验证码

- 接口地址 `/api/sendEmailCode`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明                   | 是否必填 |
  | --------- | ---------------------- | -------- |
  | email     | 注册邮箱               | 是       |
  | checkCode | 图片验证码             | 是       |
  | type      | 类型 0:注册 1:找回密码 | 是       |


- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 注册

- 接口地址 `/api/register`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明       | 是否必填 |
  | --------- | ---------- | -------- |
  | email     | 注册邮箱   | 是       |
  | nickName  | 昵称       | 是       |
  | password  | 原始传输   | 是       |
  | emailCode | 邮箱验证码 | 是       |
  | checkCode | 图片验证码 | 是       |


- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

  > [!NOTE]
  >
  > 密码8-18位
  >
  > 至少包含一位数字 至少包含一个字母

#### 登录

- 接口地址 `/api/login`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明            | 是否必填 |
  | --------- | --------------- | -------- |
  | email     | 注册邮箱        | 是       |
  | password  | 密码md5之后传输 | 是       |
  | checkCode | 图片验证码      | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"nickName": "Masttf",
  		"provice": "浙江省",
  		"userId": "3782512644",
  		"isAdmin": false
  	}
  }
  ```

#### 重置密码

- 接口地址 `/api/resetPwd`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明       | 是否必填 |
  | --------- | ---------- | -------- |
  | email     | 注册邮箱   | 是       |
  | password  | 原始传输   | 是       |
  | checkCode | 图片验证码 | 是       |
  | emailCode | 邮箱验证码 | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 获取登录用户信息

- 接口地址 `/api/getUserInfo`

- 是否需要登录

  否

- 请求参数
  无

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"nickName": "Masttf",
  		"provice": "浙江省",
  		"userId": "3782512644",
  		"isAdmin": false
  	}
  }
  ```

#### 退出登录

- 接口地址 `/api/logout`

- 是否需要登录

  否

- 请求参数
  无

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 获取系统设置

- 接口地址 `/api/getSysSetting`

- 是否需要登录

  否

- 请求参数
  无

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"commentOpen": true
  	}
  }
  ```


#### 上传临时图片

- 接口地址 `/api/file/uploadImage`

- 是否需要登录

  否

- 请求参数

  | 参数名 | 说明   | 是否必填 |
  | ------ | ------ | -------- |
  | file   | 文件流 | 是       |


- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"localPath": "temp/xB4vXFhhY0YropUY4TAnKsa3bwnJuW.jpg",
  		"originalFileName": "avatar.jpg"
  	}
  }
  ```

#### 获取图片

- 接口地址 `/api/file/getImage/{imageFolder}/{imageName}`

- 是否需要登录

  否

- 请求参数

  | 参数名      | 说明     | 是否必填 |
  | ----------- | -------- | -------- |
  | imageFolder | 文件目录 | 是       |
  | imageName   | 文件名   | 是       |

- 返回

  图片文件流 直接在< img src="/api/file/getImage/202301/xxx.jpg"/> 即可

#### 获取头像

- 接口地址 `/api/file/getAvatar/{userId}`

- 是否需要登录

  否

- 请求参数

  | 参数名 | 说明   | 是否必填 |
  | ------ | ------ | -------- |
  | userId | 用户ID | 是       |


- > [!NOTE]
  >
  > 如果没有头像会返回默认头像 `default.webp`

- 返回
  图片文件流 直接在< img src="/file/getAvatar/1211212"/> 即可

#### 获取文章评论

- 接口地址 `/api/comment/loadComment`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明                                         | 是否必填  |
  | --------- | -------------------------------------------- | --------- |
  | articleId | 文章ID                                       | 是        |
  | pageNo    | 页码                                         | 否 默认1  |
  | orderType | 排序类型 0:根据火热程度排序 1:根据时间倒序排 | 否 默认 0 |

  > [!NOTE]
  >
  > 固定一次是50条

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"totalCount": 14,
  		"pageSize": 50,
  		"pageNo": 1,
  		"pageTotal": 1,
  		"list": [
  			{
  				"commentId": 10019,
  				"pCommentId": 0,
  				"articleId": "RtiXj832TFL4nhW",
  				"content": null,
  				"imgPath": "202505/It6jn58X4wMwI3hOWYuRmxpBSrq98j.jpg",
  				"userId": "3782512644",
  				"nickName": "Masttf",
  				"userIpAddress": "浙江省",
  				"replyUserId": null,
  				"replyNickName": null,
  				"topType": 0,
  				"postTime": "2025-05-17 19:21:49",
  				"goodCount": 0,
  				"likeType": null,
  				"children": null
  			},
  			
        
  					{
  						"commentId": 10015,
  						"pCommentId": 10000,
  						"articleId": "RtiXj832TFL4nhW",
  						"content": "这是发布图片测试",
  						"imgPath": null,
  						"userId": "3782512644",
  						"nickName": "Masttf",
  						"userIpAddress": "浙江省",
  						"replyUserId": null,
  						"replyNickName": null,
  						"topType": 0,
  						"postTime": "2025-05-15 19:56:26",
  						"goodCount": 0,
  						"likeType": null,
  						"children": null
  					}
  				]
  			}
  		]
  	}
  }
  ```

#### 评论点赞

- 接口地址 `/api/comment/doLike`

- 是否需要登录

  是

- 请求参数

  | 参数名    | 说明   | 是否必填 |
  | --------- | ------ | -------- |
  | commentId | 评论ID | 是       |

- 返回

  ```
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"commentId": 10004,
  		"pCommentId": 10000,
  		"articleId": "RtiXj832TFL4nhW",
  		"content": "test",
  		"imgPath": null,
  		"userId": "1890524956",
  		"nickName": "测试账号",
  		"userIpAddress": "未知",
  		"replyUserId": null,
  		"replyNickName": null,
  		"topType": 0,
  		"postTime": "2025-05-11 14:50:23",
  		"goodCount": 0,
  		"likeType": 0,
  		"children": null
  	}
  }
  ```

#### 置顶/取消置顶

- 接口地址 `/api/comment/changeTopType`

- 是否需要登录

  是

- 请求参数

  | 参数名    | 说明                       | 是否必填 |
  | --------- | -------------------------- | -------- |
  | commentId | 评论ID                     | 是       |
  | topType   | 置顶类型 0:取消置顶 1:置顶 | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 发布评论

- 接口地址 `/api/comment/postComment`

- 是否需要登录

  是

- 请求参数

  | 参数名      | 说明       | 是否必填             |
  | ----------- | ---------- | -------------------- |
  | articleId   | 文章ID     | 是                   |
  | pCommentId  | 父级评论   | 是 一级评论传0       |
  | content     | 评论内容   | 图片和内容必须有一个 |
  | image       | 图片文件流 | 图片和内容必须有一个 |
  | replyUserId | 回复他人ID | 否                   |

  > [!NOTE]
  >
  > 评论长度5-800

- 返回

  ```json
  //一级评论返回本身
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"commentId": 10020,
  		"pCommentId": 0,
  		"articleId": "RtiXj832TFL4nhW",
  		"content": null,
  		"imgPath": "202505/u7o6y0dO7QjbBTLV48gfDJ2qB0feHy.jpg",
  		"userId": "1890524956",
  		"nickName": "测试账号",
  		"userIpAddress": "浙江省",
  		"replyUserId": null,
  		"replyNickName": null,
  		"topType": 0,
  		"postTime": "2025-05-17 20:12:38",
  		"goodCount": null,
  		"likeType": null,
  		"children": null
  	}
  }
  //二级评论返回一级评论的所有列表
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": [
  		{
  			"commentId": 10015,
  			"pCommentId": 10000,
  			"articleId": "RtiXj832TFL4nhW",
  			"content": "这是发布图片测试",
  			"imgPath": null,
  			"userId": "3782512644",
  			"nickName": "Masttf",
  			"userIpAddress": "浙江省",
  			"replyUserId": null,
  			"replyNickName": null,
  			"topType": 0,
  			"postTime": "2025-05-15 19:56:26",
  			"goodCount": 0,
  			"likeType": null,
  			"children": null
  		},
  		{
  			"commentId": 10021,
  			"pCommentId": 10000,
  			"articleId": "RtiXj832TFL4nhW",
  			"content": null,
  			"imgPath": "202505/0J8h3ABPmUnwPXAZ1hqJoSDth42wiH.jpg",
  			"userId": "1890524956",
  			"nickName": "测试账号",
  			"userIpAddress": "浙江省",
  			"replyUserId": null,
  			"replyNickName": null,
  			"topType": 0,
  			"postTime": "2025-05-17 20:13:32",
  			"goodCount": 0,
  			"likeType": null,
  			"children": null
  		}
  	]
  }
  ```

#### 获取文章列表

- 接口地址 `/api/forum/loadArticle`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明                       | 是否必填 |
  | --------- | -------------------------- | -------- |
  | pBoardId  | 父级板块ID                 | 否       |
  | boardId   | 板块ID                     | 否       |
  | orderType | 0:热门1:发布时间2:最新发布 | 否       |
  | pageNo    | 页码                       | 否 默认1 |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"totalCount": 1,
  		"pageSize": 1,
  		"pageNo": 1,
  		"pageTotal": 1,
  		"list": [
  			{
  				"articleId": "rRnX4Oz6vHihElY",
  				"boardId": 10003,
  				"boardName": "Vue",
  				"pBoardId": 10000,
  				"pBoardName": "前端",
  				"userId": "1890524956",
  				"nickName": "测试账号",
  				"userIpAddress": "未知",
  				"title": "响应式基础",
  				"cover": null,
  				"content": null,
  				"markdownContent": null,
  				"summary": null,
  				"postTime": "2023-01-16 09:34:34",
  				"readCount": 1,
  				"goodCount": 0,
  				"commentCount": 0,
  				"topType": 0,
  				"attachmentType": 0,
  				"editorType": 1
  			}
  		]
  	}
  }
  ```

#### 获取文章详情

- 接口地址 `/api/forum/getArticleDetail`

- 是否需要登录

  否

- 请求参数

  | 参数名    | 说明   | 是否必填 |
  | --------- | ------ | -------- |
  | articleId | 文章ID | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"article": {
  			"articleId": "RtiXj832TFL4nhW",
  			"boardId": 10003,
  			"boardName": "Vue",
  			"pBoardId": 10000,
  			"pBoardName": "前端",
  			"userId": "1890524956",
  			"nickName": "测试账号",
  			"userIpAddress": "未知",
  			"title": "第一个帖子，带图，带附件",
  			"cover": "202301/8Hyca1SDrUWhBRy.jpeg",
  			"content": "<p data-v-md-line=\"1\">第一个帖子，下面是图片<br>\r\n<img src=\"/api/file/getImage/202301/F9TerZiONdqrZBk5o3NDqeRJuIlTaP.jpeg\" alt=\"图片\"></p>\r\n",
  			"markdownContent": "第一个帖子，下面是图片\r\n![图片](/api/file/getImage/202301/F9TerZiONdqrZBk5o3NDqeRJuIlTaP.jpeg)",
  			"summary": "第一个帖子，带图，带附件，这里是摘要",
  			"postTime": "2023-01-15 18:01:23",
  			"readCount": 103,
  			"goodCount": 1,
  			"commentCount": 20,
  			"topType": 0,
  			"attachmentType": 1,
  			"editorType": 1
  		},
  		"articleAttachments": {
  			"fileId": "014569155409431",
  			"fileSize": 425672,
  			"fileName": "帅照.zip",
  			"downloadCount": 0,
  			"fileType": 0,
  			"integral": 1
  		},
  		"haveLike": false
  	}
  }
  ```

#### 点赞文章

- 接口地址 `/api/forum/doLike`

- 是否需要登录

  是

- 请求参数

  | 参数名    | 说明   | 是否必填 |
  | --------- | ------ | -------- |
  | articleId | 文章ID | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 获取用户附件下载详情

- 接口地址 `/api/forum/getUserDownloadInfo`

- 是否需要登录

  是

- 请求参数

  | 参数名 | 说明   | 是否必填 |
  | ------ | ------ | -------- |
  | fileId | 附件ID | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"userIntegral": 16,
  		"haveDownload": false
  	}
  }
  ```

#### 下载附件

- 接口地址 `/api/forum/attachmentDownload`

- 是否需要登录

  是

- 请求参数

  | 参数名 | 说明   | 是否必填 |
  | ------ | ------ | -------- |
  | fileId | 附件ID | 是       |

- 返回
  返回文件流，直接在新窗口中打开该窗口即可下载

#### 发布文章获取板块

- 接口地址 `/api/forum/loadBoard4Post`

- 是否需要登录

  是

- 请求参数
  无

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": [
  		{
  			"boardId": 10000,
  			"pBoardId": 0,
  			"boardName": "前端",
  			"cover": null,
  			"boardDesc": "前端",
  			"sort": 1,
  			"postType": 1,
  			"children": [
  				{
  					"boardId": 10003,
  					"pBoardId": 10000,
  					"boardName": "Vue",
  					"cover": null,
  					"boardDesc": "Vue",
  					"sort": 1,
  					"postType": 1,
  					"children": [],
  					"pboardId": 10000
  				},
  				{
  					"boardId": 10004,
  					"pBoardId": 10000,
  					"boardName": "React",
  					"cover": null,
  					"boardDesc": "React",
  					"sort": 2,
  					"postType": 1,
  					"children": [],
  					"pboardId": 10000
  				}
  			],
  			"pboardId": 0
  		},
  		{
  			"boardId": 10001,
  			"pBoardId": 0,
  			"boardName": "后端",
  			"cover": null,
  			"boardDesc": "后端",
  			"sort": 2,
  			"postType": 1,
  			"children": [
  				{
  					"boardId": 10005,
  					"pBoardId": 10001,
  					"boardName": "React",
  					"cover": null,
  					"boardDesc": "React",
  					"sort": 1,
  					"postType": 1,
  					"children": [],
  					"pboardId": 10001
  				}
  			],
  			"pboardId": 0
  		}
  	]
  }
  ```

#### 发布文章

- 接口地址 `/api/forum/postArticle`

- 是否需要登录

  是

- 请求参数

  | 参数名          | 说明                           | 是否必填               |
  | --------------- | ------------------------------ | ---------------------- |
  | cover           | 封面，文件流                   | 否                     |
  | attachment      | 附件，文件流                   | 否                     |
  | integral        | 附件下载所需积分               | 否                     |
  | pBoardId        | 父级板块ID                     | 是                     |
  | boardId         | 板块ID                         | 否                     |
  | title           | 标题  长度 150                 | 是                     |
  | content         | 内容                           | 是                     |
  | markdownContent | markdown内容                   | markdown编辑的时候必传 |
  | editorType      | 编辑器类型 0:富文本 1:markdown | 是                     |
  | summary         | 简介  长度200                  | 否                     |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"articleId": "6wW7MbKFN4"
  	}
  }
  ```

#### 修改文章获取详情

- 接口地址 `/api/forum/articleDetail4Update`

- 是否需要登录

  是

- 请求参数

  | 参数名    | 说明   | 是否必填 |
  | --------- | ------ | -------- |
  | articleId | 文章ID | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"article": {
  			"articleId": "69tCu71FeD",
  			"boardId": 10003,
  			"boardName": "Vue",
  			"pBoardId": 10000,
  			"pBoardName": "前端",
  			"userId": "3782512644",
  			"nickName": "Masttf",
  			"userIpAddress": "浙江省",
  			"title": "Mastset",
  			"cover": "202505/u0MDB2LQ3PE3aVMM4IVpGXna1Qspvl.jpeg",
  			"content": "hello,thisitest",
  			"markdownContent": null,
  			"summary": "estttfdsfs",
  			"postTime": "2025-05-17 21:22:30",
  			"readCount": 1,
  			"goodCount": 0,
  			"commentCount": 0,
  			"topType": 0,
  			"attachmentType": 1,
  			"editorType": 0
  		},
  		"articleAttachments": {
  			"fileId": "Hhyg7wZDKU",
  			"fileSize": 15105,
  			"fileName": "组队名单.xlsx",
  			"downloadCount": 0,
  			"fileType": 2,
  			"integral": 0
  		},
  		"haveLike": false
  	}
  }
  ```

#### 修改文章

- 接口地址 `/api/forum/updateArticle`

- 请求参数

  | 参数名          | 说明                           | 是否必填               |
  | --------------- | ------------------------------ | ---------------------- |
  | articleId       | 文章ID                         | 是                     |
  | cover           | 封面，文件流                   | 否                     |
  | attachment      | 附件，文件流                   | 否                     |
  | integral        | 附件下载所需积分               | 否                     |
  | pBoardId        | 父级板块ID                     | 是                     |
  | boardId         | 板块ID                         | 否                     |
  | title           | 标题  长度 150                 | 是                     |
  | content         | 内容                           | 是                     |
  | markdownContent | markdown内容                   | markdown编辑的时候必传 |
  | editorType      | 编辑器类型 0:富文本 1:markdown | 是                     |
  | summary         | 简介  长度200                  | 否                     |
  | attachmentType  | 0没有附件 1有附件              | 是                     |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"articleId": "69tCu71FeD"//原文章id
  	}
  }
  ```


#### 获取用户信息

- 接口地址 `/api/user/getUserInfo`

- 是否需要登录

  否

- 请求参数

  | 参数名 | 说明   | 是否必填 |
  | ------ | ------ | -------- |
  | userId | 用户ID | 是       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"userId": "1890524956",
  		"nickName": "测试账号",
  		"sex": 1,
  		"personDescription": "我只是一个测试账号而已",
  		"joinTime": "2023-01-15",
  		"lastLoginTime": "2025-05-17",
  		"currentIntegral": 16,
  		"postCount": 6,
  		"likeCount": 3
  	}
  }
  ```

#### 获取用户积分记录

- 接口地址 `/api/user/loadUserIntegralRecord`

- 是否需要登录

  是

- 请求参数

  | 参数名          | 说明     | 是否必填       |
  | --------------- | -------- | -------------- |
  | pageNo          | 页码     | 否，默认第一页 |
  | createTimeStart | 开始时间 | 否             |
  | createTimeEnd   | 结束时间 | 否             |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"totalCount": 27,
  		"pageSize": 15,
  		"pageNo": 1,
  		"pageTotal": 2,
  		"list": [
  			{
  				"userId": "3782512644",
  				"operType": 5,
  				"operTypeDesc": "发布文章",
  				"integral": 1,
  				"createTime": "2025-05-17"
  			},
  			{
  				"userId": "3782512644",
  				"operType": 4,
  				"operTypeDesc": "发布评论",
  				"integral": 1,
  				"createTime": "2025-05-15"
  			}
  		]
  	}
  }
  ```

#### 修改个人信息

- 接口地址 `/api/user/updateUserInfo`

- 是否需要登录

  是

- 请求参数

  | 参数名     | 说明             | 是否必填 |
  | ---------- | ---------------- | -------- |
  | sex        | 性别 0:女 1:男   | 否       |
  | personDesc | 个人描述 最大100 | 否       |
  | avatar     | 头像 图片文件流  | 否       |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": null
  }
  ```

#### 获取用户未读消息数

- 接口地址 `/api/user/getUserMessageCount`

- 是否需要登录

  是

- 请求参数
  无

- 返回

  ```json
  {
      "status":"success",
      "code":200,
      "msg":"请求成功",
      "data":{
          "total":3,//总数
          "sys":15,//系统消息数
          "reply":1,//评论消息数
          "likePost":1,//文章点赞数
          "likeComment":1,//评论点赞数
      }
  }    
  ```

#### 获取消息列表

- 接口地址 `/api/user/loadMessageList`

- 是否需要登录

  是

- 请求参数

  | 参数名 | 说明                                                         | 是否必填 |
  | ------ | ------------------------------------------------------------ | -------- |
  | code   | 编号 sys:系统消息 reply:评论消息 likePost:文章点赞数 likeComment:评论点赞数 downloadAttachment:下载附件 | 是       |
  | pageNo | 页码                                                         | 否 默认1 |

- 返回

  ```json
    {
        "status":"success",
        "code":200,
        "msg":"请求成功",
        "data":{
            "totalCount":3, //总记录数
            "pageSize":50,//分页大小
            "pageNo":1,//页码
            "pageTotal":1,//总页数
            "list":[
                {
                    "messageId":10003,//消息ID
                    "articleId":"a7x6Ebr5AQkwLL2",//消息ID
                    "articleTitle":"第一个帖子",//消息ID
                    "commentId":10003,//评论ID
                    "sendUserId":"1877685590",//发送人ID
                    "sendNickName":"lomo",//发送人昵称
                    "messageType":1,//消息类型 0:系统消息 1:评论 2:文章点赞  3:评论点赞
                    "messageContent":"真·福利",//消息内容
                    "createTime":"2023-01-02 15:34:07"//创建时间
                }
            ]
        }
    } 
  ```

#### 获取用户帖子情况

- 接口地址 `/api/user/loadUserArticle`

- 是否需要登录

  否

- 请求参数

  | 参数名 | 说明                                       | 是否必填 |
  | ------ | ------------------------------------------ | -------- |
  | userId | 用户ID                                     | 是       |
  | type   | 类型 0:发帖 1:评论过的文章  2:点赞过的文章 | 是       |
  | pageNo | 页码                                       | 否默认1  |

- 返回

  ```json
  {
  	"status": "success",
  	"code": 200,
  	"msg": "请求成功",
  	"data": {
  		"totalCount": 1,
  		"pageSize": 15,
  		"pageNo": 1,
  		"pageTotal": 1,
  		"list": [
  			{
  				"articleId": "RtiXj832TFL4nhW",
  				"boardId": 10003,
  				"boardName": "Vue",
  				"pBoardId": 10000,
  				"pBoardName": "前端",
  				"userId": "1890524956",
  				"nickName": "测试账号",
  				"userIpAddress": "未知",
  				"title": "第一个帖子，带图，带附件",
  				"cover": "202301/8Hyca1SDrUWhBRy.jpeg",
  				"content": null,
  				"markdownContent": null,
  				"summary": "第一个帖子，带图，带附件，这里是摘要",
  				"postTime": "2023-01-15 18:01:23",
  				"readCount": 103,
  				"goodCount": 0,
  				"commentCount": 20,
  				"topType": 0,
  				"attachmentType": 1,
  				"editorType": 1
  			}
  		]
  	}
  }
  ```

  