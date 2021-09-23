package me.dreamvoid.miraimc.bukkit.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "miraimc";
    }

    @Override
    public String getAuthor() {
        return "DreamVoid";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String getRequiredPlugin() {
        return "MiraiMC";
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin(getRequiredPlugin()) != null;
    }

    @Override
    public String onRequest(OfflinePlayer p, String params) {
        String[] args = params.split("_");
        switch (args[0].toLowerCase()) {
            case "isonline":{
                if(args.length>=2) {
                    MiraiBot miraiBot = MiraiBot.getBot(Long.parseLong(args[1]));
                    if (miraiBot.isExist() && miraiBot.isOnline()) {
                        return "true";
                    } else return "false";
                }
                break;
            }
            case "counts":{
                return String.valueOf(MiraiBot.getOnlineBots().toArray().length);
            }
            case "nick":{
                if(args.length>=2){
                    MiraiBot miraiBot = MiraiBot.getBot(Long.parseLong(args[1]));
                    if (miraiBot.isExist() && miraiBot.isOnline()) {
                        return miraiBot.getNick();
                    }
                }
                break;
            }
            case "friendcounts":{
                MiraiBot miraiBot = MiraiBot.getBot(Long.parseLong(args[1]));
                if (miraiBot.isExist() && miraiBot.isOnline()) {
                    return String.valueOf(miraiBot.getFriendList().toArray().length);
                }
                break;
            }
            case "groupcounts":{
                MiraiBot miraiBot = MiraiBot.getBot(Long.parseLong(args[1]));
                if (miraiBot.isExist() && miraiBot.isOnline()) {
                    return String.valueOf(miraiBot.getGroupList().toArray().length);
                }
                break;
            }
        }
        return null;
    }
}
