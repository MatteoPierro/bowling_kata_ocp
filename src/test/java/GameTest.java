import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class GameTest {

    @Test
    void before_the_game_starts_the_score_is_0() {
        Game game = new Game();
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    void when_0_pins_are_knocked_down_in_the_first_frame_the_score_is_0() {
        Game game = new Game();

        game.roll(0);
        game.roll(0);

        assertThat(game.score()).isEqualTo(0);
    }
}
