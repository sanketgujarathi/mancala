package com.bol.assignment.engine;

import com.bol.assignment.domain.Player;

import java.util.Optional;

public interface Board {

    void init(Player player1, Player player2);

    Optional<Player> update(Player player, int choosenPit);

    Player getWinner();

    void showBoard();
}
