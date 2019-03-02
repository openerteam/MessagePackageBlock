package cn.team.message.common;


/**
 * 消息平台接口
 * @author  Kalen
 * @date 2019-03-01
 */
public interface MessagePlatformInterface<T extends Message> {

    /**
     * 发送消息
     * @param message
     * @return
     */
    public boolean sendMessage(T message);

    /**
     * 平台名称
     * @return
     */
    public String getName();


    /**
     * 检测消息是否合法
     * @param message
     * @return
     */
    public boolean checkMessage(T message);



    /**
     * 推送平台对应的客户端
     *
     * @return
     */
    public abstract String getApplicationType();

}
