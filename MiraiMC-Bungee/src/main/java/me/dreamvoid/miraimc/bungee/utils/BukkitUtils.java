package me.dreamvoid.miraimc.bungee.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 此处存放由Bukkit代码反编译而来的工具
 * 因为我的主力是Bukkit，一些Bukkit习惯的API其他平台没有
 * @author SpigotMC
 */
public class BukkitUtils {
    public static List<Map<?, ?>> getMapList(List<?> list) {
        List<Map<?, ?>> result = new ArrayList();
        if (list != null) {

            for (Object object : list) {
                if (object instanceof Map) {
                    result.add((Map) object);
                }
            }

        }
        return result;
    }
}
