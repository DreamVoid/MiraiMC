package test;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class JavaMain {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update("QRCode".getBytes(StandardCharsets.UTF_8));
        byte[] md5 = m.digest();
        for(int i =0 ;i<md5.length;i++){
            System.out.println(md5[i]);
        }
        System.out.println(Arrays.equals(new byte[]{-6, -127, 29, -75, 79, 68, 2, -7, -15, -24, 106, 21, -50, 23, 76, -88}, md5));


        /*
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
*/
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
      /*  } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
