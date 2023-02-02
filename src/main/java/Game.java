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
        return rollScore(rollIndex) + rollScore(rollIndex + 1);
    }

    private Integer rollScore(int rollIndex) {
        if (rollIndex >= rolls.size()) {
            return 0;
        }
        return rolls.get(rollIndex);
    }

    public void roll(int knockedDownPins) {
        rolls.add(knockedDownPins);
    }
}
