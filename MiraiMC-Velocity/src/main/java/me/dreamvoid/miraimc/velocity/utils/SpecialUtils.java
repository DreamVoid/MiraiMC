package me.dreamvoid.miraimc.velocity.utils;

import com.velocitypowered.api.command.CommandSource;
import me.dreamvoid.miraimc.commands.ICommandSender;
import net.kyori.adventure.text.Component;

public class SpecialUtils {
    public static ICommandSender getSender(CommandSource sender){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(Component.text(Color.translate(message)));
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }
        };
    }
}
