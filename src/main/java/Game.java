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
            if (isStrike(roll)) {
                score += STRIKE_SCORE + strikeBonus(roll);
                roll += 1;
            } else {
                score += frameScore(roll, frameNumber);
                roll += 2;
            }
            frameNumber++;
        }
        return score;
    }

    private int strikeBonus(int roll) {
        return knockedDownPinsIn(roll + 1) + knockedDownPinsIn(roll + 2);
    }

    private Integer frameScore(int roll, int frameNumber) {
        int frameScore = baseFrameScore(roll);

        assertScore(frameNumber, frameScore);

        if (isSpare(roll)) {
            frameScore += spareBonus(roll);
        }

        return frameScore;
    }

    private Integer spareBonus(int roll) {
        return knockedDownPinsIn(roll + 2);
    }

    private boolean isStrike(Integer roll) {
        return knockedDownPinsIn(roll) == PINS_IN_A_FRAME;
    }

    private boolean isSpare(Integer roll) {
        return knockedDownPinsIn(roll) + knockedDownPinsIn(roll + 1) == PINS_IN_A_FRAME;
    }

    private Integer baseFrameScore(int roll) {
        if (isFrameOpen(roll)) {
            return 0;
        }
        return knockedDownPinsIn(roll) + knockedDownPinsIn(roll + 1);
    }

    private boolean isFrameOpen(int rollIndex) {
        return rollIndex + 1 >= rolls.size();
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
        int knockedPinsSample = frames.frames.stream().mapToInt(f -> f.rolls.stream().mapToInt(Integer::intValue).sum()).sum();
        int knockedPinsControl = rolls.stream().mapToInt(Integer::intValue).sum();
        assert knockedPinsSample == knockedPinsControl : "expected " + knockedPinsSample + " to be equal to " + knockedPinsControl;
    }

    private void assertScore(int frameNumber, int frameScore) {
        int scoreVariant = frames.get(frameNumber).score();
        assert frameScore == scoreVariant : "expected " + frameScore + " to be equal to " + scoreVariant + " for frame " + (frameNumber + 1);
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
            return this.frames.get(frameNumber);
        }

        private class Frame {
            private final List<Integer> rolls = new ArrayList<>();

            public void roll(int knockedDownPins) {
                this.rolls.add(knockedDownPins);
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
        }
    }
}
