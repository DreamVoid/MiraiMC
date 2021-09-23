package me.dreamvoid.miraimc.internal;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class PluginUpdate {
    public static String getVersion() throws IOException {
        URL url = new URL("https://api.github.com/repos/DreamVoid/MiraiMC/releases/latest");
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

        HashMap datax = new Gson().fromJson(sb.toString(),HashMap.class);
        return (String) datax.get("name");
    }
}
