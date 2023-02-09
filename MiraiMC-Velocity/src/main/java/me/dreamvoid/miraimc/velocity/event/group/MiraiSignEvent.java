package me.dreamvoid.miraimc.velocity.event.group;

import me.dreamvoid.miraimc.velocity.event.bot.AbstractBotEvent;
import net.mamoe.mirai.event.events.SignEvent;

/**
 * (Velocity) 群 - 打卡事件
 */
public class MiraiSignEvent extends AbstractBotEvent{
    private final SignEvent event;

    public MiraiSignEvent(SignEvent event){
        super(event);
        this.event = event;
    }

    public boolean hasRank(){
        return event.hasRank();
    }

    public boolean isCancelled(){
        return event.isCancelled();
    }

    public boolean isIntercepted(){
        return event.isIntercepted();
    }

    public Integer getRank(){
        return event.getRank();
    }

    public String getSign(){
        return event.getSign();
    }

    public long getUserID(){
        return event.getUser().getId();
    }

    public String getUserNick(){
        return event.getUser().getNick();
    }

    public void intercept(){
        event.intercept();
    }

    public void cancel(){
        event.cancel();
    }
}
