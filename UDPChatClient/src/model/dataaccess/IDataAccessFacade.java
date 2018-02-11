package model.dataaccess;

import model.ChatServer;

import java.io.IOException;
import java.util.List;

public interface IDataAccessFacade {
    List<ChatServer> readNetworkList() throws IOException, ClassNotFoundException;
    void writeNetworkList(List<ChatServer> chatServers);
}
