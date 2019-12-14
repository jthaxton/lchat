package net.peer;
import java.net.InetAddress;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;
import java.lang.ClassNotFoundException;
import java.io.IOException;
import java.lang.Exception;


public class ThisPeer {
    final private PeerDHT peer;

    public ThisPeer(int peerId) throws Exception {
        peer = new PeerBuilderDHT(new PeerBuilder(Number160.createHash(peerId)).ports(4000 + peerId).start()).start();

        FutureBootstrap fb = this.peer.peer().bootstrap().inetAddress(InetAddress.getByName("127.0.0.1")).ports(4001)
                .start();
        fb.awaitUninterruptibly();
        if (fb.isSuccess()) {
            peer.peer().discover().peerAddress(fb.bootstrapTo().iterator().next()).start().awaitUninterruptibly();
        }
    }

    private String get(String name) throws ClassNotFoundException, IOException {
        FutureGet futureGet = peer.get(Number160.createHash(name)).start();
        futureGet.awaitUninterruptibly();
        if (futureGet.isSuccess()) {
            return futureGet.dataMap().values().iterator().next().object().toString();
        }
        return "not found";
    }

    private void store(String name, String ip) throws IOException {
        peer.put(Number160.createHash(name)).data(new Data(ip)).start().awaitUninterruptibly();
    }
    

}