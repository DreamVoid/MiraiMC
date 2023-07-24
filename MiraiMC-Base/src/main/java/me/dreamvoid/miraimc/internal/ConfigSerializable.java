package me.dreamvoid.miraimc.internal;

public class ConfigSerializable {
    public General general;
    
    public static class General{
        public boolean allow__bStats = true;
        public boolean check__update = true;
        public boolean disable__safe__warning__message = false;
        public String mirai__working__dir = "default";
        public String mirai__core__version = "stable";
        public String maven__repo__url = "https://repo.huaweicloud.com/repository/maven/";
        public boolean enable__http__api = false;
        public boolean legacy__event__support = false;
        public boolean auto__open__qrcode__file = false;
    }
    
    public Bot bot;
    
    public static class Bot{
        public boolean disable__network__logs = false;
        public boolean disable__bot__logs = false;
        public UseMinecraftLogger use__minecraft__logger;
        public boolean log__events = true;
        public ContactCache contact__cache;
        
        public static class UseMinecraftLogger {
            public boolean bot__logs = true;
            public boolean network__logs = true;
        }
        
        public static class ContactCache{
            public boolean enable__friend__list__cache = false;
            public boolean enable__group__member__list__cache = false;
            public int save__interval__millis = 60000;
        }
    }
    
    public Database database;
    
    public static class Database{
        public String type = "sqlite";
        public MySQL mysql;

        public static class MySQL{
            public String address = "localhost";
            public String username = "miraimc";
            public String password = "miraimc";
            public String database = "miraimc";
            public Pool pool;
            
            public static class Pool{
                public int connectionTimeout = 30000;
                public int idleTimeout = 600000;
                public int maxLifetime = 1800000;
                public int maximumPoolSize = 15;
                public int keepaliveTime = 0;
                public int minimumIdle = 5;
            }
        }
    }

    public HttpApi http__api;

    public static class HttpApi{
        public String url = "http://localhost:8080";
        public MessageFetch message__fetch;

        public static class MessageFetch{
            public int interval = 10;
            public int count = 10;
        }
    }
}
