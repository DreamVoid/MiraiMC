package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.BukkitPlugin;
import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
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
    private YamlConfiguration autologin;
    private final Logger Logger;
    private File AutoLoginFile;

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
        autologin = YamlConfiguration.loadConfiguration(AutoLoginFile);
    }

    public List<Map<?, ?>> loadAutoLoginList() {
        FileConfiguration data = autologin;
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

    public void addAutoLoginBot(long Account, String Password){
        FileConfiguration data = autologin;
        Map<String, Long> account = new HashMap<>();
        account.put("account",Account);
        data.getMapList("accounts").add(account);
        try {
            data.save(AutoLoginFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
