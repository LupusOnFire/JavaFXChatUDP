package logic.entity;

import java.net.InetAddress;

public class Client implements Observer {
    private String username;
    private InetAddress ip;
    private int port;
    private boolean alive;

    public Client(String username, InetAddress ip, int port) {
        this.username = username;
        this.ip = ip;
        this.port = port;
        alive = true;
    }

    public String getUsername() {
        return username;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void update(String message) {
        System.out.println(message);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
