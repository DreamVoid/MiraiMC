package me.dreamvoid.miraimc.api.bot;

import net.mamoe.mirai.contact.OtherClient;
import net.mamoe.mirai.message.code.MiraiCode;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;

/**
 * MiraiMC 其他客户端
 * @since 1.7
 */
@SuppressWarnings("unused")
public class MiraiOtherClient {
	private final OtherClient client;

	/**
	 * 获取指定的其他客户端实例
	 * @param otherClient 其他客户端
	 */
	public MiraiOtherClient(OtherClient otherClient) {
		this.client = otherClient;
	}

	/**
	 * 向其他客户端发送消息
	 * @param message 消息文本
	 */
	public void sendMessage(String message){
		client.sendMessage(message);
	}

	/**
	 * 向其他客户端发送消息<br>
	 * 此方法将自动转换为Mirai Code，可用于发送图片等特殊消息
	 * @param message Mirai Code格式的消息文本
	 */
	public void sendMessageMirai(String message){
		client.sendMessage(MiraiCode.deserializeMiraiCode(message));
	}

	/**
	 * 获取其他客户端ID
	 * @return 客户端ID
	 */
	public long getID(){
		return client.getId();
	}

	/**
	 * 获取其他客户端名称
	 * @return 客户端名称
	 */
	public String getDeviceName(){
		return client.getInfo().getDeviceName();
	}

	/**
	 * 获取其他客户端类型
	 * @return 客户端类型
	 */
	public String getDeviceKind(){
		return client.getInfo().getDeviceKind();
	}

	/**
	 * 上传一个图片，返回图片ID用于发送消息
	 * @param imageFile 图片文件
	 * @return 图片ID
	 */
	public String uploadImage(File imageFile) {
		return ExternalResource.uploadAsImage(imageFile, client).getImageId();
	}

	/**
	 * 发送闪照
	 * @param image 图片文件
	 */
	public void sendFlashImage(File image) {
		client.sendMessage(FlashImage.from(client.uploadImage(ExternalResource.create(image).toAutoCloseable())));
	}

	/**
	 * 发送闪照
	 * @param imageID 图片ID
	 */
	public void sendFlashImage(String imageID) {
		client.sendMessage(FlashImage.from(imageID));
	}
}
