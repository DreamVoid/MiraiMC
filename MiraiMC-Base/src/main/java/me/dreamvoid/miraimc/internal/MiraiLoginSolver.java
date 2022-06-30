package me.dreamvoid.miraimc.internal;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.network.CustomLoginFailedException;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class MiraiLoginSolver extends LoginSolver {
    private Thread threads;

    /**
     * 等待线程（用于判断用户侧是否完成验证）
     */
    private static final List<Bot> deviceVerifyWait = new ArrayList<>();

    /**
     * 验证码（用户提供的验证码或ticket）
     */
    private static final HashMap<Bot,String> deviceVerifyCode = new HashMap<>();

    private final CustomLoginFailedException loginCancelException = new CustomLoginFailedException(true,"用户终止登录") {};
    private final CustomLoginFailedException loginErrorException = new CustomLoginFailedException(true,"登录时出现严重错误") {};

    /**
     * 图片验证码
     * @param bot 机器人实例
     * @param imageData 图片内容
     * @param continuation (?)
     * @return 验证码结果
     */
    @Nullable
    @Override
    public String onSolvePicCaptcha(@NotNull Bot bot, byte[] imageData, @NotNull Continuation<? super String> continuation) {
        // 建立机器人账号文件夹
        File ImageDir = new File(Config.PluginDir,"verify-image");
        if(!ImageDir.exists() &&!ImageDir.mkdirs()) {
            throw new RuntimeException("Failed to create folder " + ImageDir.getPath());
        }

        // 验证码保存到本地
        File imageFile = new File(ImageDir,bot.getId()+"-verify.png");
        try (OutputStream fos = Files.newOutputStream(imageFile.toPath())) {
            fos.write(imageData);
            fos.flush();
        } catch (IOException e) {
            bot.getLogger().warning("保存验证码图片文件时出现异常，原因: "+e);
        }

        try {
            threads = new Thread(() -> {
                deviceVerifyWait.add(bot);
                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要文字验证码验证");
                bot.getLogger().warning("请找到下面的文件并识别文字验证码");
                bot.getLogger().warning(imageFile.getPath());
                bot.getLogger().warning("识别完成后，请输入指令 /miraiverify piccaptcha "+bot.getId()+" <验证码>");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify piccaptchacancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://wiki.miraimc.dreamvoid.me/troubleshoot/verify-guide#word-captcha");
                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
            });
            threads.start();
            threads.join();
        } catch (InterruptedException | IllegalThreadStateException e) {
            bot.getLogger().warning("启动验证线程时出现异常，原因: " + e);
            throw loginErrorException;
        }

        if (deviceVerifyCode.containsKey(bot)) {
            String result = deviceVerifyCode.get(bot);
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            return result;
        } else {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            throw loginCancelException;
        }
    }

    /**
     * 滑块验证码
     * @param bot 机器人实例
     * @param verifyUrl 滑动验证码验证链接
     * @param continuation (?)
     * @return 验证码解决成功后获得的 ticket
     */
    @Nullable
    @Override
    public String onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation) {
        try {
            threads = new Thread(() -> {
                deviceVerifyWait.add(bot);
                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要滑动验证码验证");
                bot.getLogger().warning("请使用手机QQ打开以下链接进行验证");
                bot.getLogger().warning(verifyUrl);
                bot.getLogger().warning("验证完成后，请输入指令 /miraiverify slidercaptcha "+bot.getId()+" <ticket>");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify slidercaptchacancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://wiki.miraimc.dreamvoid.me/troubleshoot/verify-guide#slide-captcha");
                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
            });
            threads.start();
            threads.join();
        } catch (InterruptedException | IllegalThreadStateException e) {
            bot.getLogger().warning("启动验证线程时出现异常，原因: " + e);
            throw loginErrorException;
        }

        if (deviceVerifyCode.containsKey(bot)) {
            String result = deviceVerifyCode.get(bot);
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            return result;
        } else {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            throw loginCancelException;
        }
    }

    /**
     * 设备锁
     * @param bot 机器人实例
     * @param verifyUrl 设备锁验证链接
     * @param continuation (?)
     * @return 任意值
     */
    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation){
        try {
            threads = new Thread(() -> {
                deviceVerifyWait.add(bot);
                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要设备锁验证");
                bot.getLogger().warning("请使用手机QQ打开以下链接进行验证");
                bot.getLogger().warning(verifyUrl);
                bot.getLogger().warning("验证完成后，请输入指令 /miraiverify unsafedevice "+bot.getId());
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify unsafedevicecancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://wiki.miraimc.dreamvoid.me/troubleshoot/verify-guide#device-locker");
                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
            });
            threads.start();
            threads.join();
        } catch (InterruptedException | IllegalThreadStateException e) {
            bot.getLogger().warning("启动验证线程时出现异常，原因: " + e);
            throw loginErrorException;
        }

        if (deviceVerifyCode.containsKey(bot)) {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            return null;
        } else {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            throw loginCancelException;
        }
    }

    /**
     * 解决登录验证<br>
     * 此方法只可用于设备锁
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 机器人不存在时抛出
     * @see #solve(long, String)
     */
    public static void solve(long BotAccount) throws NoSuchElementException {
        deviceVerifyCode.put(Bot.getInstance(BotAccount), "continue");
        deviceVerifyWait.remove(Bot.getInstance(BotAccount));
    }

    /**
     * 解决登录验证<br>
     * 此方法只可用于验证码
     * @param BotAccount 机器人账号
     * @param Code 验证内容
     * @throws NoSuchElementException 机器人不存在时抛出
     * @see #solve(long)
     */
    public static void solve(long BotAccount, String Code) throws NoSuchElementException {
        deviceVerifyCode.put(Bot.getInstance(BotAccount),Code);
        deviceVerifyWait.remove(Bot.getInstance(BotAccount));
    }

    /**
     * 取消一个机器人的登录验证流程
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 机器人不存在时抛出
     */
    public static void cancel(long BotAccount) throws NoSuchElementException {
        deviceVerifyWait.remove(Bot.getInstance(BotAccount));
    }

    /**
     * 取消所有机器人的登录验证流程
     */
    public static void cancelAll(){
        deviceVerifyWait.clear();
        deviceVerifyCode.clear();
    }
}
