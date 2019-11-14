package com.bol.assignment.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Repository
public class MancalaBoardImpl implements Board {

    int[] board;
    Map<Player, PlayArea> playAreaMap;
    Player player1;
    Player player2;
    boolean gameOver;
    private static Logger log = LoggerFactory.getLogger(MancalaBoardImpl.class);

    @Override
    public void init(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        playAreaMap = new HashMap<>();
        playAreaMap.put(this.player1, new PlayArea(0, 6, 36));
        playAreaMap.put(this.player2, new PlayArea(7, 13, 36));
        initializeBoard();
        gameOver = false;
    }

    private void initializeBoard() {
        this.board = new int[14];
        playAreaMap.entrySet().forEach(p -> Arrays.fill(this.board, p.getValue().startIndex, p.getValue().mancalaIndex, 6));
    }

    @Override
    public Optional<Player> update(Player player, int choosenPit){

        if(gameOver){
            log.error("Invalid invocation. Game is over!");
            throw new IllegalStateException("Invalid invocation. Game is over!");
        }
        PlayArea ownArea = this.playAreaMap.get(player);
        PlayArea opponentArea = this.playAreaMap.get(getOpponent(player));
        int choosenPitIndex = ownArea.startIndex + choosenPit - 1;
        int remainingStones = this.board[choosenPitIndex];
        if (remainingStones == 0) {
            log.error("Empty pit selected! Choose a different pit.");
            return of(player);
        }
        int currentPitIndex = choosenPitIndex;
        board[choosenPitIndex] = 0;
        while (remainingStones > 0) {
            currentPitIndex = incrementIndex(currentPitIndex);
            if (landedInOwnArea(ownArea, currentPitIndex)) {
                if (currentPitIndex == ownArea.mancalaIndex) {
                    ownArea.stonesInPlay--;
                } else if (remainingStones == 1 && this.board[currentPitIndex] == 0) {
                    int oppositePit = opponentArea.startIndex + (ownArea.mancalaIndex - currentPitIndex) - 1;
                    this.board[ownArea.mancalaIndex] += this.board[oppositePit] + remainingStones;
                    ownArea.stonesInPlay--;
                    opponentArea.stonesInPlay -= this.board[oppositePit];
                    this.board[oppositePit] = 0;
                    break;
                }
            } else {
                if (currentPitIndex == opponentArea.mancalaIndex) {
                    continue;
                } else {
                    ownArea.stonesInPlay--;
                    opponentArea.stonesInPlay++;
                }
            }
            this.board[currentPitIndex]++;
            remainingStones--;
        }
        if (ownArea.stonesInPlay == 0) {
            gameOver = true;
            log.info("Game Over! Player {} ran out of stones", player.getPlayerName());
            return empty();

        }
        return landedInOwnArea(ownArea, currentPitIndex) ? of(player): of(getOpponent(player));
    }

    private Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    private int incrementIndex(int currIndex) {
        return currIndex == this.board.length - 1 ? 0 : currIndex + 1;
    }

    private boolean landedInOwnArea(PlayArea ownArea, int currentPitIndex) {

        return currentPitIndex >= ownArea.startIndex && currentPitIndex <= ownArea.mancalaIndex;
    }


    @Override
    public Player getWinner() {
        if(!gameOver){
            log.error("Invalid invocation. Game is not over!");
            throw new IllegalStateException("Invalid invocation. Game is not over!");
        }
        PlayArea player1Area = this.playAreaMap.get(player1);
        PlayArea player2Area = this.playAreaMap.get(player2);
        return player1Area.getTotalStones() > player2Area.getTotalStones() ? player1 : player2;

    }

    @Override
    public void showBoard() {
        PlayArea player1Area = this.playAreaMap.get(player1);
        int[] player1Pits = Arrays.copyOfRange(this.board, player1Area.startIndex, player1Area.mancalaIndex + 1);
        PlayArea player2Area = this.playAreaMap.get(player2);
        int[] player2Pits = Arrays.copyOfRange(this.board, player2Area.startIndex, player2Area.mancalaIndex + 1);
        log.info("******Current Board******");
        log.info(Arrays.toString(IntStream.range(0, player2Pits.length).map(i -> player2Pits[player2Pits.length - 1 - i]).toArray()));
        log.info(Arrays.toString(player1Pits));

    }

    class PlayArea {
        int startIndex;
        int mancalaIndex;
        int stonesInPlay;

        PlayArea(int startIndex, int mancalaIndex, int stonesInPlay) {
            this.startIndex = startIndex;
            this.mancalaIndex = mancalaIndex;
            this.stonesInPlay = stonesInPlay;
        }

        int getTotalStones() {
            return board[this.mancalaIndex] + this.stonesInPlay;
        }

    }


}
