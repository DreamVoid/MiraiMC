package me.dreamvoid.miraimc.internal;

import java.io.File;

public class Config {
    public static File PluginDir;

    public static boolean Gen_AllowBStats;
    public static boolean Gen_DisableSafeWarningMessage;
    public static String Gen_MiraiWorkingDir;
    public static boolean Gen_AddProperties_MiraiNoDesktop;
    public static boolean Gen_AddProperties_MiraiSliderCaptchaSupported;

    public static boolean Bot_DisableNetworkLogs;
    public static boolean Bot_DisableBotLogs;
    public static boolean Bot_UseBukkitLogger_BotLogs;
    public static boolean Bot_UseBukkitLogger_NetworkLogs;
    public static boolean Bot_LogEvents;
    public static boolean Bot_ContactCache_EnableFriendListCache;
    public static boolean Bot_ContactCache_EnableGroupMemberListCache;
    public static long Bot_ContactCache_SaveIntervalMillis;

    public static String DB_Type;
    public static String DB_MySQL_Address;
    public static String DB_MySQL_Username;
    public static String DB_MySQL_Password;
    public static String DB_MySQL_Database;
    public static int DB_MySQL_Poll_ConnectionTimeout;
    public static int DB_MySQL_Poll_IdleTimeout;
    public static int DB_MySQL_Poll_MaxLifetime;
    public static int DB_MySQL_Poll_MaximumPoolSize;
    public static int DB_MySQL_Poll_KeepaliveTime;
    public static int DB_MySQL_Poll_MinimumIdle;
}
