package Part2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGUI extends Application {

    @Override
    public void start(Stage stage) {
        RaceModel model = new RaceModel();
        RaceView view = new RaceView(model);
        RaceController controller = new RaceController(model, view);

        controller.init();

        Scene scene = new Scene(view.getRoot(), 600, 300);

        stage.setTitle("Typing Race Simulator");
        stage.setScene(scene);
        stage.show();
    }

        public static void main(String[] args) {
            launch();
        }
    
}
