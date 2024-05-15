package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer extends UDPServer{
    public final static int DEFAULT_PORT = 7;
    @Override
    public void respond(DatagramSocket socket, DatagramPacket incoming) {
        DatagramPacket reply = new DatagramPacket(incoming.getData(),
                incoming.getLength(), incoming.getAddress(), incoming.getPort());
        try {
            socket.send(reply);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public UDPEchoServer() {
        super(DEFAULT_PORT);
    }

    public static void main(String[] args) {
        UDPServer server = new UDPEchoServer();
        Thread t = new Thread(server);
        t.start();
    }
}
