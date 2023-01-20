package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.exception.AbnormalStatusException;
import me.dreamvoid.miraimc.bungee.utils.BukkitUtils;
import me.dreamvoid.miraimc.internal.Config;
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
        // 建立控制台文件夹
        File ConsoleDir = new File(Utils.getMiraiDir(), "config/Console");
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
                        BotConfiguration.MiraiProtocol Protocol = null;
                        boolean ShouldUseHttpApi = false;
                        String ProtocolName = data.getString("configuration.protocol").toUpperCase();
                        if (ProtocolName.equals("HTTPAPI")){
                            ShouldUseHttpApi = true;
                        } else {
                            try {
                                Protocol = BotConfiguration.MiraiProtocol.valueOf(ProtocolName);
                            } catch (IllegalArgumentException ignored) {
                                Logger.warning("[AutoLogin] Unknown protocol "+ProtocolName+", using ANDROID_PHONE instead.");
                                Protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE;
                            }
                        }
                        if(ShouldUseHttpApi){
                            if(Config.General.EnableHttpApi) {
                                try {
                                    MiraiHttpAPI httpAPI = new MiraiHttpAPI(Config.HttpApi.Url);
                                    httpAPI.bind(httpAPI.verify(Password).session, Account);
                                    Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: HTTPAPI");
                                } catch (AbnormalStatusException e){
                                    Logger.warning("[AutoLogin] 使用HTTPAPI登录机器人时出现异常，状态码："+e.getCode()+"，原因: " + e.getMessage());
                                } catch(IOException ex) {
                                    ex.printStackTrace();
                                    Logger.severe("[AutoLogin] Failed for auto login account "+ Account +" using protocol HTTPAPI due unknown error");
                                }
                            } else {
                                Logger.severe("[AutoLogin] Failed for auto loging account: "+ Account +" due http-api is disabled");
                            }
                        } else{
                            Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        }
                    }
                }
            } catch (IOException e) {
                Logger.severe("执行自动登录时出现异常，原因: " + e);
            }
        };
        plugin.getProxy().getScheduler().runAsync(plugin, thread);
    }

    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        try {
            // 获取自动登录文件
            Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
            List<Map<?, ?>> list = BukkitUtils.getMapList(data.getList("accounts"));

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
