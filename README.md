# 尚好房javaSSM

#### 介绍
javaSSM练手项目：由一个SSM单体架构更新为一个分布式架构
# java-项目
## 1、简介
尚好房javaSSM分布式项目
尚好房是一个二手房管理服务平台，开放优质资源和线上能力，聚合线上线下二手房产资源，打造一个全方位二手房服务生态市场，为消费者提供优质房产服务资源。
## 2、技术
基础框架：SSM
分布式框架：ssm + Dubbo + zookeeper      
spring session redis实现session共享 
图片服务器：七牛云                 
后台管理权限控制：spring-security  
前端用户登录判断：拦截器           
后台管理模板：Thymeleaf           
前端技术:Vue+Axios     
## 3、所用软件
- JDK：1.8
- mysql:5.7.39
- spring:5.2.7.RELEASE
- springMVC:
- mybaits:3.4.5
- Dubbo:2.6.0
- zookeeper:3.4.7


## 4、项目模块
最终分布式架构模块

shf-parent：根目录，管理子模块：

​	common-util：公共类模块

​	model：实体类模块

​	service：dubbo服务父节点

​		service-acl：权限服务模块

​		service-house：房源服务模块

​		service-user：用户服务模块

​	service-api：dubbo服务api接口

​	web：前端（dubbo服务消费者）

​		web-admin：后台管理系统

​		web-front：网站前端
#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
