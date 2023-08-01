package me.dreamvoid.miraimc.api.bot.group.active;

import net.mamoe.mirai.contact.active.MemberMedalInfo;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * MiraiMC 群成员头衔详情
 * @since mirai 2.13
 */
@SuppressWarnings("unused")
public class MiraiMemberMedalInfo {
    private final MemberMedalInfo info;

    public MiraiMemberMedalInfo(MemberMedalInfo info){
        this.info = info;
    }

    /**
     * 获取当前佩戴的头衔的颜色
     * @return 当前佩戴的头衔的颜色
     */
    public String getColor(){
        return info.getColor();
    }

    /**
     * 获取拥有的所有头衔
     * @return 拥有的所有头衔
     */
    public Set<MiraiMemberMedalType> getMedals(){
        return info.getMedals().stream().map(MiraiMemberMedalType::new).collect(Collectors.toSet());
    }

    /**
     * 获取当前佩戴的头衔
     * @return 当前佩戴的头衔
     */
    public String getTitle(){
        return info.getTitle();
    }

    /**
     * 获取当前佩戴的头衔类型
     * @return 当前佩戴的头衔类型
     */
    public MiraiMemberMedalType getWearing(){
        return new MiraiMemberMedalType(info.getWearing());
    }
}
