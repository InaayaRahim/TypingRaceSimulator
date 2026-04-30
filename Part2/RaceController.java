package Part2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import Part2.TypingStyle;

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
        System.out.println("START PRESSED");

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

            switch (i % 4) { // Assign different typing styles based on the typist's index to create diversity in typing behavior
                case 0:
                    t.setTypingStyle(TypingStyle.TOUCH_TYPIST);
                    break;
                case 1:
                    t.setTypingStyle(TypingStyle.HUNT_AND_PECK);
                    break;
                case 2:
                    t.setTypingStyle(TypingStyle.PHONE_THUMBS);
                    break;
                case 3:
                    t.setTypingStyle(TypingStyle.VOICE_TO_TEXT);
                    break;
            }

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

            if (passage == null || passage.isEmpty()) {
                view.getStatusLabel().setText("Please select a passage first.");
                return; 
            }

            for (Typist t : model.getTypists()) {

                double baseSpeed = t.getBaseSpeed();

                double currentSpeed = baseSpeed;

                // Apply modifiers to speed based on the model's settings

                // Night Shift Modifier
                if (model.isNightShift()) {
                    currentSpeed *= 0.85; // Reduce speed by 15% during night shift
                }

                // Caffeine Mode Modifier
                if (model.isCaffeineMode() && tick[0] <= 10){
                    currentSpeed *= 1.5; // Increase speed by 50% for the first 10 ticks when caffeine mode is active
                }

                t.setCurrentSpeed(currentSpeed); // Update the typist's speed based on modifiers

                // Burnout System
                t.updateBurnout(); // Update burnout status for each typist

                t.typeTick(model.isAutocorrect());; // Move each typist forward based on their speed
            }

            // redraw lanes
            view.getLanesBox().getChildren().clear();

            for (Typist t : model.getTypists()) {

                int pos = Math.min(t.getPosition(), passage.length());

                // Ensure position does not exceed passage length to avoid StringIndexOutOfBoundsException when creating the visual representation of the typist's progress
                if (pos > passage.length()) {
                    pos = passage.length(); // Ensure position does not exceed passage length
                }

                TextFlow flow = new TextFlow();

                Text done = new Text(passage.substring(0, pos)); // Text for the portion of the passage that has been typed
                done.setFill(Color.HOTPINK); // Set color for typed portion

                // Cursor
                Text cursor = new Text("|"); // Cursor to indicate current position
                cursor.setFill(Color.BLACK); // Set color for cursor

                // Remaining Text
                Text remaining = new Text(passage.substring(pos)); // Text for the portion of the passage that has not been typed
                remaining.setFill(Color.LIGHTGRAY); // Set color for remaining portion

                flow.getChildren().addAll(done, cursor, remaining); // Combine the typed portion, cursor, and remaining portion into a single TextFlow for display

                String visual = passage.substring(0, pos) + "|" + passage.substring(pos); // Add a cursor to indicate current position

                String name = t.getName() + " (" + t.getTypingStyle() + ")"; // Include the typist's name and typing style in the label

                if (t.isBurntOut()) {
                    name += " (Burnt Out)"; // Indicate if the typist is currently burnt out
                }

                Label label = new Label(name); // Create a label for the typist's name
                VBox container = new VBox(label, flow); // Create a container to hold both the name and the visual representation of the typist's progress

                view.getLanesBox().getChildren().add(container); // Add the container to the lanes box in the view to display the typist's progress
            }

            for (Typist t : model.getTypists()) {
                if (t.getPosition() >= passage.length()) {
                    timeline.stop(); // Stop the timeline when a typist reaches the end of the passage

                    view.getStatusLabel().setText(t.getName() + " wins!"); // Update status label to announce the winner

                    break; // Break instead of return to ensure the timeline is properly stopped and the UI updates with the winner announcement
                }
            }
        });

        timeline.getKeyFrames().add(frame);

        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until a winner is declared

        timeline.play();
    }
}