package cn.team.message.proxy;


import cn.team.message.common.TerminalType;
import cn.team.message.push.common.AbstractPushMessage;
import cn.team.message.push.common.InnerPushMessage;
import cn.team.message.push.common.PushQueueManager;
import cn.team.message.push.platform.PushMessageByJPush;

import java.util.Map;

/**
 * 推送消息代理类
 *
 * @author Kalen
 * @date 2019-03-01
 */
public class PushPlatformProxy {
    /**
     * 消息队列管理器
     */
    private PushQueueManager pushQueueManager;

//    private MessageGenerator messageGenerator;

    /**
     * 代理实体
     */
    private static PushPlatformProxy pushPlatformProxy;

    private PushPlatformProxy() {
//        messageGenerator = MessageGenerator.getPushMessageGenerator();
        pushQueueManager = PushQueueManager.getInstances();
        initPushPlatforms();
        pushQueueManager.startQueue();
    }

    private PushPlatformProxy(PushMessageByJPush... jPushes) {
//        messageGenerator = MessageGenerator.getPushMessageGenerator();
        pushQueueManager = PushQueueManager.getInstances();
        initPushPlatforms(jPushes);
        pushQueueManager.startQueue();
    }

    public static PushPlatformProxy getInstance(PushMessageByJPush... jPushes) {
        if (pushPlatformProxy == null) {
            pushPlatformProxy = new PushPlatformProxy(jPushes);
        }
        return pushPlatformProxy;
    }

    public static PushPlatformProxy getInstance() {
        if (pushPlatformProxy == null) {
            pushPlatformProxy = new PushPlatformProxy();
        }
        return pushPlatformProxy;
    }

    /**
     * 最直接的方式
     *
     * @param title
     * @param content
     * @param registrationId
     * @param terminal
     */
    public void sendMessage(String title, String content, String registrationId, TerminalType terminal, String applicationType) {
        AbstractPushMessage message = InnerPushMessage.create(terminal, title, content, registrationId, applicationType);
        sendMessage(message);
    }

    public void sendMessage(String title, String content, Map extra, String registerId, TerminalType terminal, String applicationType) {
        AbstractPushMessage message = InnerPushMessage.create(terminal, title, content, registerId, applicationType, extra);
        sendMessage(message);
    }

    public void sendMessage(AbstractPushMessage message) {
        pushQueueManager.add(message);
    }

    /**
     * 约定的action发送
     *
     * @param action
     * @param device
     * @param places
     */
//    public void sendMessageByAction(String action, Device device, String... places) {
//        if (action == null || action.equals("")) {
//            return;
//        }
//        String titleCommand = "";
//        String contentCommand = "";
//        int index = action.indexOf(">");
//        if (index != -1) {
//            titleCommand = action.substring(0, index);
//            contentCommand = action.substring(index + 1);
//        }
//        try {
//            sendMessage(messageGenerator.value(titleCommand + "_title"), messageGenerator.format(titleCommand + "_" + contentCommand + "_content", places), device);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 约定的action发送
     *
     * @param action
     * @param devices
     * @param places
     */
//    public void sendMessageByAction(String action, ArrayList<Device> devices, String... places) {
//        for (Device device : devices) {
//            sendMessageByAction(action, device, places);
//        }
//    }


    /**
     * 指定的设备，适用于通过sql查询得出的
     *
     * @param title
     * @param message
     * @param devices
     */
//    public void sendMessage(String title, String message, ArrayList<Device> devices) {
//        for (Device device : devices) {
//            sendMessage(title, message, device);
//        }
//    }

    /**
     * 指定的设备，适用于通过sql查询得出的
     *
//     * @param title
//     * @param message
     */
//    public void sendMessage(String title, String message, Device device) {
//        sendMessage(title, message, device.getRegisterid(), device.getTerminal(), device.getClientApp());
//    }


    public void initPushPlatforms(PushMessageByJPush... jPushes) {
        for (PushMessageByJPush jPush : jPushes) {
            pushQueueManager.addPushPlatform(jPush);
        }
    }
}
