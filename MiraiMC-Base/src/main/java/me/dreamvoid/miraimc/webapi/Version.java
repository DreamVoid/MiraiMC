package me.dreamvoid.miraimc.webapi;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

public final class Version {
	@SerializedName("latest")
	public String latest;

	@SerializedName("latest-pre")
	public String latest_pre;

	@SerializedName("versions")
	public HashMap<String,Integer> versions;

	@SerializedName("blocked")
	public List<Integer> blocked;
}
