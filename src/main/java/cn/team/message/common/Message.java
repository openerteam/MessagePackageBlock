package cn.team.message.common;

/**
 * 消息实体
 * 非及时消息通常分为短信、推送
 * 推送需要针对不同设备类型，如ios、android；针对不同app如医生端、患者端；针对不同平台推送发送，如极光、百度等；针对不同平台发送方式，如自定义消息、通知
 * 短信需要针对不同平台的短信
 * @author  Kalen
 * @date 2019-03-01
 */
public interface  Message {

    /**
     * 终端类型
     * @return
     */
    public  TerminalType getTerminalType();

    /**
     * 消息内容
     * @return
     */
    public  String getContent();

    /**
     * 消息是否合法
     * @return
     */
    public  Boolean checkValid();

    /**
     * 消息类型
     * @return
     */
    public  MessageType getMessageType();

    /**
     * 消息接收应用
     * @return
     */
    public String getApplicationType();
}
