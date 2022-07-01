package me.dreamvoid.miraimc.internal;

import java.io.File;

/**
 * MiraiMC 插件配置<br>
 * 如果添加新配置，此处请使用大驼峰命名法
 */
public class Config {
    public static File PluginDir;

    public static class General{ // general
        public static boolean AllowBStats; // allow-bStats
        public static boolean CheckUpdate; // check-update
        public static boolean DisableSafeWarningMessage; // disable-safe-warning-message
        public static String MiraiWorkingDir; // mirai-working-dir
        public static class AddProperties{ // add-properties
            public static boolean MiraiNoDesktop; // mirai.no-desktop
            public static boolean MiraiSliderCaptchaSupported; // mirai.slider.captcha.supported
        }
        public static String MiraiCoreVersion; // mirai-core-version
        public static String MavenRepoUrl; // maven-repo-url
        public static boolean EnableHttpApi; // enable-http-api
        public static boolean LegacyEventSupport; // legacy-event-support
    }

    public static class Bot{ // bot
        public static boolean DisableNetworkLogs; // disable-network-logs
        public static boolean DisableBotLogs; // disable-bot-logs
        public static class UseMinecraftLogger{ // use-minecraft-logger
            public static boolean BotLogs; // bot-logs
            public static boolean NetworkLogs; // network-logs
        }
        public static boolean LogEvents; // log-events
        public static class ContactCache{ // contact-cache
            public static boolean EnableFriendListCache; // enable-friend-list-cache
            public static boolean EnableGroupMemberListCache; // enable-group-member-list-cache
            public static long SaveIntervalMillis; // save-interval-millis
        }
    }

    public static class Database{ // database
        public static String Type; // type
        public static class MySQL{ // mysql
            public static String Address; // address
            public static String Username; // username
            public static String Password; // password
            public static String Database; // database
            public static class Poll{ // pool
                public static int ConnectionTimeout; // connectionTimeout
                public static int IdleTimeout; // idleTimeout
                public static int MaxLifetime; // maxLifetime
                public static int MaximumPoolSize; // maximumPoolSize
                public static int KeepaliveTime; // keepaliveTime
                public static int MinimumIdle; // minimumIdle
            }
        }
    }

    public static class HttpApi{ // http-api
        public static String Url; // url
        public static class MessageFetch{ // message-fetch
            public static long Interval; // interval
            public static int Count; // count
        }
    }
}
