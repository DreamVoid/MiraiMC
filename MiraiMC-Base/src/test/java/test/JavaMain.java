package test;

import me.dreamvoid.miraimc.httpapi.MiraiHttpAPI;
import me.dreamvoid.miraimc.httpapi.response.Bind;
import me.dreamvoid.miraimc.httpapi.response.SendMessage;
import me.dreamvoid.miraimc.httpapi.response.Verify;

public class JavaMain {
    public static void main(String[] args){
        try {
            MiraiHttpAPI api = new MiraiHttpAPI("http://localhost:8080");

            System.out.println("Verify");
            Verify verify = api.verify("INITKEYwaYvbCS3");
            System.out.println("code: " + verify.code);
            System.out.println("session:" + verify.session);
            System.out.println("msg: " + verify.msg);

            System.out.println("Bind");
            Bind bind = api.bind(verify.session, Long.parseLong(args[0]));
            System.out.println("code: " + bind.code);
            System.out.println("msg: " + bind.msg);

            System.out.println("SendFriendMessage");
            SendMessage sendFriendMessage = api.sendFriendMessage(verify.session, Long.parseLong(args[1]), "123456");
            System.out.println("code: " + sendFriendMessage.code);
            System.out.println("msg: " + sendFriendMessage.msg);
            System.out.println("messageId: " + sendFriendMessage.messageId);

            System.out.println("SendGroupMessage");
            SendMessage sendGroupMessage = api.sendGroupMessage(verify.session, Long.parseLong(args[2]), "123456");
            System.out.println("code: " + sendGroupMessage.code);
            System.out.println("msg: " + sendGroupMessage.msg);
            System.out.println("messageId: " + sendGroupMessage.messageId);

            /*System.out.println("FetchMessage");
            FetchMessage fetchMessage = api.fetchMessage("mKjSL1Ic", 10);
            System.out.println("code: " + fetchMessage.code);
            System.out.println("msg: " + fetchMessage.msg);
            for(FetchMessage.Data data : fetchMessage.data) {
                for(Message msg : data.messageChain){
                    System.out.println("Type: " + msg.type);
                    System.out.println("Text: " + msg.text);
                    System.out.println("url: " + msg.url);
                }
            }*/

            System.out.println("Release");
            //Release release = api.release(verify.session, Long.parseLong(args[0]));
            //System.out.println("code: " + release.code);
            //System.out.println("msg: " + release.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
