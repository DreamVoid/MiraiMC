package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.utils.Config;
import me.dreamvoid.miraimc.interfaces.IMiraiAutoLogin;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@SuppressWarnings("rawtypes")
public class MiraiAutoLogin implements IMiraiAutoLogin {

    public MiraiAutoLogin(NukkitPlugin plugin) {
        this.plugin = plugin;
        logger = new NukkitLogger("MiraiMC-AutoLogin", plugin.getLogger());
        logger.setParent(Utils.getLogger());
        Instance = this;
    }

    private final NukkitPlugin plugin;
    private final Logger logger;
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
    public List<Map<?, ?>> loadAutoLoginList() {
        Config data = new Config(AutoLoginFile, Config.YAML);
        List<Map<?,?>> list = new ArrayList<>();
        for(Map<?,?> map : data.getMapList("accounts")) list.add(map);
        return list;
    }

    @Override
    public void doStartUpAutoLogin() {
        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {
                logger.info("Starting auto login task.");

                for(Map<?,?> map : loadAutoLoginList()){
                    Map<?,?> password = (Map<?, ?>) map.get("password");
                    Map<?,?> configuration = (Map<?, ?>) map.get("configuration");
                    long Account = Long.parseLong(String.valueOf(map.get("account")));
                    if(Account != 123456){
                        try {
                            String Password = password.get("value").toString();
                            BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(configuration.get("protocol").toString().toUpperCase());
                            logger.info("Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        } catch (IllegalArgumentException ignored) {
                            logger.warning("读取自动登录文件时发现未知的协议类型，请修改: " + configuration.get("protocol"));
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        // 获取自动登录文件
        Config data = new Config(AutoLoginFile, Config.YAML);
        List<Map> list = data.getMapList("accounts");

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

        // 添加
        list.add(account);
        data.set("accounts", list);
        try {
            data.save(AutoLoginFile);
        } catch (Exception e) {
            Utils.resolveException(e, logger, "保存自动登录文件时出现异常！");
            return false;
        }
        return true;
    }

    @Override
    public boolean delAutoLoginBot(long Account){
        // 获取自动登录文件
        Config data = new Config(AutoLoginFile, Config.YAML);
        List<Map> list = data.getMapList("accounts");

        for (Map<?, ?> bots : list) {
            if (Long.parseLong(String.valueOf(bots.get("account"))) == Account) {
                list.remove(bots);
                break;
            }
        }
        data.set("accounts", list);

        try {
            data.save(AutoLoginFile);
        } catch (Exception e) {
            Utils.resolveException(e, logger, "保存自动登录文件时出现异常！");
            return false;
        }
        return true;
    }
}
