package me.dreamvoid.miraimc.internal;

public class ConfigSerializable {
    public General general;
    
    public static class General{
        public final boolean allow__bStats = true;
        public final boolean check__update = true;
        public final boolean disable__safe__warning__message = false;
        public final String mirai__working__dir = "default";
        public final String mirai__core__version = "stable";
        public final String maven__repo__url = "https://repo.huaweicloud.com/repository/maven/";
        public final boolean enable__http__api = false;
        public final boolean legacy__event__support = false;
        public final boolean auto__open__qrcode__file = false;
    }
    
    public Bot bot;
    
    public static class Bot{
        public final boolean disable__network__logs = false;
        public final boolean disable__bot__logs = false;
        public UseMinecraftLogger use__minecraft__logger;
        public final boolean log__events = true;
        public ContactCache contact__cache;
        
        public static class UseMinecraftLogger {
            public final boolean bot__logs = true;
            public final boolean network__logs = true;
        }
        
        public static class ContactCache{
            public final boolean enable__friend__list__cache = false;
            public final boolean enable__group__member__list__cache = false;
            public final int save__interval__millis = 60000;
        }
    }
    
    public Database database;
    
    public static class Database{
        public final String type = "sqlite";
        public MySQL mysql;

        public static class MySQL{
            public final String address = "localhost";
            public final String username = "miraimc";
            public final String password = "miraimc";
            public final String database = "miraimc";
            public Pool pool;
            
            public static class Pool{
                public final int connectionTimeout = 30000;
                public final int idleTimeout = 600000;
                public final int maxLifetime = 1800000;
                public final int maximumPoolSize = 15;
                public final int keepaliveTime = 0;
                public final int minimumIdle = 5;
            }
        }
    }

    public HttpApi http__api;

    public static class HttpApi{
        public final String url = "http://localhost:8080";
        public MessageFetch message__fetch;

        public static class MessageFetch{
            public final int interval = 10;
            public final int count = 10;
        }
    }
}
