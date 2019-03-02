package cn.team.message.sms.platform;


import cn.team.message.push.platform.PushMessageByJPush;
import cn.team.message.sms.common.AbstractSMSMessage;
import cn.team.message.sms.common.AbstractSMSMessagePlatform;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Kalen
 * @date 2019-03-01
 */
public class SMSMessageByLK extends AbstractSMSMessagePlatform {

    private static String username = "CDJS007954";
    private static String password = "zm0513@";
    private static String ip = "sdk2.028lk.com";
    private static int port = 80;
    private static String action = "/sdk2/BatchSend2.aspx";


    public static SMSMessageByLK createLKSMS() {
        SMSMessageByLK jpush = new SMSMessageByLK();
        return jpush;
    }


    protected boolean sendMessageToAll(AbstractSMSMessage message) {
        String param = null;
        try {
            param = getParam(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sendSMSRequest(param);
    }



    private String getParam(AbstractSMSMessage message) throws UnsupportedEncodingException {
        String param = "CorpID=" + username + "&Pwd=" + password + "&Mobile=" + message.getPhone()
                + "&Content=" + message.getContent() + "&Cell=&SendTime=" + "";
        return param;
    }



    private boolean sendSMSRequest(String param) {
        PrintWriter out = null;
        BufferedReader in = null;

        String result = "";
        try {
            URL realUrl = new URL("https://"+ip + action);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new Integer(result).intValue() > 0;
    }

    public String getName() {
        return "LK";
    }
}
