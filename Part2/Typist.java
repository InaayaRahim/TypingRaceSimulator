package Part2;

import Part2.TypingStyle;

public class Typist {
    private String name;
    private double position = 0; // Position in terms of characters typed, can be a double to allow for speed adjustments
    
    private double baseSpeed = 1.0; // Permanent speed
    private double speed = 1.0; // Current speed, which can be modified by different modes (e.g., caffeine, night shift)

    private double accuracy = 0.9; // default accuracy, can be adjusted for different typists and can affect the chance of sliding back due to typos

    private boolean burntOut = false; // Flag to indicate if the typist has burnt out
    private int burnoutTimer = 0; // Timer to track how long the typist has been burning out

    private TypingStyle style = TypingStyle.TOUCH_TYPIST; // Default typing style, can be set to different styles for different typists
    private KeyboardType keyboard = KeyboardType.MEMBRANE; // Default keyboard type, can be set to different types for different typists
    private Accessory accessory = Accessory.NONE; // Default accessory, can be set to different accessories for different typists

    public Typist(String name) {
        this.name = name;
    }

    public void typeTick(boolean autocorrectEnabled) {
        if (burntOut) {
            updateBurnout(); // If the typist is burnt out, we need to update the burnout status and skip moving forward
            return;
        }

        double effectiveAccuracy = accuracy; // Start with the base accuracy

        if (accessory == Accessory.HEADPHONES) {
            effectiveAccuracy += 0.05; 
        }

        if (accessory == Accessory.ENERGY_DRINK) {
            effectiveAccuracy += 0.03; 
        }

        effectiveAccuracy = Math.min(1.0, effectiveAccuracy); // Ensure accuracy does not exceed 100%

        if (Math.random() < effectiveAccuracy) { // If the typist types correctly based on their accuracy
            position += speed; // Move forward by the current speed
        } else {
            slideBack(autocorrectEnabled); // If they make a typo, they slide back, and the penalty is reduced if autocorrect is enabled
        }
    }

    public void slideBack(boolean autocorrectEnabled) {
        double penalty = autocorrectEnabled ? 0.5 : 1.0; // If autocorrect is enabled, the penalty for sliding back is reduced by half

        position = Math.max(0, position - penalty); // Ensure position does not go below 0
    }

    public void updateBurnout() {
        if (burntOut) {
            burnoutTimer--;

            if (burnoutTimer <= 0) {
                burntOut = false; // Typist recovers from burnout after the timer runs out
            }

            return; // If the typist is currently burnt out, they cannot move forward
        }

        double burnoutChance = 0.02 * getBurnoutModifier(); // Base burnout chance is 2%, modified by the typist's burnout modifier

        if (accessory == Accessory.WRIST_SUPPORT) {
            burnoutChance *= 0.7; // Wrist support can reduce the chance of burnout by 30% 
        }

        // Random burnout trigger
        if (Math.random() < 0.02 * getBurnoutModifier()) { // 2% chance to burn out each tick, modified by the typist's burnout modifier
            burntOut = true;
            burnoutTimer = 10; // Burnout lasts for 10 ticks
        }
    }

    public double getBurnoutModifier() {
        switch (style) {
            case TOUCH_TYPIST:
                return 1.0; // Standard burnout chance for touch typists
            case HUNT_AND_PECK:
                return 1.3; // Higher burnout chance for hunt and peck typists due to less efficient typing style
            case PHONE_THUMBS:
                return 1.1; // Slightly higher burnout chance for phone thumbs users due to the awkward typing position and smaller keys
            case VOICE_TO_TEXT:
                return 0.7; // Lower burnout chance for voice to text users since they are not physically typing
            default:
                return 1.0; // Default burnout modifier
        }
    }

    public void setTypingStyle(TypingStyle style) {
        this.style = style; // Set the typing style for the typist, which can affect their burnout chance and potentially other factors in the future

        switch (style) {
            case TOUCH_TYPIST:
                this.accuracy = 0.95; // Touch typists have high accuracy
                break;
            case HUNT_AND_PECK:
                this.accuracy = 0.75; // Hunt and peck typists have lower accuracy
                break;
            case PHONE_THUMBS:
                this.accuracy = 0.85; // Phone thumbs users have moderate accuracy
                break;
            case VOICE_TO_TEXT:
                this.accuracy = 0.98; // Voice to text users have high accuracy, but can still make mistakes due to misrecognition
                break;
        }
    }

    public TypingStyle getTypingStyle() {
        return style; // Get the current typing style of the typist, which can be used to determine their burnout modifier and other characteristics
    }

    public void setKeyboardType(KeyboardType keyboard) {
        this.keyboard = keyboard; // Set the keyboard type for the typist, which can affect their speed and accuracy in future iterations

        switch (keyboard) {
            case MECHANICAL:
                baseSpeed *= 1.2; 
                accuracy *= 1.05; 
                break;
            case MEMBRANE:
                break; // Membrane keyboards are the baseline, so no changes to speed or accuracy
            case TOUCHSCREEN:
                accuracy *= 0.85; 
                break;
            case STENOGRAPHY:
                baseSpeed *= 1.5; 
                accuracy *= 0.95; 
                break;
        }
    }

    public KeyboardType getKeyboardType() {
        return keyboard; 
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory; // Set the accessory for the typist, which can affect their speed, accuracy, and burnout chance in future iterations
    }

    public Accessory getAccessory() {
        return accessory; 
    }

    public int getPosition() {
        return (int) Math.floor(position); // convert to int for display purposes, but internally we can keep it as a double for more precise movement
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.baseSpeed = speed; 
        this.speed = speed;
    }

    public void setCurrentSpeed(double speed) {
        this.speed = speed; // Set the current speed without changing the base speed, allowing for temporary modifiers
    }

    public double getBaseSpeed() {
        return baseSpeed; // Get the permanent base speed of the typist, which can be used to reset speed after modifiers wear off
    }

    public boolean isBurntOut() {
        return burntOut;
    }

    public double getAccuracy() {
        return accuracy;
    }
}
