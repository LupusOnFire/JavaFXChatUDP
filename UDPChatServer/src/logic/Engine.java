package logic;


import logic.entity.Client;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Engine implements Subject, Protocol, Runnable {

    private List<Client> clients;
    private DatagramSocket socket;
    private DatagramPacket dgp;
    private byte[] buf;


    public Engine(int serverPort)  {
        clients = new ArrayList<>();
        socket = null;
        buf = new byte[1024];
        dgp = new DatagramPacket(buf, buf.length);

        try {
            socket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        System.out.println("Server started on port: " + Constants.SERVER_PORT);
        while (true) {
            buf = new byte[1024];
            dgp = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(dgp);
                String rcvd = new String(dgp.getData(), 0, dgp.getLength());
                System.out.println(rcvd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //extract data
            String raw = new String(dgp.getData(), 0, dgp.getLength());
            String header = "";
            String data = "";

            if (raw.length() >= 4) {
                header = raw.substring(0, 4);
            }
            if (raw.length() >= 6) {
                data = raw.substring(5, raw.length());
            }


            //handle requests
            switch (header) {
                //user wants to connect
                case "JOIN": {
                    join(dgp, data);
                    break;
                }
                //user sends a message
                case "DATA": {
                    data(dgp);
                    break;
                }
                //ping user to see if they are still connected
                case "ALVE": {
                    heartbeat(dgp);
                    break;
                }
                //user wants to quit
                case "QUIT": {
                    quit(dgp);
                    break;
                }
                case "RMOV": {
                    list();
                    break;
                }
                default:
                    break;
            }
        }
    }

    /*
     *Observer pattern
     */
    public void register(Client c) {
        clients.add(c);
        System.out.println(c.getUsername() + " Connected from " + c.getIp() + " on port " + c.getPort());
        System.out.println("Total clients: " + clients.size());
    }

    public void unregister(Client c) {
        int clientIndex = clients.indexOf(c);
        clients.remove(clientIndex);
    }

    public void notifyObserver(DatagramPacket message) {
        for (Client client : clients) {
            message.setAddress(client.getIp());
            message.setPort(client.getPort());
            try {
                socket.send(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Protocol implementation
     */
    @Override
    public void join(DatagramPacket dgp, String data) {
        if (data.length() > 12) {
            data = data.substring(0, 12);
        }
        for (Client client : clients) {
            if (client.getUsername().equals(data)) {
                try {
                    duplicateName(dgp.getAddress(), dgp.getPort());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        Client c = new Client(data, dgp.getAddress(), dgp.getPort());
        register(c);
        try {
            joinConfirm(c);
            list();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void joinConfirm(Client client) throws IOException {
        String message = "J_OK";
        buf = message.getBytes();
        dgp = new DatagramPacket(new byte[1024], 0);
        dgp.setAddress(client.getIp());
        dgp.setPort(client.getPort());
        dgp.setData(buf);
        socket.send(dgp);

    }

    @Override
    public void duplicateName(InetAddress ip, int port) throws IOException {
        String message = "JERR";
        buf = message.getBytes();
        DatagramPacket dgp = new DatagramPacket(new byte[1024], 0);
        dgp.setAddress(ip);
        dgp.setPort(port);
        dgp.setData(buf);
        socket.send(dgp);
    }

    @Override
    public void data(DatagramPacket message) {
        notifyObserver(message);
    }

    @Override
    public void heartbeat(DatagramPacket dgp) {
        for (Client c : clients) {
            if (dgp.getPort() == c.getPort() && dgp.getAddress().equals(dgp.getAddress())) {
                c.setAlive(true);
                System.out.println("Heartbeat from: " + c.getUsername());
                break;
            }
        }
    }

    @Override
    public void quit(DatagramPacket dgp) {
        for (Client c : clients) {
            if (c.getIp().equals(dgp.getAddress()) && c.getPort() == dgp.getPort()) {
                unregister(c);
                System.out.println(c.getUsername() + " left");
                break;
            }
        }
        list();
    }

    @Override
    public void list() {
        String names = "LIST ";
        for (Client c : clients) {
            names += c.getUsername() + " ";
        }
        DatagramPacket msg = new DatagramPacket(new byte[1024], 0);
        buf = names.getBytes();
        msg.setData(buf);
        notifyObserver(msg);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println("tick");
                ListIterator<Client> cList = clients.listIterator();
                System.out.println("Clients: " + "is " + clients.size());
                boolean removed = false;
                while (cList.hasNext()) {
                    Client c = cList.next();
                    if (c.isAlive() == false) {
                        cList.remove();
                        System.out.println("removed " + c.getUsername());
                        removed = true;
                    }
                }
                //list();
                for (Client c : clients) {
                    c.setAlive(false);
                }

                //THIS IS A HACK
                if (removed) {
                    DatagramSocket socket2 = new DatagramSocket();
                    DatagramPacket dgp = new DatagramPacket(new byte[1024], 1024, InetAddress.getLocalHost(), Constants.SERVER_PORT);
                    dgp.setData("RMOV".getBytes());
                    socket2.send(dgp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
