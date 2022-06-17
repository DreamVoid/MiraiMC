package me.dreamvoid.miraimc.bukkit.event.group.setting;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import net.mamoe.mirai.event.events.GroupSettingChangeEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * (Bukkit) Mirai 核心事件 - 群 - 群设置 - 群设置改变（抽象）
 */
abstract class AbstractGroupSettingChangeEvent extends Event {
	public AbstractGroupSettingChangeEvent(GroupSettingChangeEvent event){
		super(true);
		this.event = event;
	}

	private static final HandlerList handlers = new HandlerList();
	private final GroupSettingChangeEvent event;

	public @NotNull HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() { return event.getBot().getId(); }

	/**
	 * 获取群号
	 * @return 群号
	 */
	public long getGroupID(){
		return event.getGroup().getId();
	}

	/**
	 * 获取哈希值
	 * @return 哈希值
	 */
	public int getHashCode(){
		return event.hashCode();
	}

	/**
	 * 获取原始事件内容<br>
	 * [!] 不推荐使用
	 * @return 原始事件内容
	 */
	@Override
	public String toString() {
		return event.toString();
	}

	/**
	 * 获取群实例
	 * @return MiraiGroup 实例
	 */
	public MiraiGroup getGroup(){
		return new MiraiGroup(event.getBot(), event.getGroup().getId());
	}
}
