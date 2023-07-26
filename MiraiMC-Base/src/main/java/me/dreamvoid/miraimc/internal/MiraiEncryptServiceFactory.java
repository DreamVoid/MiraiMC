package me.dreamvoid.miraimc.internal;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import kotlinx.coroutines.CoroutineScope;
import me.dreamvoid.miraimc.MiraiMCConfig;
import me.dreamvoid.miraimc.MiraiMCPlugin;
import me.dreamvoid.miraimc.internal.encryptservice.TLV544Provider;
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
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public class MiraiEncryptServiceFactory implements EncryptService.Factory {
    private final ConcurrentHashMap.KeySetView<Object, Boolean> created = ConcurrentHashMap.newKeySet();
    private static File config;

    public static void install(){
        MiraiEncryptServiceFactoryKt.install();
        config = new File(MiraiMCConfig.PluginDir, "services.json");
        if (!config.exists()) {
            try (InputStream in = MiraiMCPlugin.class.getResourceAsStream("/services.json")) {
                assert in != null;
                Files.copy(in, config.toPath());
            } catch (IOException e) {
                Utils.logger.warning("Failed to create services.json");
            }
        }
    }

    @NotNull
    @Override
    public EncryptService createForBot(@NotNull EncryptServiceContext context, @NotNull CoroutineScope scope) {
        /*
        if (created.add(context.getId())) {
            throw new UnsupportedOperationException("repeated create EncryptService");
        }
        */

        MiraiLogger logger = Bot.getInstance(context.getId()).getLogger();

        BotConfiguration.MiraiProtocol protocol = MiraiEncryptServiceFactoryKt.getProtocol(context);
        switch (protocol){
            case ANDROID_PHONE: case ANDROID_PAD:{
                String version = MiraiEncryptServiceFactoryKt.getProtocolVersion(protocol);
                ServerConfig server;
                try {
                    Type type = new TypeToken<Services>(){}.getType();
                    Services services = new Gson().fromJson(new FileReader(config), type);
                    server = services.get(version);
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
                                System.setProperty("MiraiMC.EncryptService.REQUEST_TOKEN_INTERVAL", "0");
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
                    case "kiliokuara/magic-signer-guide": case "kiliokuara": case "magic-signer-guide": case "vivo50":{
                        try {
                            String about = Utils.Http.get(server.base);
                            logger.info("magic-signer-guide by "+server.base+" about \n" + about);
                            if(about.trim().equals("void")){
                                logger.warning("请更新 magic-signer-guide 的 docker 镜像");
                            }
                            if(!about.contains(version)){
                                throw new IllegalStateException("magic-signer-guide by "+server.base+" 与 "+protocol+"("+version+") 似乎不匹配");
                            }
                        } catch (IOException cause) {
                            throw new RuntimeException("请检查 magic-signer-guide by "+server.base+" 的可用性", cause);
                        }
                        throw new UnsupportedOperationException(); // 暂时做不出来
                    }
                    default:throw new UnsupportedOperationException(server.type);
                }
            }
            case ANDROID_WATCH: default: throw new UnsupportedOperationException(protocol.name());
            case IPAD:
            case MACOS:{
                logger.error("$protocol 尚不支持签名服务，大概率登录失败");
                return new TLV544Provider();
            }
        }

        //created.remove(context.getId());
    }

    private static class Services extends LinkedHashMap<String, ServerConfig> {

    }

    private static class ServerConfig {
        @SerializedName("base_url")
        String base;

        @SerializedName("type")
        String type = "";

        @SerializedName("key")
        String key = "";

        @SerializedName("server_identity_key")
        String serverIdentityKey = "";

        @SerializedName("authorization_key")
        String authorizationKey = "";
    }
}
