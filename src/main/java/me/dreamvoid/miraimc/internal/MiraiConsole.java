package me.dreamvoid.miraimc.internal;

import net.mamoe.mirai.console.terminal.MiraiConsoleImplementationTerminal;
import net.mamoe.mirai.console.terminal.MiraiConsoleTerminalLoader;

public class MiraiConsole {

    // 我就是要用Maven构建Mirai Console :D

    public void launchMiraiConsole(){
        MiraiConsoleTerminalLoader.INSTANCE.startAsDaemon(new MiraiConsoleImplementationTerminal());
    }
}
