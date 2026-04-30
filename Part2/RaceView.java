package Part2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import java.util.List;
import java.util.ArrayList;

import Part2.TypingStyle;
import Part2.Accessory;
import Part2.KeyboardType;

public class RaceView {
    private VBox root = new VBox(10); // Vertical box with spacing of 10 pixels

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

        rebuildPlayerConfigs(seatSelector.getValue()); // Initialize player configurations based on default number of typists

        seatSelector.setOnAction(e -> {
            rebuildPlayerConfigs(seatSelector.getValue()); // Rebuild player configurations when the number of typists changes
        });

        passageDisplay.setWrapText(true); // Allow passage text to wrap within the label

        // Lanes area replaces single passage display to allow for multiple typists
        root.getChildren().addAll(
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
            lanesBox
        );
    }

    private void rebuildPlayerConfigs(int seats) {
        playerConfigs.clear(); // Clear existing configurations
        playerConfigBox.getChildren().clear(); // Clear existing UI components

        for (int i = 1; i <= seats; i++) {
            PlayerConfig config = new PlayerConfig(i); // Create a new player configuration for each typist
            playerConfigs.add(config); // Add the configuration to the list

            playerConfigBox.getChildren().add(
                new Label("Player " + i)
            ); // Add the configuration UI to the view

            playerConfigBox.getChildren().add(config.getUI()); // Add the configuration UI to the view
        }
    }

    // Getters for UI components to allow the controller to interact with them
    public VBox getRoot() {
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
}


