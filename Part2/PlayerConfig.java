package Part2;

import javafx.scene.control.*;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlayerConfig {
    private ComboBox<TypingStyle> styleBox = new ComboBox<>();
    private ComboBox<KeyboardType> keyboardBox = new ComboBox<>();
    private ComboBox<Accessory> accessoryBox = new ComboBox<>();

    private TextField symbolField = new TextField("🔺");
    private ColorPicker colorPicker = new ColorPicker(Color.WHITE);

    private Label statsLabel = new Label();

    public PlayerConfig(int playerNumber) {
        styleBox.getItems().addAll(TypingStyle.values());
        styleBox.setValue(TypingStyle.TOUCH_TYPIST); // Default typing style

        keyboardBox.getItems().addAll(KeyboardType.values());
        keyboardBox.setValue(KeyboardType.MEMBRANE); // Default keyboard type

        accessoryBox.getItems().addAll(Accessory.values());
        accessoryBox.setValue(Accessory.NONE); // Default accessory

        styleBox.setPrefWidth(120);
        keyboardBox.setPrefWidth(120);
        accessoryBox.setPrefWidth(120);
        symbolField.setPrefWidth(60);

        symbolField.setPromptText("Symbol");

        statsLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        styleBox.setOnAction(e -> updateStats());
        keyboardBox.setOnAction(e -> updateStats());
        accessoryBox.setOnAction(e -> updateStats());

        updateStats(); // Initial stats update

    }

    private void updateStats() {
        double speed = 1.0;
        double accuracy = 1.0;
        double burnout = 1.0;

        // Apply modifiers based on selected typing style
        switch (getStyle()) {
            case TOUCH_TYPIST:
                accuracy *= 1.05;
                break;
            case HUNT_AND_PECK:
                accuracy *= 0.85;
                burnout *= 1.3;
                break;
            case PHONE_THUMBS:
                accuracy *= 0.95;
                burnout *= 1.1;
                break;
            case VOICE_TO_TEXT:
                accuracy *= 1.1;
                burnout *= 0.7;
                break;
        }

        // Apply modifiers based on selected keyboard type
        switch (getKeyboard()) {
            case MECHANICAL:
                speed *= 1.2;
                accuracy *= 1.05;
                break;
            case MEMBRANE:
                break; // Baseline, no changes
            case TOUCHSCREEN:
                accuracy *= 0.85;
                break;
            case STENOGRAPHY:
                speed *= 1.5;
                accuracy *= 0.95;
                break;
        }

        // Apply modifiers based on selected accessory
        switch (getAccessory()) {
            case WRIST_SUPPORT:
                burnout *= 0.7;
                break;
            case ENERGY_DRINK:
                accuracy *= 1.03;
                break;
            case HEADPHONES:
                accuracy *= 1.05;
                break;
        }

        String text = String.format(
            "Speed: %.0f%% | Accuracy: %.0f%% | Burnout: %.0f%%",
            speed * 100,
            accuracy * 100,
            burnout * 100
        );

        statsLabel.setText(text);
    }

    public TypingStyle getStyle() {
        return styleBox.getValue();
    }

    public KeyboardType getKeyboard() {
        return keyboardBox.getValue();
    }

    public Accessory getAccessory() {
        return accessoryBox.getValue();
    }

    public String getSymbol() {
        return symbolField.getText();
    }

    public Color getColor() {
        return colorPicker.getValue();
    }

    public HBox getUI() {
        return new HBox(10, 
            styleBox, 
            keyboardBox, 
            accessoryBox,
            symbolField,
            colorPicker,
            statsLabel
        ); // Horizontal box with spacing of 10 pixels
    }
}