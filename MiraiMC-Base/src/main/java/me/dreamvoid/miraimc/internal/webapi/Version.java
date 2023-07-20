package me.dreamvoid.miraimc.internal.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import me.dreamvoid.miraimc.internal.Utils;

import java.io.IOException;
import java.lang.reflect.Array;
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
		if (INSTANCE != null) {
			System.out.println("2: Not null!");
			return INSTANCE;
		}

		List<String> list = new ArrayList<>(Info.init().apis);

		//IOException e = null; // 用于所有API都炸掉的时候抛出的
		ArrayList<IOException> ex = new ArrayList<>();

		for (String s : list) {
			if (!s.endsWith("/")) s += "/";
			try {
				INSTANCE = new Gson().fromJson(Utils.Http.get(s + "version.json"), Version.class);
				break;
			} catch (IOException e) {
				ex.add(e);
			}
		}

		if (INSTANCE != null) {
			return INSTANCE;
		} else {
			Utils.logger.warning("所有API均请求失败，请检查您的互联网连接。");

			for(int i = 0; i < list.size(); i++){
				String s = list.get(i);
				if(!s.endsWith("/")) s += "/";
				Utils.logger.warning(s + " - " + ex.get(i));
			}

			throw new IOException("All api fetching failed.");
		}
	}
}
