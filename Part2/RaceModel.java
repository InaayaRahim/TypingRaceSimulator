package Part2;

public class RaceModel {
    
    private String passageText = "default passage text";
    private int passageLength = 40;

    public void setPassage(String text) {
        this.passageText = text;
        this.passageLength = text.length();
    }

    public String getPassageText() {
        return passageText;
    }

    public int getPassageLength() {
        return passageLength;
    }
}
