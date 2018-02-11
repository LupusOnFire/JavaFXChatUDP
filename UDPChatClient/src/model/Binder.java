package model;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import view.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Binder {
    private ObservableList<String> fxUsers;
    private ObservableList<Message> fxMessages;
    private ChatServer server;

    public Binder(ObservableList<String> fxUsers, ObservableList<Message> fxMessages, ObservableList<String> fxTimestamps, ChatServer server) {
        this.fxUsers = fxUsers;
        this.fxMessages = fxMessages;
        this.server = server;
    }

    public void setUsers(String usernames){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] usernameArray = usernames.split(" ");

                if (!fxUsers.isEmpty()) {
                    //setConnectionMessages(usernameArray);
                    fxUsers.clear();
                } else {
                    setJoinMessage();
                }
                for (int i = 0; i < usernameArray.length; i++) {
                    fxUsers.add(usernameArray[i]);
                }
            }
        });
    }
    public void addMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] data = message.split(":");
                String message = data[1];

                //if the user typed ':' in his message
                if (data.length > 1) {
                    for (int i = 2; i < data.length; i++) {
                        message += ":" + data[i];
                    }
                }

                fxMessages.add(new Message(getTimestamp(), data[0], message));
            }
        });
    }

    public String getTimestamp(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss]");

        return sdf.format(cal.getTime());
    }
    //this could probably be optimized
    /*public void setConnectionMessages(String[] usernameArray) {
        ArrayList<String> loneUser = new ArrayList();
        for (int i = 0; i < usernameArray.length; i++) {
            boolean userFound = false;
            for (String user : fxUsers) {
                if (user.equals(usernameArray[i])) {
                    userFound = true;
                    break;
                }
            }
            if (!userFound) {
                addMessage("*:" + usernameArray[i] + " has joined.");
            } else {
                addMessage("*:" + usernameArray[i] + " has left.");
            }
        }
    }*/
    public void setJoinMessage(){
        addMessage("*:" + "Now talking on " + server.getLabel() + " (" + server.getIp() +":"+server.getPort() + ")");
    }
}
