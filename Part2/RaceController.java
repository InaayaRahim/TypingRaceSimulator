package Part2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class RaceController {
    private RaceModel model;
    private RaceView view;

    // Constructor takes the model and view to allow the controller to manage interactions between them
    public RaceController(RaceModel model, RaceView view) {
        this.model = model;
        this.view = view;
    }

    // Initialize the controller by setting up event handlers for the passage selector and start button
    public void init() {

        // Temporary test setup
        model.addTypist(new Typist("Player 1"));
        model.addTypist(new Typist("Player 2"));
        model.addTypist(new Typist("Player 3"));

        view.getPassageSelector().setOnAction(e -> handleSelection());
        view.getStartButton().setOnAction(e -> handleStart());
    }

    // Handle the passage selection by updating the model with the chosen passage and updating the view accordingly
    private void handleSelection() {
        String choice = view.getPassageSelector().getValue();

        if (choice == null) {
            return;
        }

        switch (choice) {
            case "Short":
                model.setPassage("The sun is shining so bright.");
                break;

            case "Medium":
                model.setPassage("The peaceful village was surrounded by rolling hills and lush greenery, with a gentle river flowing through it.");
                break;

            case "Long":
                model.setPassage("The beautiful city of Paris is known for its iconic landmarks, rich history, and vibrant culture. From the majestic Eiffel Tower to the world-renowned Louvre Museum, there is no shortage of attractions to explore. The charming streets are lined with quaint cafes, boutique shops, and stunning architecture that captivates visitors from around the globe.");
                break;

            case "Custom":
                model.setPassage(view.getCustomInput().getText());
                break;
        }

        // Update status label with passage length
        view.getStatusLabel().setText("Passage selected (" + model.getPassageLength() + " characters)");
    }

    private void setupLanes() {
        view.getLanesBox().getChildren().clear();

        for (Typist t : model.getTypists()) {
            Label lane = new Label(t.getName() + " |");
            view.getLanesBox().getChildren().add(lane);
        }
    }

    // Simulate the typing progress by advancing through the passage and updating the display with a cursor
    private void handleStart() {
        if (model.getPassage().isEmpty()) {
            view.getStatusLabel().setText("Please select a passage first.");
            return;
        }

        model.resetTypists();

        setupLanes();

        view.getStatusLabel().setText("Race started!");

        // Create a Timeline to simulate the typing progress
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.millis(100), e -> {

                // move typists
                for (Typist t : model.getTypists()) {
                    t.moveForward();
                }

                // redraw lanes
                view.getLanesBox().getChildren().clear();

                for (Typist t : model.getTypists()) {

                    String bar = "|";

                    for (int i = 0; i < t.getPosition(); i++) {
                        bar = "-" + bar;
                    }

                    Label lane = new Label(t.getName() + " " + bar);

                    view.getLanesBox().getChildren().add(lane);
                }
            })
        );

        timeline.setCycleCount(model.getPassageLength());
        timeline.play();
    }
}