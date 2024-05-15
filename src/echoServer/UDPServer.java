package echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public abstract class UDPServer implements Runnable {
    private final int bufferSize;
    private final int port;
    private boolean isShutDown = false;
    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        try (DatagramSocket socket = new DatagramSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                if (isShutDown)
                    return;
                DatagramPacket incoming = new DatagramPacket(buffer,
                        buffer.length);
                try {
                    socket.receive(incoming);
                    this.respond(socket, incoming);
                } catch (SocketTimeoutException ex) {
                    if (isShutDown)
                        return;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage() + "\n" + ex);
                }
            }
        } catch (SocketException ex) {
            System.err.println("Could not bind to port: " + port + "\n" + ex);
        }

    }

    public abstract void respond(DatagramSocket socket, DatagramPacket incoming);

    public UDPServer(int port) {

        bufferSize = 8192;
        this.port = port;
    }

    public UDPServer() {
        bufferSize = 8192;
        this.port = 0;
    }

    public UDPServer(int bufferSize, int port) {
        this.bufferSize = bufferSize;
        this.port = port;
    }
    public void shutDown() {
        isShutDown = true;
    }
} 
