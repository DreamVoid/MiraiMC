# MiraiMC
适用于Minecraft服务器的Mirai机器人

## 介绍
MiraiMC 是一个基于[Mirai](https://github.com/mamoe/mirai)的Bukkit插件，能够让你在Minecraft服务器上使用Mirai QQ机器人程序，同时提供一些API帮助开发者简单的调用机器人接口为自己的插件实现多样的功能。

## 开始使用
### 服主
如果你是服主，正在被接入QQ机器人所困扰（尤其是Linux和面板服），那么只需要下载本插件即可方便快捷的接入并使用QQ机器人且无需使用额外的软件。

请按下面的步骤开始使用MiraiMC：
* 下载插件，并将插件文件放入plugins文件夹
* [可选] 下载基于MiraiMC开发的其他插件（如果有的话），并将这些插件放入plugins文件夹
* 启动服务端（如果尚未启动）或使用诸如PlugMan的插件加载插件
* 使用指令“**/mirai login <账号> <密码>**”登录你的机器人账号
* [额外] 如果你同时使用了基于MiraiMC开发的插件，请在这些插件的配置文件中调整有关MiraiMC的配置
* 享受优雅的QQ机器人服务！

可以在这里找到更为详细的使用教程：https://wiki.miraimc.dreamvoid.ml/

### 开发者
如果你是插件开发者，正在考虑让自己的插件能够对接QQ机器人，那么只需要使用本插件提供的API即可方便快捷的实现需求而无需让服主进行额外的配置。

你可以简单的使用[MiraiMC开发模板](https://github.com/DreamVoid/MiraiMC-Template)来开始开发一个全新的插件

你也可以为现有插件引入MiraiMC，只需按照以下步骤即可接入MiraiMC：

* 将下面的代码复制到pom.xml的dependencies项或直接将插件jar文件作为外部库导入
```
<dependency>
    <groupId>io.github.dreamvoid</groupId>
    <artifactId>MiraiMC</artifactId>
    <!--请确保版本为Github上的最新版本-->
    <version>1.1</version>
</dependency>
```
* 参照[Javadoc](https://docs.miraimc.dreamvoid.ml)或[MiraiMC开发模板](https://github.com/DreamVoid/MiraiMC-Template)编写相关的代码
* 发布你的插件

可以在这里找到更为详细的开发教程：https://wiki.miraimc.dreamvoid.ml/

## 指令和权限
### 指令
| 命令 | 描述 | 权限 |
| ---------------------------- | ---------------------- | ---------- |
| /mirai  | MiraiMC 机器人主命令 | miraimc.command.mirai |
| /mirai login <账号> <密码> [协议] | 登录一个机器人（可多次执行此命令以登录多个机器人） | miraimc.command.mirai.login |
| /mirai logout <账号> | 退出并关闭一个机器人 | miraimc.command.mirai.logout |
| /mirai list | 列出当前在线的机器人 | miraimc.command.mirai.list |
| /mirai sendfriendmessage <账号> <好友> <消息> | 向指定好友发送消息 | miraimc.command.mirai.sendfriendmessage |
| /mirai sendfriendnudge <账号> <好友> | 向指定好友发送戳一戳 | miraimc.command.mirai.sendfriendnudge |
| /mirai sendfgroupmessage <账号> <群号> <消息> | 向指定群发送消息 | miraimc.command.mirai.sendgroupmessage |
| /mirai sendgroupnudge <账号> <群号> | 向指定群发送戳一戳 | miraimc.command.mirai.sendgroupnudge |
| /mirai checkonline <账号> | 检查指定机器人是否在线 | miraimc.command.mirai.checkonline |
| /miraimc | MiraiMC 插件主命令 | miraimc.command.miraimc |
| /miraimc reload | 重新加载配置文件 | miraimc.command.miraimc.reload |
### 权限
| 权限节点 | 描述 | 默认 |
| ---------------------------- | ---------------------- | ---------- |
| miraimc.command.mirai | 允许使用 /mirai | OP |
| miraimc.command.mirai.* | 允许使用 /mirai 的所有子命令 | OP |
| miraimc.command.mirai.login | 允许使用 /mirai login | OP |
| miraimc.command.mirai.logout | 允许使用 /mirai logout | OP |
| miraimc.command.mirai.list | 允许使用 /mirai list | OP |
| miraimc.command.mirai.sendfriendmessage | 允许使用 /mirai sendfriendmessage | OP |
| miraimc.command.mirai.sendfriendnudge | 允许使用 /mirai sendfriendnudge | OP |
| miraimc.command.mirai.sendgroupmessage | 允许使用 /mirai sendgroupmessage | OP |
| miraimc.command.mirai.sendgroupnudge | 允许使用 /mirai sendgroupnudge | OP |
| miraimc.command.mirai.checkonline | 允许使用 /mirai checkonline | OP |
| miraimc.command.miraimc | 允许使用 /miraimc | OP |
| miraimc.command.miraimc.* | 允许使用 /miraimc 的所有子命令 | OP |
| miraimc.command.miraimc.reload | 允许使用 /miraimc reload | OP |

## 下一步的目标
- [X] 初步实现CoreAPI的基础功能
- [X] 初步完善指令和权限系统
- [X] 初步完善API接口供其他插件开发者调用
- [ ] 加入自动登录机器人的功能
- [ ] 像mirai console一样加载mirai console插件
