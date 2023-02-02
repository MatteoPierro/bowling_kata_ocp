import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Integer> rolls = new ArrayList<>();

    public int score() {
        int score = 0;
        for (int rollIndex = 0; rollIndex < rolls.size();) {
            score += frameScore(rollIndex);
            rollIndex += 2;
        }
        return score;
    }

    private Integer frameScore(int rollIndex) {
        Integer frameScore = rolls.get(rollIndex);
        if (rollIndex + 1 < rolls.size()) {
            frameScore += rolls.get(rollIndex + 1);
        }
        return frameScore;
    }

    public void roll(int knockedDownPins) {
        rolls.add(knockedDownPins);
    }
}
