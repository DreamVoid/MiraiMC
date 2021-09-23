# MiraiMC
適用於Minecraft服務器的Mirai機器人

[中文（简体）](https://github.com/DreamVoid/MiraiMC/blob/main/README.md) | [中文（繁體）](https://github.com/DreamVoid/MiraiMC/blob/main/README_zh-Hant.md) | [English](https://github.com/DreamVoid/MiraiMC/blob/main/README_en-US.md)

## 介紹
MiraiMC 是一個基於[Mirai](https://github.com/mamoe/mirai)的Bukkit插件，能夠讓你在Minecraft服務器上使用Mirai QQ機器人程序，同時提供一些API幫助開發者簡單的調用機器人接口為自己的插件實現多樣的功能。

## 下載
* 穩定版本
  * [Github 發布頁](https://github.com/DreamVoid/MiraiMC/releases)
  * [Gitee 發布頁](https://gitee.com/dreamvoid/MiraiMC/releases) (中國)
* 開發版本
  * [Jenkins CI](https://ci.dreamvoid.ml/job/MiraiMC/)

## 開始使用
### 服主
如果你是服主，正在被接入QQ機器人所困擾（尤其是Linux和麵板服），那麼只需要下載本插件即可方便快捷的接入並使用QQ機器人且無需使用額外的軟件。

請按下面的步驟開始使用MiraiMC：
* 下載插件，並將插件文件放入plugins文件夾
* 下載基於MiraiMC開發的其他插件（如果有的話），並將這些插件放入plugins文件夾
* 啟動服務端（如果尚未啟動）或使用諸如PlugMan的插件加載插件
* 使用指令“**/mirai login <賬號> <密碼>**”登錄你的機器人賬號
* 如果你同時使用了基於MiraiMC開發的插件，請在這些插件的配置文件中調整有關MiraiMC的配置
* 享受優雅的QQ機器人服務！

可以在這裡找到更為詳細的使用教程：https://wiki.miraimc.dreamvoid.ml/

### 開發者
如果你是插件開發者，正在考慮讓自己的插件能夠對接QQ機器人，那麼只需要使用本插件提供的API即可方便快捷的實現需求而無需讓服主進行額外的配置。

你可以簡單的使用[MiraiMC開發模板](https://github.com/DreamVoid/MiraiMC-Template)來開始開發一個全新的插件

你也可以為現有插件引入MiraiMC，只需按照以下步驟即可接入MiraiMC：

* 將下面的代碼複製到pom.xml的```dependencies```項或直接將插件jar文件作為外部庫導入
```
<dependency>
    <groupId>io.github.dreamvoid</groupId>
    <artifactId>MiraiMC-Integration</artifactId>
    <!--請確保版本為Github上的最新版本-->
    <version>1.5</version>
    <scope>provided</scope>
</dependency>
```
* 參照[Javadoc](https://docs.miraimc.dreamvoid.ml)或[MiraiMC開發模板](https://github.com/DreamVoid/MiraiMC-Template)編寫相關的代碼
* 發布你的插件

可以在這裡找到更為詳細的開發教程：https://wiki.miraimc.dreamvoid.ml/

## 指令和權限
### 指令
| 命令 | 描述 | 權限 |
| ---------------------------- | ---------------------- | ---------- |
| /mirai  | MiraiMC 機器人主命令 | miraimc.command.mirai |
| /mirai login <賬號> <密碼> [協議] | 登錄一個機器人（可多次執行此命令以登錄多個機器人） | miraimc.command.mirai.login |
| /mirai logout <賬號> | 退出並關閉一個機器人 | miraimc.command.mirai.logout |
| /mirai list | 列出當前在線的機器人 | miraimc.command.mirai.list |
| /mirai sendfriendmessage <賬號> <好友> <消息> | 向指定好友發送消息 | miraimc.command.mirai.sendfriendmessage |
| /mirai sendfriendnudge <賬號> <好友> | 向指定好友發送戳一戳 | miraimc.command.mirai.sendfriendnudge |
| /mirai sendfgroupmessage <賬號> <群號> <消息> | 向指定群發送消息 | miraimc.command.mirai.sendgroupmessage |
| /mirai checkonline <賬號> | 檢查指定機器人是否在線 | miraimc.command.mirai.checkonline |
| /mirai autologin add <賬號> <密碼> [協議] | 添加一個自動登錄機器人賬號 | miraimc.command.mirai.autologin |
| /mirai autologin remove <賬號> | 移除一個自動登錄機器人賬號 | miraimc.command.mirai.autologin |
| /mirai autologin list | 查看自動登錄機器人賬號列表 | miraimc.command.mirai.autologin |
| /miraimc | MiraiMC 插件主命令 | miraimc.command.miraimc |
| /miraimc bind add <玩家名> <QQ號> | 為玩家和QQ號添加綁定 | miraimc.command.miraimc.bind |
| /miraimc bind getplayer <玩家名> | 獲取指定玩家名綁定的QQ號 | miraimc.command.miraimc.bind |
| /miraimc bind getqq <QQ號> | 獲取指定QQ號綁定的玩家名 | miraimc.command.miraimc.bind |
| /miraimc bind removeplayer <玩家名> | 刪除一個玩家的綁定 | miraimc.command.miraimc.bind |
| /miraimc bind removeqq <QQ號> | 刪除一個QQ號的綁定 | miraimc.command.miraimc.bind |
| /miraimc reload | 重新加載配置文件 | miraimc.command.miraimc.reload |

### 權限
| 權限節點 | 描述 | 默認 |
| ---------------------------- | ---------------------- | ---------- |
| miraimc.command.mirai | 允許使用 /mirai | OP |
| miraimc.command.mirai.* | 允許使用 /mirai 的所有子命令 | OP |
| miraimc.command.mirai.login | 允許使用 /mirai login | OP |
| miraimc.command.mirai.logout | 允許使用 /mirai logout | OP |
| miraimc.command.mirai.list | 允許使用 /mirai list | OP |
| miraimc.command.mirai.sendfriendmessage | 允許使用 /mirai sendfriendmessage | OP |
| miraimc.command.mirai.sendfriendnudge | 允許使用 /mirai sendfriendnudge | OP |
| miraimc.command.mirai.sendgroupmessage | 允許使用 /mirai sendgroupmessage | OP |
| miraimc.command.mirai.checkonline | 允許使用 /mirai checkonline | OP |
| miraimc.command.mirai.autologin | 允許使用 /mirai autologin | OP |
| miraimc.command.miraimc | 允許使用 /miraimc | OP |
| miraimc.command.miraimc.* | 允許使用 /miraimc 的所有子命令 | OP |
| miraimc.command.miraimc.bind | 允許使用 /miraimc bind 及下屬子命令 | OP |
| miraimc.command.miraimc.reload | 允許使用 /miraimc reload | OP |

## 下一步的目標
- [X] 初步實現CoreAPI的基礎功能
- [X] 初步完善指令和權限系統
- [X] 初步完善API接口供其他插件開發者調用
- [X] 加入自動登錄機器人的功能
- [X] 添加剩餘未添加的Mirai事件
- [X] 重寫登錄流程以適應Bukkit（和未來支持的Bungee，甚至Sponge）
- [X] 支持BungeeCord
- [ ] 完全兼容mirai console的結構供已在使用mirai的服主使用
- [ ] 支持Sponge
- [ ] 像mirai console一樣加載mirai console插件
