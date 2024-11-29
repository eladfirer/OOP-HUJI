/**
 * HumanPlayer. this is a class for a human player who wants to play the game.
 * human player chooses in every turn a number between [0, Board size - 1] that
 * represents a block on the board.
 * @see Player
 */
public class HumanPlayer implements Player {
    /**
     * static objects for ERROR messages to user.
     */
    private static final String OUT_OF_BOUNDS_ERROR = "Invalid mark position." +
            " Please choose a valid position:";
    private static final String POSITION_OCCUPIED_ERROR = "Mark position is already occupied." +
            " Please choose a valid position:";

    HumanPlayer() {}

    @Override
    public void playTurn(Board board, Mark mark) {
        System.out.println(String.format("Player %s, type coordinates:", mark));
        int boardSize = board.getSize();
        int move = KeyboardInput.readInt();
        boolean validMove = false;
        while(!validMove) {
            if(move >= board.getSize()*board.getSize() || move < 0){
                System.out.println(OUT_OF_BOUNDS_ERROR);
                move = KeyboardInput.readInt();
            }
            else if(board.getMark(move/boardSize,move%boardSize) != Mark.BLANK){
                System.out.println(POSITION_OCCUPIED_ERROR);
                move = KeyboardInput.readInt();
            }
            else{
                validMove = true;
            }
        }
        board.putMark(mark,move/boardSize,move%boardSize);
    }

}
