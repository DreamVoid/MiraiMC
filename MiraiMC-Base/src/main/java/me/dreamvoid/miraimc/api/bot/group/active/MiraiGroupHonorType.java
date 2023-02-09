package me.dreamvoid.miraimc.api.bot.group.active;

import net.mamoe.mirai.data.GroupHonorType;

/**
 * MiraiMC 群荣誉信息
 * @since mirai 2.13
 */
public class MiraiGroupHonorType {
    private final GroupHonorType type;

    public MiraiGroupHonorType(GroupHonorType type){
        this.type = type;
    }

    public int getID(){
        return type.getId();
    }

    public int hashCode(){
        return type.hashCode();
    }

    @Override
    public String toString(){
        return type.toString();
    }
}
