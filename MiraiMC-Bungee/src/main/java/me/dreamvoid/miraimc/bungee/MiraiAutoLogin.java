package me.dreamvoid.miraimc.bungee;

import me.dreamvoid.miraimc.interfaces.IMiraiAutoLogin;
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
        logger = Logger.getLogger("MiraiMC-AutoLogin");
        logger.setParent(Utils.getLogger());
    }

    private final BungeePlugin plugin;
    private final Logger logger;
    private static File AutoLoginFile;

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
                String defaultText = "accounts: "+ System.lineSeparator();
                File writeName = AutoLoginFile;
                try (FileWriter writer = new FileWriter(writeName);
                     BufferedWriter out = new BufferedWriter(writer)
                ) {
                    out.write(defaultText);
                    out.flush();
                }
            } catch (IOException e) {
                Utils.resolveException(e, logger, "创建自动登录文件时出现异常！");
            }
        }
    }

    @Override
    public List<Map<?, ?>> loadAutoLoginList() throws IOException{
        Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.YamlConfiguration.class).load(AutoLoginFile);
        return SpecialUtils.getMapList(data.getList("accounts"));
    }

    @Override
    public void startAutoLogin() {
        Runnable thread = () -> {
            logger.info("Starting auto login task.");

            try {
                for(Object list : loadAutoLoginList()){
                    Configuration data = ConfigurationProvider.getProvider(net.md_5.bungee.config.JsonConfiguration.class).load(list.toString());

                    long Account = data.getLong("account");
                    if(Account != 123456){
                        try {
                            String Password = data.getString("password.value");
                            BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(data.getString("configuration.protocol").toUpperCase());
                            logger.info("Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        } catch (IllegalArgumentException ignored) {
                            logger.warning("读取自动登录文件时发现未知的协议类型，请修改: " + data.getString("configuration.protocol"));
                        }
                    }
                }
            } catch (IOException e) {
                logger.severe("执行自动登录时出现异常，原因: " + e);
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
            Utils.resolveException(e, logger, "保存自动登录文件时出现异常！");
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteAutoLoginBot(long Account){
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
            Utils.resolveException(e, logger, "保存自动登录文件时出现异常！");
            return false;
        }
        return true;
    }
}
