# QuizWeb
A web Application for quiz or questionnaire survey. Made by Spring + SpringMVC + Hibernate 

##使用说明：
1、部署war文件：

QuizWeb/QuizWeb1.0.war

2、配置以下路径的属性文件：

QuizWeb/src/main/resources/META-INF/properties/*

3、导入数据库模板文件：

QuizWeb/Dump20180318.sql

##功能介绍：
###1. 前端（用户界面）
1.1 打开首页，点击进入投票页面。加了些jQuery美化插件：
![ios视觉差效果（飞机在飞有木有）](https://upload-images.jianshu.io/upload_images/6240664-ed7d33ae875b4cdd.gif?imageMogr2/auto-orient/strip)
![粒子效果](https://upload-images.jianshu.io/upload_images/6240664-272909d0be7b7a58.gif?imageMogr2/auto-orient/strip)

1.2 提供侧边导航：
![导航栏](https://upload-images.jianshu.io/upload_images/6240664-be9d05fec3600f0d.gif?imageMogr2/auto-orient/strip)

1.3 提供辅助填表，一键全优：
![一键全优](https://upload-images.jianshu.io/upload_images/6240664-ba35e36f3e5b9e43.gif?imageMogr2/auto-orient/strip)

1.4 表单验证。用户点击提交按钮后，检查答题情况，提示用户：
![提示未完成题目](https://upload-images.jianshu.io/upload_images/6240664-da82fc09fb9e6b33.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![提示用户投票是否有效](https://upload-images.jianshu.io/upload_images/6240664-a5a1312b5424a264.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/450)

1.5 防止重复投票（可由后台管理员开放）。完成一次投票后：
![投票成功](https://upload-images.jianshu.io/upload_images/6240664-53d49e3b952c49af.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)
再次投票会提示：
![投票失败](https://upload-images.jianshu.io/upload_images/6240664-496c8632d3680af2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)

###2. 后端（管理员界面）
2.1 输入密码，登录后台：
![登录界面](https://upload-images.jianshu.io/upload_images/6240664-a93c7364642fbd62.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/650)
![管理员界面](https://upload-images.jianshu.io/upload_images/6240664-b575da380c0ca4a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2.2 功能区如下：
![功能区](https://upload-images.jianshu.io/upload_images/6240664-1fbe4e1246c55049.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 每2s实时更新投票情况。
- “开放投票”按钮会刷新本轮投票情况，去除“无法重复投票”状态；
- “无限投票”开关打开时，永不限制重复投票；
- “下载文件”按钮会将结果导出为word文档，压缩为zip文件，提供下载；
- “过滤废票”开关打开后，会按规则去除无效票，改变统计结果。

2.3 点击人名，查看结果。如果打开“过滤废票”开关，会显示去除废票后的结果：
![查看详细结果](https://upload-images.jianshu.io/upload_images/6240664-b5daecc9202dc003.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
2.4 下载结果。如果打开“过滤废票”开关，会得到去除废票后的结果：
![下载得到“测评结果.zip”文件](https://upload-images.jianshu.io/upload_images/6240664-ffa1f989c5bc0ebf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/720)
![解压得到按部门分类目录结构](https://upload-images.jianshu.io/upload_images/6240664-5cc47f3b23179c99.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![得到自动生成的word文件xxx.doc](https://upload-images.jianshu.io/upload_images/6240664-01a9c388468d39cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

