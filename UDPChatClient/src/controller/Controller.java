package controller;

import javafx.collections.ObservableList;
import model.Binder;
import model.logic.Heartbeat;
import model.logic.PacketHandler;
import model.ChatServer;
import model.logic.SocketThread;
import model.dataaccess.IDataAccessFacade;
import model.dataaccess.txt.FacadeTxt;
import model.repository.ChatServerRepository;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Controller {
    SocketThread socketThread;
    PacketHandler packetHandler;
    IDataAccessFacade dataAccessFacade;
    ChatServerRepository csRepo;
    Binder binder;
    Heartbeat heartbeat;

    public Controller() throws IOException {
        dataAccessFacade    = new FacadeTxt();
        csRepo              = new ChatServerRepository(dataAccessFacade);
    }
    public void connect(String username) {
        socketThread.connect(username);
        /*heartbeat = new Heartbeat(this);
        Thread thread = new Thread(heartbeat);
        thread.start();*/
    }

    public void receive() {
        Thread thread = new Thread(socketThread);
        thread.start();
    }
    public void send(String header, String data) {
        socketThread.send(header, data);
    }

    public List<ChatServer> getNetworkList(){
        return csRepo.getNetworkList();
    }

    public void setChatServer(ChatServer chatServer){
        csRepo.setCurrentServer(chatServer);
    }

    public void addServer(String label, String ip, String port) {
        csRepo.addServer(label, ip, port);
    }

    public void editServer(ChatServer chatServer, String label, String ip, String port){
        csRepo.editServer(chatServer, label, ip, port);
    }

    public void deleteServer(ChatServer chatServer){
        csRepo.deleteServer(chatServer);
    }


    public void createSocket() {
        try {
            this.socketThread = new SocketThread(packetHandler, csRepo.getCurrentServer());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void createBinder(ObservableList users, ObservableList messages, ObservableList timestamps, ChatServer server) {
        this.binder = new Binder(users, messages, timestamps, server);
        this.packetHandler = new PacketHandler(binder);
    }
}
