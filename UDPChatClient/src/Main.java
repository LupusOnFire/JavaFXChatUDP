import javafx.application.Application;
import view.Gui;

public class Main {
    public static void main(String[] args) {
        /*Controller controller;
        try {
            controller = new Controller();
            String username = "Guffe";
            controller.connect(username);
            controller.send(username,"NIGGER NIGGER CHICKEN DINNER");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Application.launch(Gui.class, args);
    }
}
