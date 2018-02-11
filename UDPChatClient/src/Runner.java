import controller.Controller;
import model.*;
import model.dataaccess.txt.Serializer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        InetAddress ip = InetAddress.getByName("192.168.0.10");
        ChatServer s = new ChatServer("Gentoo", ip, 32500);
        List<ChatServer> chatServers = new ArrayList<ChatServer>();
        chatServers.add(s);
        //controller.saveNetworkList(chatServers);



        //List<ChatServer> chatServers = controller.getNetworkList();
        for (ChatServer server : chatServers) {
            System.out.println("Name: " + server.getLabel() + " IP: " + server.getIp().toString() + " Port: " + server.getPort());
        }
    }
}
