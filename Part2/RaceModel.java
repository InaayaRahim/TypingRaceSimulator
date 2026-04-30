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

    public void updateTitles(List<Typist> typists, Typist winner) {
        for (Typist t : typists) {
            // Win Tracking
            if (t == winner) {
                t.setConsecutiveWins(t.getConsecutiveWins() + 1); // Increment the consecutive wins for the winner
                t.setTotalWins(t.getTotalWins() + 1); // Increment the total wins for the winner
            } else {
                t.setConsecutiveWins(0); // Reset the consecutive wins for the other typists
            }

            // Title Assignment
            if (t.getConsecutiveWins() >= 3) {
                t.setTitle("Speed Demon"); 
            } else if (t.getRacesWithoutBurnout() >= 5) {
                t.setTitle("Iron Fingers");  
            } else if (t.getTotalWins() >= 5) {
                t.setTitle("Veteran Typis");
            } else {
                t.setTitle("Rookie"); // Assign the "Rookie" title to all other typists
            }
        }
    }

    public void updateRanks(List<Typist> typists) {
        for (Typist t : typists) {
            int points = leaderboard.getOrDefault(t.getName(), 0); // Get the total points for the typist from the leaderboard, or 0 if they don't have any points

            if (points >= 20) {
                t.setRank("Diamond");
                t.setAccuracyModifier(1.1); // Increase accuracy by 10% for Diamond rank
            }
            else if (points >= 12) {
                t.setRank("Gold");
                t.setAccuracyModifier(1.05); // Increase accuracy by 5% for Gold rank
            }
            else if (points >= 6) {
                t.setRank("Silver");
                t.setAccuracyModifier(1.0); // No accuracy modifier for Silver rank
            }
            else {
                t.setRank("Bronze");
                t.setAccuracyModifier(0.95); // Decrease accuracy by 5% for Bronze rank
            }
        }
    }
}
