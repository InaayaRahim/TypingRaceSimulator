package Part2;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RaceView {
    private VBox root = new VBox(10); // Vertical box with spacing of 10 pixels

    // UI components
    private ComboBox<String> passageSelector = new ComboBox<>();
    private TextField customInput = new TextField();

    private Button startButton = new Button("Start Race");
    private Label statusLabel = new Label("Select a passage");

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

        // Lanes area replaces single passage display to allow for multiple typists
        root.getChildren().addAll(
            statusLabel,
            passageSelector,
            customInput,
            startButton,
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
}


