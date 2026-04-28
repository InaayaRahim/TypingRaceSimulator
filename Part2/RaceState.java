package Part2;

public class RaceState {
    
    private String passageText = "default passage text";
    private int passageLength = 40;

    public void setPassageText(String text) {
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
