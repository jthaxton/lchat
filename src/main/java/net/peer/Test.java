package net.peer;
import java.net.Inet4Address;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;

public class Test {
    public String myIp() {
        try {
            String localHost = Inet4Address.getLocalHost().toString();
            return localHost;
        } catch (Exception e) {
            return e.toString();
        }
    }

    public void start() {
        Peer p = new PeerMaker();
    }


}