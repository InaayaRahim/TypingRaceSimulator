# TypingRaceSimulator

Object Oriented Programming Project — ECS414U

## Project Structure

```
TypingRaceSimulator/
├── Part1/    # Textual simulation (Java, command-line)
├── Part2/    # GUI simulation (JavaFX)
└── .git/     # Git repository (hidden)
```

## Part 1 — Textual Simulation

### How to compile

```bash
cd Part1
javac Typist.java TypingRace.java
```

### How to run

The race is started by calling `startRace()` on a `TypingRace` object.

A simple way to test this is to add a `main` method to `TypingRace`. An example test setup can be added in TypingRace.java:

```java
public static void main(String[] args) {
    TypingRace race = new TypingRace(40);
    race.addTypist(new Typist('①', "TURBOFINGERS", 0.85), 1);
    race.addTypist(new Typist('②', "QWERTY_QUEEN",  0.60), 2);
    race.addTypist(new Typist('③', "HUNT_N_PECK",   0.30), 3);
    race.startRace();
}
```

Then run:

```bash
java TypingRace
```

## Part 2 — GUI Simulation (JavaFX)

### How to compile

From the project root:

```bash
javac --module-path /path/to/javafx-sdk/lib \
--add-modules javafx.controls \
-d bin \
Part2/*.java
```

### How to run

```bash
java --module-path /path/to/javafx-sdk/lib \
--add-modules javafx.controls \
-cp bin Part2.MainGUI
```
### GUI Entry Point

The GUI can be launched using:
- Part2.MainGUI
- Or via the method:

```java
MainGUI.startRaceGUI(args)
```

### Features

Race Configuration
- Select predefined passages (short / medium / long)
- Custom passage input
- Adjustable number of players (2-6)
- Modifiers:
    - Autocorrect 
    - Caffeine Mode
    - Night Shift

Custom Typists
- Typing styles:
    - Touch Typist
    - Hunt & Peck
    - Phone Thumbs
    - Voice-to-Text
- Keyboard types: 
    - Mechanical
    - Membrane
    - Touchscreen
    - Stenography
- Custom symbols and colours
- Accessories affecting performance:
    - Wrist Support
    - Energy Drink
    - Headphones
- Attribution impact specified for each customisation choice

Statistics System
- WPM calculation per race
- Accuracy tracking
- Burnout tracking
- Personal best WPM storage
- Full race history per typist

Leaderboard System (Option A)
- Points based on finishing position
- Burnout penalty system
- Cumulative leaderboard across races
- Titles:
    - Speed Demon
    - Iron Fingers
    - Veteran Typist
    - Rookie

Comparison System
- Compare two players by:
    - WPM
    - Accuracy
    - Burnouts

Optional Analytics
- Race history tracking
- Performance trends
- WPM progression over time

## Dependencies

- Java Development Kit (JDK) 11 or higher
- JavaFX SDK (for Part 2 only)

## Notes

- All code is designed to run using command-line tools.
- No IDE-specific configuration is required.
- Part 2 builds a full GUI simulation on top of Part 1 mechanics.
- The GUI is launched via MainGUI, with an additional startRaceGUI() method included for specification compliance.

## Git Workflow

- Main branch contains stable code
- GUI development was completed on the `gui-development` branch
- Final version merged into main
- Full .git history included in submission
