package Part2;

public class RaceController {
    private RaceModel model;
    private RaceView view;

    public RaceController(RaceModel model, RaceView view) {
        this.model = model;
        this.view = view;
    }

    public void setup() {
        view.getStartButton().setOnAction(e -> handleStart());
        view.getPassageSelector().setOnAction(e -> handleSelection());
    }

    private void handleStart() {
        view.getStatusLabel().setText("Starting race");
        // TODO: Launch race animation and logic
    }

    private void handleSelection() {
        String choice = view.getPassageSelector().getValue();

        if (choice == null) {
            return;
        }

        switch (choice) {
            case "Short":
                model.setPassage("The quick brown fox jumps over the lazy dog.");
                break;

            case "Medium":
                model.setPassage("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
                break;

            case "Long":
                model.setPassage("In a village of La Mancha, the name of which I have no desire to call to mind, there lived not long since one of those gentlemen that keep a lance in the lance-rack, an old buckler, a lean hack, and a greyhound for coursing.");
                break;

            case "Custom":
                String custom = view.getCustomPassageInput().getText();
                if (!custom.isEmpty()) {
                    model.setPassage(custom);
                }
                break;
        }
        view.getStatusLabel().setText("Passage selected (" + model.getPassageLength() + " characters)");
    }
}