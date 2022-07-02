# MiraiMC
Mirai QQ robots for Minecraft servers

**For global users:** I am preparing to internationalize and will be released to SpigotMC after completion. If your native language is English and understands basic Chinese, please tell me through Issues. The current Readme may not be the latest, but I will soon update.

[中文（简体）](https://github.com/DreamVoid/MiraiMC/blob/main/README.md) | [中文（繁體）](https://github.com/DreamVoid/MiraiMC/blob/main/README_zh-Hant.md) | [English](https://github.com/DreamVoid/MiraiMC/blob/main/README_en-US.md)

## Introduction
MiraiMC is a [Mirai](https://github.com/mamoe/mirai)-based Bukkit plugin that allows you to use the QQ robot on the Minecraft server. It also provides some APIs to help developers simply call the robot interface to implement various functions for their plugins.

### What is QQ robot?
Like using Discord bots and [Telegram bots](https://core.telegram.org/bots), MiraiMC uses [Tencent QQ](https://en.qq.com/English1033.html) accounts to implement robot functions. In China, these accounts are generally called "QQ robots".

Although Tencent QQ runs best in China, Tencent is also developing Tencent QQ globally, so MiraiMC still works normally.

## Download
* Stable Version
  * [GitHub Releases](https://github.com/DreamVoid/MiraiMC/releases)
  * [MCBBS](https://www.mcbbs.net/thread-1207462-1-1.html) (China)
    * [Nukkit](https://www.mcbbs.net/thread-1260246-1-1.html) (China)
* Dev Version
  * [GitHub Actions CI](https://github.com/DreamVoid/MiraiMC/actions/workflows/build-maven.yml?query=is%3Asuccess)

## Get starting
### Server owner
If you are a server owner and are bothered by accessing QQ robots (especially Linux and panel servers), then you only need to download this plugin to access and use QQ robots conveniently and without additional software.

Please follow the steps below to start using MiraiMC：
* Download the plugin, and put the plugin file into the plugins folder
* Download other plugins (if any) developed based on MiraiMC and put these plugins into the plugins folder
* Start the server (if it is not already started) or use a plugin such as PlugMan to load
* Use the command "**/mirai login \<account> \<password>**" to login your robot account
* If you are using plugins developed based on MiraiMC at the same time, please adjust the configuration of MiraiMC in the configuration files of these plugins
* Enjoy the elegant QQ robot service!

A more detailed tutorial can be found here: https://wiki.miraimc.dreamvoid.me/

### Plugin developer
If you are a plug-in developer and are considering allowing your plugin to connect to QQ robots, you only need to use the API provided by this plugin to quickly and easily achieve your requirements without additional configuration.

You can simply use [MiraiMC Development Template](https://github.com/MiraiMC/MiraiMC-Template) to start developing a new plugin

You can also introduce MiraiMC for existing plugins, just follow the following steps:

Copy the following code to the ```dependencies``` of pom.xml or directly import the plugin jar file as an external library.
```
<dependency>
    <groupId>io.github.dreamvoid</groupId>
    <artifactId>MiraiMC-Integration</artifactId>
    <version>1.7-pre2</version>
    <scope>provided</scope>
</dependency>
```
* Refer to [Javadoc](https://jd.miraimc.dreamvoid.me) or [MiraiMC Development Template](https://github.com/MiraiMC/MiraiMC-Template) to write related code
* Publish your plugin

A more detailed development tutorial can be found here: https://en.wiki.miraimc.dreamvoid.me/

## Commands and permissions
### Commands
| Command | Description | Permission |
| ---------------------------- | ---------------------- | ---------- |
| /mirai  | MiraiMC Robot Command | miraimc.command.mirai |
| /mirai login \<account> \<password> \[protocol] | Login a robot (can execute multiple times to login multiple robots) | miraimc.command.mirai.login |
| /mirai logout \<account> | Exit and close a robot | miraimc.command.mirai.logout |
| /mirai list | List currently online robots | miraimc.command.mirai.list |
| /mirai sendfriendmessage \<account> \<friend> \<message> | Send message to friend | miraimc.command.mirai.sendfriendmessage |
| /mirai sendfriendnudge \<account> \<friend> | Send nudge to friend | miraimc.command.mirai.sendfriendnudge |
| /mirai sendfgroupmessage \<account> \<group> <message> | Send message to group | miraimc.command.mirai.sendgroupmessage |
| /mirai checkonline \<account> | Check if robot is online | miraimc.command.mirai.checkonline |
| /mirai autologin add \<account> \<password> \[protocol] | Add an auto-login robot account | miraimc.command.mirai.autologin |
| /mirai autologin remove \<account> | Remove an auto-login robot account | miraimc.command.mirai.autologin |
| /mirai autologin list | List auto-login robot accounts | miraimc.command.mirai.autologin |
| /miraimc | MiraiMC Plugin Command | miraimc.command.miraimc |
| /miraimc bind add \<player> \<QQ> | Add bindings for players and QQ numbers | miraimc.command.miraimc.bind |
| /miraimc bind getplayer \<player> | Get QQ numbers bound to the player | miraimc.command.miraimc.bind |
| /miraimc bind getqq \<QQ> | Get player bound to the QQ numbers | miraimc.command.miraimc.bind |
| /miraimc bind removeplayer \<player> | Delete a player's binding | miraimc.command.miraimc.bind |
| /miraimc bind removeqq \<QQ> | Delete a QQ numbers' binding | miraimc.command.miraimc.bind |
| /miraimc reload | Reload configuration file | miraimc.command.miraimc.reload |

### Permissions
| Permission Node | Description | Default |
| ---------------------------- | ---------------------- | ---------- |
| miraimc.command.mirai | Allow use /mirai | OP |
| miraimc.command.mirai.* | Allow use all subcommands of /mirai | OP |
| miraimc.command.mirai.login | Allow use /mirai login | OP |
| miraimc.command.mirai.logout | Allow use /mirai logout | OP |
| miraimc.command.mirai.list | Allow use /mirai list | OP |
| miraimc.command.mirai.sendfriendmessage | Allow use /mirai sendfriendmessage | OP |
| miraimc.command.mirai.sendfriendnudge | Allow use /mirai sendfriendnudge | OP |
| miraimc.command.mirai.sendgroupmessage | Allow use /mirai sendgroupmessage | OP |
| miraimc.command.mirai.checkonline | Allow use /mirai checkonline | OP |
| miraimc.command.mirai.autologin | Allow use /mirai autologin | OP |
| miraimc.command.miraimc | Allow use /miraimc | OP |
| miraimc.command.miraimc.* | Allow use all subcommands of /miraimc | OP |
| miraimc.command.miraimc.bind | Allow use all subcommands of /miraimc bind | OP |
| miraimc.command.miraimc.reload | Allow use /miraimc reload | OP |

## Next goal
- [X] Initial implementation of the basic functions of CoreAPI
- [X] Preliminary improvement of the instruction and authority system
- [X] Initially improve the API interface for other plug-in developers to call
- [X] Added the function of automatically logging in to the robot
- [X] Added remaining unadded Mirai events
- [X] Rewrite the login process to adapt to Bukkit (and future Bungee, even Sponge)
- [X] Support BungeeCord
- [X] Fully compatible with the structure of mirai console for service owners who are already using mirai
- [X] Support Sponge
- [X] Support NukkitX
- [X] Support PlaceholderAPI
- [X] Support Mirai-Http-API mode to connect to mirai-console
- [ ] Load the mirai console plugin like mirai console(Far away, PR welcome)
