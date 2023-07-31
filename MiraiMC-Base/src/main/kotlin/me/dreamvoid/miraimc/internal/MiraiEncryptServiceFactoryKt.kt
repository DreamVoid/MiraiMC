package me.dreamvoid.miraimc.internal

import net.mamoe.mirai.internal.spi.EncryptService
import net.mamoe.mirai.internal.spi.EncryptServiceContext
import net.mamoe.mirai.internal.utils.*
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.Services

class MiraiEncryptServiceFactoryKt {
    companion object{
        @JvmStatic
        fun install(){
            Services.register(EncryptService.Factory::class.qualifiedName!!, MiraiEncryptServiceFactory::class.qualifiedName!!, ::MiraiEncryptServiceFactory)
        }

        @JvmStatic
        fun getProtocol(context : EncryptServiceContext) : BotConfiguration.MiraiProtocol{
            return context.extraArgs[EncryptServiceContext.KEY_BOT_PROTOCOL]
        }

        @JvmStatic
        @Suppress("INVISIBLE_MEMBER")
        fun getProtocolVersion(protocol : BotConfiguration.MiraiProtocol) : String {
            return MiraiProtocolInternal[protocol].ver
        }
    }
}