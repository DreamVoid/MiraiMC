package me.dreamvoid.miraimc.commands;

public interface ICommandExecutor {
    boolean onCommand(ICommandSender sender, String[] args);
}
