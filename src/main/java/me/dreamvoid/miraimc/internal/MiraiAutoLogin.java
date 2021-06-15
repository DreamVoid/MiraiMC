package me.dreamvoid.miraimc.internal;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
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

        // 建立机器人账号文件夹
        File ConfigDir = new File(String.valueOf(MiraiDir),"config");
        if(!(ConfigDir.exists())){ if(!(ConfigDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立当前机器人账号配置文件夹和相应的配置
        File ConsoleDir = new File(String.valueOf(ConfigDir), "Console");
        if(!(ConsoleDir.exists())){ if(!(ConsoleDir.mkdir())) { logger.warning("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        File AutoLoginFile = new File(ConsoleDir, "AutoLogin.yml");
        autologin = YamlConfiguration.loadConfiguration(AutoLoginFile);
    }

    public String loadAutoLoginList(){
        return autologin.getList("accounts").toString();
    }
}
