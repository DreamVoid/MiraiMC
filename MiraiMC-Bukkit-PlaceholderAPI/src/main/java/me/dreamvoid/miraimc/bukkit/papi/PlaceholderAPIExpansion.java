package me.dreamvoid.miraimc.bukkit.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

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
        Plugin plugin = Bukkit.getPluginManager().getPlugin(getRequiredPlugin());
        return plugin != null && plugin.isEnabled();
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
                if(args.length>=2) {
                    MiraiBot miraiBot = MiraiBot.getBot(Long.parseLong(args[1]));
                    if (miraiBot.isExist() && miraiBot.isOnline()) {
                        return String.valueOf(miraiBot.getFriendList().toArray().length);
                    }
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
            case "bindqq": {
                if (args.length >= 2) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                    return String.valueOf(MiraiMC.getBinding(player.getUniqueId().toString()));
                } else return String.valueOf(MiraiMC.getBinding(p.getUniqueId().toString()));
            }
            case "binduuid":{
                if(args.length >= 2) {
                    return MiraiMC.getBinding(Long.parseLong(args[1]));
                }
                break;
            }
            case "bindname":{
                if(args.length >= 2) {
                    return Bukkit.getOfflinePlayer(MiraiMC.getBinding(Long.parseLong(args[1]))).getName();
                }
                break;
            }
            default:return null;
        }
        return null;
    }
}
