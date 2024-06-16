package me.dreamvoid.miraimc.internal.config;

import java.io.File;
import java.io.IOException;

public abstract class PluginConfig {
    public static File PluginDir;
    protected static PluginConfig INSTANCE;

    public abstract void loadConfig() throws IOException;

    public static void reloadConfig() throws IOException {
        INSTANCE.loadConfig();
    }

    // 配置部分

    public static class General {
        public static boolean AllowBStats = true;
        public static boolean CheckUpdate = true;
        public static boolean DisableSafeWarningMessage = false;
        public static String MiraiWorkingDir = "default";
        public static String MiraiCoreVersion = "stable";
        public static String MavenRepoUrl = "https://repo.huaweicloud.com/repository/maven/";
        public static boolean EnableHttpApi = false;
        public static boolean AutoOpenQRCodeFile = false;
        public static boolean LogEvents = true;
    }

    public static class Bot{
        public static boolean DisableNetworkLogs = false;
        public static boolean DisableBotLogs = false;

        public static class UseMinecraftLogger {
            public static boolean BotLogs = true;
            public static boolean NetworkLogs = true;
        }

        public static class ContactCache{
            public static boolean EnableFriendListCache = false;
            public static boolean EnableGroupMemberListCache = false;
            public static long SaveIntervalMillis = 60000;
        }

        public static boolean RegisterEncryptService = false;
        public static boolean UpdateProtocolVersion = false;
    }

    public static class Database{
        public static String Type = "sqlite";

        public static class Drivers {

            public static class SQLite{
                public static String Path = "%plugin_folder%/database.db";
            }

            public static class MySQL{
                public static String Address = "localhost";
                public static String Username = "miraimc";
                public static String Password = "miraimc";
                public static String Database = "miraimc";
                public static String Parameters = "?useSSL=false";
            }
        }

        public static class Settings{
            public static String Prefix = "miraimc_";

            public static class Pool{
                public static int ConnectionTimeout = 30000;
                public static int IdleTimeout = 600000;
                public static int MaxLifetime = 1800000;
                public static int MaximumPoolSize = 15;
                public static int KeepaliveTime = 0;
                public static int MinimumIdle = 5;
            }
        }
    }

    public static class HttpApi{
        public static String Url = "http://localhost:8080";

        public static class MessageFetch{
            public static int Interval = 10;
            public static int Count = 10;
        }
    }
}
