# MessagePackageBlock
---

## 项目特性
1. 支持扩展多个平台推送
2. 支持每个平台多个应用
3. 支持每个每个应用多种方式（自定义、通知）
4. 支持推送、短信方式并行发送
5. 支持消息队列处理，防止消息溢出

## 快速开始
> 项目使用库
1. junit 4.4
2. commons-lang3 3.7
3. jpush-client 3.3.8

> maven引入MessagePackageBlock
```code
<dependency>
    <groupId>cn.jpush.api</groupId>
    <artifactId>jpush-client</artifactId>
    <version>3.3.8</version>
</dependency>
```
> 发送推送消息

通过封装类直接发送（以极光为例）
```code
PushMessageByJPush jPush = PushMessageByJPush.createJpush("0117823cb5f5aedfaf14d187", "31e840fa125ca8ea98573ad6", "test");
InnerPushMessage message = InnerPushMessage.create(TerminalType.ANDROID, "标题", "内容", "170976fa8ab48df61ed", "test");
System.out.println(jPush.sendMessage(message));
```
通过代理服务发送（以极光为例）
```code
PushMessageByJPush jPush = PushMessageByJPush.createJpush("0117823cb5f5aedfaf14d187", "31e840fa125ca8ea98573ad6", "test");
PushPlatformProxy proxy = PushPlatformProxy.getInstance(jPush);
InnerPushMessage message = InnerPushMessage.create(TerminalType.ANDROID, "标题", "内容", "170976fa8ab48df61ed", "test");
proxy.sendMessage(message);
```
> 发送短信消息

通过封装类直接发送（以xxx为例）
```code
InnerSmsMessage smsMessage = InnerSmsMessage.create("18708140959", "hello");
System.out.println(SMSMessageByLK.createLKSMS().sendMessage(smsMessage));;
```
通过代理服务发送（以xxx为例）
```code

SMSMessageByLK lk = SMSMessageByLK.createLKSMS();
SMSPlatformProxy proxy = SMSPlatformProxy.getInstance(lk);
InnerSmsMessage message = InnerSmsMessage.create("18708140959", "hello");
proxy.sendMessage(message);
```
## 更新记录
> 2019年03月02日
1. 消息基础框架设计
2. 推送、消息框架实现
3. 极光推送、xx消息平台扩展
4. 推送、消息代理实现
5. 加入消息队列