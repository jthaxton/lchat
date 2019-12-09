package net.peer;
import java.net.Inet4Address;


public class Client {
    public String myIp() {
        try {
            String localHost = Inet4Address.getLocalHost().toString();
            return localHost;
        } catch (Exception e) {
            return e.toString();
        }
    }
}