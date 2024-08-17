package me.dreamvoid.miraimc.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;
import me.dreamvoid.miraimc.api.MiraiMC;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.QRCodeLoginListener;
import net.mamoe.mirai.network.CustomLoginFailedException;
import net.mamoe.mirai.utils.DeviceVerificationRequests;
import net.mamoe.mirai.utils.DeviceVerificationResult;
import net.mamoe.mirai.utils.LoginSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MiraiLoginSolver extends LoginSolver {
    /**
     * 线程锁
     */
    private static final HashMap<Bot, MiraiLoginSolver> locks = new HashMap<>();

    /**
     * 验证码（用户提供的验证码或ticket）
     */
    private static final HashMap<Bot, String> codes = new HashMap<>();

    private final CustomLoginFailedException loginCancelException = new CustomLoginFailedException(true, "用户终止登录") {};

    /**
     * 图片验证码
     * @param bot 机器人实例
     * @param imageData 图片内容
     * @param continuation (?)
     * @return 验证码结果
     */
    @Nullable
    @Override
    public synchronized String onSolvePicCaptcha(@NotNull Bot bot, byte[] imageData, @NotNull Continuation<? super String> continuation) {
        locks.put(bot, this);

        // 建立机器人账号文件夹
        File ImageDir = new File(MiraiMC.getConfig().PluginDir, "verify-image");
        if (!ImageDir.exists() && !ImageDir.mkdirs()) {
            bot.getLogger().warning("无法建立验证码文件夹: " + ImageDir.getPath());
        }

        // 验证码保存到本地
        File imageFile = new File(ImageDir, bot.getId() + "-verify.png");
        try (OutputStream fos = Files.newOutputStream(imageFile.toPath())) {
            fos.write(imageData);
            fos.flush();
        } catch (IOException e) {
            bot.getLogger().warning("保存验证码图片文件时出现异常，原因: " + e);
        }

        try {
            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）需要文字验证码验证");
            bot.getLogger().warning("请找到下面的文件并识别文字验证码");
            bot.getLogger().warning(imageFile.getPath());
            bot.getLogger().warning("识别完成后，请输入指令 /miraiverify captcha " + bot.getId() + " <验证码>");
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#word-captcha");
            wait();
        } catch (InterruptedException ignored) {} // 不需要处理

        String result = codes.getOrDefault(bot, "cancel");
        locks.remove(bot, this);
        codes.remove(bot);
        if (!Objects.equals(result, "cancel")) {
            return result;
        } else {
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
    public synchronized String onSolveSliderCaptcha(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation) {
        locks.put(bot, this);
        try {
            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）需要滑动验证码验证");
            bot.getLogger().warning("请打开以下链接进行验证");
            bot.getLogger().warning(verifyUrl);
            bot.getLogger().warning("验证完成后，请输入指令 /miraiverify captcha " + bot.getId() + " <ticket>");
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#slide-captcha");
            wait();
        } catch (InterruptedException ignored) {} // 不需要处理

        String result = codes.getOrDefault(bot, "cancel");
        locks.remove(bot, this);
        codes.remove(bot);
        if (!Objects.equals(result, "cancel")) {
            return result;
        } else {
            throw loginCancelException;
        }
    }

    /**
     * 设备锁
     * @param bot 机器人实例
     * @param verifyUrl 设备锁验证链接
     * @param continuation (?)
     * @return 任意值
     * @deprecated
     * @since mirai 2.13.3
     * @see #onSolveDeviceVerification(Bot, DeviceVerificationRequests, Continuation)
     */
    @Nullable
    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public synchronized Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation){
        locks.put(bot, this);
        try {
            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）需要设备锁验证");
            bot.getLogger().warning("请打开以下链接进行验证");
            bot.getLogger().warning(verifyUrl);
            bot.getLogger().warning("验证完成后，请输入指令 /miraiverify unsafedevice " + bot.getId());
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#device-locker");
            wait();
        } catch (InterruptedException ignored) {} // 不需要处理

        String result = codes.getOrDefault(bot, "cancel");
        locks.remove(bot, this);
        codes.remove(bot);
        if (!Objects.equals(result, "cancel")) {
            return null;
        } else {
            throw loginCancelException;
        }
    }

    @Nullable
    @Override
    public synchronized Object onSolveDeviceVerification(@NotNull Bot bot, @NotNull DeviceVerificationRequests requests, @NotNull Continuation<? super DeviceVerificationResult> $completion) {
        locks.put(bot, this);
        try {
            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）需要完成设备验证");
            bot.getLogger().warning("短信验证方式" + (requests.getSms() != null ? "可用" : "不可用，请勿使用此方式"));
            bot.getLogger().warning("其他验证方式" + (requests.getSms() != null ? "可用" : "不可用，请勿使用此方式"));
            if (requests.getPreferSms()) bot.getLogger().warning("服务器要求使用短信验证码验证，但仍可以尝试其他验证方式");
            bot.getLogger().warning("如需使用短信验证方式，请输入指令 /miraiverify deviceverify " + bot.getId() + " sms");
            bot.getLogger().warning("如需使用其他验证方式，请输入指令 /miraiverify deviceverify " + bot.getId() + " fallback");
            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
            bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#device-verify");
            wait();
        } catch (InterruptedException ignored) {} // 不需要处理

        String code = codes.getOrDefault(bot, "cancel");
        codes.remove(bot);
        if (!Objects.equals(code, "cancel")) {
            try {
                switch(code){
                    case "sms":{
                        if (requests.getSms() != null) {
                            requests.getSms().requestSms(new Continuation<Unit>() {
                                @NotNull
                                @Override
                                public CoroutineContext getContext() {
                                    return Dispatchers.getIO();
                                }

                                @Override
                                public void resumeWith(@NotNull Object o) {
                                }
                            });
                            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）将使用短信验证码验证");
                            bot.getLogger().warning("一条包含验证码的短信将会发送到地区代码为" + requests.getSms().getCountryCode() + "、号码为" + requests.getSms().getPhoneNumber() + "的手机上");
                            bot.getLogger().warning("收到验证码后，请输入指令 /miraiverify deviceverify " + bot.getId() + " <验证码>");
                            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId() + "，取消登录后需要等待至少1分钟才能重新登录");
                            wait();
                        } else {
                            bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）不支持使用短信验证方式");
                            bot.getLogger().warning("登录可能会失败，请尝试重新登录");
                            throw new UnsupportedOperationException();
                        }
                        break;
                    }
                    case "fallback":{
                        if(requests.getFallback() != null){
                            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）将使用其他验证方式");
                            bot.getLogger().warning("请打开以下链接进行验证");
                            bot.getLogger().warning(requests.getFallback().getUrl());
                            bot.getLogger().warning("验证完成后，请输入指令 /miraiverify deviceverify " + bot.getId());
                            bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
                            wait();
                        } else {
                            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）不支持使用其他验证方式");
                            bot.getLogger().warning("登录可能会失败，请尝试重新登录");
                            throw new UnsupportedOperationException();
                        }
                        break;
                    }
                }
            } catch (InterruptedException ignored) {} // 不需要处理
        }

        DeviceVerificationResult result = null;
        code = codes.getOrDefault(bot, "cancel");
        if (!Objects.equals(code, "cancel")) {
            switch (code){
                case "sms":{
                    result = requests.getSms().solved(codes.get(bot));
                    break;
                }
                case "fallback":{
                    result = Objects.requireNonNull(requests.getFallback()).solved();
                    break;
                }
            }
        }

        locks.remove(bot, this);
        codes.remove(bot);
        if (!Objects.equals(code, "cancel")) {
            return result;
        } else {
            throw loginCancelException;
        }
    }

    @NotNull
    @Override
    public QRCodeLoginListener createQRCodeLoginListener(@NotNull Bot bot) {
        return new QRCodeLoginListener() {
            @Override
            public void onStateChanged(@NotNull Bot bot, @NotNull QRCodeLoginListener.State state) {
                bot.getLogger().info("当前登录的QQ（" + bot.getId() + "）的二维码状态已更新：" + state.name());

                if (state == State.CONFIRMED) {
                    codes.remove(bot);
                    locks.remove(bot);
                }
            }

            @Override
            public void onFetchQRCode(@NotNull Bot bot, @NotNull byte[] bytes) {
                // 建立扫码文件夹
                File ImageDir = new File(MiraiMC.getConfig().PluginDir,"qrcode-image");
                if(!ImageDir.exists() &&!ImageDir.mkdirs()) {
                    bot.getLogger().warning("无法建立验证码文件夹: " + ImageDir.getPath());
                }

                File imageFile = new File(ImageDir, bot.getId() + ".png");

                boolean saveSuccess = false;

                try {
                    OutputStream os = Files.newOutputStream(imageFile.toPath());
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                    saveSuccess = true;
                } catch (IOException e) {
                    bot.getLogger().warning("保存二维码图片文件时出现异常，原因: "+e);
                }

                bot.getLogger().warning("当前登录的QQ（" + bot.getId() + "）的登录二维码已准备好");
                bot.getLogger().warning("请找到下面的文件并使用登录当前QQ的客户端识别二维码");
                bot.getLogger().warning(imageFile.getPath());
                bot.getLogger().warning("识别完成后，请在客户端完成验证流程");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId() + "，5秒内将会取消登录");
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#qrcode");

                if (saveSuccess && MiraiMC.getConfig().General_AutoOpenQRCodeFile) {
                    try {
                        Runtime.getRuntime().exec(new String[]{"explorer", imageFile.getPath()});
                        bot.getLogger().info("已尝试使用系统方式直接打开二维码图片");
                    } catch (IOException ignored) {
                        bot.getLogger().warning("打开二维码图片失败，仍然可以找到并手动打开文件");
                    }
                }
            }

            /**
             * 每5秒调用一次，mirai 会等待此方法返回才会检查二维码状态
             */
            @Override
            public void onIntervalLoop() {
                if(codes.containsKey(bot) && codes.get(bot).equalsIgnoreCase("cancel")){
                    codes.remove(bot);
                    throw loginCancelException;
                }
            }
        };
    }

    /**
     * 解决登录验证<br>
     * 此方法只可用于设备锁
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 机器人不存在时抛出
     * @see #solve(long, String)
     */
    public static void solve(long BotAccount) throws NoSuchElementException {
        Bot bot = Bot.getInstance(BotAccount);
        codes.put(bot, "continue");
        MiraiLoginSolver solver = locks.get(bot);
        if(solver != null) {
            synchronized (solver){
                solver.notify();
            }
        }
    }

    /**
     * 解决登录验证<br>
     * 此方法可用于验证码和设备验证
     * @param BotAccount 机器人账号
     * @param Code 验证内容
     * @throws NoSuchElementException 机器人不存在时抛出
     * @see #solve(long)
     */
    public static void solve(long BotAccount, String Code) throws NoSuchElementException {
        Bot bot = Bot.getInstance(BotAccount);
        codes.put(bot, Code);
        MiraiLoginSolver solver = locks.get(bot);
        if(solver != null) {
            synchronized (solver){
                solver.notify();
            }
        }
    }

    /**
     * 取消一个机器人的登录验证流程
     * @param BotAccount 机器人账号
     * @throws NoSuchElementException 机器人不存在时抛出
     */
    public static void cancel(long BotAccount) throws NoSuchElementException {
        Bot bot = Bot.getInstance(BotAccount);
        codes.put(bot, "cancel");
        MiraiLoginSolver solver = locks.get(bot);
        if(solver != null) {
            synchronized (solver){
                solver.notify();
            }
        }
    }

    /**
     * 取消所有机器人的登录验证流程
     */
    public static void cancelAll(){
        for(Bot bot : locks.keySet()){
            codes.put(bot, "cancel");
            MiraiLoginSolver solver = locks.get(bot); // 抑制警告
            if(solver != null) {
                synchronized (solver){
                    solver.notify();
                }
            }
        }
    }
}
