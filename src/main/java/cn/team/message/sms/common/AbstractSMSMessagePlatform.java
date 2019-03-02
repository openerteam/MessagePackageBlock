package cn.team.message.sms.common;

import cn.team.message.common.MessagePlatformInterface;
import cn.team.message.common.MessageType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息平台抽象类
 * @author kalen
 * @date 2019-03-02
 */
public abstract class AbstractSMSMessagePlatform implements MessagePlatformInterface<AbstractSMSMessage> {

    private static Log LOG = LogFactory.getLog(AbstractSMSMessagePlatform.class);

    public boolean checkMessage(AbstractSMSMessage smsMessage) {
        if (!smsMessage.checkValid()) {
            LOG.error("消息信息不全，验证有效失败");
            return false;
        }
        if (smsMessage.getMessageType() != MessageType.SMS) {
            LOG.error("消息类型不符合,需要" + MessageType.SMS.name() + ",当前为：" + smsMessage.getMessageType().name());
            return false;
        }

        if (!StringUtils.equals(smsMessage.getApplicationType(), getApplicationType())) {
            LOG.error("消息接收客户端不匹配,需要" + getApplicationType() + ",当前为：" + smsMessage.getApplicationType());
            return false;
        }
        return true;
    }


    /**
     * 发送消息给手机
     * @param message
     * @return
     */
    public boolean sendMessage(AbstractSMSMessage message) {
        if (!checkMessage(message)){
            return false;
        }
        return sendMessageToAll(message);
    }

    /**
     * 发送消息到手机
     * @param message
     * @return
     */
    protected abstract boolean sendMessageToAll(AbstractSMSMessage message);

    /**
     * 推送平台对应的客户端
     *
     * @return
     */
    public String getApplicationType(){
        return "All";
    }
}
