# 纷享销客数据对接系统

## 项目介绍
企业内部系统数据与纷享销客CRM通过Open API交换数据。系统通过定时任务产生任务，任务被核心消费线程处理，从而达到数据交换

## 技术选型
- Springboot
- Spring Security
- Mybatis 
- Mysql 
- Druid
- Quartz
- ehcache
- admin-element-vue

## 项目结构
- chylee-code 代码生成器
- fxiaoke-admin-portal 管理端
- fxiaoke-admin-ui 管理端UI
- fxiaoke-common 公用模块
- fxiaoke-core 核心模块
- fxiaoke-security-starter 鉴权模块
- fxiaoke-fxk 定制业务模块

## 安装
把fxiaoke-admin-portal目录下的fxiaoke.sql导入数据库，修改application.yml数据库连接后就可以启动项目，项目启动后，修改纷享销客自建应用相关参数即可。你只需要开发自己的定制业务模块就可以完成自己的项目