package me.dreamvoid.miraimc.webapi;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public final class Info {
	private static Info INSTANCE;

	public Info(){
		INSTANCE = this;
	}

	@SerializedName("mirai")
	public HashMap<String, String> mirai;

	@SerializedName("announcement")
	public List<String> announcement;

	public static Info init() throws IOException {
		return init(false);
	}

	public static Info init(boolean force) throws IOException {
		if(force || INSTANCE == null){
			URL url = new URL("https://api.miraimc.dreamvoid.me/info.json");
			StringBuilder sb = new StringBuilder();
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoInput(true);
			httpUrlConn.setRequestMethod("GET");
			httpUrlConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");

			InputStream input = httpUrlConn.getInputStream();
			InputStreamReader read = new InputStreamReader(input, StandardCharsets.UTF_8);
			BufferedReader br = new BufferedReader(read);
			String data = br.readLine();
			while (data != null) {
				sb.append(data);
				data = br.readLine();
			}
			br.close();
			read.close();
			input.close();
			httpUrlConn.disconnect();

			INSTANCE = new Gson().fromJson(sb.toString(), Info.class);
		}
		return INSTANCE;
	}
}
