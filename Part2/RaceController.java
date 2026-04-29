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

    // Initialise the controller by setting up event handlers for the passage selector and start button
    public void init() {
        // Removed hardcoded typists

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

        // Update passage display with the selected passage from the model
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

        model.resetTypists();

        model.getTypists().clear(); // Clear existing typists before adding new ones based on seat selection

        int seats = view.getSeatSelector().getValue(); // Get the number of typists from the seat selector

        // Dynamically create typists based on the number of seats selected, with random speeds to create variability among typists
        for (int i = 1; i <= seats; i++) {
            Typist t = new Typist("Player " + i);

            // Assign random speeds to typists to create variability, with a base speed of 0.7 and a random addition up to 1.0
            t.setSpeed(0.7 + Math.random()); 

            model.addTypist(t);
        }

        // Set modifiers based on the state of the checkboxes in the view
        model.setAutocorrect(view.getAutocorrectCheck().isSelected());
        model.setCaffeineMode(view.getCaffeineCheck().isSelected());
        model.setNightShift(view.getNightShiftCheck().isSelected());

        view.getStatusLabel().setText("Race started!");

        final int[] tick = {0}; 

        // Declare timeline outside so we can stop it from within the KeyFrame when a winner is declared
        Timeline timeline = new Timeline();

        KeyFrame frame = new KeyFrame(Duration.millis(100), e -> {

            tick[0]++; // Increment the tick count

            String passage = model.getPassage();

            for (Typist t : model.getTypists()) {

                double baseSpeed = t.getSpeed();

                // Apply modifiers to speed based on the model's settings

                // Night Shift Modifier
                if (model.isNightShift()) {
                    baseSpeed *= 0.85; // Reduce speed by 15% during night shift
                }

                // Caffeine Mode Modifier
                if (model.isCaffeineMode() && tick[0] <= 10){
                    baseSpeed *= 1.5; // Increase speed by 50% for the first 10 ticks when caffeine mode is active
                }

                t.setSpeed(baseSpeed); // Update the typist's speed based on modifiers

                // Burnout System
                t.updateBurnout(); // Update burnout status for each typist

                t.moveForward(); // Move each typist forward based on their speed

                if (t.getPosition() >= passage.length()) {
                    timeline.stop(); // Stop the timeline when a typist reaches the end of the passage

                    view.getStatusLabel().setText(t.getName() + " wins!"); // Update status label to announce the winner

                    return; // Exit the loop once a winner is declared
                }
            }

            // redraw lanes
            view.getLanesBox().getChildren().clear();

            for (Typist t : model.getTypists()) {

                int pos = t.getPosition();

                // Ensure position does not exceed passage length to avoid StringIndexOutOfBoundsException when creating the visual representation of the typist's progress
                if (pos > passage.length()) {
                    pos = passage.length(); // Ensure position does not exceed passage length
                }

                String visual = passage.substring(0, pos) + "|" + passage.substring(pos); // Add a cursor to indicate current position

                String name = t.getName();

                if (t.isBurntOut()) {
                    name += " (Burnt Out)"; // Indicate if the typist is currently burnt out
                }

                view.getLanesBox().getChildren().add(
                    new Label(name + " | " + visual) // Display the typist's name and their current progress through the passage
                );
            }
        });
        
        timeline.getKeyFrames().add(frame);

        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until a winner is declared

        timeline.play();
    }
}