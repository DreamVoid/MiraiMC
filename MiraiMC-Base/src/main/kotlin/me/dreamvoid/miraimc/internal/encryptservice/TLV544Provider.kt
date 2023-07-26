package me.dreamvoid.miraimc.internal.encryptservice

import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import net.mamoe.mirai.*
import net.mamoe.mirai.internal.spi.*
import net.mamoe.mirai.internal.utils.*
import net.mamoe.mirai.utils.*
import java.io.File
import java.util.*

public class TLV544Provider : EncryptService {
    public companion object {
        internal val SALT_V1 = arrayOf("810_2", "810_7", "810_24", "810_25")
        internal val SALT_V2 = arrayOf("810_9", "810_a", "810_d", "810_f")
        internal val SALT_V3 = arrayOf("812_a")
        internal val SALT_V4 = arrayOf("812_5")

        @JvmStatic
        internal val logger: MiraiLogger = MiraiLogger.Factory.create(TLV544Provider::class)

        @JvmStatic
        internal val LIBRARY_PATH_PROPERTY: String = "xyz.cssxsh.mirai.tool.t544"

        @JvmStatic
        internal external fun sign(payload: ByteArray): ByteArray

        @JvmStatic
        private val load = java.util.concurrent.atomic.AtomicBoolean(false)

        @JvmStatic
        private fun library(): String {
            val os = when (val name = System.getProperty("os.name")) {
                "Mac OS X" -> "macos"
                "Linux" -> if (System.getenv("TERMUX_VERSION") != null) "android" else "linux"
                else -> when {
                    name.startsWith("Win") -> "windows"
                    "The Android Project" == System.getProperty("java.specification.vendor") -> "android"
                    else -> throw RuntimeException("Unknown OS $name")
                }
            }
            val arch = when (val name = System.getProperty("os.arch")) {
                "x86" -> "x86"
                "x86_64", "amd64" -> "x64"
                "aarch64" -> "arm64"
                else -> throw RuntimeException("Unknown arch $name")
            }
            return System.mapLibraryName("t544-enc-${os}-${arch}")
        }

        @JvmStatic
        internal fun native() {
            if (load.get().not()) return
            val filename = library()
            val file = File(System.getProperty(LIBRARY_PATH_PROPERTY, filename))
            if (file.isFile.not()) {
                this::class.java.getResource(filename)?.let { resource ->
                    file.writeBytes(resource.readBytes())
                } ?: kotlin.run {
                    logger.error("not found: $filename")
                }
            } else {
                this::class.java.getResource("$filename.sha256")?.let { sha256 ->
                    val hash = sha256.readText().trim()
                    val digest = java.security.MessageDigest.getInstance("SHA-256")
                    val now = digest.digest(file.readBytes()).toUHexString("").lowercase()
                    if (hash != now) {
                        logger.warning("SHA256 not match $hash with ${file.toPath().toUri()}")
                    }
                } ?: kotlin.run {
                    logger.error("not found: $filename.sha256")
                }
            }
            logger.info("load: ${file.toPath().toUri()}")
            if (load.compareAndSet(false, true)) {
                System.load(file.absolutePath)
            }
        }

        @JvmStatic
        public fun install() {
            Services.register(
                "net.mamoe.mirai.internal.spi.EncryptService",
                "me.dreamvoid.miraimc.internal.encryptservice.TLV544Provider",
                ::TLV544Provider
            )
        }
    }

    init {
        native()
    }

    @Suppress("INVISIBLE_MEMBER")
    override fun encryptTlv(context: EncryptServiceContext, tlvType: Int, payload: ByteArray): ByteArray? {
        if (tlvType != 0x544) return null
        val command = context.extraArgs[EncryptServiceContext.KEY_COMMAND_STR]
        val protocol = try {
            context.extraArgs[EncryptServiceContext.KEY_BOT_PROTOCOL]
        } catch (_: NoSuchElementException) {
            Bot.getInstanceOrNull(context.id)?.configuration?.protocol
        }

        logger.info("t544 command: $command with $protocol")

        return when (command) {
            in SALT_V2 -> {
                // from MiraiGo
                sign(payload.copyInto(ByteArray(payload.size) { 0 }, 4, 4))
            }
            else -> {
                sign(payload)
            }
        }
    }

    override fun initialize(context: EncryptServiceContext) {
        // ...
    }

    override fun qSecurityGetSign(
        context: EncryptServiceContext,
        sequenceId: Int,
        commandName: String,
        payload: ByteArray
    ): EncryptService.SignResult? {
        return null
    }

    override fun toString(): String {
        val library = File(System.getProperty(LIBRARY_PATH_PROPERTY, library()))
        return "TLV544Provider(library=${library.toPath().toUri()})"
    }
}