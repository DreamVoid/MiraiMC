package me.dreamvoid.miraimc.commands;

public interface ICommandSender {
    void sendMessage(String message);

    boolean hasPermission(String permission);
}
