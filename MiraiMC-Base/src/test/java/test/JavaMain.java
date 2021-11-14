package test;

import me.dreamvoid.miraimc.internal.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.internal.httpapi.response.Verify;

public class JavaMain {
    public static void main(String[] args){
        MiraiHttpAPI api = new MiraiHttpAPI("http://localhost:8080");
        Verify verify = api.verify("INITKEY2dx8KU94");
        System.out.println("code: " + verify.code);
        System.out.println("session:" + verify.session);
        System.out.println("msg: " + verify.msg);
    }
}
