package me.dreamvoid.miraimc.internal;

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

    public PluginUpdate() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/DreamVoid/MiraiMC/main/version");
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

        String[] version = sb.toString().split(";");

        String[] release = version[0].split(":");
        latestRelease = release[0];
        latestReleaseNo = Integer.parseInt(release[1]);

        String[] preRelease = version[1].split(":");
        latestPreRelease = preRelease[0];
        latestPreReleaseNo = Integer.parseInt(preRelease[1]);
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
}
