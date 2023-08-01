package me.dreamvoid.miraimc.internal

import kotlinx.coroutines.*
import net.mamoe.mirai.internal.spi.EncryptService
import net.mamoe.mirai.internal.spi.EncryptServiceContext
import net.mamoe.mirai.internal.utils.*
import net.mamoe.mirai.utils.*
import kotlin.coroutines.CoroutineContext

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

        @JvmStatic
        fun getDeviceInfo(context: EncryptServiceContext) : DeviceInfo{
            return context.extraArgs[EncryptServiceContext.KEY_DEVICE_INFO]
        }

        @JvmStatic
        fun getQimei36(context: EncryptServiceContext) : String{
            return context.extraArgs[EncryptServiceContext.KEY_QIMEI36]
        }

        @JvmStatic
        fun getChannel(context: EncryptServiceContext) : EncryptService.ChannelProxy{
            return context.extraArgs[EncryptServiceContext.KEY_CHANNEL_PROXY]
        }

        @JvmStatic
        fun setCoroutineCompletionInvoke(context: CoroutineContext, code: () -> Unit){
            context.job.invokeOnCompletion { code() }
        }

        @JvmStatic
        fun getCommand(context: EncryptServiceContext) : String{
            return context.extraArgs[EncryptServiceContext.KEY_COMMAND_STR]
        }

        @JvmStatic
        fun getCoroutineContextKt(coroutineContext: CoroutineContext) : CoroutineContext{
            return coroutineContext + SupervisorJob(coroutineContext[Job]) + CoroutineExceptionHandler { context, exception ->
                when (exception) {
                    is CancellationException -> {
                        // ...
                    }
                    else -> {
                        MiraiLogger.Factory.create(MiraiEncryptServiceFactory::class).warning({"with ${context[CoroutineName]}"}, exception)
                    }
                }
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        @JvmStatic
        fun channelSendMessage(channel : EncryptService.ChannelProxy, remark: String, commandName: String, uin: Long, data: ByteArray) : EncryptService.ChannelResult? {
            var result : EncryptService.ChannelResult? = null

            GlobalScope.launch(Dispatchers.Default) {
                val result0 = withContext(Dispatchers.IO) {
                    channel.sendMessage(remark,commandName,uin,data)
                }
                result = result0
            }

            return result
        }
    }
}