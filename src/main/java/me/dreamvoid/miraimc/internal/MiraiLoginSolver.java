package me.dreamvoid.miraimc.internal;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class MiraiLoginSolver extends LoginSolver {
    private static Thread threads;

    private static final HashMap<Bot,Boolean> deviceVerifyContinue = new HashMap<>();
    private static final HashMap<Bot,Boolean> deviceVerifyCanceled = new HashMap<>();

    @Nullable
    @Override
    public Object onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        return null;
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        return null;
    }

    /**
     * @param bot 机器人实例
     * @param verifyUrl 设备锁验证链接
     * @param continuation (?)
     * @return 返回任意值
     */
    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation) throws IllegalThreadStateException {
        deviceVerifyCanceled.put(bot,false);
        threads = new Thread(() -> {
            deviceVerifyContinue.put(bot,false);
            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要验证设备锁");
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
        //threads.notify();
        deviceVerifyContinue.put(Bot.getInstance(BotAccount),true);
        deviceVerifyCanceled.put(Bot.getInstance(BotAccount),Canceled);
    }
}
