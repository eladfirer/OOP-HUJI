/**
 * Player. this class represents every type of player in game.
 * each player has the method playTurn.
 */
public interface Player {
    /**
     * makes a turn on board for player.
     * @param board board of game.
     * @param mark player's mark.
     */
    void playTurn(Board board, Mark mark);
}
