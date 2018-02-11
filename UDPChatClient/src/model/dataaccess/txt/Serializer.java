package model.dataaccess.txt;

import model.ChatServer;

import java.io.*;
import java.util.List;

public class Serializer {
    final String FILE_PATH = "src/model/dataaccess/txt/networks.ser";

    public Serializer() {

    }
    public void writeNetworkList(List<ChatServer> networks){
        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(networks);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public List<ChatServer> readNetworkList() throws IOException, ClassNotFoundException {
        List<ChatServer> chatServers = null;
        FileInputStream fileIn = new FileInputStream(FILE_PATH);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        chatServers = (List<ChatServer>) in.readObject();
        in.close();
        fileIn.close();
        return chatServers;
    }
}
