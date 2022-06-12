package me.dreamvoid.miraimc.bukkit.event.group.member;

import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import net.mamoe.mirai.event.events.GroupMemberEvent;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

abstract class AbstractGroupMemberEvent extends Event {
	public AbstractGroupMemberEvent(GroupMemberEvent event){
		super(true);
		this.event = event;
	}

	private static final HandlerList handlers = new HandlerList();
	private final GroupMemberEvent event;

	public @NotNull HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }

	/**
	 * 获取机器人账号
	 * @return 机器人账号
	 */
	public long getBotID() { return event.getBot().getId(); }

	/**
	 * 返回目标群的群号
	 * @return 群号
	 */
	public long getGroupID() { return event.getGroup().getId(); }

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

	/**
	 * 获取群员实例
	 * @return MiraiNormalMember 实例
	 */
	public MiraiNormalMember getMember(){
		return new MiraiNormalMember(event.getGroup(), event.getMember().getId());
	}

	/**
	 * 获取哈希值
	 * @return 哈希值
	 */
	public int getHashCode() {
		return event.hashCode();
	}
}
