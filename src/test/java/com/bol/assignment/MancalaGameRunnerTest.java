package com.bol.assignment;


import com.bol.assignment.engine.MancalaBoardImpl;
import com.bol.assignment.engine.Board;
import com.bol.assignment.service.Game;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class MancalaGameRunnerTest {

    @Mock
    private Game game;

    private MancalaGameRunner mancalaGameRunner;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mancalaGameRunner = new MancalaGameRunner(game);
    }

    @Test
    public void testRunMethod() {
        Board board = new MancalaBoardImpl(6, 6);
        //when(game.play()).thenReturn(board);

        mancalaGameRunner.run("args");

        verify(game, times(1)).init();
        verify(game, times(1)).play();
    }
}