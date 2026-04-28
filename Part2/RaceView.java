package Part2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RaceView {
    private VBox root = new VBox(10); // Vertical box with spacing of 10 pixels

    // UI components
    private ComboBox<String> passageSelector = new ComboBox<>();
    private TextField customPassageInput = new TextField();

    private Button startButton = new Button("Start Race");
    private Label statusLabel = new Label("Select a passage");

    // Constructor takes the RaceState to allow for future updates based on state changes
    public RaceView(RaceSate state) {
        // Set up passage selector
        passageSelector.getItems().addAll(
            "Short",
            "Medium",
            "Long",
            "Custom"
        );

        // Set up custom passage input
        customPassageInput.setPromptText("Enter custom passage here");

        // Add components to the root layout
        root.getChildren().addAll(
            statusLabel,
            new Label("Select Passage:"),
            passageSelector,
            customPassageInput,
            startButton
        );
    }

    // Getters for UI components to allow the controller to interact with them
    public VBox getRoot() {
        return root;
    }
    
    public ComboBox<String> getPassageSelector() {
        return passageSelector;
    }
    
    public TextField getCustomPassageInput() {
        return customPassageInput;
    }
    
    public Button getStartButton() {
        return startButton;
    }
    
    public Label getStatusLabel() {
        return statusLabel;
    }
}


