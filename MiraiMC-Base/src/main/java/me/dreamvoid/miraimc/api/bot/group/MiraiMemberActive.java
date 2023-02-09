package me.dreamvoid.miraimc.api.bot.group;

import me.dreamvoid.miraimc.api.bot.group.active.MiraiGroupHonorType;
import me.dreamvoid.miraimc.api.bot.group.active.MiraiMemberMedalInfo;
import net.mamoe.mirai.contact.active.MemberActive;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * MiraiMC 群活跃度相关属性
 * @since mirai 2.13
 */
public class MiraiMemberActive {
    private final MemberActive active;

    public MiraiMemberActive(MemberActive active){
        this.active = active;
    }

    /**
     * @return 查询头衔佩戴情况
     */
    public MiraiMemberMedalInfo queryMedal(){
        return new MiraiMemberMedalInfo(active.queryMedal());
    }

    /**
     * 获取群荣誉标识
     * @return 群荣誉标识
     */
    public Set<MiraiGroupHonorType> getHonors(){
        return active.getHonors().stream().map(MiraiGroupHonorType::new).collect(Collectors.toSet());
    }

    /**
     * 获取群活跃积分
     * @return 群活跃积分
     */
    public int getPoint(){
        return active.getPoint();
    }

    /**
     * 获取群活跃等级. 取值为 1~6 (包含)
     * @return 群活跃等级
     */
    public int getRank(){
        return active.getRank();
    }

    /**
     * 获取群荣誉等级. 取值为 1~100 (包含)
     * @return 群荣誉等级
     */
    public int getTemperature(){
        return active.getTemperature();
    }
}
