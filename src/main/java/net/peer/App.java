package net.peer;

import java.util.Random;

import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.p2p.Peer;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import net.tomp2p.futures.FutureDiscover;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.storage.Data;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
// import net.tomp2p.futures.BaseFutureAdapter;

public class App 
{
    public static void shutdown(Peer peer) {
        peer.shutdown();
    }

    public static void main( String[] args ) throws Exception
    {
        Client client = new Client();
        

            Random rnd = new Random();
            KeyPairGenerator gen = KeyPairGenerator.getInstance("DSA");
            KeyPair pair1 = gen.generateKeyPair();
            KeyPair pair2 = gen.generateKeyPair();
            Peer peer = new PeerBuilder(pair1).ports(4001).start();

            Peer another = new PeerBuilder(pair2).masterPeer(peer).ports(4002).start();
            FutureDiscover future = another.discover().peerAddress(peer.peerAddress()).start();
            future.awaitUninterruptibly();

            FutureBootstrap fb = another.bootstrap().peerAddress(peer.peerAddress()).start();
            fb.awaitUninterruptibly();

            PeerDHT pdht = new PeerBuilderDHT(another).start();
            Data data = new Data("test");
            Number160 nr = new Number160(rnd);
            FuturePut fp = pdht.put(nr).data(data).start();
            fp.awaitUninterruptibly();

            FutureGet fg = pdht.get(nr).start();
            System.out.println(fg.data());

        System.out.println("Client at " + client.myIp());
    }
}
