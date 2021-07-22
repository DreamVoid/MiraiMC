package me.dreamvoid.miraimc.bukkit;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MiraiAutoLogin {

    public MiraiAutoLogin(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.Logger = Utils.Logger;
        Instance = this;
    }

    private BukkitPlugin plugin;
    private me.dreamvoid.miraimc.bungee.BungeePlugin bungee;
    private final Logger Logger;
    private static File AutoLoginFile;
    public static MiraiAutoLogin Instance;

    public MiraiAutoLogin(me.dreamvoid.miraimc.bungee.BungeePlugin bungee) {
        this.bungee = bungee;
        this.Logger = Utils.Logger;
        Instance = this;
    }

    public void loadFile() {
        File MiraiDir;
        if(!(Config.Gen_MiraiWorkingDir.equals("default"))){
            MiraiDir = new File(Config.Gen_MiraiWorkingDir);
        } else {
            MiraiDir = new File(Config.PluginDir.getPath(),"MiraiBot");
        }
        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立配置文件夹
        File ConfigDir = new File(String.valueOf(MiraiDir),"config");
        if(!(ConfigDir.exists())){ if(!(ConfigDir.mkdir())) { Logger.warning("Unable to create folder: \"" + ConfigDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立控制台文件夹
        File ConsoleDir = new File(String.valueOf(ConfigDir), "Console");
        if(!(ConsoleDir.exists())){ if(!(ConsoleDir.mkdir())) { Logger.warning("Unable to create folder: \"" + ConsoleDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立自动登录文件
        AutoLoginFile = new File(ConsoleDir, "AutoLogin.yml");
        if(!AutoLoginFile.exists()) {
            try {
                if(!AutoLoginFile.createNewFile()){ throw new IOException(); }
                String defaulttext = "accounts: "+System.getProperty("line.separator");
                File writeName = AutoLoginFile;
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    out.write(defaulttext);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Map<?, ?>> loadAutoLoginList() {
        if(plugin != null){
            FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
            return data.getMapList("accounts");
        } else if(bungee != null){
            try {
                Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
                // TO DO: 增加BungeeCord环境下的配置读取
                //return data.getMapList("accounts");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else
            return null;
    }

    public void doStartUpAutoLogin() {
        Runnable thread = () -> {
            Logger.info("[AutoLogin] Starting auto-bot task.");
            for(Map<?,?> map : loadAutoLoginList()){
                Map<?,?> password = (Map<?, ?>) map.get("password");
                Map<?,?> configuration = (Map<?, ?>) map.get("configuration");
                Integer Account = (Integer) map.get("account");
                String Password = password.get("value").toString();
                BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(configuration.get("protocol").toString());

                Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                MiraiBot.Instance.doBotLogin(Account, Password, Protocol);
            }
        };
        if(plugin != null){
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, thread);
        } else if(bungee != null){
            bungee.getProxy().getScheduler().runAsync(bungee, thread);
        }
    }

    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        if(plugin != null) {
            // 获取现有的机器人列表
            FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
            List<Map<?, ?>> list = data.getMapList("accounts");

            // 新建用于添加进去的Map
            Map<Object, Object> account = new HashMap<>();

            // account 节点
            account.put("account", Account);

            // password 节点
            Map<Object, Object> password = new HashMap<>();
            password.put("kind", "PLAIN");
            password.put("value", Password);
            account.put("password", password);

            // configuration 节点
            Map<Object, Object> configuration = new HashMap<>();
            configuration.put("protocol", Protocol);
            configuration.put("device", "device.json");
            account.put("configuration", configuration);

            // 添加
            list.add(account);
            data.set("accounts", list);
            try {
                Logger.info("save");
                data.save(AutoLoginFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else // TO DO: 增加BungeeCord环境下的配置读取
            return false;
    }

    public boolean delAutoLoginBot(long Account){
        if(plugin != null) {
            FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
            List<Map<?, ?>> list = data.getMapList("accounts");

            for (Map<?, ?> bots : list) {
                if ((Integer) bots.get("account") == Account) {
                    list.remove(bots);
                    break;
                }
            }

            data.set("accounts", list);

            try {
                data.save(AutoLoginFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else // TO DO: 增加BungeeCord环境下的配置读取
            return false;
    }
}
