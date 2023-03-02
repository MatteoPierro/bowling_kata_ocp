import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GameTest {

    private final Game game = new Game();

    @Test
    void before_the_game_starts_the_score_is_0() {
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    void when_0_pins_are_knocked_down_in_the_first_frame_the_score_is_0() {
        game.roll(0);
        game.roll(0);

        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    void when_1_pin_is_knocked_down_in_the_first_frame_the_score_is_1() {
        game.roll(1);
        game.roll(0);

        assertThat(game.score()).isEqualTo(1);
    }

    @Test
    void when_a_spare_occurs_the_next_roll_is_added_as_a_bonus_of_the_first_frame_score() {
        game.roll(3);
        game.roll(7);
        game.roll(1);

        assertThat(game.score()).isEqualTo(11);
    }

    @Test
    void when_a_spare_occurs_the_first_roll_of_the_next_frame_is_added_as_a_bonus_of_the_first_frame_score() {
        game.roll(3);
        game.roll(7); // frame1: 10 + 1
        game.roll(1);
        game.roll(2); // frame2: 3

        assertThat(game.score()).isEqualTo(10 + 1 + 3);
    }

    @Test
    void strike() {
        game.roll(10); // frame1: strike
        game.roll(5);  // frame2
        game.roll(4);

        assertThat(game.score()).isEqualTo(10 + (5 + 4) + 9);
    }
}
