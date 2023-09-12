# 基础介绍

| 说明     | 内容 |
| :------- | ---- |
| 项目名   | zerogchat |
| 作者     | buxia97 |
| 数据库IP | 127.0.0.1 |
| 数据库名 | 0gchat |

## chat_ban表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| Id | Id | Integer | int | 10 | NO |  |
| ip | ip | String | varchar | 500 | YES | IP地址 |
| created | created | Integer | int | 10 | YES | 封禁时间 |

## chat_chat表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| Id | Id | Integer | int | 10 | NO |  |
| name | name | String | varchar | 255 | YES | 聊天室名称 |
| intro | intro | String | varchar | 500 | YES | 聊天室简介 |
| pic | pic | String | varchar | 400 | YES | 聊天室图标 |
| status | status | Integer | int | 10 | YES | 聊天室状态（0关闭，1开启） |

## chat_configs表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| Id | Id | Integer | int | 10 | NO |  |
| banText | banText | String | text | 65535 | YES | 违禁词列表，英文逗号分割 |
| rootName | rootName | String | varchar | 255 | YES | 管理员名称 |
| logo | logo | String | varchar | 400 | YES | 管理员or站点logo |
| website | website | String | varchar | 400 | YES | 官方地址 |

## chat_msg表结构说明
| 代码字段名 | 字段名 | 数据类型（代码） | 数据类型 | 长度 | NullAble | 注释 |
| :--------- | ------ | ---------------- | -------- | ---- | -------------- | ---- |
| Id | Id | Integer | int | 10 | NO |  |
| userName | userName | String | varchar | 255 | YES | 用户名称 |
| ip | ip | String | varchar | 400 | YES | IP地址 |
| type | type | Integer | int | 10 | YES | 类型，0图文消息，1加入通知，2系统消息，3卡片消息 |
| text | text | String | text | 65535 | YES | 消息内容 |
| url | url | String | varchar | 400 | YES | 网址 |








