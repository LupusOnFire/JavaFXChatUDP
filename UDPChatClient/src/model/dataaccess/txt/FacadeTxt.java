package model.dataaccess.txt;

import model.ChatServer;
import model.dataaccess.IDataAccessFacade;

import java.io.IOException;
import java.util.List;

public class FacadeTxt implements IDataAccessFacade {
    Serializer serializer;
    public FacadeTxt(){
        serializer = new Serializer();
    }

    @Override
    public List<ChatServer> readNetworkList() throws IOException, ClassNotFoundException {
        return serializer.readNetworkList();
    }

    @Override
    public void writeNetworkList(List<ChatServer> chatServers) {
        serializer.writeNetworkList(chatServers);
    }
}
