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
            colorPicker
        ); // Horizontal box with spacing of 10 pixels
    }
}