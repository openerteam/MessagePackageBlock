package cn.team.message.push.common;




import cn.team.message.common.Message;
import cn.team.message.common.MessageType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 推送消息抽象类
 * @author  Kalen
 * @date 2019-03-01
 */
public abstract class AbstractPushMessage implements Message {

    /**
     * 消息标题
     * @return
     */
    public abstract String getTitle();

    /**
     * 消息推送码
     * @return
     */
    public abstract List getRegistrations();

    /**
     * 消息附加信息
     * @return
     */
    public abstract Map<String, String> getExtras();
    /**
     * 是否为自定义消息
     * @return
     */
    public abstract Boolean isCustomMessage();

    /**
     * 判断消息主体信息是否有效
     * @return
     */
    public Boolean checkValid() {

        if (getApplicationType() == null || getMessageType() == null || getTerminalType() == null){
            return false;
        }

        if (StringUtils.isEmpty(getTitle()) || StringUtils.isEmpty(getContent())){
            return false;
        }

        if (getRegistrations() == null || getRegistrations().size() == 0){
            return false;
        }

        return true;
    }

    /**
     * 消息类型
     * @return
     */
    public MessageType getMessageType(){
        return MessageType.PUSH;
    }

}
