package view;

import com.sun.org.apache.xpath.internal.operations.Bool;
import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.ChatServer;

import java.io.IOException;
import java.util.List;

public class LoginScene {
    private Controller controller;
    private Stage primaryStage;
    private Scene scene;
    private BorderPane borderPane;

    private TextField txtUsername;
    private Label lblUsername;
    private HBox hBoxUsername;
    private HBox hBoxNetwork;
    private VBox vBoxNetworkButtons;
    private VBox vBoxContainer;
    private Button btnConnect;
    private Button btnClose;
    private Button btnAddServer;
    private Button btnDeleteServer;
    private Button btnEditServer;

    private ListView<ChatServer> serverListView;
    private ScrollPane serverPane;

    public Scene getScene(Controller controller, Stage primaryStage) {
        this.controller     = controller;
        this.primaryStage   = primaryStage;
        this.serverListView = new ListView<>();

        serverListView.getItems().addAll(controller.getNetworkList());
        serverListView.getSelectionModel().select(0);

        Label lblHeader1 = new Label("User information");
        Label lblHeader2 = new Label("Networks");
        lblHeader1.setId("Header"); lblHeader2.setId("Header");

        hBoxUsername    = new HBox();
        lblUsername     = new Label("Username:");
        lblUsername.setMinWidth(90);


        txtUsername     = new TextField("");
        txtUsername.setMinWidth(230);
        txtUsername.setMinHeight(30);
        txtUsername.setMaxHeight(30);


        hBoxUsername.getChildren().addAll(lblUsername, txtUsername);
        hBoxUsername.setMinWidth(320);
        hBoxUsername.setAlignment(Pos.CENTER);
        hBoxUsername.setPadding(new Insets(10, 0, 35, 0));

        serverPane = new ScrollPane();
        serverPane.setMinHeight(170);
        serverPane.setMaxHeight(170);
        serverPane.setMinWidth(210);
        serverPane.setMaxWidth(210);
        serverPane.setContent(serverListView);
        serverPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        serverPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



        vBoxNetworkButtons  = new VBox();
        btnAddServer        = new Button("Add");
        btnDeleteServer     = new Button("Delete");
        btnEditServer       = new Button("Edit");

        vBoxNetworkButtons.getChildren().addAll(btnAddServer, btnDeleteServer, btnEditServer);
        vBoxNetworkButtons.setSpacing(5);

        hBoxNetwork = new HBox();
        hBoxNetwork.setSpacing(10);
        hBoxNetwork.getChildren().addAll(serverPane, vBoxNetworkButtons);
        hBoxNetwork.setPadding(new Insets(15, 0, 0, 0));

        btnConnect = new Button("Connect");
        btnClose   = new Button("Close");
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15, 0, 15, 0));
        borderPane.setLeft(btnClose);
        borderPane.setRight(btnConnect);



        vBoxContainer = new VBox();
        vBoxContainer.setPadding(new Insets(15));
        vBoxContainer.setSpacing(5);
        vBoxContainer.getChildren().addAll(lblHeader1, hBoxUsername, lblHeader2, hBoxNetwork, borderPane);
        vBoxContainer.getStylesheets().add("view/style/style.css");


        btnAddServer.setOnAction(e -> {
            try {
                btnEditClicked(false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnEditServer.setOnAction(e -> {
            try {
                btnEditClicked(true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        btnDeleteServer.setOnAction(e->btnDeleteServerClicked());
        btnClose.setOnAction(e->btnCloseClicked());
        btnConnect.setOnAction(e->btnConnectClicked());

        scene = new Scene(vBoxContainer, 350, 365);
        return scene;
    }

    private void btnDeleteServerClicked() {
        controller.deleteServer(serverListView.getSelectionModel().getSelectedItem());
        serverListView.getItems().remove(serverListView.getSelectionModel().getSelectedItem());
    }

    private void btnCloseClicked() {
        Platform.exit();
        System.exit(0);
    }

    public void btnEditClicked(Boolean edit) throws IOException {

        Stage stage = new Stage();
        stage.setTitle("New server");

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        ServerEditScene serverEditScene = new ServerEditScene();
        stage.setScene(serverEditScene.getScene(controller, serverListView, stage, edit));
        stage.show();
    }
    public void btnConnectClicked() {
        ChatServer cs = serverListView.getSelectionModel().getSelectedItem();
        controller.setChatServer(cs);
        primaryStage.setTitle(txtUsername.getText() + "@" + cs.getIp());
        primaryStage.setScene(new ChatScene().getScene(controller, primaryStage, txtUsername.getText(), cs));
    }
}
