package me.dreamvoid.miraimc.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

			if(LocalCache){
				try {
					File cache = new File(Config.PluginDir, "apis.json");
					if (cache.exists()) {
						String[] localString = new Gson().fromJson(new FileReader(cache), String[].class);
						List<String> local = new ArrayList<>(Arrays.asList(localString)); // 这里不双重调用下面的removeAll就不能用
						local.removeAll(list);
						list.addAll(local);
					}
				} catch (Exception ignored) { }
			}

			IOException e = null; // 用于所有API都炸掉的时候抛出的

			for(String s : list){
				if(!s.endsWith("/")) s += "/";
				try {
					INSTANCE = new Gson().fromJson(Utils.Http.get(s + "info.json"), Info.class);

					// API数据保存到本地
					File cache = new File(Config.PluginDir, "apis.json");
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
