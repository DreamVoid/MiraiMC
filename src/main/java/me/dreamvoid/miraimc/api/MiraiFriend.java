package me.dreamvoid.miraimc.api;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;

public class MiraiFriend {
    private final Friend friend;

    public MiraiFriend(Bot bot, long friendAccount) throws NullPointerException{
        friend = bot.getFriend(friendAccount);
    }

    public void sendMessage(String message){
        friend.sendMessage(message);
    }
}
