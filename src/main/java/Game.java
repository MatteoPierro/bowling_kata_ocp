import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {

    private final Frames frames = new Frames();

    public void roll(int knockedDownPins) {
        frames.current().roll(knockedDownPins);

        if (frames.current().isCompleted()) {
            frames.moveToNextFrame();
        }
    }

    public int score() {
        int score = 0;
        for (int frameNumber = 0; frameNumber < frames.count(); frameNumber += 1) {
            Frame frame = frames.get(frameNumber);
            if (frame.isStrike()) {
                score += frame.score() + strikeBonus(frameNumber);
            } else if (frame.isSpare()) {
                score += frame.score() + spareBonus(frameNumber);
            } else {
                score += frame.score();
            }
        }
        return score;
    }

    private int spareBonus(int frameNumber) {
        Frame nextFrame = frames.get(frameNumber + 1);
        return nextFrame.knockedPinsInFirstRoll();
    }

    private int strikeBonus(int frameNumber) {
        Frame nextFrame = frames.get(frameNumber + 1);
        int strikeBonus = nextFrame.score();
        if (nextFrame.isStrike()) {
            nextFrame = frames.get(frameNumber + 2);
            strikeBonus += nextFrame.knockedPinsInFirstRoll();
        }
        return strikeBonus;
    }

    private class Frames {
        private final LinkedList<Frame> frames = new LinkedList<>();

        public Frames() {
            frames.add(new Frame());
        }

        private Frame get(int frameNumber) {
            return frames.get(frameNumber);
        }

        public int count() {
            return frames.size();
        }

        private void moveToNextFrame() {
            if (count() == 10) {
                return;
            }

            frames.add(new Frame());
        }

        private Frame current() {
            return frames.getLast();
        }
    }

    private class Frame {
        private static final int PINS_IN_A_FRAME = 10;
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

            return isStrike() || rolls.size() >= 2;
        }

        private boolean isSpare() {
            return score() == PINS_IN_A_FRAME;
        }

        public int knockedPinsInFirstRoll() {
            if (rolls.isEmpty()) return 0;

            return rolls.get(0);
        }

        private boolean isStrike() {
            return knockedPinsInFirstRoll() == PINS_IN_A_FRAME;
        }
    }
}
