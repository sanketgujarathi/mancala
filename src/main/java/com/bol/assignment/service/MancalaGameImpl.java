package com.bol.assignment.service;

import com.bol.assignment.engine.Board;
import com.bol.assignment.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MancalaGameImpl implements Game {

    private static Logger log = LoggerFactory.getLogger(MancalaGameImpl.class);
    private Player player1;
    private Player player2;
    private Board board;

    public MancalaGameImpl(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
    }


    @Override
    public void init() {
        log.info("Welcome to the game of Mancala!");
        this.player1.init();
        this.player2.init();
        this.board.init(this.player1, this.player2);
        log.info("Mancala game initialised...");
    }

    @Override
    public void play() {

        Optional<Player> currentPlayer = Optional.of(this.player1);
        while (currentPlayer.isPresent()) {
            board.showBoard();
            Player player = currentPlayer.get();
            currentPlayer = board.update(player, player.getNextMove());

        }
        board.showBoard();
    }


    @Override
    public Player getWinner() {
        return board.getWinner();
    }
}
