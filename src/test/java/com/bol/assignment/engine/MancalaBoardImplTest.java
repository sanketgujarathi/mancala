package com.bol.assignment.engine;

import com.bol.assignment.domain.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class MancalaBoardImplTest {

    private MancalaBoardImpl mancalaBoard;

    @Mock
    private Player player1;

    @Mock
    private Player player2;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mancalaBoard = new MancalaBoardImpl(6, 6);
        mancalaBoard.init(player1, player2);
    }

    @Test
    public void test_board_gets_intitialized_correctly() {
        int[] afterInit = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        assertThat(mancalaBoard.board, is(afterInit));
        assertThat(mancalaBoard.player1, is(player1));
        assertThat(mancalaBoard.player2, is(player2));
        assertNotNull(mancalaBoard.playAreaMap);
        assertTrue(mancalaBoard.playAreaMap.containsKey(player1));
        assertTrue(mancalaBoard.playAreaMap.containsKey(player2));
    }

    @Test
    public void when_last_stone_lands_in_own_pit() {
        int[] beforeMove = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        int[] afterMove = {0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 1);
        assertThat(mancalaBoard.board, is(afterMove));
        Assert.assertTrue(player.isPresent());
        assertThat(player.get(), is(player1));
    }

    @Test
    public void when_last_stone_lands_in_opponents_pit() {
        int[] beforeMove = {6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0};
        int[] afterMove = {6, 6, 6, 6, 6, 0, 1, 7, 7, 7, 7, 7, 6, 0};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 6);
        assertThat(mancalaBoard.board, is(afterMove));
        Assert.assertTrue(player.isPresent());
        assertThat(player.get(), is(player2));
    }

    @Test
    public void when_last_stone_lands_in_own_empty_pit() {
        int[] beforeMove = {1, 0, 8, 8, 8, 8, 2, 0, 7, 7, 7, 7, 7, 1};
        int[] afterMove = {0, 0, 8, 8, 8, 8, 10, 0, 7, 7, 7, 0, 7, 1};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 1);
        assertThat(mancalaBoard.board, is(afterMove));
        Assert.assertTrue(player.isPresent());
        assertThat(player.get(), is(player1));
    }

    @Test
    public void when_last_stone_lands_in_oppositions_empty_pit() {
        int[] beforeMove = {0, 0, 8, 8, 8, 8, 2, 7, 6, 6, 6, 6, 6, 0};
        int[] afterMove = {1, 0, 8, 8, 8, 8, 2, 0, 7, 7, 7, 7, 7, 1};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player2, 1);
        assertThat(mancalaBoard.board, is(afterMove));
        Assert.assertTrue(player.isPresent());
        assertThat(player.get(), is(player1));
    }


    @Test
    public void when_game_over_player_not_returned() {
        int[] beforeMove = {2, 0, 2, 12, 0, 1, 24, 0, 0, 0, 0, 0, 7, 24};
        int[] afterMove = {3, 1, 3, 13, 1, 2, 24, 0, 0, 0, 0, 0, 0, 25};
        mancalaBoard.playAreaMap.get(player2).stonesInPlay = 7;
        mancalaBoard.playAreaMap.get(player1).stonesInPlay = 17;

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player2, 6);
        assertThat(mancalaBoard.board, is(afterMove));
        assertFalse(player.isPresent());
        assertTrue(mancalaBoard.gameOver);

    }

    @Test
    public void when_opponents_mancala_encountered_skip_it() {
        int[] beforeMove = {0, 0, 8, 8, 8, 8, 10, 0, 7, 7, 7, 0, 7, 1};
        int[] afterMove = {0, 0, 8, 8, 8, 0, 20, 1, 8, 8, 8, 1, 0, 1};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 6);
        assertThat(mancalaBoard.board, is(afterMove));
        assertTrue(player.isPresent());
        assertThat(player.get(), is(player1));
    }

    @Test(expected = IllegalStateException.class)
    public void move_not_allowed_when_game_over() {
        int[] beforeMove = {3, 1, 3, 13, 1, 2, 24, 0, 0, 0, 0, 0, 0, 25};
        mancalaBoard.playAreaMap.get(player1).stonesInPlay = 23;
        mancalaBoard.playAreaMap.get(player2).stonesInPlay = 0;
        mancalaBoard.gameOver = true;

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 1);
        assertThat(mancalaBoard.board, is(beforeMove));
        assertFalse(player.isPresent());
    }

    @Test
    public void when_player_chooses_empty_pit_replay_turn() {
        int[] beforeMove = {0, 0, 8, 8, 8, 8, 10, 0, 7, 7, 7, 0, 7, 1};
        int[] afterMove = {0, 0, 8, 8, 8, 8, 10, 0, 7, 7, 7, 0, 7, 1};

        mancalaBoard.board = beforeMove;
        Optional<Player> player = mancalaBoard.update(player1, 1);
        assertThat(mancalaBoard.board, is(afterMove));
        assertTrue(player.isPresent());
        assertThat(player.get(), is(player1));
    }


    @Test
    public void test_winner_returned_after_game_over() { //TODO tst name
        int[] afterMove = {3, 1, 3, 13, 1, 2, 24, 0, 0, 0, 0, 0, 0, 25};
        mancalaBoard.playAreaMap.get(player1).stonesInPlay = 23;
        mancalaBoard.playAreaMap.get(player2).stonesInPlay = 0;
        mancalaBoard.gameOver = true;

        mancalaBoard.board = afterMove;
        Player player = mancalaBoard.getWinner();
        assertThat(mancalaBoard.board, is(afterMove));
        assertThat(player, is(player1));
    }

    @Test(expected = IllegalStateException.class)
    public void test_error_returned_after_game_not_over() { //TODO tst name
        int[] afterMove = {3, 1, 3, 13, 1, 2, 24, 0, 0, 0, 0, 0, 0, 25};
        mancalaBoard.playAreaMap.get(player1).stonesInPlay = 23;
        mancalaBoard.playAreaMap.get(player2).stonesInPlay = 0;
        mancalaBoard.gameOver = false;

        mancalaBoard.board = afterMove;
        mancalaBoard.getWinner();

    }
}