package cn.team.message.push.common;


import cn.team.message.common.TerminalType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送消息默认实现类
 *
 * @author Kalen
 * @date 2019-03-01
 */
public class InnerPushMessage extends AbstractPushMessage {

    private TerminalType terminalType;

    private String title;

    private String content;

    private Map<String, String> extras;

    private List<String> registrations = new ArrayList<String>();

    private String applicationType;

    private Boolean customMessage;

    /**
     * 用于仅仅发送推送通知情况
     *
     * @param terminalType
     * @param title
     * @param content
     * @param registrationId
     * @param applicationType
     * @return
     */
    public static InnerPushMessage create(TerminalType terminalType, String title, String content, String registrationId, String applicationType) {
        InnerPushMessage innerMessage = new InnerPushMessage();
        innerMessage.terminalType = terminalType;
        innerMessage.title = title;
        innerMessage.content = content;
        innerMessage.customMessage = false;
        innerMessage.registrations.add(registrationId);
        innerMessage.applicationType = applicationType;
        return innerMessage;
    }

    /**
     * 用于发送推送通知存在附加信息
     *
     * @param terminalType
     * @param title
     * @param content
     * @param registrationId
     * @param applicationType
     * @param extras
     * @return
     */
    public static InnerPushMessage create(TerminalType terminalType, String title, String content, String registrationId, String applicationType, Map<String, String> extras) {
        InnerPushMessage innerMessage = create(terminalType, title, content, registrationId, applicationType);
        innerMessage.extras = extras;
        return innerMessage;
    }

    /**
     * 用于发送推送自定义消息
     *
     * @param terminalType
     * @param title
     * @param content
     * @param registrationId
     * @param applicationType
     * @param customMessage
     * @param extras
     * @return
     */
    public static InnerPushMessage create(TerminalType terminalType, String title, String content, String registrationId, String applicationType, Boolean customMessage, Map<String, String> extras) {
        InnerPushMessage innerMessage = create(terminalType, title, content, registrationId, applicationType, extras);
        innerMessage.customMessage = customMessage;
        return innerMessage;
    }

    public TerminalType getTerminalType() {
        return terminalType;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    public String getApplicationType() {
        return applicationType;
    }

    public List getRegistrations() {
        return registrations;
    }

    public Map<String, String> getExtras() {
        if (extras == null) {
            return new HashMap<String, String>();
        }
        return extras;
    }


    @Override
    public Boolean isCustomMessage() {
        return customMessage;
    }

}
