<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
    <h1> MiraiMC </h1>

[![GitHub release](https://img.shields.io/github/v/release/DreamVoid/MiraiMC?style=flat-square
)](https://github.com/DreamVoid/MiraiMC/releases/latest)
[![CodeFactor](https://www.codefactor.io/Content/badges/A.svg)](https://www.codefactor.io/repository/github/dreamvoid/miraimc)
[![GitHub Actions CI](https://img.shields.io/github/actions/workflow/status/DreamVoid/MiraiMC/maven.yml?style=flat-square)](https://github.com/DreamVoid/MiraiMC/actions/workflows/maven.yml?query=is%3Asuccess)
![GitHub all releases](https://img.shields.io/github/downloads/DreamVoid/MiraiMC/total?style=flat-square)

</div>

---

MiraiMC 是适用于 Minecraft 服务器的 Mirai 机器人

## 介绍
MiraiMC 是一个基于 [Mirai](https://github.com/mamoe/mirai) 的 Minecraft 服务端插件，能够让你在 Minecraft 服务器上使用 Mirai QQ 机器人程序，同时提供一些 API 帮助开发者简单的调用机器人接口为自己的插件实现多样的功能。

## 一切开发旨在学习，请勿用于非法用途

- 本项目保证永久开源，欢迎提交 PR，但是请不要提交用于非法用途的功能。
- 如果某功能被大量运用于非法用途或严重侵害插件使用者权益，那么该功能将会被移除。
- 本插件完全免费开源，没有任何收费，请勿二次贩卖。
- 鉴于项目的特殊性，作者可能在任何时间**停止更新**或**删除项目**

## 下载
* 稳定版本
  * [Modrinth](https://modrinth.com/plugin/miraimc/versions)
  * [GitHub 发布页](https://github.com/DreamVoid/MiraiMC/releases)
* 开发版本
  * [GitHub Actions CI](https://github.com/DreamVoid/MiraiMC/actions/workflows/maven.yml?query=is%3Asuccess)

## 开始使用（服务器）
MiraiMC 是一个服务端插件，因此你只需按照安装插件的方式安装 MiraiMC 即可。

1. 从“下载”部分下载适用的 MiraiMC 插件，并将插件文件放入插件/模组文件夹（取决于服务端类型）。
2. 如果服务端正在运行，请完全停止服务端。之后，启动服务端。
3. 如果你同时使用了基于 MiraiMC 开发的插件，请在这些插件的配置文件中调整有关 MiraiMC 的配置。
4. 最后，登录你的机器人账号即可开始享受优雅的 QQ 机器人服务！

可以在这里找到更为详细的使用教程：https://docs.miraimc.dreamvoid.me/

## 开始使用（插件开发者）
* 使用 MiraiMC 开发模板开发一个全新的插件:
  - [Maven](https://github.com/MiraiMC/MiraiMC-Template)
  - [Gradle KotlinDSL](https://github.com/MiraiMC/MiraiMC-Template-Gradle-KotlinDSL)
* 查阅有关 MiraiMC 开发的知识：https://docs.miraimc.dreamvoid.me/
* 查阅 Javadoc：https://jd.miraimc.dreamvoid.me

### 依赖

以下列出的配置默认添加全平台的 MiraiMC 依赖。如果你只需要特定平台的 MiraiMC 依赖，请将 `Integration` 更改为对应平台的名称，如 `Bukkit` 和 `Velocity`。

#### Maven 
```
<dependency>
    <groupId>io.github.dreamvoid</groupId>
    <artifactId>MiraiMC-Integration</artifactId>
    <version>1.9.1</version>
    <scope>provided</scope>
</dependency>
```

#### Gradle
```
implementation 'io.github.dreamvoid:MiraiMC-Integration:1.9.1'
```

## 指令和权限
### 指令
| 命令                                      | 描述                        | 权限                                      |
|-----------------------------------------|---------------------------|-----------------------------------------|
| /mirai                                  | MiraiMC 机器人主命令            | miraimc.command.mirai                   |
| /mirai login <账号> <密码> \[协议]            | 登录一个机器人（可多次执行此命令以登录多个机器人） | miraimc.command.mirai.login             |
| /mirai logout <账号>                      | 退出并关闭一个机器人                | miraimc.command.mirai.logout            |
| /mirai list                             | 列出当前在线的机器人                | miraimc.command.mirai.list              |
| /mirai sendfriendmessage <账号> <好友> <消息> | 向指定好友发送消息                 | miraimc.command.mirai.sendfriendmessage |
| /mirai sendfriendnudge <账号> <好友>        | 向指定好友发送戳一戳                | miraimc.command.mirai.sendfriendnudge   |
| /mirai sendgroupmessage <账号> <群号> <消息>  | 向指定群发送消息                  | miraimc.command.mirai.sendgroupmessage  |
| /mirai checkonline <账号>                 | 检查指定机器人是否在线               | miraimc.command.mirai.checkonline       |
| /mirai autologin add <账号> <密码> \[协议]    | 添加一个自动登录机器人账号             | miraimc.command.mirai.autologin         |
| /mirai autologin remove <账号>            | 移除一个自动登录机器人账号             | miraimc.command.mirai.autologin         |
| /mirai autologin list                   | 查看自动登录机器人账号列表             | miraimc.command.mirai.autologin         |
| /miraimc                                | MiraiMC 插件主命令             | miraimc.command.miraimc                 |
| /miraimc bind add <玩家名> <QQ号>           | 为玩家和QQ号添加绑定               | miraimc.command.miraimc.bind            |
| /miraimc bind getplayer <玩家名>           | 获取指定玩家名绑定的QQ号             | miraimc.command.miraimc.bind            |
| /miraimc bind getqq <QQ号>               | 获取指定QQ号绑定的玩家名             | miraimc.command.miraimc.bind            |
| /miraimc bind removeplayer <玩家名>        | 删除一个玩家的绑定                 | miraimc.command.miraimc.bind            |
| /miraimc bind removeqq <QQ号>            | 删除一个QQ号的绑定                | miraimc.command.miraimc.bind            |
| /miraimc reload                         | 重新加载配置文件                  | miraimc.command.miraimc.reload          |

### 权限
| 权限节点                                    | 描述                            | 默认 |
|-----------------------------------------|-------------------------------|:--:|
| miraimc.command.mirai                   | 允许使用 /mirai                   | OP |
| miraimc.command.mirai.*                 | 允许使用 /mirai 的所有子命令            | OP |
| miraimc.command.mirai.login             | 允许使用 /mirai login             | OP |
| miraimc.command.mirai.logout            | 允许使用 /mirai logout            | OP |
| miraimc.command.mirai.list              | 允许使用 /mirai list              | OP |
| miraimc.command.mirai.sendfriendmessage | 允许使用 /mirai sendfriendmessage | OP |
| miraimc.command.mirai.sendfriendnudge   | 允许使用 /mirai sendfriendnudge   | OP |
| miraimc.command.mirai.sendgroupmessage  | 允许使用 /mirai sendgroupmessage  | OP |
| miraimc.command.mirai.checkonline       | 允许使用 /mirai checkonline       | OP |
| miraimc.command.mirai.autologin         | 允许使用 /mirai autologin         | OP |
| miraimc.command.miraimc                 | 允许使用 /miraimc                 | OP |
| miraimc.command.miraimc.*               | 允许使用 /miraimc 的所有子命令          | OP |
| miraimc.command.miraimc.bind            | 允许使用 /miraimc bind 及下属子命令     | OP |
| miraimc.command.miraimc.reload          | 允许使用 /miraimc reload          | OP |

## 许可证

[GNU Affero General Public License v3.0](https://github.com/DreamVoid/MiraiMC/blob/main/LICENSE)

## 致谢

感谢以下人员/团队/项目为 MiraiMC 做出的贡献！

* [mamoe/mirai](https://github.com/mamoe/mirai)：强大的QQ机器人支持库，MiraiMC 的核心和基础。
* [lucko/helper](https://github.com/lucko/helper)：为 MiraiMC 提供动态加载 mirai 核心的解决方案。
* [brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP)：高效的 SQL 连接池，为 MiraiMC 提供数据库连接方案。
* [LT_Name](https://github.com/lt-name)：为 MiraiMC 优化了大量 Nukkit 代码，也为 MiraiMC 和我本人提供了很多帮助。
* 你。

## 风险提示

MiraiMC 的源码仅托管于 Git‎Hub (https‎://‎github‎.‎com/DreamVoid/MiraiMC)，任何其他平台的源代码如 GitCode 等平台并非本人发布或控制，**谨防恶意后门**。

作者不对上文列出的下载地址以外的插件来源负责。

## 下一步的目标
- [X] 初步实现 CoreAPI 的基础功能
- [X] 初步完善指令和权限系统
- [X] 初步完善 API 接口供其他插件开发者调用
- [X] 加入自动登录机器人的功能
- [X] 添加剩余未添加的 Mirai 事件
- [X] 重写登录流程以适应 Bukkit（和未来支持的 Bungee，甚至 Sponge）
- [X] 支持 BungeeCord
- [X] 完全兼容 mirai console 的结构供已在使用 mirai 的服主使用
- [X] 支持 Sponge
- [X] 支持 NukkitX
- [X] 支持 PlaceholderAPI
- [X] ~~引入 Mirai-Http-API 模式来连接到 mirai-console~~
- [X] 支持 Folia
- [ ] 插件所有文本支持多语言
- [ ] 做一个图形界面
- [ ] 像 mirai console 一样加载 mirai console 插件（遥遥无期，欢迎 PR）

[DreamVoid](https://github.com/DreamVoid) 与 [MiraiMC](https://github.com/MiraiMC)，用 ❤ 制作。
