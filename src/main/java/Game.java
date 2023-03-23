import java.util.ArrayList;
import java.util.List;

public class Game {

    private static final int STRIKE_SCORE = 10;
    private static final int PINS_IN_A_FRAME = 10;

    private List<Integer> rolls = new ArrayList<>();

    public int score() {
        int score = 0;
        for (int rollIndex = 0; rollIndex < rolls.size(); ) {
            if (isStrike(rollIndex)) {
                score += STRIKE_SCORE + strikeBonus(rollIndex);
                rollIndex += 1;
            } else {
                score += frameScore(rollIndex);
                rollIndex += 2;
            }
        }
        return score;
    }

    private int strikeBonus(int rollIndex) {
        return knockedDownPins(rollIndex + 1) + knockedDownPins(rollIndex + 2);
    }

    private Integer frameScore(int rollIndex) {
        Integer frameScore = baseFrameScore(rollIndex);
        if (isSpare(rollIndex)) {
            frameScore += spareBonus(rollIndex);
        }
        return frameScore;
    }

    private Integer spareBonus(int rollIndex) {
        return knockedDownPins(rollIndex + 2);
    }

    private boolean isStrike(Integer rollIndex) {
        return knockedDownPins(rollIndex) == PINS_IN_A_FRAME;
    }

    private boolean isSpare(Integer rollIndex) {
        return knockedDownPins(rollIndex) + knockedDownPins(rollIndex + 1) == PINS_IN_A_FRAME;
    }

    private Integer baseFrameScore(int rollIndex) {
        if (isFrameOpen(rollIndex)) {
            return 0;
        }
        return knockedDownPins(rollIndex) + knockedDownPins(rollIndex + 1);
    }

    private boolean isFrameOpen(int rollIndex) {
        return rollIndex + 1 >= rolls.size();
    }

    private Integer knockedDownPins(int rollIndex) {
        if (rollIndex >= rolls.size()) {
            return 0;
        }
        return rolls.get(rollIndex);
    }

    public void roll(int knockedDownPins) {
        rolls.add(knockedDownPins);
    }
}
