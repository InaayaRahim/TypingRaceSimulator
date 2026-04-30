package Part2;

public class Typist {
    private String name;
    private double position = 0; // Position in terms of characters typed, can be a double to allow for speed adjustments
    
    private double baseSpeed = 1.0; // Permanent speed
    private double speed = 1.0; // Current speed, which can be modified by different modes (e.g., caffeine, night shift)

    private boolean burntOut = false; // Flag to indicate if the typist has burnt out
    private int burnoutTimer = 0; // Timer to track how long the typist has been burning out

    private TypingStyle style = TypingStyle.TOUCH_TYPIST; // Default typing style, can be set to different styles for different typists

    public Typist(String name) {
        this.name = name;
    }

    public void moveForward() {
        if (burntOut) {
            return; // If the typist is burnt out, they cannot move forward
        }

        position+= speed; // Move forward by the speed amount, which can be adjusted for different typists
    }

    public void slideBack(boolean autocorrectEnabled) {
        double penalty = autocorrectEnabled ? 0.5 : 1.0; // If autocorrect is enabled, the penalty for sliding back is reduced by half

        position = Math.max(0, position - 1); // Ensure position does not go below 0
    }

    public void updateBurnout() {
        if (burntOut) {
            burnoutTimer--;

            if (burnoutTimer <= 0) {
                burntOut = false; // Typist recovers from burnout after the timer runs out
            }

            return; // If the typist is currently burnt out, they cannot move forward
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
}
