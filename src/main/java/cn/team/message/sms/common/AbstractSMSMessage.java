package cn.team.message.sms.common;


import cn.team.message.common.Message;
import cn.team.message.common.MessageType;
import cn.team.message.common.TerminalType;
import org.apache.commons.lang3.StringUtils;

/**
 * 短信消息抽象类
 *
 * @author Kalen
 * @date 2019-03-01
 */
public abstract class AbstractSMSMessage implements Message {

    /**
     * 消息接收手机号
     *
     * @return
     */
    public abstract String getPhone();

    /**
     * 针对所有终端，因为不同终端发送方式以便一样
     *
     * @return
     */
    public TerminalType getTerminalType() {
        return TerminalType.ALL;
    }

    /**
     * 针对所有客户端，暂不支持不同客户端发送平台不一样，也没有必要
     *
     * @return
     */
    public String getApplicationType() {
        return "All";
    }

    /**
     * 检测短信消息合法性
     *
     * @return
     */
    public Boolean checkValid() {
        if (getApplicationType() == null || getMessageType() == null || getTerminalType() == null) {
            return false;
        }

        if (StringUtils.isEmpty(getContent())) {
            return false;
        }

        if (StringUtils.isEmpty(getPhone())) {
            return false;
        }

        return true;
    }

    /**
     * 消息类型
     * @return
     */
    public MessageType getMessageType() {
        return MessageType.SMS;
    }
}
