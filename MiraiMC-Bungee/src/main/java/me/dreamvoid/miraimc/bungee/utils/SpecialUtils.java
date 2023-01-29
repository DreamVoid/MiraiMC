package me.dreamvoid.miraimc.bungee.utils;

import me.dreamvoid.miraimc.commands.ICommandSender;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 此处存放由Bukkit代码反编译而来的工具
 * 因为我的主力是Bukkit，一些Bukkit习惯的API其他平台没有
 * @author SpigotMC
 */
public class SpecialUtils {
    public static List<Map<?, ?>> getMapList(List<?> list) {
        List<Map<?, ?>> result = new ArrayList<>();
        if (list != null) {

            for (Object object : list) {
                if (object instanceof Map) {
                    result.add((Map<?,?>) object);
                }
            }

        }
        return result;
    }

    public static ICommandSender getSender(CommandSender sender){
        return new ICommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }
        };
    }
}
