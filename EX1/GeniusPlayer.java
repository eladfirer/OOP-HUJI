/**
 * GeniusPlayer. this player beats clever player in default mode (size - 4, win streak - 3)
 * more than 55% of games. <br>
 * Player strategy: <br>
 * A. Start from the second column of the board. <br>
 * B. Attempt to place a mark in the first block (top row) of the current column. <br>
 * C. If the block is occupied, move to the next block in the same column.<br>
 * D. If the entire column is occupied, proceed to the next column.<br>
 * E. Continue this process column by column until a block is found.<br>
 * or all the blocks from the second column to the last one are checked.<br>
 * F. If all from the second column to the last one are occupied,
 *  start checking the first column and follow the same logic.<br>
 * G. Continue this process until a block is found or all blocks are checked.<br>
 * @see Player
 */
public class GeniusPlayer implements Player {
    GeniusPlayer() {}
    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();
        int row = 0, col = 1;
        for(col = 1; col < boardSize; col++) {
            for(row = 0; row < boardSize; row++){
                if (board.putMark(mark, row, col)) {
                    return;
                }
            }
        }
        col = 0;
        for(row=0; row < boardSize; row++) {
            if (board.putMark(mark, row, col)) {
                return;
            }
        }
    }
}
