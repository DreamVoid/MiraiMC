package me.dreamvoid.miraimc.paper.utils;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.dreamvoid.miraimc.commands.ICommandSender;
import org.bukkit.ChatColor;

@SuppressWarnings({"UnstableApiUsage", "deprecation"})
public class SenderUtils {
    public static ICommandSender getSender(CommandSourceStack stack){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                stack.getSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return stack.getSender().hasPermission(permission);
            }
        };
    }
}
