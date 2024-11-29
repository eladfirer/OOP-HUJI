/**
 * Tournament. this class is responsible for running a tournament of Tic Tac Toe.
 * @see Game
 */
public class Tournament {
    private int rounds;
    private Renderer renderer;
    private Player player1;
    private Player player2;

    /**
     * Constructor.
     * @param rounds number of rounds in tournament.
     * @param renderer render for game.
     * @param player1 player number 1.
     * @param player2 player number 2.
     * @see Player
     * @see Renderer
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Responsible for running a full tournament
     * @param size size of board for each game.
     * @param winStreak the number of consecutive marks required for a player to win a game.
     * @param playerName1 player number 1 type.
     * @param playerName2 player number 2 type.
     * @see Game
     * @see Board
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        Mark mark1 = Mark.X;
        Game game;
        int player1Victories = 0;
        int ties = 0;
        for (int i = 0; i < rounds; i++) {
            Mark result;
            if (mark1 == Mark.X) {
                result = this.runGame(this.player1, this.player2, size, winStreak);
            } else {
                result = this.runGame(this.player2, this.player1, size, winStreak);
            }

            if (result == mark1) {
                player1Victories++;
            }
            else if(result == Mark.BLANK) {
                ties++;
            }

            // each game player1 and player 2 switch marks
            mark1 = (mark1 == Mark.X) ? Mark.O : Mark.X;
        }
        int player2Victories = this.rounds - player1Victories - ties;
        System.out.println(String.format("Player 1, %s won: %d rounds", playerName1, player1Victories));
        System.out.println(String.format("Player 2, %s won: %d rounds", playerName2, player2Victories));
        System.out.println(String.format("Ties: %d", ties));
    }

    /**
     * runs a single game.
     * @param size size of board for each game.
     * @param winStreak the number of consecutive marks required for a player to win a game.
     * @param playerX player with mark X.
     * @param playerO player with mark O.
     * @see Game
     */
    private Mark runGame(Player playerX, Player playerO, int size, int winStreak) {
        Game game = new Game(playerX, playerO, size, winStreak, this.renderer);
        return game.run();
    }

    public static void main(String[] args) {
        // extracting data from user
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        String rendererName = args[3];
        String playerName1 = args[4].toLowerCase();
        String playerName2 = args[5].toLowerCase();

        // initializing players
        Player player1 = PlayerFactory.buildPlayer(playerName1);
        Player player2 = PlayerFactory.buildPlayer(playerName2);

        // initializing renderer
        Renderer renderer = RendererFactory.buildRenderer(rendererName,size);

        // starting tournament
        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, playerName1, playerName2);
    }
}