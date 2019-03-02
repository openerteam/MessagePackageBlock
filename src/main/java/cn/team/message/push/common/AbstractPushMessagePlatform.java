package cn.team.message.push.common;


import cn.team.message.common.MessagePlatformInterface;
import cn.team.message.common.MessageType;
import cn.team.message.common.TerminalType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 推送消息平台抽象类
 *
 * @author Kalen
 * @date 2019-03-01
 */
public abstract class AbstractPushMessagePlatform implements MessagePlatformInterface<AbstractPushMessage> {

    private static Log LOG = LogFactory.getLog(AbstractPushMessagePlatform.class);

    /**
     * 检测推送消息是否有效
     *
     * @param pushMessage
     * @return
     */
    public boolean checkMessage(AbstractPushMessage pushMessage) {
        if (!pushMessage.checkValid()) {
            LOG.error("消息信息不全，验证有效失败");
            return false;
        }
        if (pushMessage.getMessageType() != MessageType.PUSH) {
            LOG.error("消息类型不符合,需要" + MessageType.PUSH.name() + ",当前为：" + pushMessage.getMessageType().name());
            return false;
        }

        if (!StringUtils.equals(pushMessage.getApplicationType(), getApplicationType())) {
            LOG.error("消息接收客户端不匹配,需要" + getApplicationType() + ",当前为：" + pushMessage.getApplicationType());
            return false;
        }
        return true;
    }

    /**
     * 发送消息实现
     *
     * @param pushMessage
     * @return
     */
    public boolean sendMessage(AbstractPushMessage pushMessage) {
        if (!checkMessage(pushMessage)) {
            return false;
        }

        boolean result = false;
        try {
            if (pushMessage.getTerminalType() == TerminalType.ANDROID) {
                result = sendMessageToAndroid(pushMessage);
            } else if (pushMessage.getTerminalType() == TerminalType.IOS) {
                result = sendMessageToIphone(pushMessage);
            } else if (pushMessage.getTerminalType() == TerminalType.IOS) {
                result = sendMessageToAll(pushMessage);
            } else {
                LOG.error("不支持消息终端：" + pushMessage.getTerminalType().name());
            }
        } catch (Exception e) {
            LOG.error("消息发送失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 不区分平台
     *
     * @param pushMessage
     * @return
     */
    protected abstract boolean sendMessageToAll(AbstractPushMessage pushMessage) throws Exception;

    /**
     * 针对Android终端发送
     *
     * @param pushMessage
     * @return
     */
    protected abstract boolean sendMessageToAndroid(AbstractPushMessage pushMessage) throws Exception;

    /**
     * 针对ios终端发送
     *
     * @param pushMessage
     * @return
     */
    protected abstract boolean sendMessageToIphone(AbstractPushMessage pushMessage) throws Exception;

    /**
     * 初始化推送平台
     */
    protected abstract void init(String applicationType);

}
