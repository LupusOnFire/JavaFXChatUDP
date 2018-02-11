package view;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ChatServer;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class ServerEditScene {

    private Controller controller;
    private Scene scene;
    private VBox vBoxContainer;
    private HBox hBoxServerLabel;
    private HBox hBoxServerIp;
    private HBox hBoxServerPort;
    private Label lblServerLabel;
    private Label lblServerIp;
    private Label lblServerPort;
    private TextField txtServerLabel;
    private TextField txtServerIp;
    private TextField txtServerPort;
    private Button btnSave;
    private Button btnCancel;
    private BorderPane borderPane;
    private ListView<ChatServer> serverListView;
    private Stage primaryStage;


    public Scene getScene(Controller controller, ListView<ChatServer> serverListView, Stage primaryStage, Boolean edit) throws IOException {
        this.controller = controller;
        this.primaryStage = primaryStage;
        this.serverListView = serverListView;
        vBoxContainer   = new VBox();
        hBoxServerLabel = new HBox();
        hBoxServerIp    = new HBox();
        hBoxServerPort  = new HBox();


        lblServerLabel = new Label("Server Label:");
        lblServerLabel.setMinWidth(90);

        txtServerLabel     = new TextField("");
        txtServerLabel.setMinWidth(230);
        txtServerLabel.setMinHeight(30);
        txtServerLabel.setMaxHeight(30);

        hBoxServerLabel.getChildren().addAll(lblServerLabel, txtServerLabel);
        hBoxServerLabel.setMinWidth(320);
        hBoxServerLabel.setAlignment(Pos.CENTER);


        lblServerIp = new Label("Server IP:");
        lblServerIp.setMinWidth(90);

        txtServerIp = new TextField("");
        txtServerIp.setMinWidth(230);
        txtServerIp.setMinHeight(30);
        txtServerIp.setMaxHeight(30);

        hBoxServerIp.getChildren().addAll(lblServerIp, txtServerIp);
        hBoxServerIp.setMinWidth(320);
        hBoxServerIp.setAlignment(Pos.CENTER);


        lblServerPort = new Label("Server Port:");
        lblServerPort.setMinWidth(90);

        txtServerPort     = new TextField("");
        txtServerPort.setMinWidth(230);
        txtServerPort.setMinHeight(30);
        txtServerPort.setMaxHeight(30);

        hBoxServerPort.getChildren().addAll(lblServerPort, txtServerPort);
        hBoxServerPort.setMinWidth(320);
        hBoxServerPort.setAlignment(Pos.CENTER);

        if (edit) {
            txtServerLabel.setText(serverListView.getSelectionModel().getSelectedItem().getLabel());
            String serverIp = serverListView.getSelectionModel().getSelectedItem().getIp().toString();
            serverIp = serverIp.substring(1,serverIp.length());
            txtServerIp.setText(serverIp);
            txtServerPort.setText(serverListView.getSelectionModel().getSelectedItem().getPort()+"");
        }


        btnSave = new Button("Save");
        btnCancel = new Button("Cancel");


        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(15, 0, 15, 0));
        borderPane.setLeft(btnSave);
        borderPane.setRight(btnCancel);

        btnSave.setOnAction(e -> {
            try {
                btnSaveClicked(edit);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        });

        vBoxContainer.getChildren().addAll(hBoxServerLabel, hBoxServerIp, hBoxServerPort, borderPane);
        vBoxContainer.setPadding(new Insets(15));
        vBoxContainer.setSpacing(5);
        vBoxContainer.getStylesheets().add("view/style/style.css");
        scene = new Scene(vBoxContainer, 350, 175);
        return scene;
    }
    public void btnSaveClicked(Boolean edit) throws UnknownHostException {
        if (edit) {
            controller.editServer(serverListView.getSelectionModel().getSelectedItem(), txtServerLabel.getText(), txtServerIp.getText(), txtServerPort.getText());
        } else {
            controller.addServer(txtServerLabel.getText(), txtServerIp.getText(), txtServerPort.getText());
        }
        serverListView.getItems().clear();
        serverListView.getItems().addAll(controller.getNetworkList());
        primaryStage.close();
    }

}
