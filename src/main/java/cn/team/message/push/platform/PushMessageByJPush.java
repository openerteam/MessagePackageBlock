package cn.team.message.push.platform;


import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.team.message.push.common.AbstractPushMessage;
import cn.team.message.push.common.AbstractPushMessagePlatform;

/**
 * 极光推送平台
 *
 * @author Kalen
 * @date 2019-03-01
 */
public class PushMessageByJPush extends AbstractPushMessagePlatform {
    private JPushClient jpushClient = null;
    /**
     * 极光推送 key 与 secret
     */
    private String appKey;
    private String masterSecret;
    /**
     * 是否为开发环境
     */
    private Boolean dev = false;
    /**
     * 平台对应的客户端
     */
    private String applicationType;

    private PushMessageByJPush(String masterSecret, String appKey) {
        this.masterSecret = masterSecret;
        this.appKey = appKey;
    }


    public static PushMessageByJPush createJpush(String masterSecret, String appKey, String applicationType) {
        PushMessageByJPush jpush = new PushMessageByJPush(masterSecret, appKey);
        jpush.init(applicationType);
        return jpush;
    }


    @Override
    protected boolean sendMessageToAndroid(AbstractPushMessage pushMessage) throws Exception {
        PushPayload.Builder build = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(pushMessage.getRegistrations()))
                .setNotification(Notification.android(pushMessage.getContent(), pushMessage.getTitle(), pushMessage.getExtras()))
                .setMessage(Message.newBuilder().setTitle(pushMessage.getTitle()).setMsgContent(pushMessage.getContent()).addExtras(pushMessage.getExtras()).build());
        PushPayload payload = build.build();
        return sendMessageByPushPayload(payload);
    }

    @Override
    protected boolean sendMessageToAll(AbstractPushMessage pushMessage) throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(pushMessage.getRegistrations()))
                .setNotification(Notification.alert(pushMessage.getContent()))
                .setOptions(Options.newBuilder().setApnsProduction(true).build())
                .build();
        return sendMessageByPushPayload(payload);
    }

    /**
     * 通过极光包体发送消息
     *
     * @param payload
     * @return
     */
    private boolean sendMessageByPushPayload(PushPayload payload) throws APIConnectionException, APIRequestException {
        PushResult result = jpushClient.sendPush(payload);
        return result.isResultOK();
    }

    @Override
    protected boolean sendMessageToIphone(AbstractPushMessage pushMessage) throws Exception {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(pushMessage.getRegistrations()))
                .setNotification(Notification.ios_auto_badge())
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(pushMessage.getContent())
                                //   .setBadge(1)
                                .setSound("default")
                                .addExtras(pushMessage.getExtras())
                                .build())
                        .build())
                .setMessage(Message.content(pushMessage.getContent()))
                .setOptions(Options.newBuilder()
                        .setApnsProduction(dev)
                        .build())
                .build();
        return sendMessageByPushPayload(payload);
    }

    public void setDev(Boolean dev) {
        this.dev = dev;
    }

    @Override
    protected void init(String applicationType) {
        this.jpushClient = new JPushClient(masterSecret, appKey);
        this.applicationType = applicationType;
    }

    public String getName() {
        return "极光推送";
    }


    public String getApplicationType() {
        return applicationType;
    }
}
