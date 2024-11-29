/**
 * CleverPlayer. this player beats whatever player in default mode (size - 4, win streak - 3)
 * more than 55% of games. <br>
 * Player strategy: <br>
 * A. Start from the first row of the board. <br>
 * B. Attempt to place a mark in the first block (leftmost column) of the current row. <br>
 * C. If the block is occupied, move to the next block in the same row. <br>
 * D. If the entire row is occupied, proceed to the next row. <br>
 * E. Continue this process row by row until a block is found or all blocks are checked.<br>
 * @see Player
 */
public class CleverPlayer implements Player {
    CleverPlayer() {}
    @Override
    public void playTurn(Board board, Mark mark){
        int boardSize = board.getSize();
        int row, col;
        for (row = 0; row < boardSize; row++) {
            for (col = 0; col < boardSize; col++) {
                if(board.putMark(mark,row, col)){
                    return;
                }
            }
        }
    }
}
