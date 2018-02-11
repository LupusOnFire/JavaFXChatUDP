package logic;

import logic.entity.Client;
import logic.entity.Observer;

import java.net.DatagramPacket;

public interface Subject {
    void register(Client c);
    void unregister(Client c);
    void notifyObserver(DatagramPacket message);

}
