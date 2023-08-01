package me.dreamvoid.miraimc.api.bot.group.active;

import net.mamoe.mirai.contact.active.MemberMedalType;

/**
 * MiraiMC 群成员头衔类型
 * @since mirai 2.13
 */
@SuppressWarnings("unused")
public class MiraiMemberMedalType {
    private final MemberMedalType type;

    public MiraiMemberMedalType(MemberMedalType type){
        this.type = type;
    }

    public int getMask(){
        return type.getMask();
    }

    public String getName(){
        return type.name();
    }

    public int getOrdinal(){
        return type.ordinal();
    }
}
