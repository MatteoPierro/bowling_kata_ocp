import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private static final int STRIKE_SCORE = 10;
    private static final int PINS_IN_A_FRAME = 10;

    private final List<Integer> rolls = new ArrayList<>();
    private final Frames frames = new Frames();

    public int score() {
        int score = 0;
        int frameNumber = 0;
        for (int roll = 0; roll < rolls.size(); ) {
            Frame frame = frames.get(frameNumber);
            if (isStrike(roll)) {
                score += STRIKE_SCORE + strikeBonus(roll);
                roll += 1;
            } else if (frame.isSpare()) {
                score += frame.score() + spareBonus(frameNumber);
                roll += 2;
            } else {
                score += frame.score();
                roll += 2;
            }
            frameNumber++;
        }
        return score;
    }

    private int spareBonus(int frameNumber) {
        Frame nextFrame = frames.get(frameNumber + 1);
        return nextFrame.knockedPinsInFirstRoll();
    }

    private int strikeBonus(int roll) {
        return knockedDownPinsIn(roll + 1) + knockedDownPinsIn(roll + 2);
    }

    private boolean isStrike(Integer roll) {
        return knockedDownPinsIn(roll) == PINS_IN_A_FRAME;
    }

    private Integer knockedDownPinsIn(int roll) {
        if (roll >= rolls.size()) {
            return 0;
        }
        return rolls.get(roll);
    }

    public void roll(int knockedDownPins) {
        frames.current().roll(knockedDownPins);
        rolls.add(knockedDownPins);

        assertKnockedPins();
    }

    private void assertKnockedPins() {
        int knockedPinsSample = frames.allKnockedPins();
        int knockedPinsControl = rolls.stream().mapToInt(Integer::intValue).sum();
        assert knockedPinsSample == knockedPinsControl : "expected " + knockedPinsSample + " to be equal to " + knockedPinsControl;
    }

    private class Frames {
        private final LinkedList<Frame> frames = new LinkedList<>();

        public Frames() {
            frames.add(new Frame());
        }

        public Frame current() {
            Frame last = frames.getLast();

            if (last.isCompleted()) {
                frames.add(new Frame());
            }

            return frames.getLast();
        }

        private Frame get(int frameNumber) {
            return frames.get(frameNumber);
        }

        private int allKnockedPins() {
            return frames.stream().mapToInt(f -> f.knockedPins()).sum();
        }

    }

    private class Frame {
        private final List<Integer> rolls = new ArrayList<>();

        public void roll(int knockedDownPins) {
            rolls.add(knockedDownPins);
        }

        public int score() {
            if (!isCompleted()) {
                return 0;
            }

            return rolls.stream().mapToInt(Integer::intValue).sum();
        }

        public boolean isCompleted() {
            if (rolls.isEmpty()) {
                return false;
            }

            return rolls.get(0) == 10 || rolls.size() == 2;
        }

        private int knockedPins() {
            return rolls.stream().mapToInt(Integer::intValue).sum();
        }

        private boolean isSpare() {
            return knockedPins() == PINS_IN_A_FRAME;
        }

        public int knockedPinsInFirstRoll() {
            return rolls.get(0);
        }
    }
}
