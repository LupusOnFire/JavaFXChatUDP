package view;

import javafx.beans.property.SimpleStringProperty;

public class Message {
    private final SimpleStringProperty timestamp;
    private final SimpleStringProperty username;
    private final SimpleStringProperty message;

    public Message(String timestamp, String username, String message){
        this.timestamp = new SimpleStringProperty(timestamp);
        this.username = new SimpleStringProperty(username);
        this.message = new SimpleStringProperty(message);
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public SimpleStringProperty timestampProperty() {
        return timestamp;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp.set(timestamp);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}