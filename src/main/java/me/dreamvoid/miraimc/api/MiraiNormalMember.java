package me.dreamvoid.miraimc.api;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;

public class MiraiNormalMember{
    private final NormalMember member;

    public MiraiNormalMember(Group group,long account) throws NullPointerException{
        member = group.get(account);
    }

    public long getId(){
        return member.getId();
    }
}
