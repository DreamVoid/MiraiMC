package me.dreamvoid.miraimc.velocity;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;
import me.dreamvoid.miraimc.velocity.utils.AutoLoginObject;
import net.mamoe.mirai.utils.BotConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MiraiAutoLogin {

    public MiraiAutoLogin(VelocityPlugin plugin) {
        this.plugin = plugin;
        this.Logger = Utils.logger;
        Instance = this;
    }

    private final VelocityPlugin plugin;
    private final Logger Logger;
    private static File AutoLoginFile;
    public static MiraiAutoLogin Instance;

    public void loadFile() {
        // 建立文件夹
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

    public static List<AutoLoginObject.Accounts> loadAutoLoginList() throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(AutoLoginObject.class));
        InputStream inputStream = new FileInputStream(AutoLoginFile);
        AutoLoginObject data = yaml.loadAs(inputStream, AutoLoginObject.class);
        if(data.getAccounts() == null){
            data.setAccounts(new ArrayList<>());
        }
        return data.getAccounts();
    }

    public void doStartUpAutoLogin() {
        Runnable thread = () -> {
            try {
                Logger.info("[AutoLogin] Starting auto login task.");
                for(AutoLoginObject.Accounts accounts : loadAutoLoginList()){
                    AutoLoginObject.Password password = accounts.getPassword();
                    AutoLoginObject.Configuration configuration = accounts.getConfiguration();
                    long Account = accounts.getAccount();
                    if(Account != 123456){
                        try {
                            String Password = password.getValue();
                            BotConfiguration.MiraiProtocol Protocol = BotConfiguration.MiraiProtocol.valueOf(configuration.getProtocol());

                            Logger.info("[AutoLogin] Auto login bot account: " + Account + " Protocol: " + Protocol.name());
                            MiraiBot.doBotLogin(Account, Password, Protocol);
                        } catch (IllegalArgumentException ex){
                            Logger.warning("读取自动登录文件时发现未知的协议类型，请修改: " + configuration.getProtocol());
                        }
                    }
                }
            } catch (InterruptedException|FileNotFoundException e) {
                if(Config.Gen_FriendlyException) {
                    Logger.warning("登录机器人时出现异常，原因: " + e.getLocalizedMessage());
                } else e.printStackTrace();
            }
        };
        plugin.getServer().getScheduler().buildTask(plugin, thread).schedule();
    }

    public static boolean addAutoLoginBot(long Account, String Password, String Protocol){
        try {
            // 获取现有的机器人列表
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(AutoLoginFile);
            AutoLoginObject data = yaml.loadAs(inputStream, AutoLoginObject.class);
            if(data.getAccounts() == null){
                data.setAccounts(new ArrayList<>());
            }

            // 新建用于添加进去的Map
            AutoLoginObject.Accounts account = new AutoLoginObject.Accounts();

            // account 节点
            account.setAccount(Account);

            // password 节点
            AutoLoginObject.Password password = new AutoLoginObject.Password();
            password.setKind("PLAIN");
            password.setValue(Password);
            account.setPassword(password);

            // configuration 节点
            AutoLoginObject.Configuration configuration = new AutoLoginObject.Configuration();
            configuration.setDevice("device.json");
            configuration.setProtocol(Protocol);
            account.setConfiguration(configuration);

            // 添加
            List<AutoLoginObject.Accounts> accounts = data.getAccounts();
            accounts.add(account);
            data.setAccounts(accounts);
            Yaml yaml1 = new Yaml();

            File writeName = AutoLoginFile;
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(yaml1.dumpAs(data, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean delAutoLoginBot(long Account){
        try {
            // 获取现有的机器人列表
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(AutoLoginFile);
            AutoLoginObject data = yaml.loadAs(inputStream, AutoLoginObject.class);
            if(data.getAccounts() == null){
                data.setAccounts(new ArrayList<>());
            }

            for (AutoLoginObject.Accounts bots : data.getAccounts()) {
                if (bots.getAccount() == Account) {
                    data.getAccounts().remove(bots);
                    break;
                }
            }

            Yaml yaml1 = new Yaml();

            File writeName = AutoLoginFile;
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(yaml1.dumpAs(data, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}