package com.bol.assignment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Player {

    private String playerName;

    private Logger log = LoggerFactory.getLogger(Player.class);

    public int getNextMove() {

        while (true) {
            log.info("Player {} Choose a pit(1-6):", playerName);
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            int pit = Integer.parseInt(input);
            if(pit >=1 && pit <= 6){
                return pit;
            }
        }
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void init() {
        log.info("Enter your name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        this.playerName = name.toUpperCase();
    }

}
