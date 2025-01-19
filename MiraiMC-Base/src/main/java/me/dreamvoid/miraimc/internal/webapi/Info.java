package me.dreamvoid.miraimc.internal.webapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public final class Info {
	private static Info INSTANCE = null;
	/**
	 * 最后更新时间戳
	 */
	public long lastUpdate;

	private Info(){
		lastUpdate = System.currentTimeMillis();
	}

	/**
	 * MiraiMC 版本对应的 mirai 版本映射表
	 */
	@SerializedName("mirai")
	@Expose
	public HashMap<String, String> mirai;
	/**
	 * 公告板信息
	 */
	@SerializedName("announcement")
	@Expose
	public List<String> announcement;
	/**
	 * 可用的 API 地址列表
	 */
	@SerializedName("apis")
	@Expose
	public List<String> apis;

	public static Info get(boolean force) throws IOException {
		return get(Collections.singletonList("https://api.miraimc.dreamvoid.me/"), true, force);
	}

	/**
	 * @param apis API 列表
	 * @param localCache 使用本地缓存
	 * @param force 强制更新
	 * @return {@link Info} 实例
	 * @throws IOException 所有 API 请求失败时抛出
	 */
	public static Info get(List<String> apis, boolean localCache, boolean force) throws IOException {
		// 具有实例 且不是强制获取 且最后更新时间距离现在小于1天
		if((INSTANCE != null) && !force && ((System.currentTimeMillis() - INSTANCE.lastUpdate) < MiraiMC.getConfig().General_WebAPITimeout)) {
			return INSTANCE;
		}

		List<String> list = new ArrayList<>(apis);
		File CacheDir = new File(MiraiMC.getConfig().PluginDir, "cache");
		if(!CacheDir.exists() && !CacheDir.mkdirs()) throw new RuntimeException("Failed to create folder " + CacheDir.getPath());
		File cache = new File(CacheDir, "apis.json");

		if(localCache){
			try {
				if (!cache.exists()) {
					try (InputStream in = Info.class.getResourceAsStream("/apis.json")) {
						assert in != null;
						Files.copy(in, cache.toPath());
					}
				}
				String[] localString = new Gson().fromJson(new FileReader(cache), String[].class);
				List<String> local = new ArrayList<>(Arrays.asList(localString)); // 这里不双重调用下面的removeAll就不能用
				local.removeAll(list);
				list.addAll(local);
			} catch (Exception ignored) {}
		}

        for(String s : list){
			if(!s.endsWith("/")) s += "/";
			try {
				INSTANCE = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().fromJson(Utils.Http.get(s + "info.json"), Info.class);
				break;
			} catch (IOException e) {
				Utils.getLogger().warning(s + " - " + e);
			}
		}

		if(INSTANCE != null){
			// API数据保存到本地
            try (FileOutputStream fos = new FileOutputStream(cache)) {
                fos.write(new Gson().toJson(INSTANCE.apis.toArray(), String[].class).getBytes(StandardCharsets.UTF_8));
            }

            return INSTANCE;
		} else {
			throw new IOException("所有API均请求失败，请检查您的互联网连接！");
		}
	}
}
