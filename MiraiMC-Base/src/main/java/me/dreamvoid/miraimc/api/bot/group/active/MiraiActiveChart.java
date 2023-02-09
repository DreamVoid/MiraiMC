package me.dreamvoid.miraimc.api.bot.group.active;

import net.mamoe.mirai.contact.active.ActiveChart;

import java.util.Map;

/**
 * MiraiMC 活跃度数据图表<br>
 * 键是 yyyy-MM 格式的日期，值是数量
 * @since mirai 2.13
 */
public class MiraiActiveChart {
    private final ActiveChart chart;

    public MiraiActiveChart(ActiveChart chart){
        this.chart = chart;
    }

    /**
     * 获取每日活跃人数<br>
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 每日活跃人数
     */
    public Map<String, Integer> getActives(){
        return chart.getActives();
    }

    /**
     * 获取每日总人数
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 每日总人数
     */
    public Map<String, Integer> getMembers(){
        return chart.getMembers();
    }

    /**
     * 获取每日退群人数
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 每日退群人数
     */
    public Map<String, Integer> getExit(){
        return chart.getExit();
    }

    /**
     * 获取每日入群人数
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 每日入群人数
     */
    public Map<String, Integer> getJoin(){
        return chart.getJoin();
    }

    /**
     * 获取每日申请人数
     * 活跃度数据图表， 键是 yyyy-MM 格式的日期，值是数量
     * @return 每日申请人数
     */
    public Map<String, Integer> getSentences(){
        return chart.getSentences();
    }
}
