package test;

import me.dreamvoid.miraimc.internal.PluginUpdate;

import java.io.IOException;

public class JavaMain {
    public static void main(String[] args){
        try {
            System.out.println(new PluginUpdate().getLatestRelease());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
