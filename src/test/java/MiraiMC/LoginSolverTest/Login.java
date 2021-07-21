package MiraiMC.LoginSolverTest;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.io.File;

public class Login {
    public static void main(String[] args) {
        privateBotLogin(Long.parseLong(args[0]), args[1], BotConfiguration.MiraiProtocol.valueOf(args[2]));
    }

    private static void privateBotLogin(long Account, String Password, BotConfiguration.MiraiProtocol Protocol){
        System.out.println("登录新的机器人账号: "+ Account+", 协议: "+ Protocol.name());

        // 建立mirai数据文件夹
        File MiraiDir;
        MiraiDir = new File(System.getProperty("user.dir"),"MiraiBot");

        if(!(MiraiDir.exists())){ if(!(MiraiDir.mkdir())) { System.out.println("Unable to create folder: \"" + MiraiDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立机器人账号文件夹
        File BotDir = new File(String.valueOf(MiraiDir),"bots");
        if(!(BotDir.exists())){ if(!(BotDir.mkdir())) { System.out.println("Unable to create folder: \"" + BotDir.getPath()+"\", make sure you have enough permission."); } }

        // 建立当前机器人账号配置文件夹和相应的配置
        File BotConfig = new File(String.valueOf(BotDir), String.valueOf(Account));
        if(!(BotConfig.exists())){ if(!(BotConfig.mkdir())) { System.out.println("Unable to create folder: \"" + BotConfig.getPath()+"\", make sure you have enough permission."); } }

        // 登录前的准备工作
        Bot bot = BotFactory.INSTANCE.newBot(Account, Password, new BotConfiguration(){{
            // 设置登录信息
            setProtocol(Protocol); // 目前不打算让用户使用其他两个协议
            setWorkingDir(BotConfig);
            fileBasedDeviceInfo();

            // 使用自己的验证解决器
            setLoginSolver(new MyLoginSolver());
        }});

        // 开始登录
        bot.login();
        System.out.println(bot.getNick()+"("+bot.getId()+") 登录成功");
    }
}
