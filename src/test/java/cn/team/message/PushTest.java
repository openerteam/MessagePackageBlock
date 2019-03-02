package cn.team.message;


import cn.team.message.common.TerminalType;
import cn.team.message.proxy.PushPlatformProxy;
import cn.team.message.push.common.InnerPushMessage;
import cn.team.message.push.platform.PushMessageByJPush;
import org.junit.Test;


/**
 * Created by magicbeans on 2017/7/13.
 */
public class PushTest {

    @Test
    public  void testByPlatform() {
        PushMessageByJPush jPush = PushMessageByJPush.createJpush("0117823cb5f5aedfaf14d187", "31e840fa125ca8ea98573ad6", "test");
        InnerPushMessage message = InnerPushMessage.create(TerminalType.ANDROID, "标题", "内容", "170976fa8ab48df61ed", "test");
        System.out.println(jPush.sendMessage(message));
    }

    @Test
    public  void testByProxy() {
        PushMessageByJPush jPush = PushMessageByJPush.createJpush("0117823cb5f5aedfaf14d187", "31e840fa125ca8ea98573ad6", "test");
        PushPlatformProxy proxy = PushPlatformProxy.getInstance(jPush);
        InnerPushMessage message = InnerPushMessage.create(TerminalType.ANDROID, "标题", "内容", "170976fa8ab48df61ed", "test");
        proxy.sendMessage(message);

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
