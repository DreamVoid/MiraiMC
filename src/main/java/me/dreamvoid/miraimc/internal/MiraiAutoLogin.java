package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MiraiAutoLogin {

    public MiraiAutoLogin(BukkitPlugin plugin) {
        this.plugin = plugin;
        this.Logger = Utils.getLogger();
    }

    private final BukkitPlugin plugin;
    private final Logger Logger;
    private static File AutoLoginFile;

    public void loadFile() {
        File MiraiDir;
        if(!(Config.config.getString("general.mirai-working-dir", "default").equals("default"))){
            MiraiDir = new File(Config.config.getString("general.mirai-working-dir", "default"));
        } else {
            MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
        }
        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立配置文件夹
        File ConfigDir = new File(String.valueOf(MiraiDir),"config");
        if(!(ConfigDir.exists())){ if(!(ConfigDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立控制台文件夹
        File ConsoleDir = new File(String.valueOf(ConfigDir), "Console");
        if(!(ConsoleDir.exists())){ if(!(ConsoleDir.mkdir())) { Logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立自动登录文件
        AutoLoginFile = new File(ConsoleDir, "AutoLogin.yml");
        if(!AutoLoginFile.exists()) {
            try {
                AutoLoginFile.createNewFile();
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
        FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
        return data.getMapList("accounts");
    }

    public void doStartUpAutoLogin() {
        new BukkitRunnable(){
            @Override
            public void run() {
                Logger.info("[AutoLogin] Starting auto-bot task.");
                for(Map<?,?> map : loadAutoLoginList()){
                    Map<?,?> password = (Map<?, ?>) map.get("password");
                    Map<?,?> configuration = (Map<?, ?>) map.get("configuration");
                    Integer Account = (Integer) map.get("account");
                    String Password = password.get("value").toString();
                    BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(configuration.get("protocol").toString());

                    Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                    new MiraiBot().doBotLogin(Account, Password, Protocol);
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        // 获取现有的机器人列表
        FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
        List<Map<?, ?>> list = data.getMapList("accounts");

        // 新建用于添加进去的Map
        Map<Object, Object> account = new HashMap<>();

        // account 节点
        account.put("account", Account);

        // password 节点
        Map<Object, Object> password = new HashMap<>();
        password.put("kind","PLAIN");
        password.put("value",Password);
        account.put("password",password);

        // configuration 节点
        Map<Object, Object> configuration = new HashMap<>();
        configuration.put("protocol", Protocol);
        configuration.put("device", "device.json");
        account.put("configuration",configuration);

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
    }

    public boolean delAutoLoginBot(long Account){
        FileConfiguration data = YamlConfiguration.loadConfiguration(AutoLoginFile);
        List<Map<?, ?>> list = data.getMapList("accounts");

        for(Map<?,?> bots : list){
            if((Integer) bots.get("account") == Account){
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
    }
}
