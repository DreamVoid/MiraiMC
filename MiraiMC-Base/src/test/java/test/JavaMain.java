package test;

import java.io.IOException;

import static me.dreamvoid.miraimc.internal.PluginUpdate.getVersion;

public class JavaMain {
    public static void main(String[] args){
        try {
            System.out.println(getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
