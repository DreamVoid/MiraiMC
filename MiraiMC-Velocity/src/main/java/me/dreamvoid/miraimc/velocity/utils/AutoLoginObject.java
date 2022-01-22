package me.dreamvoid.miraimc.velocity.utils;

import java.util.List;

public class AutoLoginObject {
    private List<Accounts> accounts;

    public static class Accounts{
        private long account;
        private Password password;
        private Configuration configuration;

        public Configuration getConfiguration() {
            return configuration;
        }

        public long getAccount() {
            return account;
        }

        public Password getPassword() {
            return password;
        }

        public void setAccount(long account) {
            this.account = account;
        }

        public void setConfiguration(Configuration configuration) {
            this.configuration = configuration;
        }

        public void setPassword(Password password) {
            this.password = password;
        }
    }

    public static class Password{
        private String kind;
        private String value;

        public String getKind() {
            return kind;
        }

        public String getValue() {
            return value;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Configuration{
        private String protocol;
        private String device;

        public String getDevice() {
            return device;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "AutoLoginObject{" +
                "accounts=" + accounts +
                '}';
    }
}