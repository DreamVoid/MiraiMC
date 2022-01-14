package me.dreamvoid.miraimc.sponge.utils;

import java.util.List;

public class AutoLoginObject {
    public List<Accounts> accounts;

    public static class Accounts{
        public long account;
        public Password password;
        public Configuration configuration;
    }

    public class Password{
        public String kind;
        public String value;
    }

    public class Configuration{
        public String protocol;
        public String device;
    }
}
