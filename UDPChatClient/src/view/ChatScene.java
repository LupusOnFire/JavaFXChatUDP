package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.ChatServer;


public class ChatScene {
    private Scene scene;
    private HBox hBoxContainer;
    private HBox hBoxInput;
    private VBox vBoxChat;
    private VBox vBoxUsers;
    private VBox vBoxContainer;
    private TextField txtUserInput;
    private Label lblUsername;
    private ListView lViewUsers;
    private TableView tViewChat;
    private TableColumn colTimestamps;
    private TableColumn colUsernames;
    private TableColumn colMessages;


    public Scene getScene(Controller controller, Stage primaryStage, String username, ChatServer server) {

        ObservableList<String> timestamps = FXCollections.observableArrayList();
        ObservableList<Message> messages = FXCollections.observableArrayList();
        ObservableList<String> users = FXCollections.observableArrayList();

        lViewUsers = new ListView<>(users);
        lViewUsers.setMinHeight(590);
        lViewUsers.getItems().addAll(users);
        lViewUsers.setId("Userlist");

        tViewChat       = new TableView();
        colTimestamps   = new TableColumn("");
        colUsernames    = new TableColumn("");
        colMessages     = new TableColumn("");

        colTimestamps.setCellValueFactory(new PropertyValueFactory<Message,String>("timestamp"));
        colUsernames.setCellValueFactory(new PropertyValueFactory<Message,String>("username"));
        colMessages.setCellValueFactory(new PropertyValueFactory<Message,String>("message"));
        colUsernames.setStyle( "-fx-alignment: CENTER-RIGHT;");

        colTimestamps.setMinWidth(75);
        colUsernames.setMinWidth(75);
        colMessages.setMinWidth(680);
        colMessages.setMaxWidth(680);
        tViewChat.minWidth(844);
        tViewChat.setMinHeight(590);
        tViewChat.setMaxHeight(590);

        tViewChat.setItems(messages);
        tViewChat.getColumns().addAll(colTimestamps, colUsernames, colMessages);


        controller.createBinder(users, messages, timestamps, server);
        controller.createSocket();
        controller.connect(username);
        controller.receive();


        lViewUsers.setMinWidth(150);
        lViewUsers.setMaxWidth(150);

        vBoxUsers       = new VBox();

        vBoxUsers.setMinWidth(150);
        vBoxUsers.setMaxWidth(150);

        vBoxUsers.getChildren().add(lViewUsers);

        hBoxContainer = new HBox();
        hBoxContainer.getChildren().addAll(tViewChat, vBoxUsers);
        hBoxContainer.setMinHeight(600);
        hBoxContainer.setPadding(new Insets(15));


        txtUserInput = new TextField();
        txtUserInput.setMinWidth(920);
        txtUserInput.setMaxHeight(25);

        lblUsername = new Label(username);
        lblUsername.setMinWidth(75);
        lblUsername.setMaxWidth(75);

        hBoxInput = new HBox();
        hBoxInput.getChildren().addAll(lblUsername, txtUserInput);
        hBoxInput.setAlignment(Pos.CENTER);
        hBoxInput.setMinHeight(55);
        hBoxInput.setPadding(new Insets(15));

        vBoxContainer = new VBox();
        vBoxContainer.getChildren().addAll(hBoxContainer, hBoxInput);




        //make enter submit the message
        txtUserInput.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                controller.send("DATA",username + ":" + txtUserInput.getText());
                txtUserInput.setText("");
            }
        });

        //set autoscroll for the autoupdating tables
        tViewChat.getItems().addListener((ListChangeListener<Message>) c -> tViewChat.scrollTo(c.getList().size()-1));


        primaryStage.setOnHiding(event -> Platform.runLater(new Runnable() {
            @Override
            public void run() {
                controller.send("QUIT","");
            }
        }));


        txtUserInput.setId("Chatinput");

        vBoxContainer.getStylesheets().add("view/style/style.css");
        scene = new Scene(vBoxContainer, 1024, 660);
        return scene;

    }

}
