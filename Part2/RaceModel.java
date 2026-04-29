package Part2;

import java.util.ArrayList;
import java.util.List;

public class RaceModel {
    
    private String passage = "";
    private int passageLength = 0;

    // List of all racers
    private List<Typist> typists = new ArrayList<>();

    // Modifiers
    private boolean autocorrect = false;
    private boolean caffeineMode = false;
    private boolean nightShift = false;

    // Set the passage and update the passage length and reset the current index for tracking progress
    public void setPassage(String passage) {
        this.passage = passage;
        this.passageLength = passage.length();
    }

    // Getters for passage and passage length to allow the controller to access this information
    public String getPassage() {
        return passage;
    }

    public int getPassageLength() {
        return passageLength;
    }

    // Typists
    public void addTypist(Typist t) {
        typists.add(t);
    }

    public List<Typist> getTypists() {
        return typists;
    }

    public void resetTypists() {
        for (Typist t : typists) {
            while (t.getPosition() > 0) {
                t.slideBack(autocorrect); // Slide back the typist to the start, using the autocorrect modifier to determine the penalty
            }
        }
    }

    // Modifiers

    // Autocorrect
    public void setAutocorrect(boolean value) {
        this.autocorrect = value;
    }

    public boolean isAutocorrect() {
        return autocorrect;
    }

    // Caffeine Mode
    public void setCaffeineMode(boolean value) {
        this.caffeineMode = value;
    }

    public boolean isCaffeineMode() {
        return caffeineMode;
    }

    // Night Shift
    public void setNightShift(boolean value) {
        this.nightShift = value;
    }

    public boolean isNightShift() {
        return nightShift;
    }
}
