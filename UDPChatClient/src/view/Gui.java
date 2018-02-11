package view;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Gui extends Application {
    public Scene scene;
    Controller controller;

    @Override
    public void start(Stage primaryStage){
        try {
            controller = new Controller();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage = new Stage();
        scene = new LoginScene().getScene(controller, primaryStage);

        primaryStage.setTitle("UDP Chat Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}