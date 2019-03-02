package cn.team.message.proxy;



import cn.team.message.common.MessageGenerator;
import cn.team.message.common.MessagePlatformInterface;
import cn.team.message.sms.common.AbstractSMSMessage;
import cn.team.message.sms.common.InnerSmsMessage;
import cn.team.message.sms.platform.SMSMessageByLK;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息代理类
 * @author Kalen
 * @date 2019-03-01
 */
public class SMSPlatformProxy {
    private static SMSPlatformProxy smsPlatformProxy;
    private List<MessagePlatformInterface> platforms;
    private MessageGenerator messageGenerator;

    private SMSPlatformProxy() {
        messageGenerator = MessageGenerator.getSMSMessageGenerator();
        platforms = new ArrayList<MessagePlatformInterface>();
        addSMSPlatform(SMSMessageByLK.createLKSMS());
    }

    public static SMSPlatformProxy getInstance() {
        if (smsPlatformProxy == null) {
            smsPlatformProxy = new SMSPlatformProxy();
        }
        return smsPlatformProxy;
    }

    public boolean sendMessage(String phone, String content) {
        boolean result = false;
        AbstractSMSMessage message = InnerSmsMessage.create(phone, content);
        for (MessagePlatformInterface platformInterface : platforms) {
            if (platformInterface == null) {
                continue;
            }
            result = platformInterface.sendMessage(message);
        }
        return result;
    }


    public boolean sendMessageByActionAndParam(String phone, String action, String... param) {
        try {
            String message = messageGenerator.format(action, param);
            return sendMessage(phone, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

 
    public static String generatorRandomCode(int number) {
        String str = "";
        for (int i = 0; i < number; i++) {
            str += (int) (Math.random() * 10);
        }
        return str;
    }

    public void addSMSPlatform(MessagePlatformInterface abstractPushPlatform) {
        platforms.add(abstractPushPlatform);
    }

}
