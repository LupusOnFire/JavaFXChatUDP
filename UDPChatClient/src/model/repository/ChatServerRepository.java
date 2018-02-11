package model.repository;

import model.ChatServer;
import model.dataaccess.IDataAccessFacade;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class ChatServerRepository {
    private IDataAccessFacade dataAccessFacade;
    private ChatServer currentServer;

    public ChatServerRepository(IDataAccessFacade dataAccessFacade) {
        this.dataAccessFacade = dataAccessFacade;
    }

    public List<ChatServer> getNetworkList() {
        try {
            return dataAccessFacade.readNetworkList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveNetworkList(List<ChatServer> chatServers){
        dataAccessFacade.writeNetworkList(chatServers);
    }


    public void setCurrentServer(ChatServer cs) {
        this.currentServer = cs;
    }

    public ChatServer getCurrentServer() {
        return currentServer;
    }

    public void deleteServer(ChatServer cs){
        List<ChatServer> serverList = getNetworkList();
        ChatServer finalCs = cs;
        ChatServer csDel = serverList.stream().filter(x -> x.getLabel().equals(finalCs.getLabel())).findFirst().orElse(null);
        serverList.remove(csDel);
        saveNetworkList(serverList);
    }

    public void addServer(String label, String ip, String port) {
        List<ChatServer> serverList = getNetworkList();
        ChatServer s = null;
        try {
            s = new ChatServer(label, InetAddress.getByName(ip), Integer.parseInt(port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        serverList.add(s);
        saveNetworkList(serverList);
    }
    public void editServer(ChatServer cs,String label, String ip, String port) {
        ChatServer finalCs = cs;
        List<ChatServer> serverList = getNetworkList();
        ChatServer csEdit = serverList.stream().filter(x -> x.getLabel().equals(finalCs.getLabel())).findFirst().orElse(null);
        csEdit.setLabel(label);
        try {
            csEdit.setIp(InetAddress.getByName(ip));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        csEdit.setPort(Integer.parseInt(port));
        saveNetworkList(serverList);
    }
}
