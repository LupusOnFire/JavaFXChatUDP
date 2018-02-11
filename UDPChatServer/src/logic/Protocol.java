package logic;

import logic.entity.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public interface Protocol {
    void join(DatagramPacket dgp, String data);
    void joinConfirm(Client client) throws IOException;
    void duplicateName(InetAddress ip, int port) throws IOException;
    void data(DatagramPacket message);
    void heartbeat(DatagramPacket dgp);
    void quit(DatagramPacket dgp);
    void list();
}
