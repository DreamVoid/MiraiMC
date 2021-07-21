package me.dreamvoid.miraimc.internal;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class MiraiLoginSolver extends LoginSolver {
    private static Thread threads;

    private static final HashMap<Bot,Boolean> deviceVerifyContinue = new HashMap<>();
    private static final HashMap<Bot,Boolean> deviceVerifyCanceled = new HashMap<>();

    private static final HashMap<Bot,String> deviceVerifyCode = new HashMap<>();

    /**
     * @param bot 机器人实例
     * @param imageData 图片内容
     * @param continuation (?)
     * @return 验证码结果
     */
    @Nullable
    @Override
    public String onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] imageData, @NotNull Continuation<? super String> continuation) {
        deviceVerifyCanceled.put(bot,false);

        // 建立机器人账号文件夹
        File ImageDir = new File(String.valueOf(Config.PluginDir),"verifyimage");
        if(!(ImageDir.exists())){ if(!(ImageDir.mkdir())) { bot.getLogger().warning("Unable to create folder: \"" + ImageDir.getPath()+"\", make sure you have enough permission."); } }

        // 验证码保存到本地
        File imageFile = new File(ImageDir,bot.getId()+"-verify.png");
        try (OutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageData);
            fos.flush();
        } catch (IOException e) { e.printStackTrace(); }

        threads = new Thread(() -> {
            deviceVerifyContinue.put(bot,false);
            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要文字验证码验证");
            bot.getLogger().warning("请找到下面的文件并识别文字验证码");
            bot.getLogger().warning(imageFile.getPath());
            bot.getLogger().warning("识别完成后，请输入指令 /miraiverify piccaptcha "+bot.getId()+" <验证码>");
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify piccaptchacancel "+bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://github.com/DreamVoid/MiraiMC");
            while(true){
                if(deviceVerifyContinue.get(bot)){
                    break;
                }
            }
        });
        threads.start();
        try {
            threads.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!deviceVerifyCanceled.containsKey(bot) || deviceVerifyCanceled.get(bot)){
            deviceVerifyCanceled.remove(bot);
            deviceVerifyContinue.remove(bot);
            throw new IllegalThreadStateException("用户终止登录");
        } else return deviceVerifyCode.get(bot);
    }

    /**
     * @param bot 机器人实例
     * @param verifyUrl 滑动验证码验证链接
     * @param continuation (?)
     * @return 验证码解决成功后获得的 ticket
     */
    @Nullable
    @Override
    public String onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation) {
        deviceVerifyCanceled.put(bot,false);
        threads = new Thread(() -> {
            deviceVerifyContinue.put(bot,false);
            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要滑动验证码验证");
            bot.getLogger().warning("请使用手机QQ打开以下链接进行验证");
            bot.getLogger().warning(verifyUrl);
            bot.getLogger().warning("验证完成后，请输入指令 /miraiverify slidercaptcha "+bot.getId()+" <ticket>");
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify slidercaptchacancel "+bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://github.com/DreamVoid/MiraiMC");
            while(true){
                if(deviceVerifyContinue.get(bot)){
                    break;
                }
            }
        });
        threads.start();
        try {
            threads.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!deviceVerifyCanceled.containsKey(bot) || deviceVerifyCanceled.get(bot)){
            deviceVerifyCanceled.remove(bot);
            deviceVerifyContinue.remove(bot);
            throw new IllegalThreadStateException("用户终止登录");
        } else return deviceVerifyCode.get(bot);
    }

    /**
     * @param bot 机器人实例
     * @param verifyUrl 设备锁验证链接
     * @param continuation (?)
     * @return 任意值
     */
    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation) throws IllegalThreadStateException {
        deviceVerifyCanceled.put(bot,false);
        threads = new Thread(() -> {
            deviceVerifyContinue.put(bot,false);
            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要设备锁验证");
            bot.getLogger().warning("请使用手机QQ打开以下链接进行验证");
            bot.getLogger().warning(verifyUrl);
            bot.getLogger().warning("验证完成后，请输入指令 /miraiverify unsafedevice "+bot.getId());
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify unsafedevicecancel "+bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://github.com/DreamVoid/MiraiMC");
            while(true){
                if(deviceVerifyContinue.get(bot)){
                    break;
                }
            }
        });
        threads.start();
        try {
            threads.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!deviceVerifyCanceled.containsKey(bot) || deviceVerifyCanceled.get(bot)){
            deviceVerifyCanceled.remove(bot);
            deviceVerifyContinue.remove(bot);
            throw new IllegalThreadStateException("用户终止登录");
        } else return null;
    }

    public static void solveUnsafeDeviceLoginVerify(long BotAccount, boolean Canceled) throws NoSuchElementException {
        deviceVerifyContinue.put(Bot.getInstance(BotAccount),true);
        deviceVerifyCanceled.put(Bot.getInstance(BotAccount),Canceled);
    }

    public static void solveSliderCaptcha(long BotAccount, boolean Canceled) throws NoSuchElementException {
        deviceVerifyContinue.put(Bot.getInstance(BotAccount),true);
        deviceVerifyCanceled.put(Bot.getInstance(BotAccount),Canceled);
    }
    public static void solveSliderCaptcha(long BotAccount, String ticket) throws NoSuchElementException {
        deviceVerifyContinue.put(Bot.getInstance(BotAccount),true);
        deviceVerifyCanceled.put(Bot.getInstance(BotAccount),false);
        deviceVerifyCode.put(Bot.getInstance(BotAccount),ticket);
    }
}
