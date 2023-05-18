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
    void when_a_strike_occurs_the_next_two_rolls_are_added_as_a_bonus() {
        game.roll(10); // frame1: strike
        game.roll(10); // frame2: strike
        game.roll(5);  // frame3
        game.roll(4);

        assertThat(game.score()).isEqualTo(10 + (10 + 5) + 10 + (5 + 4) + 9);
    }

    @Test
    void knocking_down_one_pin_each_roll() {
        for (int i = 0; i < 20; i++) {
            game.roll(1);
        }

        assertThat(game.score()).isEqualTo(20);
    }

    @Test
    void playing_a_realistic_full_game() {
        game.roll(10); // frame1: strike
        game.roll(9);  // frame2: spare
        game.roll(1);
        game.roll(5);  // frame3: spare
        game.roll(5);
        game.roll(7);  // frame4
        game.roll(2);
        game.roll(10); // frame5: strike
        game.roll(10); // frame6: strike
        game.roll(10); // frame7: strike
        game.roll(9);  // frame8
        game.roll(0);
        game.roll(8);  // frame9: spare
        game.roll(2);
        game.roll(9);  // frame10: spare
        game.roll(1);
        game.roll(10); // frame10: bonus additional shot

        assertThat(game.score()).isEqualTo(187);
    }
}
