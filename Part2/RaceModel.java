package Part2;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RaceModel {
    
    private String passage = "";
    private int passageLength = 0;

    // List of all racers
    private List<Typist> typists = new ArrayList<>();

    // Modifiers
    private boolean autocorrect = false;
    private boolean caffeineMode = false;
    private boolean nightShift = false;

    private Map<String, Double> personalBests = new HashMap<>(); // Map to track personal best times for each typist

    private Map<String, Integer> leaderboard = new HashMap<>(); // Map to track leaderboard standings based on WPM

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

    public double getPersonalBest(String name) {
        return personalBests.getOrDefault(name, 0.0); // Return the personal best time for the typist
    }

    public boolean updatePersonalBest(String name, double wpm) {
        double currentBest = personalBests.getOrDefault(name, 0.0); // Get the current personal best time for the typist, or 0.0 if they don't have one yet

        if (wpm > currentBest) { // If the new WPM is better than the current personal best, update it
            personalBests.put(name, wpm);
            return true; // Return true to indicate that a new personal best was set
        }

        return false; // Return false to indicate that the personal best was not updated
    }

    public Map<String, Integer> getLeaderboard() {
        return leaderboard;
    }

    public void addPoints(String typistName, int points) {
        leaderboard.put(typistName, leaderboard.getOrDefault(typistName, 0) + points); // Add points to the typist's total in the leaderboard
    }
}
