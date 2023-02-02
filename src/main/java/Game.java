import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Integer> rolls = new ArrayList<>();

    public int score() {
        int score = 0;
        for (int rollIndex = 0; rollIndex < rolls.size();) {
            score += rolls.get(rollIndex);
            if (rollIndex + 1 < rolls.size()) {
                score += rolls.get(rollIndex + 1);
            }
            rollIndex += 2;
        }
        return score;
    }

    public void roll(int knockedDownPins) {
        rolls.add(knockedDownPins);
    }
}
