package Part2;

public class RaceRecord {
    private final double wpm;
    private final double accuracy;
    private final int burnouts;
    private final int finalPosition;

    public RaceRecord(double wpm, double accuracy, int burnouts, int finalPosition) {
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.burnouts = burnouts;
        this.finalPosition = finalPosition;
    }

    public double getWpm() {
        return wpm;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public int getBurnouts() {
        return burnouts;
    }

    public int getFinalPosition() {
        return finalPosition;
    }
}