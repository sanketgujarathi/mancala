package com.bol.assignment;

import com.bol.assignment.domain.Player;
import com.bol.assignment.service.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MancalaGameRunner implements CommandLineRunner {

    private Game game;

    private Logger log = LoggerFactory.getLogger(MancalaGameRunner.class);

    public MancalaGameRunner(Game game) {
        this.game = game;
    }

    public static void main(String[] args) {
        SpringApplication.run(MancalaGameRunner.class, args);
    }

    @Override
    public void run(String... args) {
        game.init();
        game.play();
        Player winner = game.getWinner();
        displayWinner(winner);
    }

    private void displayWinner(Player winner) {
        log.info("Player {} wins the game", winner.getPlayerName());

    }
}
