import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Integer> rolls = new ArrayList<>();

    public int score() {
        return rolls.stream().mapToInt(Integer::intValue).sum();
    }

    public void roll(int knockedDownPins) {
        rolls.add(knockedDownPins);
    }
}
