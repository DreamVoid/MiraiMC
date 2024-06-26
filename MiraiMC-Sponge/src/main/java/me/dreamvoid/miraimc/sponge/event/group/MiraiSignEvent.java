package me.dreamvoid.miraimc.sponge.event.group;

import me.dreamvoid.miraimc.sponge.event.bot.AbstractBotEvent;
import net.mamoe.mirai.event.events.SignEvent;
import org.spongepowered.api.event.Cause;

/**
 * (Sponge) 群 - 打卡事件
 */
@SuppressWarnings("unused")
public class MiraiSignEvent extends AbstractBotEvent{
    private final SignEvent event;

    public MiraiSignEvent(SignEvent event, Cause cause){
        super(event, cause);
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
