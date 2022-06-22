package me.dreamvoid.miraimc.internal;

import com.google.gson.Gson;
import me.dreamvoid.miraimc.webapi.Version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PluginUpdate {
    private final String latestRelease;
    private final int latestReleaseNo;
    private final String latestPreRelease;
    private final int latestPreReleaseNo;
    private final Version version;

    public PluginUpdate() throws IOException {
        URL url = new URL("https://api.miraimc.dreamvoid.ml/version.json");
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

        version = new Gson().fromJson(sb.toString(), Version.class);

        latestRelease = version.latest;
        latestReleaseNo = version.versions.get(version.latest);

        latestPreRelease = version.latest_pre;
        latestPreReleaseNo = version.versions.get(version.latest_pre);
    }

    public String getLatestRelease() {
        return latestRelease;
    }

    public int getLatestReleaseNo() {
        return latestReleaseNo;
    }

    public String getLatestPreRelease() {
        return latestPreRelease;
    }

    public int getLatestPreReleaseNo() {
        return latestPreReleaseNo;
    }

    public boolean isBlocked(String PluginVersion){
        return version.blocked.contains(version.versions.get(PluginVersion));
    }
}
