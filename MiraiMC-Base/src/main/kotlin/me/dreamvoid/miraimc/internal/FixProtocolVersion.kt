package me.dreamvoid.miraimc.internal

import kotlinx.serialization.json.*
import me.dreamvoid.miraimc.api.MiraiMC
import net.mamoe.mirai.internal.utils.*
import net.mamoe.mirai.utils.*
import java.io.*
import java.net.*
import java.time.*

@Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")
object FixProtocolVersion {

    private val clazz = MiraiProtocolInternal::class.java

    private val constructor = clazz.constructors.single()

    @PublishedApi
    internal val protocols: MutableMap<BotConfiguration.MiraiProtocol, MiraiProtocolInternal> by lazy {
        try {
            MiraiProtocolInternal.protocols
        } catch (_: NoSuchMethodError) {
            with(MiraiProtocolInternal) {
                this::class.members
                    .first { "protocols" == it.name }
                    .call(this)
            }.cast()
        }
    }

    @PublishedApi
    internal fun <T> MiraiProtocolInternal.field(name: String, default: T): T {
        @Suppress("UNCHECKED_CAST")
        return kotlin.runCatching {
            val field = clazz.getDeclaredField(name)
            field.isAccessible = true
            field.get(this) as T
        }.getOrElse {
            default
        }
    }

    @PublishedApi
    internal fun MiraiProtocolInternal.change(block: MiraiProtocolInternalBuilder.() -> Unit): MiraiProtocolInternal {
        val builder = MiraiProtocolInternalBuilder(this).apply(block)
        return when (constructor.parameterCount) {
            10 -> constructor.newInstance(
                builder.apkId,
                builder.id,
                builder.ver,
                builder.sdkVer,
                builder.miscBitMap,
                builder.subSigMap,
                builder.mainSigMap,
                builder.sign,
                builder.buildTime,
                builder.ssoVersion
            )
            11 -> constructor.newInstance(
                builder.apkId,
                builder.id,
                builder.ver,
                builder.sdkVer,
                builder.miscBitMap,
                builder.subSigMap,
                builder.mainSigMap,
                builder.sign,
                builder.buildTime,
                builder.ssoVersion,
                builder.supportsQRLogin
            )
            else -> this
        } as MiraiProtocolInternal
    }

    @PublishedApi
    internal class MiraiProtocolInternalBuilder(impl: MiraiProtocolInternal) {
        var apkId: String = impl.field("apkId", "")
        var id: Long = impl.field("id", 0)
        var ver: String = impl.field("ver", "")
        var sdkVer: String = impl.field("sdkVer", "")
        var miscBitMap: Int = impl.field("miscBitMap", 0)
        var subSigMap: Int = impl.field("subSigMap", 0)
        var mainSigMap: Int = impl.field("mainSigMap", 0)
        var sign: String = impl.field("sign", "")
        var buildTime: Long = impl.field("buildTime", 0)
        var ssoVersion: Int = impl.field("ssoVersion", 0)
        var supportsQRLogin: Boolean = impl.field("supportsQRLogin", false)
    }

    /**
     * fix-protocol-version 插件最初的功能
     *
     * 根据本地代码检查协议版本更新
     */
    @JvmStatic
    fun update() {
        protocols.compute(BotConfiguration.MiraiProtocol.ANDROID_PHONE) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    apkId = "com.tencent.mobileqq"
                    id = 537163098
                    ver = "8.9.58.11170"
                    sdkVer = "6.0.0.2545"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x0214_10E0
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1684467300L
                    ssoVersion = 20
                }
                impl.id < 537163098 -> impl.apply {
                    apkId = "com.tencent.mobileqq"
                    id = 537163098
                    ver = "8.9.58"
                    buildVer = "8.9.58.11170"
                    sdkVer = "6.0.0.2545"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x0214_10E0
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1684467300L
                    ssoVersion = 20
                    appKey = "0S200MNJT807V3GE"
                    supportsQRLogin = false
                }
                else -> impl
            }
        }
        protocols.compute(BotConfiguration.MiraiProtocol.ANDROID_PAD) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    apkId = "com.tencent.mobileqq"
                    id = 537161402
                    ver = "8.9.58.11170"
                    sdkVer = "6.0.0.2545"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x0214_10E0
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1684467300L
                    ssoVersion = 20
                }
                impl.id < 537161402 -> impl.apply {
                    apkId = "com.tencent.mobileqq"
                    id = 537161402
                    ver = "8.9.58"
                    buildVer = "8.9.58.11170"
                    sdkVer = "6.0.0.2545"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x0214_10E0
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1684467300L
                    ssoVersion = 20
                    appKey = "0S200MNJT807V3GE"
                    supportsQRLogin = false
                }
                else -> impl
            }
        }
        protocols.compute(BotConfiguration.MiraiProtocol.ANDROID_WATCH) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    apkId = "com.tencent.qqlite"
                    id = 537065138
                    ver = "2.0.8"
                    sdkVer = "6.0.0.2365"
                    miscBitMap = 0x00F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x00FF_32F2
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1559564731L
                    ssoVersion = 5
                    supportsQRLogin = true
                }
                impl.id < 537065138 -> impl.apply {
                    apkId = "com.tencent.qqlite"
                    id = 537065138
                    ver = "2.0.8"
                    buildVer = "2.0.8"
                    sdkVer = "6.0.0.2365"
                    miscBitMap = 0x00F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x00FF_32F2
                    sign = "A6 B7 45 BF 24 A2 C2 77 52 77 16 F6 F3 6E B6 8D"
                    buildTime = 1559564731L
                    ssoVersion = 5
                    supportsQRLogin = true
                }
                else -> impl
            }
        }
        protocols.compute(BotConfiguration.MiraiProtocol.IPAD) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    apkId = "com.tencent.minihd.qq"
                    id = 537155074
                    ver = "8.9.50.611"
                    sdkVer = "6.0.0.2535"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x001E_10E0
                    sign = "AA 39 78 F4 1F D9 6F F9 91 4A 66 9E 18 64 74 C7"
                    buildTime = 1676531414L
                    ssoVersion = 19
                }
                impl.id < 537155074 -> impl.apply {
                    apkId = "com.tencent.minihd.qq"
                    id = 537155074
                    ver = "8.9.50"
                    buildVer = "8.9.50.611"
                    sdkVer = "6.0.0.2535"
                    miscBitMap = 0x08F7_FF7C
                    subSigMap = 0x0001_0400
                    mainSigMap = 0x001E_10E0
                    sign = "AA 39 78 F4 1F D9 6F F9 91 4A 66 9E 18 64 74 C7"
                    buildTime = 1676531414L
                    ssoVersion = 19
                    appKey = "0S200MNJT807V3GE"
                    supportsQRLogin = false
                }
                else -> impl
            }
        }
        protocols.compute(BotConfiguration.MiraiProtocol.MACOS) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    id = 537128930
                    ver = "6.8.2.21241"
                    buildTime = 1647227495
                }
                impl.id < 537128930 -> impl.apply {
                    id = 537128930
                    ver = "6.8.2"
                    buildVer = "6.8.2.21241"
                    buildTime = 1647227495
                }
                else -> impl
            }
        }
    }

    /**
     * 从 [RomiChan/protocol-versions](https://github.com/RomiChan/protocol-versions) 同步最新协议
     *
     * @since 1.6.0
     */
    @Deprecated(
        message = "sync 作用不明确，故废弃",
        ReplaceWith(
            """fetch(protocol = protocol, version = "latest")""",
            "xyz.cssxsh.mirai.tool.FixProtocolVersion.fetch"
        )
    )
    @JvmStatic
    fun sync(protocol: BotConfiguration.MiraiProtocol): Unit = fetch(protocol = protocol, version = "latest")

    /**
     * 从 [RomiChan/protocol-versions](https://github.com/RomiChan/protocol-versions) 获取指定版本协议
     *
     * @since 1.9.6
     */
    @JvmStatic
    fun fetch(protocol: BotConfiguration.MiraiProtocol, version: String) {
        val (file, url) = when (protocol) {
            BotConfiguration.MiraiProtocol.ANDROID_PHONE -> {
                File(File(MiraiMC.getConfig().PluginDir,"protocol"),"android_phone.json") to
                    when (version) {
                        "", "latest" -> URL("https://raw.githubusercontent.com/RomiChan/protocol-versions/master/android_phone.json")
                        else -> URL("https://raw.githubusercontent.com/RomiChan/protocol-versions/master/android_phone/${version}.json")
                    }
            }
            BotConfiguration.MiraiProtocol.ANDROID_PAD -> {
                File(File(MiraiMC.getConfig().PluginDir,"protocol"),"android_pad.json") to
                    when (version) {
                        "", "latest" -> URL("https://raw.githubusercontent.com/RomiChan/protocol-versions/master/android_pad.json")
                        else -> URL("https://raw.githubusercontent.com/RomiChan/protocol-versions/master/android_pad/${version}.json")
                    }
            }
            else -> throw IllegalArgumentException("不支持同步的协议: ${protocol.name}")
        }

        val json: JsonObject = kotlin.runCatching {
            url.openConnection()
                .apply {
                    connectTimeout = 30_000
                    readTimeout = 30_000
                }
                .getInputStream().use { it.readBytes() }
                .decodeToString()
        }.recoverCatching { throwable ->
            try {
                URL("https://ghproxy.com/$url").openConnection()
                    .apply {
                        connectTimeout = 30_000
                        readTimeout = 30_000
                    }
                    .getInputStream().use { it.readBytes() }
                    .decodeToString()
            } catch (cause: Throwable) {
                val exception = throwable.cause as? java.net.UnknownHostException ?: throwable
                exception.addSuppressed(cause)
                throw exception
            }
        }.fold(
            onSuccess = { text ->
                val online = Json.parseToJsonElement(text).jsonObject
                check(online.getValue("app_id").jsonPrimitive.long != 0L) { "载入的 ${protocol.name.lowercase()}.json 有误" }
                file.writeText(text)
                file.setLastModified(online.getValue("build_time").jsonPrimitive.long * 1000)
                online
            },
            onFailure = { cause ->
                throw IllegalStateException("从 $url 下载协议失败", cause)
            }
        )

        store(protocol, json)
    }

    /**
     * 从本地加载协议
     *
     * @since 1.8.0
     */
    @JvmStatic
    fun load(protocol: BotConfiguration.MiraiProtocol) {
        val file = File(File(MiraiMC.getConfig().PluginDir,"protocol"),"${protocol.name.lowercase()}.json")
        val json: JsonObject = Json.parseToJsonElement(file.readText()).jsonObject

        store(protocol, json)
    }

    @JvmStatic
    private fun store(protocol: BotConfiguration.MiraiProtocol, json: JsonObject) {
        check(json.getValue("app_id").jsonPrimitive.long != 0L) { "载入的 ${protocol.name.lowercase()}.json 有误" }
        protocols.compute(protocol) { _, impl ->
            when {
                null == impl -> null
                impl.runCatching { id }.isFailure -> impl.change {
                    apkId = json.getValue("apk_id").jsonPrimitive.content
                    id = json.getValue("app_id").jsonPrimitive.long
                    ver = json.getValue("sort_version_name").jsonPrimitive.content
                    sdkVer = json.getValue("sdk_version").jsonPrimitive.content
                    miscBitMap = json.getValue("misc_bitmap").jsonPrimitive.int
                    subSigMap = json.getValue("sub_sig_map").jsonPrimitive.int
                    mainSigMap = json.getValue("main_sig_map").jsonPrimitive.int
                    sign = json.getValue("apk_sign").jsonPrimitive.content.hexToBytes().toUHexString(" ")
                    buildTime = json.getValue("build_time").jsonPrimitive.long
                    ssoVersion = json.getValue("sso_version").jsonPrimitive.int
                }
                else -> impl.apply {
                    apkId = json.getValue("apk_id").jsonPrimitive.content
                    id = json.getValue("app_id").jsonPrimitive.long
                    buildVer = json.getValue("sort_version_name").jsonPrimitive.content
                    ver = buildVer.substringBeforeLast(".")
                    sdkVer = json.getValue("sdk_version").jsonPrimitive.content
                    miscBitMap = json.getValue("misc_bitmap").jsonPrimitive.int
                    subSigMap = json.getValue("sub_sig_map").jsonPrimitive.int
                    mainSigMap = json.getValue("main_sig_map").jsonPrimitive.int
                    sign = json.getValue("apk_sign").jsonPrimitive.content.hexToBytes().toUHexString(" ")
                    buildTime = json.getValue("build_time").jsonPrimitive.long
                    ssoVersion = json.getValue("sso_version").jsonPrimitive.int
                    appKey = json.getValue("app_key").jsonPrimitive.content
                }
            }
        }
    }

    /**
     * 协议版本信息
     *
     * @since 1.6.0
     */
    @JvmStatic
    fun info(): Map<BotConfiguration.MiraiProtocol, String> {
        return protocols.mapValues { (protocol, info) ->
            val version = info.field("buildVer", null as String?) ?: info.field("ver", "???")
            val epochSecond = info.field("buildTime", 0L)
            val datetime = OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSecond), ZoneId.systemDefault())

            "%-13s  %-12s  %s".format(protocol, version, datetime)
        }
    }
}