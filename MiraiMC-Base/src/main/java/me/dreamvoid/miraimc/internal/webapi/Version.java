package me.dreamvoid.miraimc.internal.webapi;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Version {
	private static Version INSTANCE;
	/**
	 * 最后更新时间戳
	 */
	public final long lastUpdate;

	private Version() {
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * 最新版本名称
	 */
	@SerializedName("latest")
	@Expose public String latest;
	/**
	 * 最新不稳定版本名称
	 */
	@SerializedName(value = "latest-pre", alternate = {"latest-unstable"})
	@Expose public String latestUnstable;
	/**
	 * 版本号映射表
	 */
	@SerializedName("versions")
	@Expose public HashMap<String, Integer> versions;
	/**
	 * 停止支持的版本号列表
	 */
	@SerializedName("blocked")
	@Expose public List<Integer> blocked;

	public static Version get(boolean force) throws IOException {
		// 第一次检查，避免大多数调用进入同步块（提升性能）
		if (INSTANCE != null && !force && ((System.currentTimeMillis() - INSTANCE.lastUpdate) < MiraiMC.getConfig().General_WebAPITimeout)) {
			return INSTANCE;
		}

		// 同步块，确保只有一个线程能够初始化
		synchronized (Version.class) {
			// 第二次检查，其他线程在同步块外等待时，INSTANCE 可能已被其他线程初始化
			if (INSTANCE != null && !force && ((System.currentTimeMillis() - INSTANCE.lastUpdate) < MiraiMC.getConfig().General_WebAPITimeout)) {
				return INSTANCE;
			}

			List<String> list = new ArrayList<>(Info.get(false).apis);

			for (String s : list) {
				if (!s.endsWith("/")) s += "/";
				try {
					INSTANCE = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(Utils.Http.get(s + "version.json"), Version.class);
					break;
				} catch (IOException e) {
					Utils.getLogger().warning(s + " - " + e);
				}
			}

			if (INSTANCE != null) {
				return INSTANCE;
			} else {
				throw new IOException("所有API均请求失败，请检查您的互联网连接！");
			}
		}
	}
}
