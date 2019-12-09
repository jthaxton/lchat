package net.peer;
import java.net.Inet4Address;

public class Server {
    public String myIp() {
        try {
            return Inet4Address.getLocalHost().toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}