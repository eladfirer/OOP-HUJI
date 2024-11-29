import java.util.Random;
/**
 * GeniusPlayer. this player doesn't have a strategy.
 * he chooses a random block in each turn.
 * @see Player
 */
public class WhateverPlayer implements Player{
    WhateverPlayer() {}

    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();
        int row, col;
        Random random = new Random();
        do {
            row = random.nextInt(boardSize);
            col = random.nextInt(boardSize);
        } while (!board.putMark(mark,row, col));
    }
}
