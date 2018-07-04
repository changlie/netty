package it.changlie;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorDemo {

    @Test
    public void test() throws Exception{
        int port = 30;
        ServerSocketChannel serverChannel = ServerSocketChannel.open( );
        ServerSocket serverSocket = serverChannel.socket( );
        Selector selector = Selector.open( );
        serverSocket.bind (new InetSocketAddress(port));
        serverChannel.configureBlocking (false);
        serverChannel.register (selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int n = selector.select( );
            if (n == 0) {
                continue; // nothing to do
            }
            Iterator it = selector.selectedKeys().iterator( );
            while (it.hasNext( )) {
                SelectionKey key = (SelectionKey) it.next( );
                if (key.isAcceptable( )) {
                    ServerSocketChannel server =
                            (ServerSocketChannel) key.channel( );
                    SocketChannel channel = server.accept( );
                    if (channel == null) {
                        ;//handle code, could happen
                    }
                    channel.configureBlocking (false);
                    channel.register (selector, SelectionKey.OP_READ);


                }
                if (key.isReadable( )) {
                    readDataFromSocket (key);
                }
                it.remove( );
            }
        }
    }

    private void readDataFromSocket(SelectionKey key) {

    }
}
