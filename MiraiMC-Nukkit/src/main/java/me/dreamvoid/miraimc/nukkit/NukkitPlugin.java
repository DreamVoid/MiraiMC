package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.OfflinePlayer;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.TextFormat;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.*;
import me.dreamvoid.miraimc.nukkit.commands.MiraiCommand;
import me.dreamvoid.miraimc.nukkit.utils.MetricsLite;

import java.io.IOException;
import java.sql.SQLException;

public class NukkitPlugin extends PluginBase {

    private static NukkitPlugin nukkitPlugin;

    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;

    public static NukkitPlugin getInstance() {
        return nukkitPlugin;
    }

    @Override
    public void onLoad() {
        nukkitPlugin = this;
        try {
            Utils.setLogger(new NukkitLogger("MiraiMC-Nukkit",null, this));
            Utils.setClassLoader(this.getClass().getClassLoader());
            new NukkitConfig(this).loadConfig();

            MiraiLoader.loadMiraiCore();
            this.MiraiEvent = new MiraiEvent(this);
            this.MiraiAutoLogin = new MiraiAutoLogin(this);
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin." );
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        getLogger().info("Mirai working dir: " + Config.Gen_MiraiWorkingDir);

        if(Config.Gen_AddProperties_MiraiNoDesktop){
            System.setProperty("mirai.no-desktop","MiraiMC");
        }
        if(Config.Gen_AddProperties_MiraiSliderCaptchaSupported){
            System.setProperty("mirai.slider.captcha.supported","MiraiMC");
        }

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        if(Config.Bot_LogEvents){
            getLogger().info("Registering events.");
            this.getServer().getPluginManager().registerEvents(new Events(this), this);
        }

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException e) {
                    if(Config.Gen_FriendlyException) {
                        getLogger().warning("Failed to initialize SQLite database, reason: " + e);
                    } else e.printStackTrace();
                }
                break;
            }
            case "mysql": {
                getLogger().info("Initializing MySQL database.");
                Utils.initializeMySQL();
                break;
            }
        }

        // bStats统计
        if(Config.Gen_AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 12744;
            new MetricsLite(this, pluginId);
        }

        // 安全警告
        if(!(Config.Gen_DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        // 注册命令
        getServer().getCommandMap().register("", new MiraiCommand("mirai", "MiraiBot Bot Command."));

        getServer().getScheduler().scheduleAsyncTask(this, new AsyncTask() {
            @Override
            public void onRun() {
                getLogger().info("Checking update...");
                try {
                    PluginUpdate fetch = new PluginUpdate();
                    String version = !getDescription().getVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                    if(!getDescription().getVersion().equals(version)){
                        getLogger().info("已找到新的插件更新，最新版本: " + version);
                        getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                    } else getLogger().info("你使用的是最新版本");
                } catch (IOException e) {
                    getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                }
            }
        });

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Stopping bot event listener.");
        MiraiEvent.stopListenEvent();

        getLogger().info("Closing all bots");
        MiraiLoginSolver.closeAllVerifyThreads();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).doLogout();
        }

        switch (Config.DB_Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Closing SQLite database.");
                try {
                    Utils.closeSQLite();
                } catch (SQLException e) {
                    getLogger().error("Failed to close SQLite database!");
                    getLogger().error("Reason: " + e);
                }
                break;
            }
            case "mysql": {
                getLogger().info("Closing MySQL database.");
                Utils.closeMySQL();
                break;
            }
        }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // 我能怎么办？Nukkit也没个完善的开发文档可以参考，Javadoc又没搜索，摸索半天不知道怎么注册指令，只能把方法丢到主类了
        NukkitPlugin plugin = this;
        switch (command.getName().toLowerCase()){
            case "miraimc" : {
                if(!(args.length == 0)) {
                    switch (args[0].toLowerCase()) {
                        case "reload": {
                            if(sender.hasPermission("miraimc.command.miraimc.reload")){
                                NukkitConfig.reloadConfig();
                                sender.sendMessage(TextFormat.colorize('&', "&a配置文件已经重新加载，部分配置可能需要重新启动服务器才能生效！"));
                            } else sender.sendMessage(TextFormat.colorize('&',"&c你没有足够的权限执行此命令！"));
                            break;
                        }
                        case "bind": {
                            if(sender.hasPermission("miraimc.command.miraimc.bind")){
                                if(args.length >= 2){
                                    switch (args[1].toLowerCase()){
                                        case "add": {
                                            if(args.length>=4){
                                                plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                                                    @Override
                                                    public void onRun() {
                                                        String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        long qqid = Long.parseLong(args[3]);
                                                        MiraiMC.addBinding(uuid,qqid);
                                                        sender.sendMessage(TextFormat.colorize('&',"&a已添加绑定！"));
                                                    }
                                                });
                                            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind add <玩家名> <QQ号>"));
                                            break;
                                        }
                                        case "removeplayer":{
                                            if(args.length>=3){
                                                plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                                                    @Override
                                                    public void onRun() {
                                                        String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        MiraiMC.removeBinding(uuid);
                                                        sender.sendMessage(TextFormat.colorize('&',"&a已移除相应绑定！"));
                                                    }
                                                });
                                            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind removeplayer <玩家名>"));
                                            break;
                                        }
                                        case "removeqq":{
                                            if(args.length>=3){
                                                plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                                                    @Override
                                                    public void onRun() {
                                                        long qqid = Long.parseLong(args[2]);
                                                        MiraiMC.removeBinding(qqid);
                                                        sender.sendMessage(TextFormat.colorize('&',"&a已移除相应绑定！"));
                                                    }
                                                });
                                            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind removeqq <QQ号>"));
                                            break;
                                        }
                                        case "getplayer":{
                                            if(args.length>=3){
                                                plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                                                    @Override
                                                    public void onRun() {
                                                        String uuid = plugin.getServer().getOfflinePlayer(args[2]).getUniqueId().toString();
                                                        long qqId = MiraiMC.getBinding(uuid);
                                                        if(qqId!=0){
                                                            sender.sendMessage(TextFormat.colorize('&',"&a绑定的QQ号："+qqId));
                                                        } else sender.sendMessage(TextFormat.colorize('&',"&c未找到符合条件的记录！"));
                                                    }
                                                });
                                            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind getplayer <玩家名>"));
                                            break;
                                        }
                                        case "getqq":{
                                            if(args.length>=3){
                                                plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
                                                    @Override
                                                    public void onRun() {
                                                        long qqid = Long.parseLong(args[2]);
                                                        String UUID = MiraiMC.getBinding(qqid);
                                                        if(!UUID.equals("")){
                                                            OfflinePlayer player = (OfflinePlayer) plugin.getServer().getOfflinePlayer(UUID); // 对于此方法来说，任何玩家都存在. 亲测是真的
                                                            sender.sendMessage(TextFormat.colorize('&',"&a绑定的玩家名："+player.getName()));
                                                        } else sender.sendMessage(TextFormat.colorize('&',"&c未找到符合条件的记录！"));
                                                    }
                                                });
                                            } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法: /miraimc bind getqq <QQ号>"));
                                            break;
                                        }
                                        default:{
                                            sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /miraimc bind 查看帮助！"));
                                            break;
                                        }
                                    }
                                } else {
                                    sender.sendMessage(TextFormat.colorize('&',"&6&lMiraiMC&r &b插件帮助菜单&r &a玩家绑定"));
                                    sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind add <玩家名> <QQ号>:&r 为玩家和QQ号添加绑定"));
                                    sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind getplayer <玩家名>:&r 获取指定玩家名绑定的QQ号"));
                                    sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind getqq <QQ号>:&r 获取指定QQ号绑定的玩家名"));
                                    sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind removeplayer <玩家名>:&r 删除一个玩家的绑定"));
                                    sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind removeqq <QQ号>:&r 删除一个QQ号的绑定"));
                                }
                            } else sender.sendMessage(TextFormat.colorize('&',"&c你没有足够的权限执行此命令！"));
                            break;
                        }
                        case "help": {
                            sender.sendMessage(TextFormat.colorize('&',"&6&lMiraiMC&r &b插件帮助菜单"));
                            sender.sendMessage(TextFormat.colorize('&',"&6/miraimc bind:&r 玩家绑定菜单"));
                            sender.sendMessage(TextFormat.colorize('&',"&6/miraimc reload:&r 重新加载插件"));
                            break;
                        }
                        default:{
                            sender.sendMessage(TextFormat.colorize('&',"&c未知或不完整的命令，请输入 /miraimc help 查看帮助！"));
                            break;
                        }
                    }
                    return true;
                } else {
                    sender.sendMessage("This server is running "+ plugin.getDescription().getName() +" version "+ plugin.getDescription().getVersion()+" by "+ plugin.getDescription().getAuthors().toString().replace("[","").replace("]",""));
                    return false;
                }
            }
            case "miraiverify":{
                switch (args[0].toLowerCase()){
                    case "unsafedevice":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),false);
                            sender.sendMessage(TextFormat.colorize('&',"&a已将验证请求提交到服务器"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify unsafedevice <账号>"));
                        break;
                    }
                    case "unsafedevicecancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveUnsafeDeviceLoginVerify(Long.parseLong(args[1]),true);
                            sender.sendMessage(TextFormat.colorize('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify unsafedevicecancel <账号>"));
                        break;
                    }
                    case "slidercaptcha":{
                        if(args.length >= 3){
                            sender.sendMessage(TextFormat.colorize('&',"&a已将ticket提交到服务器"));
                            MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),args[2]);
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify slidercaptcha <账号> <ticket>"));
                        break;
                    }
                    case "slidercaptchacancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solveSliderCaptcha(Long.parseLong(args[1]),true);
                            sender.sendMessage(TextFormat.colorize('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify slidercaptchacancel <账号>"));
                        break;
                    }
                    case "piccaptcha":{
                        if(args.length >= 3){
                            sender.sendMessage(TextFormat.colorize('&',"&a已将验证码提交到服务器"));
                            MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),args[2]);
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify piccaptcha <账号> <验证码>"));
                        break;
                    }
                    case "piccaptchacancel":{
                        if(args.length >= 2){
                            MiraiLoginSolver.solvePicCaptcha(Long.parseLong(args[1]),true);
                            sender.sendMessage(TextFormat.colorize('&',"&a已取消登录验证流程"));
                        } else sender.sendMessage(TextFormat.colorize('&',"&c无效的参数！用法：/miraiverify piccaptchacancel <账号>"));
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
