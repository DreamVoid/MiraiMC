package me.dreamvoid.miraimc.api.bot.group;

import me.dreamvoid.miraimc.api.bot.group.active.MiraiActiveChart;
import me.dreamvoid.miraimc.api.bot.group.active.MiraiActiveRankRecord;
import net.mamoe.mirai.contact.active.GroupActive;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MiraiMC 群活跃度管理
 * @since mirai 2.13
 */
@SuppressWarnings("unused")
public class MiraiGroupActive {
    private final GroupActive active;

    public MiraiGroupActive(GroupActive active){
        this.active = active;
    }

    /**
     * 等级头衔列表，键是等级，值是头衔
     * @return 等级头衔列表
     */
    public Map<Integer, String> getRankTitles(){
        return active.getRankTitles();
    }

    /**
     * 活跃度头衔列表，键是等级，值是头衔。操作成功时会同时刷新活跃度头衔信息。
     * @return 活跃度头衔列表
     */
    public Map<Integer, String> getTemperatureTitles(){
        return active.getTemperatureTitles();
    }

    /**
     * 设置等级头衔列表，键是等级，值是头衔。操作成功时会同时刷新等级头衔信息。
     * @param value 等级头衔列表
     */
    public void setRankTitles(Map<Integer, String> value){
        active.setRankTitles(value);
    }

    /**
     * 设置活跃度头衔列表，键是等级，值是头衔。操作成功时会同时刷新活跃度头衔信息。
     * @param value 活跃度头衔列表
     */
    public void setTemperatureTitles(Map<Integer, String> value){
        active.setTemperatureTitles(value);
    }

    /**
     * 是否在群聊中显示荣誉
     * @return 是否显示荣誉
     */
    public boolean isHonorVisible(){
        return active.isHonorVisible();
    }

    /**
     * 是否在群聊中显示活跃度
     * @return 是否显示活跃度
     */
    public boolean isTemperatureVisible(){
        return active.isTemperatureVisible();
    }

    /**
     * 是否在群聊中显示头衔
     * @return 是否显示头衔
     */
    public boolean isTitleVisible(){
        return active.isTitleVisible();
    }

    /**
     * 设置是否在群聊中显示荣誉
     * @param visible 是否显示荣誉
     */
    public void setHonorVisible(boolean visible){
        active.setHonorVisible(visible);
    }

    /**
     * 设置是否在群聊中显示活跃度。操作成功时会同时刷新等级头衔信息。
     * @param visible 是否显示活跃度
     */
    public void setTemperatureVisible(boolean visible){
        active.setTemperatureVisible(visible);
    }

    /**
     * 设置是否在群聊中显示头衔。操作成功时会同时刷新等级头衔信息。
     * @param visible 是否显示头衔
     */
    public void setTitleVisible(boolean visible){
        active.setTitleVisible(visible);
    }

    /**
     * 刷新 {@link MiraiMemberActive} 中的属性 (不包括 honors 和 temperature)
     */
    public void refresh(){
        active.refresh();
    }

    /**
     * 获取活跃度排行榜，通常是前五十名
     * @return 活跃度排行榜列表
     */
    public List<MiraiActiveRankRecord> queryActiveRank(){
        return active.queryActiveRank().stream().map(MiraiActiveRankRecord::new).collect(Collectors.toList());
    }

    /**
     * 获取活跃度图表数据<br>
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 活跃度数据图表
     */
    public MiraiActiveChart queryChart(){
        return new MiraiActiveChart(active.queryChart());
    }

    //public void queryHonorHistory(){ } // TODO: 未能实现
}
