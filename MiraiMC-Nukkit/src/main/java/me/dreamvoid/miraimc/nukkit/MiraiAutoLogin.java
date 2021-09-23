package me.dreamvoid.miraimc.nukkit;

import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.scheduler.AsyncTask;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiraiAutoLogin {

    public MiraiAutoLogin(NukkitPlugin plugin) {
        this.plugin = plugin;
        this.Logger = plugin.getLogger();
        Instance = this;
    }

    private final NukkitPlugin plugin;
    private final PluginLogger Logger;
    private static File AutoLoginFile;
    public static MiraiAutoLogin Instance;

    public void loadFile() {
        // 建立控制台文件夹
        File MiraiDir; if(!(Config.Gen_MiraiWorkingDir.equals("default"))) MiraiDir = new File(Config.Gen_MiraiWorkingDir); else MiraiDir = new File(Config.PluginDir,"MiraiBot");
        File ConsoleDir = new File(MiraiDir, "config/Console");
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

    public List<Map> loadAutoLoginList() {
        cn.nukkit.utils.Config data = new cn.nukkit.utils.Config(AutoLoginFile, cn.nukkit.utils.Config.YAML);
        return data.getMapList("accounts");
    }

    public void doStartUpAutoLogin() {
        plugin.getServer().getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {
                Logger.info("[AutoLogin] Starting auto login task.");

                for(Map<?,?> map : loadAutoLoginList()){
                    Map<?,?> password = (Map<?, ?>) map.get("password");
                    Map<?,?> configuration = (Map<?, ?>) map.get("configuration");
                    long Account = Long.parseLong(String.valueOf(map.get("account")));
                    if(Account != 123456){
                        String Password = password.get("value").toString();
                        BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(configuration.get("protocol").toString());

                        Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                        try {
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        } catch (InterruptedException e) {
                            if(Config.Gen_FriendlyException) {
                                Logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                            } else e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public boolean addAutoLoginBot(long Account, String Password, String Protocol){
        // 获取自动登录文件
        cn.nukkit.utils.Config data = new cn.nukkit.utils.Config(AutoLoginFile, cn.nukkit.utils.Config.YAML);
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
        configuration.put("protocol", Protocol);
        configuration.put("device", "device.json");
        account.put("configuration", configuration);

        // 添加
        list.add(account);
        data.set("accounts", list);
        try {
            data.save(AutoLoginFile);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delAutoLoginBot(long Account){
        // 获取自动登录文件
        cn.nukkit.utils.Config data = new cn.nukkit.utils.Config(AutoLoginFile, cn.nukkit.utils.Config.YAML);
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
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
