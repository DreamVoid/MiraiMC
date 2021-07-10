package _Catch_.Me._If._You._Can_;

import net.mamoe.mirai.utils.BotConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    @org.junit.jupiter.api.Test
    public void Test(){
        // 在这里添加用于测试的代码
        autobottest:{
            // 获取现有的机器人列表
            FileConfiguration data = YamlConfiguration.loadConfiguration(new File("F:/mirai/config/Console/AutoLogin.yml"));
            List<Map<?, ?>> list = data.getMapList("accounts");

            System.out.println("Origin: " + list);// 调试输出

            // 新建用于添加进去的Map
            Map<Object, Object> account = new HashMap<>();

            // account 节点
            account.put("account", 123456789);

            // password 节点
            Map<Object, Object> password = new HashMap<>();
            password.put("kind","PLAIN");
            password.put("value","this-is-password");
            account.put("password",password);

            // configuration 节点
            Map<Object, Object> configuration = new HashMap<>();
            configuration.put("protocol", BotConfiguration.MiraiProtocol.ANDROID_PHONE.name());
            configuration.put("device", "device.json");
            account.put("configuration",configuration);

            System.out.println("New line: " + account);// 调试输出最后结果

            // 添加
            list.add(account);
            data.set("accounts", list);

            System.out.println("Added: " + list);

            System.out.println(data.getCurrentPath());
            try {
                data.save(new File("F:/mirai/config/Console/AutoLogin.yml"));
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        delbottest:{
            // 获取现有的机器人列表
            File file = new File("F:/mirai/config/Console/AutoLogin.yml");
            FileConfiguration data = YamlConfiguration.loadConfiguration(file);
            List<Map<?, ?>> list = data.getMapList("accounts");

            System.out.println("Origin: " + list);// 调试输出

            for(Map<?,?> bots : list){
                if((Integer) bots.get("account") == 123456789){
                    list.remove(bots);
                    break;
                }
            }
            System.out.println("Removed: " + list);

            System.out.println(data.getCurrentPath());
            try {
                data.save(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

        }

    }
}
