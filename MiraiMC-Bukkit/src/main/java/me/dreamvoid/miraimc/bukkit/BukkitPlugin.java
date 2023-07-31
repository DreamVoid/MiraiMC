package me.dreamvoid.miraimc.bukkit;

import com.github.Anon8281.universalScheduler.UniversalScheduler;
import com.github.Anon8281.universalScheduler.scheduling.schedulers.TaskScheduler;
import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.IMiraiEvent;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.PlatformPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.utils.Metrics;
import me.dreamvoid.miraimc.commands.ICommandSender;
import me.dreamvoid.miraimc.commands.MiraiCommand;
import me.dreamvoid.miraimc.commands.MiraiMcCommand;
import me.dreamvoid.miraimc.commands.MiraiVerifyCommand;
import me.dreamvoid.miraimc.MiraiMCConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class BukkitPlugin extends JavaPlugin implements PlatformPlugin {
    private MiraiEvent MiraiEvent;
    private MiraiAutoLogin MiraiAutoLogin;
    private final MiraiMCPlugin lifeCycle;
    private final MiraiMCConfig platformConfig;
    private TaskScheduler scheduler; // Folia

    public BukkitPlugin(){
        lifeCycle = new MiraiMCPlugin(this);
        lifeCycle.startUp();
        platformConfig = new BukkitConfig(this);
    }

    @Override // 加载插件
    public void onLoad() {
        try {
            lifeCycle.preLoad();
            // 加载mirai核心完成，开始加载附属功能
            MiraiAutoLogin = new MiraiAutoLogin(this);
            MiraiEvent = new MiraiEvent();
        } catch (Exception e) {
            getLogger().warning("An error occurred while loading plugin.");
            e.printStackTrace();
        }
    }

    @Override // 启用插件
    public void onEnable() {
        scheduler = UniversalScheduler.getScheduler(this); // Folia

        lifeCycle.postLoad();

        // 监听事件
        if(MiraiMCConfig.General.LogEvents){
            getLogger().info("Registering events.");
            Bukkit.getPluginManager().registerEvents(new Events(), this);
        }

        // bStats统计
        if(MiraiMCConfig.General.AllowBStats && !getDescription().getVersion().contains("dev")) {
            getLogger().info("Initializing bStats metrics.");
            int pluginId = 11534;
            new Metrics(this, pluginId);
        }

        // HTTP API
        if(MiraiMCConfig.General.EnableHttpApi){
            getLogger().info("Initializing HttpAPI async task.");
            getScheduler().runTaskTimerAsynchronously(new MiraiHttpAPIResolver(this), 0, MiraiMCConfig.HttpApi.MessageFetch.Interval);
        }
    }

    @Override // 禁用插件
    public void onDisable() {
        lifeCycle.unload();
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
        getScheduler().runTaskAsynchronously(task);
    }

    @Override
    public void runTaskLaterAsync(Runnable task, long delay) {
        getScheduler().runTaskLaterAsynchronously(task, delay);
    }

    @Override
    public int runTaskTimerAsync(Runnable task, long period) {
        return getScheduler().runTaskTimerAsynchronously(task, 0, period).getTaskId();
    }

    @Override
    public void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getPluginVersion() {
        return getDescription().getVersion();
    }

    @Override
    public List<String> getAuthors() {
        return getDescription().getAuthors();
    }

    @Override
    public IMiraiAutoLogin getAutoLogin() {
        return this.MiraiAutoLogin;
    }

    @Override
    public Logger getPluginLogger() {
        return getLogger();
    }

    @Override
    public ClassLoader getPluginClassLoader(){
        return this.getClassLoader();
    }

    @Override
    public IMiraiEvent getMiraiEvent() {
        return MiraiEvent;
    }

    @Override
    public MiraiMCConfig getPluginConfig() {
        return platformConfig;
    }

    TaskScheduler getScheduler(){
        return scheduler;
    }
}
