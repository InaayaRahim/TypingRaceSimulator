package Part2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

        // Update the passage display and status label
        view.getPassageDisplay().setText(model.getPassage());

        // Update status label with passage length
        view.getStatusLabel().setText("Passage selected (" + model.getPassageLength() + " characters)");
    }

    // Simulate the typing progress by advancing through the passage and updating the display with a cursor
    private void handleStart() {
        if (model.getPassage().isEmpty()) {
            view.getStatusLabel().setText("Please select a passage first.");
            return;
        }

        // Reset the model's progress to start from the beginning
        model.resetProgress();

        view.getStatusLabel().setText("Race started!");

        // Create a Timeline to simulate the typing progress
        Timeline timeline = new Timeline (
            new KeyFrame(Duration.millis(100), e -> {
                model.advance();

                // Update the passage display with a cursor to indicate progress
                String text = model.getPassage();
                int i = model.getCurrentIndex();

                // Insert a cursor (|) at the current index to show progress
                String display = text.substring(0, i) + "|" + text.substring(i);

                // Update the passage display with the new text
                view.getPassageDisplay().setText(display);
            })
        );

        // Set the cycle count to the length of the passage so it will stop after simulating the entire passage
        timeline.setCycleCount(model.getPassageLength());
        timeline.play();
    }
}