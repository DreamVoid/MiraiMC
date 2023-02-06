package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.IMiraiAutoLogin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bungee.utils.SpecialUtils;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.utils.BotConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class MiraiAutoLogin implements IMiraiAutoLogin {

    public MiraiAutoLogin(BungeePlugin plugin) {
        this.plugin = plugin;
        this.Logger = Utils.logger;
        Instance = this;
    }

    private final BungeePlugin plugin;
    private final Logger Logger;
    private static File AutoLoginFile;
    public static MiraiAutoLogin Instance;

    @Override
    public void loadFile() {
        // 建立控制台文件夹
        File ConfigDir = new File(Utils.getMiraiDir(), "config");
        File ConsoleDir = new File(ConfigDir, "Console");
        if(!ConsoleDir.exists() &&!ConsoleDir.mkdirs()) throw new RuntimeException("Failed to create folder " + ConsoleDir.getPath());

        // 建立自动登录文件
        AutoLoginFile = new File(ConsoleDir, "AutoLogin.yml");
        if(!AutoLoginFile.exists()) {
            try {
                if(!AutoLoginFile.createNewFile()){ throw new RuntimeException("Failed to create folder " + AutoLoginFile.getPath()); }
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

    @Override
    public List<Map<?, ?>> loadAutoLoginList() throws IOException{
        Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
        return SpecialUtils.getMapList(data.getList("accounts"));
    }

    @Override
    public void doStartUpAutoLogin() {
        Runnable thread = () -> {
            Logger.info("[AutoLogin] Starting auto login task.");

            try {
                for(Object list : loadAutoLoginList()){
                    Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).load(list.toString());

                    long Account = data.getLong("account");
                    if(Account != 123456){
                        String Password = data.getString("password.value");

                        BotConfiguration.MiraiProtocol Protocol;
                        try {
                            Protocol = BotConfiguration.MiraiProtocol.valueOf(data.getString("configuration.protocol").toUpperCase());
                        } catch (IllegalArgumentException ignored) {
                            Logger.warning("[AutoLogin] Unknown protocol "+ data.getString("configuration.protocol").toUpperCase()+", using ANDROID_PHONE instead.");
                            Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                        }

                        Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                        MiraiBot.doBotLogin(Account, Password, Protocol);
                    }
                }
            } catch (IOException e) {
                Logger.severe("执行自动登录时出现异常，原因: " + e);
            }
        };
        plugin.getProxy().getScheduler().runAsync(plugin, thread);
    }

    @Override
    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        try {
            // 获取自动登录文件
            Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
            List<Map<?, ?>> list = SpecialUtils.getMapList(data.getList("accounts"));

            data.get("accounts", list);
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
            configuration.put("protocol", Protocol.toUpperCase());
            configuration.put("device", "device.json");
            account.put("configuration", configuration);

            list.add(account);
            data.set("accounts", list);

            // 保存
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(data, AutoLoginFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
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
            ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).save(data,AutoLoginFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
