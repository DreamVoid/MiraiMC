package me.dreamvoid.miraimc.httpapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchMessage {
    @SerializedName("code")
    public int code;

    @SerializedName("msg")
    public String msg;

    @SerializedName("data")
    public List<Data> data;

	public static class Data {
		/**
		 * FriendMessage | GroupMessage | TempMessage | StrangerMessage | OtherClientMessage
		 */
		@SerializedName("type")
		public String type;

		// MessageType
		@SerializedName("sender")
		public Sender sender;

		public static class Sender {
			/**
			 * 通用
			 */
			@SerializedName("id")
			public long id;

			/**
			 * 好友 & 陌生人消息
			 */
			@SerializedName("nickname")
			public String nickname;
			/**
			 * 好友 & 陌生人消息
			 */
			@SerializedName("remark")
			public String remark;

			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("memberName")
			public String memberName;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("specialTitle")
			public String specialTitle;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("permission")
			public String permission;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("joinTimestamp")
			public long joinTimestamp;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("lastSpeakTimestamp")
			public long lastSpeakTimestamp;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("muteTimeRemaining")
			public long muteTimeRemaining;
			/**
			 * 群 & 群临时消息
			 */
			@SerializedName("group")
			public Group group;

			public static class Group {
				@SerializedName("id")
				public long id;
				@SerializedName("name")
				public String name;
				@SerializedName("permission")
				public String permission;
			}

			/**
			 * 其他客户端消息
			 */
			@SerializedName("platform")
			public String platform;
		}

		@SerializedName("messageChain")
		public List<MessageChain> messageChain;

		/**
		 * 消息类型
		 */
		public static class MessageChain {
			/**
			 * Source | Quote | At | AtAll | Face | Plain | Image | FlashImage | Voice | Xml | Json | App | Poke | Dice | MarketFace | MusicShare | ForwardMessage（不可用） | File（不可用） | MiraiCode
			 */
		    @SerializedName("type") // 通用
		    public String type;

			// Source

			/**
			 * [Source] 消息的识别号，用于引用回复<br>
			 * [Quote] 被引用回复的原消息的messageId<br>
			 * [MarketFace] 商城表情唯一标识（目前商城表情仅支持接收和转发，不支持构造发送）
			 */
			@SerializedName("id")
			public int id;
			/**
			 * [Source] 时间戳
			 */
			@SerializedName("time")
			public int time;

			// Quote

			/**
			 * [Quote] 被引用回复的原消息所接收的群号，当为好友消息时为0
			 */
			@SerializedName("groupId")
			public long groupId;
			/**
			 * [Quote] 被引用回复的原消息的发送者的QQ号
			 */
			@SerializedName("senderId")
			public long senderId;
			/**
			 * [Quote] 被引用回复的原消息的接收者者的QQ号（或群号）
			 */
			@SerializedName("targetId")
			public long targetId;
			/**
			 * [Quote] 被引用回复的原消息的消息链对象
			 */
			@SerializedName("origin")
			public List<MessageChain> origin;

			// At

			/**
			 * [At] 群员QQ号
			 */
			@SerializedName("target")
			public long target;
			/**
			 * [At] At时显示的文字，发送消息时无效，自动使用群名片
			 */
			@SerializedName("display")
			public String display;

			// AtAll（没东西）

			// Face

			/**
			 * [Face] QQ表情编号，可选，优先高于name
			 */
			@SerializedName("faceId")
			public int faceId;
			/**
			 * [Face] QQ表情拼音，可选<br>
			 * [Poke] 戳一戳的类型 | "Poke": 戳一戳 | "ShowLove": 比心 | "Like": 点赞 | "Heartbroken": 心碎 | "SixSixSix": 666 | "FangDaZhao": 放大招<br>
			 * [MarketFace] 表情显示名称（目前商城表情仅支持接收和转发，不支持构造发送）
			 */
			@SerializedName("name")
			public String name;

			// Plain

			/**
			 * [Plain] 文字消息
			 */
			@SerializedName("text")
			public String text;

			// Image & FlashImage

			/**
			 * [Image & FlashImage] 图片的imageId，群图片与好友图片格式不同。不为空时将忽略url属性
			 */
			@SerializedName("imageId")
			public String imageId;
			/**
			 * [Image & FlashImage] 图片的URL，发送时可作网络图片的链接；接收时为腾讯图片服务器的链接，可用于图片下载<br>
			 * [Voice] 语音的URL，发送时可作网络语音的链接；接收时为腾讯语音服务器的链接，可用于语音下载
			 */
			@SerializedName("url")
			public String url;
			/**
			 * [Image & FlashImage] 图片的路径，发送本地图片，路径相对于 JVM 工作路径（默认是当前路径，可通过 -Duser.dir=...指定），也可传入绝对路径。<br>
			 * [Voice] 语音的路径，发送本地语音，路径相对于 JVM 工作路径（默认是当前路径，可通过 -Duser.dir=...指定），也可传入绝对路径。
			 */
			@SerializedName("path")
			public String path;
			/**
			 * [Image & FlashImage] 图片的 Base64 编码
			 * [Voice] 语音的 Base64 编码
			 */
			@SerializedName("base64")
			public String base64;

			// Voice

			/**
			 * [Voice] 语音的voiceId，不为空时将忽略url属性
			 */
			@SerializedName("voiceId")
			public String voiceId;
			/**
			 * [Voice] 返回的语音长度, 发送消息时可以不传
			 */
			@SerializedName("length")
			public long length;

			// Xml

			/**
			 * [XML] XML文本
			 */
			@SerializedName("xml")
			public String xml;

			/**
			 * [Json] Json文本
			 */
			// Json
			@SerializedName("json")
			public String json;

			/**
			 * [App] 内容
			 */
			// App
			@SerializedName("content")
			public String content;

			// Poke（都整完了）

			// Dice

			/**
			 * [Dice] 点数
			 */
			@SerializedName("value")
			public int value;

			// MarketFace（都整完了）

			// MusicShare

			/**
			 * 类型
			 */
			@SerializedName("kind")
			public String kind;
			/**
			 * [MusicShare] 标题
			 */
			@SerializedName("title")
			public String title;
			/**
			 * [MusicShare] 概括
			 */
			@SerializedName("summary")
			public String summary;
			/**
			 * [MusicShare] 跳转路径
			 */
			@SerializedName("jumpUrl")
			public String jumpUrl;
			/**
			 * [MusicShare] 封面路径
			 */
			@SerializedName("pictureUrl")
			public String pictureUrl;
			/**
			 * [MusicShare] 音源路径
			 */
			@SerializedName("musicUrl")
			public String musicUrl;
			/**
			 * [MusicShare] 简介
			 */
			@SerializedName("brief")
			public String brief;

			// ForwardMessage // TODO: 有点不想搞了 https://docs.mirai.mamoe.net/mirai-api-http/api/MessageType.html#forwardmessage

			// File // TODO: 也不想搞了，id还是String

			/**
			 * [MiraiCode] MiraiCode
			 */
			@SerializedName("code")
			public String code;
		}

		// TODO:EventType

		// Bot自身事件

		/**
		 * [BotOnlineEvent] 登录成功的Bot的QQ号
		 * [BotOfflineEventActive] 主动离线的Bot的QQ号
		 * [BotOfflineEventForce] 被挤下线的Bot的QQ号
		 * [BotOfflineEventDropped] 被服务器断开或因网络问题而掉线的Bot的QQ号
		 * [BotReloginEvent] 主动重新登录的Bot的QQ号
		 */
		@SerializedName("qq")
		public long qq;
	}
}
