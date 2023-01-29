package me.dreamvoid.miraimc.sponge.utils;

import me.dreamvoid.miraimc.commands.ICommandSender;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.serializer.TextSerializers;

public class SpecialUtils {
    public static ICommandSender getSender(CommandSource sender){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }
        };
    }
}
