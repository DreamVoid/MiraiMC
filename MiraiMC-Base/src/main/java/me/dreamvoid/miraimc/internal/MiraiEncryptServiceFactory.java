package me.dreamvoid.miraimc.internal;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import kotlinx.coroutines.CoroutineScope;
import me.dreamvoid.miraimc.LifeCycle;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.encryptservice.UnidbgFetchQsign;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.internal.spi.EncryptService;
import net.mamoe.mirai.internal.spi.EncryptServiceContext;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.MiraiLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MiraiEncryptServiceFactory implements EncryptService.Factory {
    public static final String REQUEST_TOKEN_INTERVAL = "MiraiMC.EncryptService.REQUEST_TOKEN_INTERVAL";
    private static File config;

    public static void install(){
        MiraiEncryptServiceFactoryKt.install();
        config = new File(PluginConfig.PluginDir, "services.json");
        if (!config.exists()) {
            try (InputStream in = LifeCycle.class.getResourceAsStream("/services.json")) {
                assert in != null;
                Files.copy(in, config.toPath());
            } catch (IOException e) {
                throw new RuntimeException("Failed to create services.json", e);
            }
        }
    }

    @NotNull
    @Override
    public EncryptService createForBot(@NotNull EncryptServiceContext context, @NotNull CoroutineScope scope) {
        MiraiLogger logger = Bot.getInstance(context.getId()).getLogger();
        BotConfiguration.MiraiProtocol protocol = MiraiEncryptServiceFactoryKt.getProtocol(context); // 协议，用Java不能直接获取，所以Kotlin桥接一下

        switch (protocol){
            case ANDROID_PHONE: case ANDROID_PAD:{
                String version = MiraiEncryptServiceFactoryKt.getProtocolVersion(protocol); // 协议版本

                ServerConfig server;
                try {
                    HashMap<String, LinkedTreeMap> services = new Gson().fromJson(new FileReader(config), new TypeToken<HashMap<String, LinkedTreeMap>>(){}.getType());
                    server = Objects.requireNonNull(new Gson().fromJson(new Gson().toJson(services.get(version)), ServerConfig.class));
                } catch (IOException e) {
                    throw new RuntimeException("配置文件读取错误，" + config, e);
                } catch (NullPointerException e){
                    throw new NoSuchElementException("没有找到对应 " + protocol + "(" + version + ") 的服务配置：" + e);
                }

                logger.info(protocol + "("+version+") server type: " + server.type + ", " + config.toPath().toUri());
                if(server.type.isEmpty()) throw new IllegalArgumentException("need server type");
                switch (server.type){
                    case "fuqiuluo/unidbg-fetch-qsign": case "fuqiuluo": case "unidbg-fetch-qsign":{
                        try {
                            String about = Utils.Http.get(server.base);
                            logger.info("unidbg-fetch-qsign by "+server.base + " about \n" + about);
                            if(!about.contains("version")){
                                System.setProperty(REQUEST_TOKEN_INTERVAL, "0");
                                logger.warning("请更新 unidbg-fetch-qsign");
                            }
                            if(!about.contains(version)){
                                throw new IllegalStateException("unidbg-fetch-qsign by "+server.base+" 与 "+protocol+"("+version+") 似乎不匹配");
                            }
                        } catch (IOException cause){
                            throw new RuntimeException("请检查 unidbg-fetch-qsign by "+server.base+" 的可用性", cause);
                        }

                        return new UnidbgFetchQsign(server.base, server.key, scope.getCoroutineContext());
                    }
                    default:throw new UnsupportedOperationException(server.type);
                }
            }
            case ANDROID_WATCH: default: throw new UnsupportedOperationException(protocol.name());
            case IPAD: case MACOS:{
                logger.error(protocol.name() + " 尚不支持签名服务");
                throw new UnsupportedOperationException(protocol.name());
            }
        }

        //created.remove(context.getId());
    }

    private static class ServerConfig {
        @SerializedName("base_url")
        String base;

        @SerializedName("type")
        String type;

        @SerializedName("key")
        String key;

        @SerializedName("server_identity_key")
        String serverIdentityKey;

        @SerializedName("authorization_key")
        String authorizationKey;
    }
}
