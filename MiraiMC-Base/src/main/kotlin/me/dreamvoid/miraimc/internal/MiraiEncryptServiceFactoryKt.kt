package me.dreamvoid.miraimc.internal

import net.mamoe.mirai.internal.spi.EncryptService
import net.mamoe.mirai.utils.Services

class MiraiEncryptServiceFactoryKt {
    companion object{
        @JvmStatic
        fun install(){
            Services.register(EncryptService.Factory::class.qualifiedName!!, MiraiEncryptServiceFactory::class.qualifiedName!!, ::MiraiEncryptServiceFactory)
        }
    }
}