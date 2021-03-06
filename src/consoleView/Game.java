package consoleView;

import heptalion.Direction;
import heptalion.Board;
import heptalion.Deck;
import heptalion.Domino;
import heptalion.Player;
import java.util.Scanner;

/**
 * Heptalion main game class
 * @author David
 */
public class Game {
    Board board;
    Player[] players;
    Deck<Domino> deck;
    private Scanner stdin = new Scanner(System.in);
    static final int NUM_PLAYERS = 2;
    private static final int MAX_TRIES = 3;
    private static final String QUIT = "Q";



    /**
     * Initialize board, deck, players
     */
    public Game(int _numPlayers) {
        board = new Board();
        deck = new Deck();
        intro();
        initPlayers();
    }

    /**
     * Play the game, announcing the winner at the end.
     */
    public void play() {
        int nextPlayer = 0;
        while (true) {
            if (!playDomino(players[nextPlayer])) {
                announceWinner(players[otherPlayer(nextPlayer)]);
                return;
            }
            nextPlayer = otherPlayer(nextPlayer);
        }
    }

    // Return index of other player
    // only works for 2 players
    private int otherPlayer(int nextPlayer) {
        return 1 - nextPlayer;
    }

    /**
     * Get player to place a domino on the board
     * @return true if the player successfully makes a play, false if not
     */
    boolean playDomino(Player player) {
        int domToPlay;
        String quit;
        int row, col;
        Direction dir;
        int tries = MAX_TRIES;
        boolean successful = false;
        while (tries > 0) {
            System.out.println(board);
            System.out.println(player);
            System.out.print("Enter " + QUIT + " to quit, any other nonblank to continue: ");
            quit = stdin.next().toUpperCase();
            if (quit.equals(QUIT))
                return false;
            domToPlay = getValidIndex(player.getHandSize());
            System.out.print("Please pick row to place (left end of) domino: ");
            row = stdin.nextInt();
            System.out.print("Please pick column to place (left end of) domino: ");
            col = stdin.nextInt();
            dir = getValidDirection();
            if (board.place(player.get(domToPlay), row, col, dir)) {
                player.remove(domToPlay);
                return true;
            }
            // To get more helpful error messages, using an exception
            // would be convenient
            System.out.println("Not a legal move. Attempts left = " + --tries);
        }
        return false;
    }

    /**
     * Get a valid direction from the user
     * @return the direction
     */
    private Direction getValidDirection() {
        boolean valid = false;
        Direction result=null;
        while(!valid){
        System.out.println("Enter the direction: LEFT, UP, RIGHT, DOWN");
        String x = stdin.next();

        if(x.equalsIgnoreCase("l")||x.equalsIgnoreCase("left"))
        {
            result = Direction.valueOf("LEFT");
            valid = true;

        }
        else if(x.equalsIgnoreCase("r")||x.equalsIgnoreCase("right"))
        {
            result = Direction.valueOf("RIGHT");
            valid = true;
        }
        else if(x.equalsIgnoreCase("u")||x.equalsIgnoreCase("up"))
        {
            result = Direction.valueOf("UP");
            valid = true;

        }
        else if(x.equalsIgnoreCase("d")||x.equalsIgnoreCase("down"))
        {
            result = Direction.valueOf("DOWN");
            valid = true;

        }
        else{
        System.out.println("Valid directions are: LEFT, UP, RIGHT, DOWN");}
        }
        return result;
    }

    /**
     * Get a valid index from the user
     * @param high  1 more than the maximum valid value
     * @return index of domino chosen
     */
    private int getValidIndex(int high) {
        int domToPlay = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print("Please choose the index of the domino you wish to play: ");
            domToPlay = stdin.nextInt();
            valid = 0 <= domToPlay && domToPlay < high;
        }
        return domToPlay;
    }

    // Announce winning player's name
    private void announceWinner(Player winningPlayer) {
        System.out.println(winningPlayer.getName() + " has won!");
    }

    // Display welcome message
    private void intro() {
        System.out.println("This is a console-based version of Néstor Romeral Andrés's Heptalion");
        System.out.println();
        System.out.println("For each turn, a player tries to place one of his/her dominoes on the board");
        System.out.println("The last player to make a legal move wins");
        System.out.println("Unlike the real game, a player loses when they either quit");
        System.out.println("  or attempt 3 consecutive illegal moves");
        System.out.println();
    }

    // Initialize players with their dominoes
    private void initPlayers() {
        players = new Player[NUM_PLAYERS];
        String name;
        deck.shuffle();

        for (int playerIndex = 0; playerIndex < NUM_PLAYERS;
             playerIndex++) {
            System.out.print("Please enter the name of player " + playerIndex + ": ");
            name = stdin.next();
            players[playerIndex] = new Player(name, board);
        }
        dealDominoes();

    }

    // Assign dominoes to each player
    private void dealDominoes() {
        Domino nextDom = deck.draw();
        int nextPlayer = 0;
        while (nextDom != null) {
            players[nextPlayer].addDomino(nextDom);
            nextPlayer = otherPlayer(nextPlayer);
            nextDom = deck.draw();
        }

    }

    /**
     * Start up game
     * @param args command line args are not used
     */
    public static void main(String[] args) {
        Game heptalion = new Game(NUM_PLAYERS);
        heptalion.play();
    }
}
