package com.bol.assignment.service;

import com.bol.assignment.domain.Board;
import com.bol.assignment.domain.MancalaBoardImpl;
import com.bol.assignment.domain.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MancalaGameImplTest {

    @Mock
    private Player player1;

    @Mock
    private Player player2;

    private Game game;
/*
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        game = new MancalaGameImpl(new MancalaBoardImpl());

    }

    @Test
    public void testInit() {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
        game.init();
        verify(player1, times(1)).init();
        verify(player2, times(1)).init();

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInitForInvalidNumberOfPlayers() {
        ByteArrayInputStream in = new ByteArrayInputStream("3".getBytes());
        System.setIn(in);
        game.init();
    }

    @Test
    public void testPlayWhenPlayer1Wins() {
        ByteArrayInputStream in = new ByteArrayInputStream("1".getBytes());
        System.setIn(in);
        game.init();
        when(player1.getNextMove()).thenReturn(ROCK);
        when(player2.getNextMove()).thenReturn(SCISSORS);
        Board board = game.play();
        assertThat(board.getWinner().get(player1), is(1));
        assertThat(board.getWinner().get(player2), is(0));
        verify(player1, times(1)).getNextMove();
        verify(player2, times(1)).getNextMove();
    }

    @Test
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