package Part2;

public class Typist {
    private String name;
    private int position = 0;

    private double speed = 1.0;
    private double accuracy = 1.0;

    public Typist(String name) {
        this.name = name;
    }

    public void moveForward() {
        position++;
    }

    public void slideBack() {
        position = Math.max(0, position - 1); // Ensure position does not go below 0
    }

    public int getPosition() {
        return position;
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

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
