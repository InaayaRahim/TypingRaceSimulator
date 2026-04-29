package Part2;

public class Typist {
    private String name;
    private double position = 0; // Position in terms of characters typed, can be a double to allow for speed adjustments
    private double speed = 1.0;


    public Typist(String name) {
        this.name = name;
    }

    public void moveForward() {
        position+= speed; // Move forward by the speed amount, which can be adjusted for different typists
    }

    public void slideBack() {
        position = Math.max(0, position - 1); // Ensure position does not go below 0
    }

    public int getPosition() {
        return (int) position; // convert to int for display purposes, but internally we can keep it as a double for more precise movement
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
