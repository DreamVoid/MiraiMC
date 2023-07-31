package me.dreamvoid.miraimc.internal.encryptservice;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.internal.MiraiEncryptServiceFactory;
import me.dreamvoid.miraimc.internal.MiraiEncryptServiceFactoryKt;
import me.dreamvoid.miraimc.internal.Utils;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.internal.spi.EncryptService;
import net.mamoe.mirai.internal.spi.EncryptServiceContext;
import net.mamoe.mirai.utils.DeviceInfo;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static net.mamoe.mirai.utils.MiraiUtils.hexToBytes;
import static net.mamoe.mirai.utils.MiraiUtils.toUHexString;

public class UnidbgFetchQsign implements EncryptService, CoroutineScope {
    private final String server;
    private final String key;
    private final CoroutineContext coroutineContext;
    private int taskId = 0;

    public UnidbgFetchQsign(String server, String key, CoroutineContext context){
        this.server = server;
        this.key = key;
        this.coroutineContext = context;
    }

    private EncryptService.ChannelProxy channel0 = null;
    private final AtomicLong token = new AtomicLong(0);
    private static final String CMD_WHITE_LIST = readText(UnidbgFetchQsign.class.getResource("/cmd.txt"), StandardCharsets.UTF_8.name());

    static String readText(URL fileUrl, String charset) {
        try {
            InputStream inputStream = fileUrl.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int _byte;
            while ((_byte = inputStream.read()) != -1) {
                byteArrayOutputStream.write(_byte);
            }
            String result = byteArrayOutputStream.toString(charset);
            byteArrayOutputStream.close();
            inputStream.close();
            return result;
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(@NotNull EncryptServiceContext context) {
        MiraiLogger logger = Bot.getInstance(context.getId()).getLogger();

        DeviceInfo device = MiraiEncryptServiceFactoryKt.getDeviceInfo(context);
        String qimei36 = MiraiEncryptServiceFactoryKt.getQimei36(context);
        ChannelProxy channel = MiraiEncryptServiceFactoryKt.getChannel(context);

        logger.info("Bot("+context.getId()+") initialize by " + server);

        channel0 = channel;

        if(token.get() == 0L){
            long uin = context.getId();

            // register - start
            try {
                String response = Utils.Http.get(server + "/register?uin=" + uin
                        + "&android_id=" + Arrays.toString(device.getAndroidId())
                        + "&guid=" + toUHexString(device.getAndroidId(), " ", 0, device.getAndroidId().length)
                        + "&qimei36=" + qimei36
                        + "&key=" + key);
                DataWrapper body = new Gson().fromJson(response, DataWrapper.class);
                if (body.code != 0) {
                    throw new IllegalStateException(body.message);
                }

                logger.info("Bot(" + uin + ") register, " + body.message);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
            // register - end

            MiraiEncryptServiceFactoryKt.setCoroutineCompletionInvoke(coroutineContext, () -> {
                try {
                    String response1 = Utils.Http.get(server + "/destroy?uin=" + uin + "&key=" + key);
                    DataWrapper body1 = new Gson().fromJson(response1, DataWrapper.class);
                    logger.info("Bot(" + uin + ") destroy, " + body1.message);
                    MiraiMCPlugin.getPlatform().cancelTask(taskId);
                } catch (Throwable cause) {
                    logger.warning("Bot(" + uin + ") destroy", cause);
                } finally {
                    token.compareAndSet(uin, 0);
                }
                return null;
            });
        }

        logger.info("Bot("+context.getId()+") initialize complete");
    }

    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        return MiraiEncryptServiceFactoryKt.getCoroutineContextKt(coroutineContext);
    }

    private static class DataWrapper{
        @SerializedName("code")
        public int code = 0;
        @SerializedName("msg")
        public String message = "";
        @SerializedName("data")
        public JsonElement data;
    }

    @Nullable
    @Override
    public byte[] encryptTlv(@NotNull EncryptServiceContext context, int tlvType, @NotNull byte[] payload) {
        MiraiLogger logger = Bot.getInstance(context.getId()).getLogger();
        if(tlvType != 0x544) return null;

        String command = MiraiEncryptServiceFactoryKt.getCommand(context);

        try {
            // customEnergy - start
            String response = Utils.Http.get(server + "/custom_energy?uin=" + context.getId()
                    + "&salt=" + toUHexString(payload, "", 0, payload.length)
                    + "&data=" + command);
            DataWrapper body = new Gson().fromJson(response, DataWrapper.class);
            if (body.code != 0) {
                throw new IllegalStateException(body.message);
            }

            logger.debug("Bot(" + context.getId() + ") custom_energy " + command + ", " + response);
            // customEnergy - end

            String data = new Gson().toJson(body.data);

            return hexToBytes(data.replace("\"", ""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public EncryptService.SignResult qSecurityGetSign(@NotNull EncryptServiceContext context, int sequenceId, @NotNull String commandName, @NotNull byte[] payload) {
        MiraiLogger logger = Bot.getInstance(context.getId()).getLogger();

        if(commandName.equals("StatSvc.register")){
            if(token.compareAndSet(0, context.getId())){
                long interval = Long.parseLong(System.getProperty(MiraiEncryptServiceFactory.REQUEST_TOKEN_INTERVAL, "2400000"));
                if (interval > 0L) {
                    final boolean[] firstRun = {true};
                    taskId = MiraiMCPlugin.getPlatform().runTaskTimerAsync(() -> {
                        if (interval < 600_000) logger.warning(MiraiEncryptServiceFactory.REQUEST_TOKEN_INTERVAL + "="+ interval +"< 600_000 (ms)");
                        if (firstRun[0]){
                            firstRun[0] = false;
                            return;
                        }

                        long uin = context.getId();

                        // requestToken - start
                        try {
                            String response = Utils.Http.get(server + "/request_token?uin=" + uin);
                            DataWrapper body = new Gson().fromJson(response, DataWrapper.class);
                            if (body.code != 0) {
                                throw new IllegalStateException(body.message);
                            }
                            logger.debug("Bot(" + uin + ") request_tiken, " + body.message);

                            List<RequestCallback> request = new Gson().fromJson(body.data, new TypeToken<List<RequestCallback>>() {}.getType());

                            callback(uin, request);
                        } catch (Throwable cause){
                            logger.error(cause);
                        }
                        // requestToken - end
                    }, interval / 50);
                }
            }
        }

        if(!CMD_WHITE_LIST.contains(commandName)) return null;

        SignResult data = sign(context.getId(),commandName,sequenceId,payload);

        callback(context.getId(),data.request);

        return new EncryptService.SignResult(hexToBytes(data.sign), hexToBytes(data.token), hexToBytes(data.extra));
    }

    private SignResult sign(long uin, String cmd, int seq, byte[] buffer){
        MiraiLogger logger = Bot.getInstance(uin).getLogger();

        try {
            String response = Utils.Http.get(server + "/sign?uin=" + uin
                    + "&cmd=" + cmd
                    + "&seq=" + seq
                    + "&buffer=" + toUHexString(buffer, "", 0, buffer.length));
            DataWrapper body = new Gson().fromJson(response, DataWrapper.class);
            if (body.code != 0) throw new IllegalStateException(body.message);

            logger.debug("Bot(" + uin + ") sign " + cmd + ", " + body.message);

            return new Gson().fromJson(body.data, SignResult.class);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void callback(long uin, List<RequestCallback> request){
        MiraiLogger logger = Bot.getInstance(uin).getLogger();

        MiraiMCPlugin.getPlatform().runTaskAsync(() -> {
            for(RequestCallback callback : request){
                logger.verbose("Bot(" + uin + ") sendMessage " + callback.cmd);
                //ChannelResult result = MiraiEncryptServiceFactoryKt.channelSendMessage(channel0, "mobileqq.msf.security", callback.cmd, 0, hexToBytes(callback.body));
                ChannelResult result = MiraiEncryptServiceFactoryKt.channelSendMessage(channel0, "mobileqq.msf.security", callback.cmd, 0, hexToBytes(callback.body));

                if (result == null) {
                    logger.debug(callback.cmd + " ChannelResult is null");
                    continue;
                }

                submit(uin,result.getCmd(), callback.id, result.getData());
            }
        });
    }

    private void submit(long uin, String cmd, int callbackId, byte[] buffer){
        MiraiLogger logger = Bot.getInstance(uin).getLogger();

        try {
            String response = Utils.Http.get(server + "/submit?uin=" + uin
                    + "&cmd=" + cmd
                    + "&callback_id=" + callbackId
                    + "&buffer=" + toUHexString(buffer, "", 0, buffer.length));
            DataWrapper body = new Gson().fromJson(response, DataWrapper.class);
            if (body.code != 0) throw new IllegalStateException(body.message);

            logger.debug("Bot(" + uin + ") submit " + cmd + ", " + body.message);
        } catch (IOException e){
            logger.error("Bot(" + uin + ") submit " + cmd, e);
        }
    }

    private static class RequestCallback {
        @SerializedName("body")
        String body;
        @SerializedName(value = "callback_id", alternate = {"callbackId"})
        int id;
        @SerializedName("cmd")
        String cmd;
    }

    private static class SignResult {
        @SerializedName("token")
        String token = "";
        @SerializedName("extra")
        String extra = "";
        @SerializedName("sign")
        String sign = "";
        @SerializedName("o3did")
        String o3did = "";
        @SerializedName("requestCallback")
        List<RequestCallback> request = Collections.emptyList();
    }
}
