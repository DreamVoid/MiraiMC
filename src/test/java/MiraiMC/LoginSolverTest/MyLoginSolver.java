package MiraiMC.LoginSolverTest;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.network.CustomLoginFailedException;
import net.mamoe.mirai.network.LoginFailedException;
import net.mamoe.mirai.network.UnsupportedSMSLoginException;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;

public class MyLoginSolver extends LoginSolver {

    @Nullable
    @Override
    public String onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        bot.getLogger().verbose("onSolvePicCaptcha");
        bot.getLogger().verbose("QQ: " + bot.getId());
        bot.getLogger().verbose("Bytes: " + bytes);
        bot.getLogger().verbose("Continuation: " + continuation);

        // 验证码保存到本地
        File file = new File(Login.BaseDir,"verify.png");
        try (OutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
            fos.flush();
            bot.getLogger().verbose("验证码图片文件已保存到: "+file.getPath());
        } catch (IOException e) { e.printStackTrace(); }

        // 先睡六秒钟
        //try { Thread.sleep(6000); } catch (InterruptedException e) { e.printStackTrace(); }
        throw new CustomLoginFailedException(true,"用户终止登录") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        };

        // 读取验证码
        /*String str;
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File(Login.BaseDir,"verify.txt")));
            str = in.readLine();
            bot.getLogger().verbose("向服务器发送验证码: "+str);
            return str;
        } catch (IOException e) { e.printStackTrace(); }
        bot.getLogger().verbose("向服务器发送null");
        return null;*/
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        bot.getLogger().verbose("onSolveSliderCaptcha");
        bot.getLogger().verbose("QQ: " + bot.getId());
        bot.getLogger().verbose("String: " + s);
        bot.getLogger().verbose("Continuation: " + continuation);

        return null;
    }

    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                bot.getLogger().verbose("onSolveUnsafeDeviceLoginVerify");
                bot.getLogger().verbose("QQ: " + bot.getId());
                bot.getLogger().verbose("String: " + s);
                bot.getLogger().verbose("Continuation: " + continuation);
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.notify();
        return null;
    }
}
