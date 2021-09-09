package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class MiraiAutoLogin {

    public MiraiAutoLogin(BungeePlugin plugin) {
        this.plugin = plugin;
        this.Logger = Utils.logger;
        Instance = this;
    }

    private final BungeePlugin plugin;
    private final Logger Logger;
    private static File AutoLoginFile;
    public static MiraiAutoLogin Instance;

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
                String defaultText = "accounts: "+System.getProperty("line.separator");
                File writeName = AutoLoginFile;
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    out.write(defaultText);
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<?> loadAutoLoginList() throws IOException{
        Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
        return data.getList("accounts");
    }

    public void doStartUpAutoLogin() {
        Runnable thread = () -> {
            Logger.info("[AutoLogin] Starting auto login task.");

            try {
                for(Object list : loadAutoLoginList()){
                    Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).load(list.toString());

                    long Account = data.getLong("account");
                    if(Account != 123456){
                        String Password = data.getString("password.value");
                        BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(data.getString("configuration.protocol"));

                        Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                        try {
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        } catch (InterruptedException e) {
                            Logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                        }
                    }
                }
            } catch (IOException e) {
                Logger.severe("执行自动登录时出现异常，原因: "+e.getLocalizedMessage());
            }
        };
        plugin.getProxy().getScheduler().runAsync(plugin, thread);
    }

    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        try {
            // 获取自动登录文件
            Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
            List<?> list = data.getList("accounts");

            // 创建一个Yaml用来添加自动登录机器人
            // 我除了这个方案想不出别的方案了
            Configuration tempConf = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load("");

            tempConf.set("account",Account);

            tempConf.set("password.kind", "PLAIN");
            tempConf.set("password.value", Password);

            tempConf.set("configuration.protocol", Protocol);
            tempConf.set("configuration.device", "device.json");

            // 保存
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(data, AutoLoginFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delAutoLoginBot(long Account){
        try {
            // 获取自动登录文件
            Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
            List<?> list = data.getList("accounts");

            // 循环判断所有机器人
            for (Object bots : list) {
                // 获取机器人信息
                Configuration bot = ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).load(bots.toString());

                // 判断账号是否相同
                if (bot.getLong("account") == Account) {
                    list.remove(bots);
                    break;
                }
            }

            // 保存
            ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).save(data,AutoLoginFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
