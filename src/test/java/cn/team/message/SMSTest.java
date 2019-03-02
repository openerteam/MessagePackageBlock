package cn.team.message;

import cn.team.message.sms.common.InnerSmsMessage;
import cn.team.message.sms.platform.SMSMessageByLK;
import org.junit.Test;

public class SMSTest {

    @Test
    public void testLKSms() {
        InnerSmsMessage smsMessage = InnerSmsMessage.create("18708140959", "hello");
        System.out.println(SMSMessageByLK.createLKSMS().sendMessage(smsMessage));;
    }
}
