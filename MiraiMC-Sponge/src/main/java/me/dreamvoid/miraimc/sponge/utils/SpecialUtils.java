package me.dreamvoid.miraimc.sponge.utils;

import me.dreamvoid.miraimc.commands.ICommandSender;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.parameter.CommandContext;

public class SpecialUtils {
    public static ICommandSender getSender(CommandContext context){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                context.sendMessage(Identity.nil(), Component.text(message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return context.hasPermission(permission);
            }
        };
    }
}
