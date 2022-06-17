package me.dreamvoid.miraimc.nukkit.event.message.postsend;

import net.mamoe.mirai.event.events.StrangerMessagePostSendEvent;
import me.dreamvoid.miraimc.nukkit.NukkitPlugin;

/**
 * (bungee) Mirai 核心事件 - 消息 - 主动发送消息后 - 陌生人消息
 */
public class MiraiStrangerMessagePostSendEvent extends AbstractMessagePostSendEvent {
    public MiraiStrangerMessagePostSendEvent(StrangerMessagePostSendEvent event) {
        super(event);
        this.event = event;

        NukkitPlugin.getInstance().getServer().getPluginManager().callEvent(new me.dreamvoid.miraimc.nukkit.event.MiraiStrangerMessagePostSendEvent(event));
    }

    private final StrangerMessagePostSendEvent event;

    /**
     * 返回接收这条信息的目标昵称
     * @return 目标昵称
     */
    public String getTargetNick(){
        return event.getTarget().getNick();
    }

    /**
     * 返回接收者的备注名
     * @return 备注名
     */
    public String getFriendRemark(){ return event.getTarget().getRemark(); }
}
