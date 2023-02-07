package me.dreamvoid.miraimc.internal;

import me.dreamvoid.miraimc.internal.webapi.Version;

import java.io.IOException;

public class PluginUpdate {
    private final String latestRelease;
    private final int latestReleaseNo;
    private final String latestPreRelease;
    private final int latestPreReleaseNo;
    private final Version version;

    public PluginUpdate() throws IOException {
        version = Version.init();

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
