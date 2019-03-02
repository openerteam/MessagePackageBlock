package cn.team.message.sms.common;


/**
 * 消息内部实体
 *
 * @author Kalen
 * @date 2019-03-01
 */
public class InnerSmsMessage extends AbstractSMSMessage {

    private String phone;

    private String content;

    @Override
    public String getPhone() {
        return phone;
    }


    public String getContent() {
        return content;
    }

    /**
     * 创建短信消息
     * @param phone
     * @param content
     * @return
     */
    public static InnerSmsMessage create(String phone, String content) {
        InnerSmsMessage innerMessage = new InnerSmsMessage();
        innerMessage.phone = phone;
        innerMessage.content = content;
        return innerMessage;
    }

}
