package com.bol.assignment.engine;

import com.bol.assignment.domain.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

    private final int NUM_OF_PITS;
    private final int INIT_STONES;
    int[] board;
    Map<Player, PlayArea> playAreaMap;
    Player player1;
    Player player2;
    boolean gameOver;
    private static Logger log = LoggerFactory.getLogger(MancalaBoardImpl.class);

    public MancalaBoardImpl(@Value("${mancala.board.pits}")int numOfPits, @Value("${mancala.board.init-stones}") int initStones) {
        NUM_OF_PITS = numOfPits;
        INIT_STONES = initStones;
        board = new int[NUM_OF_PITS * 2 + 2];
    }

    @Override
    public void init(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        playAreaMap = new HashMap<>();
        playAreaMap.put(this.player1, new PlayArea(0, board.length / 2 - 1, NUM_OF_PITS * INIT_STONES));
        playAreaMap.put(this.player2, new PlayArea(board.length / 2, board.length - 1, NUM_OF_PITS * INIT_STONES));
        initializeBoard();
        gameOver = false;
    }

    private void initializeBoard() {
        playAreaMap.forEach((k,v) -> Arrays.fill(this.board, v.startIndex, v.mancalaIndex, INIT_STONES));
    }

    @Override
    public Optional<Player> update(Player player, int choosenPit){

        if(gameOver){
            log.error("Invalid invocation. Game is over!");
            throw new IllegalStateException("Invalid invocation. Game is over!");
        }
        PlayArea ownArea = this.playAreaMap.get(player);
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
            PlayArea opponentArea = this.playAreaMap.get(getOpponent(player));
            if (landedInOwnArea(player, currentPitIndex)) {
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
                if (skipPit(player, currentPitIndex)) {
                    continue;
                } else {
                    ownArea.stonesInPlay--;
                    opponentArea.stonesInPlay++;
                }
            }
            this.board[currentPitIndex]++;
            remainingStones--;
        }
        return getPlayerForNextTurn(player, currentPitIndex);

    }

    private Player getOpponent(Player player) {
        return player == player1 ? player2 : player1;
    }

    private int incrementIndex(int currIndex) {
        return currIndex == this.board.length - 1 ? 0 : currIndex + 1;
    }

    private boolean skipPit(Player player, int currentPitIndex) {
        return currentPitIndex == playAreaMap.get(getOpponent(player)).mancalaIndex;
    }

    private boolean landedInOwnArea(Player player, int currentPitIndex) {
        PlayArea ownArea = playAreaMap.get(player);
        return currentPitIndex >= ownArea.startIndex && currentPitIndex <= ownArea.mancalaIndex;
    }


    private Optional<Player> getPlayerForNextTurn(Player player, int currentPitIndex) {
        if (ranOutOfStones(player)) {
            gameOver = true;
            log.info("Game Over! Player {} ran out of stones", player.getPlayerName());
            return empty();
        }
        return landedInOwnArea(player, currentPitIndex) ? of(player): of(getOpponent(player));
    }

    private boolean ranOutOfStones(Player player) {
        PlayArea ownArea = playAreaMap.get(player);
        return ownArea.stonesInPlay == 0;
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
