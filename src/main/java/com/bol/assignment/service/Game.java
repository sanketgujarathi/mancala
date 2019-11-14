package com.bol.assignment.service;

import com.bol.assignment.domain.Player;

public interface Game {

    void init();

    void play();

    Player getWinner();
}
