package me.dreamvoid.miraimc.internal;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class MiraiAutoLogin {

    public MiraiAutoLogin() {
        this.logger = new Utils().getLogger();
    }

    private YamlConfiguration autologin;
    private final Logger logger;

    public void loadFile() {
        File MiraiDir;
        if(!(Config.config.getString("general.mirai-working-dir", "default").equals("default"))){
            MiraiDir = new File(Config.config.getString("general.mirai-working-dir", "default"));
        } else {
            MiraiDir = new File(String.valueOf(Config.PluginDir),"MiraiBot");
        }
        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立配置文件夹
        File ConfigDir = new File(String.valueOf(MiraiDir),"config");
        if(!(ConfigDir.exists())){ if(!(ConfigDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立控制台文件夹
        File ConsoleDir = new File(String.valueOf(ConfigDir), "Console");
        if(!(ConsoleDir.exists())){ if(!(ConsoleDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立自动登录文件
        File AutoLoginFile = new File(ConsoleDir, "AutoLogin.yml");
        autologin = YamlConfiguration.loadConfiguration(AutoLoginFile);
    }

    public List<?> loadAutoLoginList() {
        // TO DO: 整活数组获取
        return autologin.getList("accounts");
    }
}
