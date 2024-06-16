package me.dreamvoid.miraimc.internal.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.config.PluginConfig;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public final class Info {
	private static Info INSTANCE = null;

	@SerializedName("mirai")
	public HashMap<String, String> mirai;

	@SerializedName("announcement")
	public List<String> announcement;

	@SerializedName("apis")
	public List<String> apis;

	public static Info init() throws IOException {
		return init(Collections.singletonList("https://api.miraimc.dreamvoid.me/"), true);
	}

	public static Info init(List<String> apis, boolean localCache) throws IOException {
		if(INSTANCE != null) {
			return INSTANCE;
		}

		List<String> list = new ArrayList<>(apis);
		File CacheDir = new File(PluginConfig.PluginDir, "cache");
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

		List<IOException> exceptions = new ArrayList<>();

		for(String s : list){
			if(!s.endsWith("/")) s += "/";
			try {
				INSTANCE = new Gson().fromJson(Utils.Http.get(s + "info.json"), Info.class);
				break;
			} catch (IOException e) {
				//Utils.getLogger().warning("Failed to get " + s +", reason: " + e);
				exceptions.add(e);
			}
		}

		if(INSTANCE != null){
			// API数据保存到本地
			FileOutputStream fos = new FileOutputStream(cache);
			fos.write(new Gson().toJson(INSTANCE.apis.toArray(), String[].class).getBytes(StandardCharsets.UTF_8));
			fos.close();

			return INSTANCE;
		} else {
			Utils.getLogger().warning("所有API均请求失败，请检查您的互联网连接。");

			for(int i = 0; i < list.size(); i++){
				String s = list.get(i);
				if(!s.endsWith("/")) s += "/";
				Utils.getLogger().warning(s + " - " + exceptions.get(i));
			}

			throw new IOException("All api fetching failed.");
		}
	}
}
