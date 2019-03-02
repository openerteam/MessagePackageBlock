package cn.team.message.push.common;

/**
 * Created by magicbeans on 2017/7/13.
 */
public class Device {

    public String registerid;

    public int terminal;

    public String phone;

    private int clientApp;

    public Device(String registerid, int terminal) {
        this.registerid = registerid;
        this.terminal = terminal;
    }
    public Device(String phone) {
        this.phone = phone;
    }

    public Device(String registerid, int terminal, String phone) {
        this.registerid = registerid;
        this.terminal = terminal;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getClientApp() {
        return clientApp;
    }

    public void setClientApp(int clientApp) {
        this.clientApp = clientApp;
    }

    public Device() {
    }

    public String getRegisterid() {
        return registerid;
    }

    public void setRegisterid(String registerid) {
        this.registerid = registerid;
    }

    public int getTerminal() {
        return terminal;
    }

    public void setTerminal(int terminal) {
        this.terminal = terminal;
    }
}
