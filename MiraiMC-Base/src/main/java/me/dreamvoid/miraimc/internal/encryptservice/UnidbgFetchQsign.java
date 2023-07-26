package me.dreamvoid.miraimc.internal.encryptservice;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.internal.spi.EncryptService;
import net.mamoe.mirai.internal.spi.EncryptServiceContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnidbgFetchQsign implements EncryptService {
    private final String server;
    private final String key;
    private final CoroutineContext context;

    public UnidbgFetchQsign(String server, String key, CoroutineContext context){
        this.server = server;
        this.key = key;
        this.context = context;
    }

    @Nullable
    @Override
    public byte[] encryptTlv(@NotNull EncryptServiceContext encryptServiceContext, int i, @NotNull byte[] bytes) {
        return new byte[0];
    }

    @Override
    public void initialize(@NotNull EncryptServiceContext encryptServiceContext) {

    }

    @Nullable
    @Override
    public SignResult qSecurityGetSign(@NotNull EncryptServiceContext encryptServiceContext, int i, @NotNull String s, @NotNull byte[] bytes) {
        return null;
    }
}
