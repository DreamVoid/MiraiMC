package me.dreamvoid.miraimc.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.IOException;
import java.util.ArrayList;
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
		if (INSTANCE == null) {
			List<String> list = new ArrayList<>(Info.init().apis);

			IOException e = null; // 用于所有API都炸掉的时候抛出的

			for (String s : list) {
				if (!s.endsWith("/")) s += "/";
				try {
					INSTANCE = new Gson().fromJson(Utils.Http.get(s + "version.json"), Version.class);
					break;
				} catch (IOException ex) {
					if(e == null) e = ex;
				}
			}

			if (INSTANCE == null) {
				assert e != null;
				throw e;
			}
		}
		return INSTANCE;
	}
}
