package Part2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

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

    private Label passageDisplay = new Label(); // Label to display the passage, replaced by lanesBox for multiple typists

    private VBox lanesBox = new VBox(5); // Container for typist lanes

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

        // Lanes area replaces single passage display to allow for multiple typists
        root.getChildren().addAll(
            statusLabel,
            passageSelector,
            customInput,

            new Label("Number of Typists:"),
            seatSelector,

            autocorrectCheck,
            caffeineCheck,
            nightShiftCheck,

            startButton,

            passageDisplay,

            lanesBox
        );
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
}


