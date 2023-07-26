package me.dreamvoid.miraimc.internal

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import net.mamoe.mirai.internal.spi.EncryptService
import net.mamoe.mirai.internal.spi.EncryptServiceContext
import net.mamoe.mirai.utils.BotConfiguration
import net.mamoe.mirai.utils.Services
import net.mamoe.mirai.internal.utils.*;

class MiraiEncryptServiceFactoryKt {
    companion object{
        @JvmStatic
        fun install(){
            Services.register(EncryptService.Factory::class.qualifiedName!!, MiraiEncryptServiceFactory::class.qualifiedName!!, ::MiraiEncryptServiceFactory)
        }

        @JvmStatic
        @Suppress("INVISIBLE_MEMBER")
        fun getProtocolVersion(protocol : BotConfiguration.MiraiProtocol) : String {
            return MiraiProtocolInternal[protocol].ver
        }

        @JvmStatic
        fun getProtocol(context : EncryptServiceContext) : BotConfiguration.MiraiProtocol{
            return context.extraArgs[EncryptServiceContext.KEY_BOT_PROTOCOL]
        }
    }

    @Serializable
    @OptIn(ExperimentalSerializationApi::class)
    private data class ServerConfig(
        @SerialName("base_url")
        val base: String,
        @SerialName("type")
        val type: String = "",
        @SerialName("key")
        val key: String = "",
        @SerialName("server_identity_key")
        @JsonNames("serverIdentityKey")
        val serverIdentityKey: String = "",
        @SerialName("authorization_key")
        @JsonNames("authorizationKey")
        val authorizationKey: String = ""
    )

}