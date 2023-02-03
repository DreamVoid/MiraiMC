package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.MiraiLoginSolver;
import me.dreamvoid.miraimc.internal.PluginUpdate;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.internal.libloader.MiraiLoader;
import me.dreamvoid.miraimc.Platform;
import me.dreamvoid.miraimc.internal.webapi.Info;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BukkitPlugin extends JavaPlugin {
    private MiraiEvent MiraiEvent;
    public MiraiAutoLogin MiraiAutoLogin;

    @Override // 加载插件
    public void onLoad() {
        System.setProperty("mirai.no-desktop", "MiraiMC");
        System.setProperty("mirai.slider.captcha.supported", "MiraiMC");

        try {
            Utils.setLogger(this.getLogger());
            Utils.setClassLoader(this.getClassLoader());
            new BukkitConfig(this).loadConfig();

            if(Config.General.MiraiCoreVersion.equalsIgnoreCase("latest")) {
                MiraiLoader.loadMiraiCore();
            } else if(Config.General.MiraiCoreVersion.equalsIgnoreCase("stable")){
                MiraiLoader.loadMiraiCore(MiraiLoader.getStableVersion(getDescription().getVersion()));
            } else {
                MiraiLoader.loadMiraiCore(Config.General.MiraiCoreVersion);
            }

            if(!Config.General.LegacyEventSupport){
                this.MiraiEvent = new MiraiEvent();
            } else this.MiraiEvent = new MiraiEventLegacy();
            this.MiraiAutoLogin = new MiraiAutoLogin(this);

            MiraiMCPlugin.setPlugin(new MiraiMCPlugin() {
                @Override
                public String getName() {
                    return getDescription().getName();
                }

                @Override
                public String getVersion() {
                    return getDescription().getVersion();
                }

                @Override
                public List<String> getAuthors() {
                    return getDescription().getAuthors();
                }

                @Override
                public Logger getLogger() {
                    return Utils.logger;
                }

                @Override
                public Platform getServer() {
                    return new Platform() {
                        @Override
                        public String getPlayerName(UUID uuid) {
                            return Bukkit.getOfflinePlayer(uuid).getName();
                        }

                        @Override
                        public UUID getPlayerUUID(String name) {
                            return Bukkit.getOfflinePlayer(name).getUniqueId();
                        }

                        @Override
                        public void runTaskAsync(Runnable task) {
                            Bukkit.getScheduler().runTaskAsynchronously(BukkitPlugin.this, task);
                        }

                    };
                }

                @Override
                public IMiraiAutoLogin getAutoLogin() {
                    return new IMiraiAutoLogin() {
                        @Override
                        public void loadFile() {
                            MiraiAutoLogin.loadFile();
                        }

                        @Override
                        public List<Map<?, ?>> loadAutoLoginList(){
                            return MiraiAutoLogin.loadAutoLoginList();
                        }

                        @Override
                        public void doStartUpAutoLogin() {
                            MiraiAutoLogin.doStartUpAutoLogin();
                        }

                        @Override
                        public boolean addAutoLoginBot(long Account, String Password, String Protocol) {
                            return MiraiAutoLogin.addAutoLoginBot(Account, Password, Protocol);
                        }

                        @Override
                        public boolean delAutoLoginBot(long Account) {
                            return MiraiAutoLogin.delAutoLoginBot(Account);
                        }
                    };
                }
            });
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override // 启用插件
    public void onEnable() {
        try { // 抛弃Forge用户，别问为什么
            Class.forName("cpw.mods.modlauncher.Launcher");
            getLogger().severe("任何Forge服务端均不受MiraiMC支持（如Catserver、Loliserver），请尽量更换其他服务端使用！");
            getLogger().severe("作者不会处理任何使用了Forge服务端导致的问题。");
            getLogger().severe("兼容性报告: https://docs.miraimc.dreamvoid.me/troubleshoot/compatibility-report");
        } catch (ClassNotFoundException ignored) {}

        getLogger().info("Mirai working dir: " + Config.General.MiraiWorkingDir);

        getLogger().info("Starting Mirai-Events listener.");
        MiraiEvent.startListenEvent();

        getLogger().info("Loading auto-login file.");
        MiraiAutoLogin.loadFile();
        MiraiAutoLogin.doStartUpAutoLogin(); // 服务器启动完成后执行自动登录机器人

        /*
        getLogger().info("Registering commands.");
        getCommand("mirai").setExecutor(new MiraiCommand(this));
        getCommand("mirai").setTabCompleter((sender, command, label, args) -> {});
        getCommand("miraimc").setExecutor(new MiraiMcCommand(this));
        getCommand("miraimc").setTabCompleter((sender, command, labels, args) -> {
            List<String> result = new ArrayList<>();
        });
        getCommand("miraiverify").setExecutor(new MiraiVerifyCommand());
        */

        if(Config.Bot.LogEvents){
            getLogger().info("Registering events.");
            Bukkit.getPluginManager().registerEvents(new Events(), this);
        }

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                getLogger().info("Initializing SQLite database.");
                try {
                    Utils.initializeSQLite();
                } catch (SQLException | ClassNotFoundException e) {
                    getLogger().warning("Failed to initialize SQLite database, reason: " + e);
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
        if(Config.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 11534;
            new Metrics(this, pluginId);
        }

        if(Config.General.CheckUpdate && !getDescription().getVersion().contains("dev")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    getLogger().info("Checking update...");
                    try {
                        PluginUpdate fetch = new PluginUpdate();
                        String version = !getDescription().getVersion().contains("-") ? fetch.getLatestRelease() : fetch.getLatestPreRelease();
                        if (fetch.isBlocked(getDescription().getVersion())) {
                            getLogger().severe("当前版本已停用，继续使用将不会得到作者的任何支持！");
                            getLogger().severe("请立刻更新到最新版本: " + version);
                            getLogger().severe("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                        } else if (!getDescription().getVersion().equals(version)) {
                            getLogger().info("已找到新的插件更新，最新版本: " + version);
                            getLogger().info("从Github下载更新: https://github.com/DreamVoid/MiraiMC/releases/latest");
                        } else getLogger().info("You are using the latest version!");
                    } catch (IOException e) {
                        getLogger().warning("An error occurred while fetching the latest version, reason: " + e);
                    }
                }
            }.runTaskAsynchronously(this);
        }

        // HTTP API
        if(Config.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getServer().getScheduler().runTaskTimerAsynchronously(this, new MiraiHttpAPIResolver(this), 0, Config.HttpApi.MessageFetch.Interval);
        }

        // 安全警告
        if(!(Config.General.DisableSafeWarningMessage)){
            getLogger().warning("确保您正在使用开源的MiraiMC插件，未知来源的插件可能会盗取您的账号！");
            getLogger().warning("请始终从Github或作者指定的其他途径下载插件: https://github.com/DreamVoid/MiraiMC");
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    List<String> announcement = Info.init().announcement;
                    if(announcement != null){
                        getLogger().info("========== [ MiraiMC 公告版 ] ==========");
                        announcement.forEach(s -> getLogger().info(s));
                        getLogger().info("=======================================");
                    }
                } catch (IOException ignored) {}
            }
        }.runTaskLaterAsynchronously(this, 40L);

        getLogger().info("All tasks done. Welcome to use MiraiMC!");
    }

    @Override // 禁用插件
    public void onDisable() {
        if(MiraiEvent != null) {
            getLogger().info("Stopping bot event listener.");
            MiraiEvent.stopListenEvent();
        }

        getLogger().info("Closing all bots");
        MiraiLoginSolver.cancelAll();
        for (long bots : MiraiBot.getOnlineBots()){
            MiraiBot.getBot(bots).close();
        }

        switch (Config.Database.Type.toLowerCase()){
            case "sqlite":
            default: {
                if (Utils.connection != null) {
                    getLogger().info("Closing SQLite database.");
                    try {
                        Utils.closeSQLite();
                    } catch (SQLException e) {
                        getLogger().severe("Failed to close SQLite database!");
                        getLogger().severe("Reason: " + e);
                    }
                }
                break;
            }
            case "mysql": {
                if(Utils.ds != null) {
                    getLogger().info("Closing MySQL database.");
                    Utils.closeMySQL();
                }
                break;
            }
        }

        getLogger().info("All tasks done. Thanks for use MiraiMC!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ICommandSender sender1 = new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }
        };

        switch (command.getName().toLowerCase()){
            case "mirai":return new MiraiCommand().onCommand(sender1, args);
            case "miraimc":return new MiraiMcCommand().onCommand(sender1, args);
            case "miraiverify":return new MiraiVerifyCommand().onCommand(sender1, args);
            default:return super.onCommand(sender, command, label, args);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result = new ArrayList<>();

        switch (command.getName().toLowerCase()){
            case "mirai":{
                if(args.length == 1){
                    String[] list = new String[]{"login", "logout", "list", "sendfriendmessage", "sendgroupmessage", "sendfriendnudge", "uploadimage", "checkonline", "autologin", "help"};
                    for(String s : list){
                        if(s.startsWith(args[0])) result.add(s);
                    }
                }

                if(args.length == 2 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
                    // 1
                    List<String> list1 = Arrays.asList("login", "logout", "sendfriendmessage", "sendgroupmessage", "sendfriendnudge", "uploadimage", "checkonline");
                    if(list1.contains(args[0].toLowerCase())){
                        for(Long l : MiraiBot.getOnlineBots()){
                            if(String.valueOf(l).startsWith(args[1])) result.add(String.valueOf(l));
                        }
                    }

                    // 2
                    if("autologin".equalsIgnoreCase(args[0])){
                        String[] list = new String[]{"add", "list", "remove"};
                        for(String s : list){
                            if(s.startsWith(args[1])) result.add(s);
                        }
                    }
                }

                if(args.length == 3 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
                    // 1
                    List<String> list1 = Arrays.asList("sendfriendmessage", "sendfriendnudge");
                    if(list1.contains(args[1].toLowerCase())){
                        try {
                            for (Long l : MiraiBot.getBot(Long.parseLong(args[1])).getFriendList()) {
                                if (String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                            }
                        } catch (NoSuchElementException ignored) {}
                    }

                    // 2
                    if("sendgroupmessage".equalsIgnoreCase(args[1])){
                        try {
                            for (Long l : MiraiBot.getBot(Long.parseLong(args[1])).getGroupList()) {
                                if (String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                            }
                        } catch (NoSuchElementException ignored) {}
                    }

                    // 3
                    if(args[0].equalsIgnoreCase("autologin")) {
                        // 1
                        if("add".equalsIgnoreCase(args[1])){
                            for(Long l : MiraiBot.getOnlineBots()){
                                if(String.valueOf(l).startsWith(args[2])) result.add(String.valueOf(l));
                            }
                        }

                        // 2
                        if ("remove".equalsIgnoreCase(args[1])) {
                            List<String> accounts = MiraiAutoLogin.loadAutoLoginList().stream().map(map -> String.valueOf(map.get("account"))).collect(Collectors.toList());
                            for(String s : accounts){
                                if(s.startsWith(args[2])) result.add(s);
                            }
                        }
                    }
                }

                if(args.length == 4 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
                    if("login".equalsIgnoreCase(args[0])){
                        result = MiraiBot.getAvailableProtocol();
                    }
                }

                if(args.length == 5 && sender.hasPermission("miraimc.command.mirai." + args[0].toLowerCase())){
                    if("autologin".equalsIgnoreCase(args[0]) && "add".equalsIgnoreCase(args[1])){
                        result = MiraiBot.getAvailableProtocol();
                    }
                }

                return result;
            }
            case "miraimc":{
                if(args.length == 1){
                    String[] list = new String[]{"bind", "reload"};
                    for(String s : list){
                        if(s.startsWith(args[0])) result.add(s);
                    }
                }

                if(args.length == 2){
                    if("bind".equalsIgnoreCase(args[0])){
                        String[] list = new String[]{"add", "getplayer", "getqq", "removeplayer", "removeqq"};
                        for(String s : list){
                            if(s.startsWith(args[1])) result.add(s);
                        }
                    }
                }

                if(args.length == 3){
                    List<String> list = Arrays.asList("add", "getplayer", "removeplayer");
                    if("bind".equalsIgnoreCase(args[0]) && list.contains(args[1].toLowerCase())){
                        for(Player p : Bukkit.getOnlinePlayers()){
                            if(p.getName().startsWith(args[2])) result.add(p.getName());
                        }
                    }
                }

                return result;
            }
            default:return super.onTabComplete(sender,command,alias,args);
        }
    }
}
