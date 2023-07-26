package me.dreamvoid.miraimc.internal;

import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.internal.spi.EncryptService;
import net.mamoe.mirai.internal.spi.EncryptServiceContext;
import org.jetbrains.annotations.NotNull;

public class MiraiEncryptServiceFactory implements EncryptService.Factory {
    @NotNull
    @Override
    public EncryptService createForBot(@NotNull EncryptServiceContext encryptServiceContext, @NotNull CoroutineScope coroutineScope) {
        return null;
    }
}
