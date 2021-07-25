package me.dreamvoid.miraimc.api;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;

public class MiraiGroup {
    private final Group group;

    public MiraiGroup(Bot bot, long groupID) throws NullPointerException {
        group = bot.getGroup(groupID);
    }

    public void sendMessage(String message){
        group.sendMessage(message);
    }
}
