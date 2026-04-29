package Part2;

public class RaceModel {
    
    private String passage = "";
    private int passageLength = 0;

    private int currentIndex = 0;

    // Set the passage and update the passage length and reset the current index for tracking progress
    public void setPassage(String passage) {
        this.passage = passage;
        this.passageLength = passage.length();
        this.currentIndex = 0; // Reset index when a new passage is set
    }

    // Getters for passage and passage length to allow the controller to access this information
    public String getPassage() {
        return passage;
    }

    public int getPassageLength() {
        return passageLength;
    }

    // Reset the progress to the beginning of the passage
    public void resetProgress() {
        this.currentIndex = 0;
    }

    // Advance the current index to simulate typing progress, ensuring it does not exceed the passage length
    public void advance() {
        if (currentIndex < passageLength) {
            currentIndex++;
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }
}
