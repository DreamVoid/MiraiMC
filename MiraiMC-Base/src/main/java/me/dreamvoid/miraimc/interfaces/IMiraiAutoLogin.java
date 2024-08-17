package me.dreamvoid.miraimc.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IMiraiAutoLogin {
    void loadFile();

    List<Map<?, ?>> loadAutoLoginList() throws IOException;

    void doStartUpAutoLogin();

    boolean addAutoLoginBot(long Account, String Password, String Protocol);

    boolean delAutoLoginBot(long Account);
}
