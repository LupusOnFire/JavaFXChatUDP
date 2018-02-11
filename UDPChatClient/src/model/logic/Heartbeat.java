package model.logic;

import controller.Controller;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Heartbeat implements Runnable{
    private Controller controller;

    public Heartbeat(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.send("ALVE", "");
        }
    }
}
