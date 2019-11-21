package com.bol.assignment.service;

import com.bol.assignment.domain.Player;
import com.bol.assignment.engine.Board;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MancalaGameImplTest {

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    @Mock
    private Board board;

    private Game game;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        game = new MancalaGameImpl(player1, player2, board);
    }

    @Test
    public void testInit() {
        game.init();
        verify(player1, times(1)).init();
        verify(player2, times(1)).init();
        verify(board, times(1)).init(player1, player2);

    }

    @Test
    public void test_play_when_player_repeats_turn() {

        when(board.update(any(Player.class), anyInt())).thenReturn(Optional.of(player1), Optional.empty());
        when(player1.getNextMove()).thenReturn(1);
        game.play();
        verify(player1, times(2)).getNextMove();
        verify(player2, never()).getNextMove();
    }

    @Test
    public void test_play_when_player_switch_turns() {

        when(board.update(any(Player.class), anyInt())).thenReturn(Optional.of(player2), Optional.of(player1), Optional.empty());
        when(player1.getNextMove()).thenReturn(1);
        when(player2.getNextMove()).thenReturn(1);
        game.play();
        verify(player1, times(2)).getNextMove();
        verify(player2, times(1)).getNextMove();
    }
    /*@Test
    public void testPlayWhenPlayer2Wins() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        game.init();
        when(player1.getNextMove()).thenReturn(PAPER);
        when(player2.getNextMove()).thenReturn(SCISSORS);

        Board board = game.play();

        assertThat(board.getWinner().get(player1), is(0));
        assertThat(board.getWinner().get(player2), is(1));
        verify(player1, times(1)).getNextMove();
        verify(player2, times(1)).getNextMove();
    }

    @Test
    public void testPlayWhenThereIsATie() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        game.init();
        when(player1.getNextMove()).thenReturn(PAPER);
        when(player2.getNextMove()).thenReturn(PAPER);

        Board board = game.play();

        assertThat(board.getWinner().get(player1), is(0));
        assertThat(board.getWinner().get(player2), is(0));
        verify(player1, times(1)).getNextMove();
        verify(player2, times(1)).getNextMove();
    }

    @Test
    public void testPlayForMultipleRounds() {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
        game.init();
        game.play();
        verify(player1, times(3)).getNextMove();
        verify(player2, times(3)).getNextMove();
    }
*/
}