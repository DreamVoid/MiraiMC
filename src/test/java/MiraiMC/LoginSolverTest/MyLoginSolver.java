package MiraiMC.LoginSolverTest;

import kotlin.coroutines.Continuation;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.utils.LoginSolver;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MyLoginSolver extends LoginSolver {

    @Nullable
    @Override
    public Object onSolvePicCaptcha(@NotNull Bot bot, @NotNull byte[] bytes, @NotNull Continuation<? super String> continuation) {
        System.out.println("onSolvePicCaptcha");
        System.out.println("QQ: " + bot.getId());
        System.out.println("Bytes: " + bytes);
        System.out.println("Continuation: " + continuation);

        return null;
    }

    @Nullable
    @Override
    public Object onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        System.out.println("onSolveSliderCaptcha");
        System.out.println("QQ: " + bot.getId());
        System.out.println("String: " + s);
        System.out.println("Continuation: " + continuation);

        return null;
    }

    @Nullable
    @Override
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String s, @NotNull Continuation<? super String> continuation) {
        Runnable thread = new Runnable() {
            @Override
            public void run() {
                    System.out.println("onSolveUnsafeDeviceLoginVerify");
                    System.out.println("QQ: " + bot.getId());
                    System.out.println("String: " + s);
                    System.out.println("Continuation: " + continuation);
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
