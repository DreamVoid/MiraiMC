package me.dreamvoid.miraimc.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public final class Info {
	private static Info INSTANCE;

	public Info(){
		INSTANCE = this;
	}

	@SerializedName("mirai")
	public HashMap<String, String> mirai;

	@SerializedName("announcement")
	public List<String> announcement;

	@SerializedName("apis")
	public List<String> apis;

	public static Info init() throws IOException {
		return init(Collections.singletonList("https://api.miraimc.dreamvoid.me/"), true);
	}

	public static Info init(List<String> apis, boolean LocalCache) throws IOException {
		if(INSTANCE == null){
			List<String> list = new ArrayList<>(apis);
			File CacheDir = new File(Config.PluginDir, "cache");
			if(!CacheDir.exists() && !CacheDir.mkdirs()) throw new RuntimeException("Failed to create folder " + CacheDir.getPath());
			File cache = new File(CacheDir, "apis.json");

			if(LocalCache){
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

			IOException e = null; // 用于所有API都炸掉的时候抛出的

			for(String s : list){
				if(!s.endsWith("/")) s += "/";
				try {
					INSTANCE = new Gson().fromJson(Utils.Http.get(s + "info.json"), Info.class);

					// API数据保存到本地
					FileOutputStream fos = new FileOutputStream(cache);
					fos.write(new Gson().toJson(INSTANCE.apis.toArray(), String[].class).getBytes(StandardCharsets.UTF_8));
					fos.close();

					break;
				} catch (IOException ex) {
					Utils.logger.warning("Failed to get " + s +", reason: " + ex);
					if(e == null ) e = ex;
				}
			}

			if(INSTANCE == null){
				assert e != null;
				throw e;
			}
		}
		return INSTANCE;
	}
}
