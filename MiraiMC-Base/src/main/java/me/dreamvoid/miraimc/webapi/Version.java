package me.dreamvoid.miraimc.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.Config;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public final class Version {
	private static Version INSTANCE;

	@SerializedName("latest")
	public String latest;

	@SerializedName("latest-pre")
	public String latest_pre;

	@SerializedName("versions")
	public HashMap<String, Integer> versions;

	@SerializedName("blocked")
	public List<Integer> blocked;

	public static Version init() throws IOException {
		return init(false);
	}

	public static Version init(boolean force) throws IOException {
		if (force || INSTANCE == null) {
			List<String> list = new ArrayList<>(Info.init().apis);

			try {
				File cache = new File(Config.PluginDir, "apis.json");
				if (cache.exists()) {
					String[] localString = new Gson().fromJson(new FileReader(cache), String[].class);
					List<String> local = new ArrayList<>(Arrays.asList(localString)); // 这里不双重调用下面的removeAll就不能用
					local.removeAll(list);
					list.addAll(local);
				}
			} catch (Exception ignored) {}

			IOException e = new IOException();
			for (String s : list) {
				if (!s.endsWith("/")) s += "/";
				try {
					INSTANCE = new Gson().fromJson(Utils.Http.get(s + "version.json"), Version.class);
				} catch (IOException ex) {
					e = ex;
				}
			}
			if (INSTANCE == null) {
				throw e;
			}
		}
		return INSTANCE;
	}
}
