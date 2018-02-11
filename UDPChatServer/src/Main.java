import logic.Constants;
import logic.Engine;


public class Main {
    public static void main(String[] args) throws Exception {
        Engine engine = new Engine(Constants.SERVER_PORT);

        //this is to check every minute if clients are alive
        //Thread thread = new Thread(engine);
        //thread.start();

        engine.listen();

    }
}
