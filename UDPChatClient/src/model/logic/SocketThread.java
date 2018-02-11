package model.logic;

import model.ChatServer;
import model.logic.PacketHandler;

import java.io.IOException;
import java.net.*;

import static model.Constants.*;

public class SocketThread implements Runnable {
    PacketHandler packetHandler;
    InetAddress serverIp;
    int serverPort;
    DatagramSocket socket;
    DatagramPacket dgp;
    byte[] buf;

    public SocketThread(PacketHandler packetHandler, ChatServer chatServer) throws UnknownHostException, SocketException {
        this.packetHandler = packetHandler;
        serverIp    = chatServer.getIp();
        serverPort  = chatServer.getPort();
        socket      = new DatagramSocket();
        dgp         = new DatagramPacket(new byte[1024], 0);
    }

    public DatagramPacket connect(String username) {
        String message = JOIN + " " + username;
        dgp.setData(message.getBytes());
        dgp.setAddress(serverIp);
        dgp.setPort(serverPort);
        try {
            socket.send(dgp);
            socket.receive(dgp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dgp;
    }

    public void send(String header, String message){
        String s = header + " " + message;
        byte[] buf = s.getBytes();
        dgp = new DatagramPacket(new byte[1024], 0);
        dgp.setAddress(serverIp);
        dgp.setPort(serverPort);
        dgp.setData(buf);
        try {
            socket.send(dgp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                buf = new byte[1024];
                dgp = new DatagramPacket(buf, buf.length);
                socket.receive(dgp);
                packetHandler.handle(dgp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }
}
