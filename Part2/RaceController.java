package Part2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.util.ArrayList;

import Part2.TypingStyle;
import Part2.KeyboardType;

import java.security.Key;
import java.util.List;

import Part2.Accessory;

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

        view.getCompareButton().setOnAction(e -> handleComparison());
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
        long startTime = System.currentTimeMillis();

        if (model.getPassage().isEmpty()) {
            view.getStatusLabel().setText("Please select a passage first.");
            return;
        }

        model.resetTypists();
        model.getTypists().clear(); // Clear existing typists before adding new ones based on seat selection

        for (Typist t : model.getTypists()) {
            t.resetForRace();
        }

        int seats = view.getSeatSelector().getValue(); // Get the number of typists from the seat selector

        // Dynamically create typists based on the number of seats selected, with random speeds to create variability among typists
        for (int i = 0; i < seats; i++) {
            PlayerConfig config = view.getPlayerConfigs().get(i); // Get the player configuration for this typist

            Typist t = new Typist("Player " + (i + 1)); // Create a new typist with a name based on the player number

            t.setTypingStyle(config.getStyle()); // Set the typing style for the typist based on the selection in the view
            t.setKeyboardType(config.getKeyboard()); // Set the keyboard type for the typist based on the selection in the view
            t.setAccessory(config.getAccessory()); // Set the accessory for the typist based on the selection in the view

            t.setSymbol(config.getSymbol()); // Set the symbol for the typist based on the input in the view
            t.setColor(config.getColor()); // Set the color for the typist based on the selection in the view

            t.setSpeed(0.7 + Math.random());

            model.addTypist(t);
        }

        updatePlayerSelectors(); // Update the player selectors in the view to reflect the newly created typists

        // Set modifiers based on the state of the checkboxes in the view
        model.setAutocorrect(view.getAutocorrectCheck().isSelected());
        model.setCaffeineMode(view.getCaffeineCheck().isSelected());
        model.setNightShift(view.getNightShiftCheck().isSelected());

        view.getStatusLabel().setText("Race started!");
        view.getResultsLabel().setText(""); // Clear previous results

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

                t.typeTick(model.isAutocorrect()); // Move each typist forward based on their speed
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
                done.setFill(t.getColor()); // Set color for typed portion

                // Cursor
                Text cursor = new Text("|"); // Cursor to indicate current position
                cursor.setFill(Color.BLACK); // Set color for cursor

                // Remaining Text
                Text remaining = new Text(passage.substring(pos)); // Text for the portion of the passage that has not been typed
                remaining.setFill(Color.LIGHTGRAY); // Set color for remaining portion

                flow.getChildren().addAll(done, cursor, remaining); // Combine the typed portion, cursor, and remaining portion into a single TextFlow for display

                String visual = passage.substring(0, pos) + "|" + passage.substring(pos); // Add a cursor to indicate current position

                String styleText = t.getTypingStyle().toString().replace("_", " ").toLowerCase(); // Get the typist's typing style as a string for display
                String keyboardText = t.getKeyboardType().toString().replace("_", " ").toLowerCase(); // Get the typist's keyboard type as a string for display
                String accessoryText = t.getAccessory().toString().replace("_", " ").toLowerCase(); // Get the typist's accessory as a string for display

                String symbol = t.getSymbol();

                if (symbol == null || symbol.isEmpty()) {
                    symbol = "🔺"; // default
                }

                String name = t.getSymbol() + " " + t.getName() + " (" + styleText + ", " + keyboardText + ", " + accessoryText + ")"; // Include the typist's name, typing style, and keyboard type in the label for display

                if (t.isBurntOut()) {
                    name += " (Burnt Out)"; // Indicate if the typist is currently burnt out
                }

                Label label = new Label(name); // Create a label for the typist's name
                label.setTextFill(t.getColor()); // Set the color of the label to match the typist's assigned color

                VBox container = new VBox(label, flow); // Create a container to hold both the name and the visual representation of the typist's progress

                view.getLanesBox().getChildren().add(container); // Add the container to the lanes box in the view to display the typist's progress
            }

            Typist winner = null;

            for (Typist t : model.getTypists()) {
                if (t.getPosition() >= passage.length()) {
                    winner = t;
                    timeline.stop(); // Stop the timeline when a typist reaches the end of the passage
                    break;
                }
            }

            if (winner != null) {
                
                List<Typist> sorted = new ArrayList<>(model.getTypists());
                sorted.sort((a, b) -> Integer.compare(b.getPosition(), a.getPosition())); // Sort typists by their position to determine the current standings

                for (int i = 0; i < sorted.size(); i++) {
                    Typist t = sorted.get(i);
                    
                    int points;

                    if (i == 0) {
                        points = 3; // First place gets 3 points
                    } else if (i == 1) {
                        points = 2; // Second place gets 2 points
                    } else if (i == 2) {
                        points = 1; // Third place gets 1 point
                    } else {
                        points = 0; // No points for others
                    }

                    if (t.isBurntOut()) {
                        points -= 1; // Burnout penalty
                    }

                    model.addPoints(t.getName(), points); // Update the leaderboard with points for each typist based on their finishing position and burnout status
                }
            }

            if (winner != null) {
                long endTime = System.currentTimeMillis();
                double minutes = (endTime - startTime) / 60000.0; // Calculate time taken in minutes

                StringBuilder results = new StringBuilder();

                results.append("Winner: ").append(winner.getName()).append("\n");

                results.append("Results:\n");

                for (Typist typist : model.getTypists()) {
                    double wpm = typist.getPosition() / 5.0 / minutes; // Calculate WPM based on the number of characters typed and time taken

                    typist.setLastWpm(wpm);

                    RaceRecord record = new RaceRecord(
                        wpm,
                        typist.getAccuracyPercentage(),
                        typist.getBurnoutCount(),
                        typist.getPosition()
                    );

                    typist.addRaceRecord(record);

                    boolean isPB = model.updatePersonalBest(typist.getName(), wpm); // Update personal best for the typist and check if it's a new record
                    double best = model.getPersonalBest(typist.getName()); // Get the current personal best for the typist

                    StringBuilder line = new StringBuilder();

                    if (typist == winner) {
                        line.append("🏆 ");
                    }

                    line.append(typist.getName())
                            .append(" | WPM: ").append(String.format("%.1f", wpm));

                    if (isPB) {
                        line.append(" (PB!)"); // Indicate if the typist achieved a new personal best
                    } else {
                        line.append(" (Best: ").append(String.format("%.1f", best)).append(" )"); // Display the current personal best if it exists
                    }

                    line.append(" | Acc: ")
                                .append(String.format("%.1f%%", typist.getAccuracyPercentage()))
                                .append(" | Burnouts: ")
                                .append(typist.getBurnoutCount())
                                .append(" | Accuracy Change: ")
                                .append(String.format("%.2f", typist.getAccuracyChange()));

                    results.append(line).append("\n");
                }

                results.append("\nLeaderboard:\n");

                model.getLeaderboard().entrySet().stream()
                    .sorted((a, b) -> b.getValue() - a.getValue())
                    .forEach(entry -> results.append(entry.getKey() + " → " + entry.getValue() + " points\n")); // Append the current leaderboard standings to the results output

                StringBuilder historyOutput = new StringBuilder();

                for (Typist t : model.getTypists()) {
                    historyOutput.append(t.getName()).append("\n");

                    for (RaceRecord r : t.getHistory()) {
                        historyOutput.append("Race → WPM ")
                                .append(String.format("%.1f", r.getWpm()))
                                .append(" | Acc: ")
                                .append(String.format("%.1f%%", r.getAccuracy()))
                                .append(" | Burnouts: ")
                                .append(r.getBurnouts())
                                .append("\n");
                    }

                    historyOutput.append("\n");
                }

                view.getResultsLabel().setText(results.toString() + "\n\n" + historyOutput.toString()); // Update the results label in the view with the formatted results for all typists
            }
        });

        timeline.getKeyFrames().add(frame);

        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until a winner is declared

        timeline.play();
    }

    private void handleComparison() {
        String playerA = view.getPlayerASelector().getValue();
        String playerB = view.getPlayerBSelector().getValue();

        String metric = view.getMetricSelector().getValue();

        if (playerA == null || playerB == null) {
            view.getComparisonLabel().setText("Please select two players.");
            return;
        }

        Typist a = null;
        Typist b = null;

        for (Typist t : model.getTypists()) {
            if (t.getName().equals(playerA)) {
                a = t;
            } else if (t.getName().equals(playerB)) {
                b = t;
            }
        }

        if (a == null || b == null) {
            return;
        }

        String result = "";

        switch (metric) {
            case "WPM":
                result = a.getName() + ": " + String.format("%.1f", a.getLastWpm()) + " vs " + b.getName() + ": " + String.format("%.1f", b.getLastWpm());
                break;
            
            case "Accuracy":
                result = a.getName() + ": " + String.format("%.1f%%", a.getAccuracyPercentage()) + " vs " + b.getName() + ": " + String.format("%.1f%%", b.getAccuracyPercentage());
                break;

            case "Burnouts":
                result = a.getName() + ": " + a.getBurnoutCount() + " vs " + b.getName() + ": " + b.getBurnoutCount();
                break;
        }

        view.getComparisonLabel().setText(result); // Update the comparison label in the view with the results of the comparison between the two selected players based on the chosen metric
    }

    private void updatePlayerSelectors() {
        view.getPlayerASelector().getItems().clear();
        view.getPlayerBSelector().getItems().clear();

        for (Typist t : model.getTypists()) {
            view.getPlayerASelector().getItems().add(t.getName());
            view.getPlayerBSelector().getItems().add(t.getName());
        }
    }
}