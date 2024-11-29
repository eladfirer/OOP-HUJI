
/**
 * The board class is responsible for keeping the board data
 * and updating it as the game run.
 *
 */
public class Board {
    private Mark[][] board;
    /**
     * size of one row / column on board.
     */
    private int boardSize;


    /**
     * Default constructor. board size will be [4][4].
     */
    public Board() {
        this.board = new Mark[4][4];
        this.boardSize = 4;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * Constructor
     *
     * @param size sets the board to be [size][size]
     */
    public Board(int size) {
        this.board = new Mark[size][size];
        this.boardSize = size;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * returns boardSize.
     */
    public int getSize() {
        return boardSize;
    }

    /**
     * Attempts to place a given mark on the specified block of the board.
     *
     * @param mark the mark to place in the specified block.
     * @param row  the row index of the block where the mark should be placed (0-based).
     * @param col  the column index of the block where the mark should be placed (0-based).
     * @return {@code true} if the block at [row][col] is empty and the mark was successfully placed,
     * {@code false} if the block is already occupied.
     */
    boolean putMark(Mark mark, int row, int col) {
        if (board[row][col] == Mark.BLANK) {
            board[row][col] = mark;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the mark at the specified block on the board.
     *
     * @param row the row index of the block to retrieve (0-based).
     * @param col the column index of the block to retrieve (0-based).
     * @return the {@link Mark} at the specified block. If the specified block is out of bounds,
     * *         returns {@link Mark#BLANK}.
     */
    Mark getMark(int row, int col) {
        if (row < boardSize && col < boardSize && row >= 0 && col >= 0) {
            return board[row][col];
        }
        return Mark.BLANK;
    }
}
