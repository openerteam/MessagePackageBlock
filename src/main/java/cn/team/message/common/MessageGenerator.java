package cn.team.message.common;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by magicbeans on 2017/7/13.
 */
@SuppressWarnings("Since15")
public class MessageGenerator {
    private ResourceBundle resource;

    public static final String PUSH_MESSAGE = "push_message";
    public static final String SMS_MESSAGE = "sms_message";
    private String name;

    public MessageGenerator(String name) {
        this.name = name;
        resource = ResourceBundle.getBundle(name, Locale.SIMPLIFIED_CHINESE);
    }


    public static MessageGenerator getSMSMessageGenerator() {
        return new MessageGenerator(SMS_MESSAGE);
    }

    public static MessageGenerator getPushMessageGenerator() {
        return new MessageGenerator(PUSH_MESSAGE);
    }

    /**
     * @param key
     * @return
     * @throws Exception
     * @Description: 获取Properties文件里面的值
     */
    public String value(String key) throws Exception {
        String result = null;
        try {
            result = new String(resource.getString(key).getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            System.out.println("获取不到的 key：" + key);
        }

        return result;
    }

    public String format(String key, String... values) {
        if (resource == null || !resource.containsKey(key)) {
            return "";
        }
        String result = null;
        try {
            result = value(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MessageFormat.format(result, values);
    }


}
