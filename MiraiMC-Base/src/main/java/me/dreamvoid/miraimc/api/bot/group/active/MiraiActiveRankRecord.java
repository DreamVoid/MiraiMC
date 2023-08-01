package me.dreamvoid.miraimc.api.bot.group.active;

import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.contact.active.ActiveRankRecord;

/**
 * MiraiMC 活跃排行榜记录
 * @since mirai 2.13
 */
@SuppressWarnings("unused")
public class MiraiActiveRankRecord {
    private final ActiveRankRecord record;

    public MiraiActiveRankRecord(ActiveRankRecord record){
        this.record = record;
    }

    /**
     * 获取发言者 ID
     * @return 发言者 ID
     */
    public long getMemberID(){
        return record.getMemberId();
    }

    /**
     * 获取发言者名称
     * @return 发言者名称
     */
    public String getMemberName(){
        return record.getMemberName();
    }

    /**
     * 获取发言者的群员实例
     * @return 发言者的群员实例
     */
    public MiraiNormalMember getMember(){
        return new MiraiNormalMember(record.getMember());
    }

    /**
     * 获取活跃积分
     * @return 活跃积分
     */
    public int getScore(){
        return record.getScore();
    }

    /**
     * 获取活跃度
     * @return 活跃度
     */
    public int getTemperature(){
        return record.getTemperature();
    }
}
