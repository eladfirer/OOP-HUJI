
/**
 * Game. this class is responsible for running a single game of Tic Tac Toe.
 */
public class Game {
    private Player playerX;
    private Player playerO;
    private Board board;
    private Renderer renderer;
    private int winStreak;
    private int boardSize;

    /**
     * Constructor. board size will be [4][4].
     * win streak will be 3.
     * @param playerX player that plays X.
     * @param playerO player that plays O.
     * @param renderer render for game.
     * @see Board
     * @see Player
     * @see Renderer
     */
    public Game(Player playerX, Player playerO, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new Board();
        // default board size and win streak values
        this.boardSize = 4;
        this.winStreak = 3;
        this.renderer = renderer;
    }

    /**
     * Constructor. board size will be [size][size].
     * win streak will be winStreak.
     * @param playerX player that plays X.
     * @param playerO player that plays O.
     * @param size size of board.
     * @param winStreak the number of consecutive marks required for a player to win the game.
     * @param renderer render for game.
     * @see Board
     * @see Player
     * @see Renderer
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.board = new Board(size);
        this.boardSize = board.getSize();
        this.winStreak = winStreak;
        this.renderer = renderer;
    }

    /**
     * returns winStreak.
     */
    public int getWinStreak(){
        return winStreak;
    }

    /**
     * returns boardSize.
     */
    public int getBoardSize(){
        return boardSize;
    }

    /**
     * This function runs a full game.
     * @return Mark of wining player. in case of a tie returns {@link Mark#BLANK}
     * @see Mark
     */
    public Mark run() {
        Mark currentMark = Mark.X;
        while (true) {
            Player currentPlayer = (currentMark == Mark.X) ? playerX : playerO;
            playTurn(currentPlayer, currentMark);
            if (isWinner(currentMark)) {
                this.renderer.renderBoard(board);
                return currentMark;
            }
            if (isBoardFull()) {
                return Mark.BLANK;
            }
            currentMark = (currentMark == Mark.X) ? Mark.O : Mark.X;
        }


    }

    /**
     * plays a single turn for a player.
     * @param player
     * @param mark player's mark.
     */
    private void playTurn(Player player, Mark mark){
        this.renderer.renderBoard(board);
        player.playTurn(board,mark);
    }

    /**
     * checks if mark is a winner.
     * @param mark
     * @return true in case of win. false otherwise.
     */
    private boolean isWinner(Mark mark) {

        // Check rows
        for (int row = 0; row < boardSize; row++) {
            if (hasWinningStreak(row, 0, 0, 1, mark)) {
                return true;
            }
        }

        // Check columns
        for (int col = 0; col < boardSize; col++) {
            if (hasWinningStreak(0, col, 1, 0, mark)) {
                return true;
            }
        }

        // Check top-left to bottom-right diagonal
        for (int row = 0; row <= boardSize - winStreak; row++) {
            for (int col = 0; col <= boardSize - winStreak; col++) {
                if (hasWinningStreak(row, col, 1, 1, mark)) {
                    return true;
                }
            }
        }

        // Check top-right to bottom-left diagonal
        for (int row = 0; row <= boardSize - winStreak; row++) {
            for (int col = winStreak - 1; col < boardSize; col++) {
                if (hasWinningStreak(row, col, 1, -1, mark)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * checks if a mark has a wining streak on board according to values.
     *
     * @param startRow the row index where the streak check begins (0-based).
     * @param startCol the column index where the streak check begins (0-based).
     * @param stepRow the row increment for each step in the streak direction.
     * @param stepCol the column increment for each step in the streak direction.
     * @param mark
     * @return true if the specified mark achieves the required winning streak. false otherwise.
     */
    private boolean hasWinningStreak(int startRow, int startCol, int stepRow, int stepCol, Mark mark) {
        int streak = 0;

        for (int i = 0; i < winStreak; i++) {
            int row = startRow + i * stepRow;
            int col = startCol + i * stepCol;

            if (row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
                return false;
            }

            if (board.getMark(row, col) == mark) {
                streak++;
            } else {
                break;
            }

            if (streak == winStreak) {
                return true;
            }
        }

        return false;
    }

    /**
     * checks if the board is full; there is no {@link Mark#BLANK} on board.
     * @return true in case the board is full. false otherwise.
     */
    private boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    return false;
                }
            }
        }
        return true;
    }
}
