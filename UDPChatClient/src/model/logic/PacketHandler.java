package model.logic;
import javafx.collections.ObservableList;
import model.Binder;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import static model.Constants.*;

public class PacketHandler {
    DatagramPacket dgp;
    Binder binder;

    public PacketHandler(Binder binder) {
        this.binder = binder;
        dgp = new DatagramPacket(new byte[1024], 0);
    }


    public void handle(DatagramPacket dgp) {
        String raw = new String(dgp.getData(), 0, dgp.getLength());
        System.out.println("RAW MESSAGE: " + raw);
        String header = raw.substring(0, 4);
        String data = null;
        if (raw.length() >= 5) {
            data = raw.substring(5, raw.length());
        }

        switch (header) {
            case J_OK: {
                //do something here
                //System.out.println("J_OK:" + header);
                break;
            }
            case DATA: {
                binder.addMessage(data);
                break;
                //do something else
            }
            case LIST: {
                //System.out.println("LIST: " + header);
                binder.setUsers(data);
                break;
            }
            default: break;
        }
        return;
    }
}
