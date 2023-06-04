package me.dreamvoid.miraimc.internal;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Dispatchers;
import me.dreamvoid.miraimc.MiraiMCConfig;
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
        File ImageDir = new File(MiraiMCConfig.PluginDir,"verify-image");
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
                bot.getLogger().warning("识别完成后，请输入指令 /miraiverify captcha "+bot.getId()+" <验证码>");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#word-captcha");
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
                bot.getLogger().warning("请打开以下链接进行验证");
                bot.getLogger().warning(verifyUrl);
                bot.getLogger().warning("验证完成后，请输入指令 /miraiverify captcha "+bot.getId()+" <ticket>");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#slide-captcha");
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
     * @deprecated
     * @since mirai 2.13.3
     * @see #onSolveDeviceVerification(Bot, DeviceVerificationRequests, Continuation)
     */
    @Nullable
    @Override
    @Deprecated
    public Object onSolveUnsafeDeviceLoginVerify(@NotNull Bot bot, @NotNull String verifyUrl, @NotNull Continuation<? super String> continuation){
        try {
            threads = new Thread(() -> {
                deviceVerifyWait.add(bot);
                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要设备锁验证");
                bot.getLogger().warning("请打开以下链接进行验证");
                bot.getLogger().warning(verifyUrl);
                bot.getLogger().warning("验证完成后，请输入指令 /miraiverify unsafedevice "+bot.getId());
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#device-locker");
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

    @Nullable
    @Override
    public Object onSolveDeviceVerification(@NotNull Bot bot, @NotNull DeviceVerificationRequests requests, @NotNull Continuation<? super DeviceVerificationResult> $completion) {
        try {
            threads = new Thread(() -> {
                deviceVerifyWait.add(bot);
                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）需要完成设备验证");
                bot.getLogger().warning("短信验证方式" + (requests.getSms() != null ? "可用" : "不可用，请勿使用此方式"));
                bot.getLogger().warning("其他验证方式" + (requests.getSms() != null ? "可用" : "不可用，请勿使用此方式"));
                if(requests.getPreferSms()) bot.getLogger().warning("服务器要求使用短信验证码验证，但仍可以尝试其他验证方式");
                bot.getLogger().warning("如需使用短信验证方式，请输入指令 /miraiverify deviceverify " + bot.getId() + " sms");
                bot.getLogger().warning("如需使用其他验证方式，请输入指令 /miraiverify deviceverify " + bot.getId() + " fallback");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#device-verify");
                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
            });
            threads.start();
            threads.join();
        } catch (InterruptedException | IllegalThreadStateException e) {
            bot.getLogger().warning("启动验证线程时出现异常，原因: " + e);
            throw loginErrorException;
        }

        String VerifyType = ""; // 验证方式，不加这个就屎山了

        if (deviceVerifyCode.containsKey(bot)) {
            VerifyType = deviceVerifyCode.get(bot);
            deviceVerifyCode.remove(bot);
            try {
                switch(VerifyType){
                    case "sms":{
                        if(requests.getSms() != null){
                            threads = new Thread(() -> {
                                deviceVerifyWait.add(bot);
                                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）将使用短信验证码验证");
                                bot.getLogger().warning("一条包含验证码的短信将会发送到地区代码为"+requests.getSms().getCountryCode()+"、号码为"+requests.getSms().getPhoneNumber()+"的手机上");
                                bot.getLogger().warning("收到验证码后，请输入指令 /miraiverify deviceverify " + bot.getId() + " <验证码>");
                                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId() + "，取消登录后需要等待至少1分钟才能重新登录");
                                requests.getSms().requestSms(new Continuation<Unit>() {
                                    @NotNull
                                    @Override
                                    public CoroutineContext getContext() {
                                        return (CoroutineContext) Dispatchers.getIO();
                                    }

                                    @Override
                                    public void resumeWith(@NotNull Object o) { }
                                });
                                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
                            });
                            threads.start();
                            threads.join();
                        } else {
                            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）不支持使用短信验证方式");
                            bot.getLogger().warning("登录可能会失败，请尝试重新登录");
                            throw new UnsupportedOperationException();
                        }
                        break;
                    }
                    case "fallback":{
                        if(requests.getFallback() != null){
                            threads = new Thread(() -> {
                                deviceVerifyWait.add(bot);
                                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）将使用其他验证方式");
                                bot.getLogger().warning("请打开以下链接进行验证");
                                bot.getLogger().warning(requests.getFallback().getUrl());
                                bot.getLogger().warning("验证完成后，请输入指令 /miraiverify deviceverify " + bot.getId());
                                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel " + bot.getId());
                                while(deviceVerifyWait.contains(bot)) if (deviceVerifyCode.containsKey(bot)) break;
                            });
                            threads.start();
                            threads.join();
                        } else {
                            bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）不支持使用其他验证方式");
                            bot.getLogger().warning("登录可能会失败，请尝试重新登录");
                            throw new UnsupportedOperationException();
                        }
                        break;
                    }
                }
            } catch (InterruptedException | IllegalThreadStateException e) {
                bot.getLogger().warning("启动验证线程时出现异常，原因: " + e);
                throw loginErrorException;
            }
        }

        DeviceVerificationResult result = null;
        if (deviceVerifyCode.containsKey(bot)) {
            switch (VerifyType){
                case "sms":{
                    result = requests.getSms().solved(deviceVerifyCode.get(bot));
                    break;
                }
                case "fallback":{
                    result = requests.getFallback().solved();
                    break;
                }
            }
        }

        if (deviceVerifyCode.containsKey(bot)) {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            return result;
        } else {
            deviceVerifyWait.remove(bot);
            deviceVerifyCode.remove(bot);
            throw loginCancelException;
        }
    }

    @NotNull
    @Override
    public QRCodeLoginListener createQRCodeLoginListener(@NotNull Bot bot) {
        return new QRCodeLoginListener() {
            @Override
            public void onStateChanged(@NotNull Bot bot, @NotNull QRCodeLoginListener.State state) {
                bot.getLogger().info("当前登录的QQ（"+bot.getId()+"）的二维码状态已更新：" + state.name());

                if(state == State.CONFIRMED){
                    deviceVerifyWait.remove(bot);
                }
            }

            @Override
            public void onFetchQRCode(@NotNull Bot bot, @NotNull byte[] bytes) {
                // 建立扫码文件夹
                File ImageDir = new File(MiraiMCConfig.PluginDir,"qrcode-image");
                if(!ImageDir.exists() &&!ImageDir.mkdirs()) {
                    throw new RuntimeException("Failed to create folder " + ImageDir.getPath());
                }

                File imageFile = new File(ImageDir, bot.getId() + ".png");

                try {
                    OutputStream os = Files.newOutputStream(imageFile.toPath());
                    os.write(bytes, 0, bytes.length);
                    os.flush();
                    os.close();
                } catch (IOException e) {
                    bot.getLogger().warning("保存二维码图片文件时出现异常，原因: "+e);
                }

                bot.getLogger().warning("当前登录的QQ（"+bot.getId()+"）的登录二维码已准备好");
                bot.getLogger().warning("请找到下面的文件并使用登录当前QQ的客户端识别二维码");
                bot.getLogger().warning(imageFile.getPath());
                bot.getLogger().warning("识别完成后，请在客户端完成验证流程");
                bot.getLogger().warning("如需取消登录，请输入指令 /miraiverify cancel "+bot.getId());
                bot.getLogger().warning("如需帮助，请参阅: https://docs.miraimc.dreamvoid.me/troubleshoot/verify-guide#qrcode");

                deviceVerifyWait.add(bot);
            }

            /**
             * 每5秒调用一次
             */
            @Override
            public void onIntervalLoop() {
                if(!deviceVerifyWait.contains(bot)){
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
        deviceVerifyCode.put(Bot.getInstance(BotAccount), "continue");
        deviceVerifyWait.remove(Bot.getInstance(BotAccount));
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
