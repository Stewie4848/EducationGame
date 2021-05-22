package com.example.educationgame;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void scoring_isCorrect() {
        Game game = new Game();
        game.difficulty = 2;
        game.setSkipTotal(5);
        game.addScore();
        assertEquals(200, game.score);
        game.removeScore();

        game.difficulty = 3;
        game.setSkipTotal(0);

        game.addScore();
        assertEquals(1800, game.score);
        assertEquals("1800", game.getScore());
    }

    @Test
    public void time_isCorrect() {
        Game game = new Game();
        game.setTime(10);
        assertEquals(10, game.time);
        assertEquals(10, game.seconds);
        assertEquals("10", game.getSeconds());
    }

}