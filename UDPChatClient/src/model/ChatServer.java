package model;

import java.net.InetAddress;

public class ChatServer implements java.io.Serializable{
    private String label;
    private InetAddress ip;
    private int port;

    public ChatServer(String label, InetAddress ip, int port) {
        this.label = label;
        this.ip = ip;
        this.port = port;
    }

    public String getLabel() {
        return label;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setIp(InetAddress ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
