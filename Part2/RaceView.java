package Part2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;

import Part2.TypingStyle;
import Part2.Accessory;
import Part2.KeyboardType;

public class RaceView {
    private VBox rootContent = new VBox(10); // Main container for the view
    private ScrollPane root = new ScrollPane(rootContent); // Scrollable root to accommodate all UI components

    // UI components
    private ComboBox<String> passageSelector = new ComboBox<>();
    private TextField customInput = new TextField();

    // Seat selector for multiple typists 
    private ComboBox<Integer> seatSelector = new ComboBox<>();

    // Modifier checkboxes
    private CheckBox autocorrectCheck = new CheckBox("Autocorrect");
    private CheckBox caffeineCheck = new CheckBox("Caffeine Mode");
    private CheckBox nightShiftCheck = new CheckBox("Night Shift");

    private Button startButton = new Button("Start Race");
    private Label statusLabel = new Label("Select a passage");

    private VBox lanesBox = new VBox(5); // Container for typist lanes

    private VBox playerConfigBox = new VBox(8); // Container for player configuration options
    private List<PlayerConfig> playerConfigs = new ArrayList<>(); // List to hold player configuration objects

    private Label passageDisplay = new Label(); // Label to display the passage, replaced by lanesBox for multiple typists

    private Label resultsLabel = new Label();

    private ComboBox<String> metricSelector = new ComboBox<>(); // ComboBox for selecting the metric to display in results
    private ComboBox<String> playerASelector = new ComboBox<>(); // ComboBox for selecting Player A in results
    private ComboBox<String> playerBSelector = new ComboBox<>(); // ComboBox for selecting Player B in results
    private Button compareButton = new Button("Compare Players"); // Button to trigger player comparison in results
    private Label comparisonLabel = new Label(); // Label to display the results of player comparison

    // Constructor takes the RaceState to allow for future updates based on state changes
    public RaceView(RaceModel model) {
        // Set up passage selector
        passageSelector.getItems().addAll(
            "Short",
            "Medium",
            "Long",
            "Custom"
        );

        // Set up custom passage input
        customInput.setPromptText("Enter custom passage here");

        // Set up seat selector for multiple typists, allowing selection from 2 to 6 typists
        seatSelector.getItems().addAll(2, 3, 4, 5, 6); 
        seatSelector.setValue(3); // Default to 3 typists

        passageDisplay.setWrapText(true); // Allow passage text to wrap within the label

        seatSelector.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                rebuildPlayerConfigs(newVal); // Rebuild player configurations when the number of typists changes
            }
        });

        rebuildPlayerConfigs(seatSelector.getValue()); // Initial build of player configurations based on default number of typists

        resultsLabel.setWrapText(true); // Allow results text to wrap within the label

        metricSelector.getItems().addAll("WPM", "Accuracy", "Burnouts");
        metricSelector.setValue("WPM"); // Default metric to display in results

        // Lanes area replaces single passage display to allow for multiple typists
        rootContent.getChildren().addAll(
            statusLabel,
            passageSelector,
            customInput,

            new Label("Number of Typists:"),
            seatSelector,

            new Label("Player Customisation:"),
            playerConfigBox, // Add player configuration options to the view

            autocorrectCheck,
            caffeineCheck,
            nightShiftCheck,

            startButton,
            passageDisplay,
            lanesBox,

            resultsLabel,

            new Label("Results Comparison:"),
            playerASelector,
            playerBSelector,
            metricSelector,
            compareButton,
            comparisonLabel
        );
    }

    private void rebuildPlayerConfigs(int seats) {
        playerConfigs.clear(); // Clear existing configurations
        playerConfigBox.getChildren().clear(); // Clear existing UI components

        for (int i = 0; i < seats; i++) {
            PlayerConfig config = new PlayerConfig(i + 1); // Create a new player configuration for each typist
            playerConfigs.add(config); // Add the configuration to the list

            VBox wrapper = new VBox(5); // Wrapper to hold the configuration UI for better spacing

            Label label = new Label("Player " + (i + 1) + " Configuration");

            wrapper.getChildren().addAll(label, config.getUI()); // Add the label and the configuration UI to the wrapper

            playerConfigBox.getChildren().add(wrapper); // Add the wrapper to the player configuration box in the view
        }
    }

    // Getters for UI components to allow the controller to interact with them
    public ScrollPane getRoot() {
        return root;
    }
    
    public ComboBox<String> getPassageSelector() {
        return passageSelector;
    }
    
    public TextField getCustomInput() {
        return customInput;
    }
    
    public Button getStartButton() {
        return startButton;
    }
    
    public Label getStatusLabel() {
        return statusLabel;
    }

    public VBox getLanesBox() {
        return lanesBox;
    }

    public Label getPassageDisplay() {
        return passageDisplay;
    }

    public CheckBox getAutocorrectCheck() {
        return autocorrectCheck;
    }

    public CheckBox getCaffeineCheck() {
        return caffeineCheck;
    }

    public CheckBox getNightShiftCheck() {
        return nightShiftCheck;
    }

    public ComboBox<Integer> getSeatSelector() {
        return seatSelector;
    }

    public List<PlayerConfig> getPlayerConfigs() {
        return playerConfigs;
    }

    public Label getResultsLabel() {
        return resultsLabel;
    }

    public ComboBox<String> getMetricSelector() {
        return metricSelector;
    }

    public ComboBox<String> getPlayerASelector() {
        return playerASelector;
    }

    public ComboBox<String> getPlayerBSelector() {
        return playerBSelector;
    }

    public Button getCompareButton() {
        return compareButton;
    }

    public Label getComparisonLabel() {
        return comparisonLabel;
    }
}


