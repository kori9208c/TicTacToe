import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
//Assignment 5 Pair 89
//Correy Eifling-patterson
//Marvellinus Vincent
public class TicTacToe {
    // Static variables for the TicTacToe class, effectively configuration options
    // Use these instead of hard-coding ' ', 'X', and 'O' as symbols for the game
    // In other words, changing one of these variables should change the program
    // to use that new symbol instead without breaking anything
    // Do NOT add additional static variables to the class!
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public static void main(String[] args) {
        // This is where the main menu system of the program will be written.
        // Hint: Since many of the game runner methods take in an array of player names,
        // you'll probably need to collect names here.

        Scanner scan = new Scanner(System.in);
        boolean quit = false;
        // store the last game
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        String[] playerNames = {"", ""};

        while (!quit) {
            System.out.println("Welcome to Tic Tac Toe!");
            System.out.println("__________________________");

            System.out.println("Please select an option:");
            System.out.println("1. Single player");
            System.out.println("2. Two player");
            System.out.println("I. Instructions");
            System.out.println("D. Display last match");
            System.out.println("Q. Quit");
            System.out.println("__________________________");
            System.out.println("Note:If confused on how to make inputs please read the instructions found by pressing 'I' and scrolling up past the menu.");
            System.out.print("select a mode:");
            String option = scan.nextLine().toUpperCase();

            //single player
            if (option.equals("1")) {
                System.out.println("Starting a one player game...");
                System.out.print("Input player name: ");
                playerNames[0] = scan.next();
                playerNames[1] = "Computer";
                gameHistory = runOnePlayerGame(playerNames);

            }
            // two player
            else if (option.equals("2")) {
                System.out.println("Starting a two player game...");
                System.out.print("Player one's name: ");
                playerNames[0] = scan.nextLine();
                System.out.print("Player two's name: ");
                playerNames[1] = scan.nextLine();
                gameHistory = runTwoPlayerGame(playerNames);

            }
            // History
            else if (option.equalsIgnoreCase("D")) {
                if (gameHistory.isEmpty()) {
                    System.out.println("No match has been played yet.");
                } else {
                    System.out.println("Last match: " + gameHistory);
                    runGameHistory(playerNames, gameHistory);
                }
            } else if (option.equalsIgnoreCase("I")) {
                System.out.println(gameInstructions());
            }
            // quit out the game
            else if (option.equalsIgnoreCase("Q")) {
                System.out.println("Quitting game...");
                quit = true;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
        System.out.println("Thank you for playing!");

    }

    private static String gameInstructions() {

        // Append column numbers
        System.out.println("Instructions:");
        System.out.println("Each player takes turns by placing symbols 'X' or 'O' on the board.");
        System.out.println("The first player to get three of their symbols in a row wins!");
        System.out.println("place your inputs by row and col.");
        System.out.println("please look at the board below.");


        return """
                   0   1   2
                0    |   |  \n
                  ---*---*---
                1    |   |  \n
                  ---*---*---
                2    |   |  \n
                """;
    }

    // Given a state, return a String which is the textual representation of the tic-tac-toe board at that state.
    private static String displayGameFromState(char[][] state) {


        StringBuilder gameString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gameString.append(" ").append(state[i][j]);
                if (j < 2) {
                    gameString.append(" |");
                }
            }
            if (i < 2) {
                gameString.append("\n");
                gameString.append("---*---*---\n");

            }
        }
        return gameString.toString();
    }

    // Returns the state of a game that has just started.
    // This method is implemented for you. You can use it as an example of how to utilize the static class variables.
    // As you can see, you can use it just like any other variable, since it is instantiated and given a value already.
    private static char[][] getInitialGameState() {return new char[][]{
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol},
                {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    // Given the player names, run the two-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        boolean gameOver = false;
        int currentPlayer = coinToss(playerNames);
        char currentPlayerSymbol;
        char[][] gameState = getInitialGameState();
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        gameHistory.add(getInitialGameState());

        while (!gameOver)
        {
            String boardString = displayGameFromState(gameState);
            System.out.println(boardString);

            if (currentPlayer == 0) {
                currentPlayerSymbol = playerOneSymbol;
            } else
            {
                currentPlayerSymbol = playerTwoSymbol;
            }

            gameState = runPlayerMove(playerNames[currentPlayer], currentPlayerSymbol, gameState);

            gameHistory.add(gameState);

            // Check if there's a winner or draw
            if (checkWin(gameState))
            {
                System.out.println(displayGameFromState(gameState));
                System.out.println(playerNames[currentPlayer] + " wins!\n");
                gameOver = true;
            }
            else if (checkDraw(gameState))
            {
                System.out.println(displayGameFromState(gameState));
                System.out.println("It's a draw!\n");
                gameOver = true;
            }

            if (currentPlayer == 0)
            {
                currentPlayer = 1;
            } else {
                currentPlayer = 0;
            }
        }

        return gameHistory;
    }


    // Given the player names (where player two is "Computer"),
    // Run the one-player game.
    // Return an ArrayList of game states of each turn -- in other words, the gameHistory
    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames)
    {
        boolean gameOver = false;
        int currentPlayer = coinToss(playerNames);
        char currentPlayerSymbol=' ';
        char[][] gameState = getInitialGameState();
        ArrayList<char[][]> gameHistory = new ArrayList<>();
        gameHistory.add(getInitialGameState());

        while (!gameOver)
        {
            String boardString = displayGameFromState(gameState);
            System.out.println(boardString);

            if (currentPlayer == 0)
            {
                currentPlayerSymbol = playerOneSymbol;
            } else
            {
                currentPlayerSymbol = playerTwoSymbol;
            }
            if (currentPlayer == 0) {
                gameState = runPlayerMove(playerNames[currentPlayer], currentPlayerSymbol, gameState);
            }
            else
            {
                System.out.println(playerNames[1] + "'s turn.");
                gameState = getCPUMove(gameState);
            }
            gameHistory.add(gameState);
            // Check if there's a winner or draw
            if (checkWin(gameState))
            {
                System.out.println(displayGameFromState(gameState));
                System.out.println(playerNames[currentPlayer] + " wins!\n");
                gameOver = true;
            }
            else if (checkDraw(gameState))
            {
                System.out.println(displayGameFromState(gameState));
                System.out.println("It's a draw!\n");
                gameOver = true;
            }

            if (currentPlayer == 0)
            {
                currentPlayer = 1;
            }
            else
            {
                currentPlayer = 0;
            }
        }
        return gameHistory;
    }


    private static int coinToss(String[] playerNames)
    {
        System.out.println("Coin toss to see who goes first...");
        double num = Math.random();
        if (num > 0.5)
        {
            System.out.println(playerNames[1] + " plays first!");
            return 1;

        }
        else
        {
            System.out.println(playerNames[0] + " plays first!");
            return 0;
        }
    }


    // Repeatedly prompts player for move in current state, returning new state after their valid move is made
    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        boolean validMove = false;
        int[] currentMove = {0, 0};

        while (!validMove)
        {
            currentMove = getInBoundsPlayerMove(playerName);
            validMove = checkValidMove(currentMove, currentState);
            if (!validMove)
            {
                System.out.println("That space is invalid, try again.");
            }
        }

        return makeMove(currentMove, playerSymbol, currentState);
    }

    // Repeatedly prompts player for move. Returns [row, column] of their desired move such that row & column are on
    // the 3x3 board, but does not check for availability of that space.
    private static int[] getInBoundsPlayerMove(String playerName) {
        Scanner sc = new Scanner(System.in);
        System.out.println(playerName+"'s turn.");
        int row = 0;
        int col = 0;
        boolean inside = false;
        while (!inside) {
            System.out.print(playerName + ", enter your row: ");
            row = sc.nextInt();
            System.out.print(playerName + ", enter your column: ");
            col = sc.nextInt();
            if ((row < 3 && row >= 0) && (col < 3 && col >= 0)) {
                inside = true;
            } else {
                System.out.println("Invalid input, please try again.");
            }
        }
            return new int[]{row,col};

    }
    // Given a [row, col] move, return true if a space is unclaimed.
    // Doesn't need to check whether move is within bounds of the board.
    private static boolean checkValidMove(int[] move, char[][] state) {
        return Character.toString(state[move[0]][move[1]]).equals(" ");
    }

    // Given a [row, col] move, the symbol to add, and a game state,
    // Return a NEW array (do NOT modify the argument currentState) with the new game state
    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {

        char[][] newState = new char[currentState.length][];
        for (int i = 0; i < currentState.length; i++) {
            newState[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }
        newState[move[0]][move[1]] = symbol;
        return newState;
    }
    // Given a state, return true if some player has won in that state
    private static boolean checkWin(char[][] state) {
        // Hint: no need to check if player one has won and if player two has won in separate steps,
        // you can just check if the same symbol occurs three times in any row, col, or diagonal (except empty space symbol)
        // But either implementation is valid: do whatever makes most sense to you.

        // Horizontals
        for(int i=0;i<3;i++)
        {
            if((state[i][0]==playerOneSymbol ||state[i][0]==playerTwoSymbol)
                    &&(state[i][0]==state[i][1]&&state[i][1]==state[i][2]))
            {
                return true;
            }
        }

        // Verticals
        for(int i=0;i<3;i++) {
            if ((state[0][i] == playerOneSymbol || state[0][i] == playerTwoSymbol)
                    && (state[0][i] == state[1][i] && state[1][i] == state[2][i])) {
                return true;
            }
        }

        // Diagonals
        return (state[1][1] == playerOneSymbol || state[1][1] == playerTwoSymbol) &&
                ((state[0][0] == state[1][1] && state[1][1] == state[2][2])
                        || (state[2][0] == state[1][1] && state[1][1] == state[0][2])
                        || (state[1][0] == state[1][1] && state[1][1] == state[1][2])
                        || (state[0][1] == state[1][1] && state[1][1] == state[2][1]));
    }

    // Given a state, simply checks whether all spaces are occupied. Does not care or check if a player has won.
    private static boolean checkDraw(char[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (state[i][j] == emptySpaceSymbol) {
                    // If there is an empty space on the board, the game is not a tie
                    return false;
                }
            }
        }
        return true;

    }

    // Given a game state, return a new game state with move from the AI
    // It follows the algorithm in the PDF to ensure a tie (or win if possible)
    private static char[][] getCPUMove(char[][] gameState) {

        // Determine all available spaces
        List<int[]> emptySpaces = getValidMoves(gameState);
        // Check for winning move
        for (int[] i : emptySpaces) {
            char[][] newState = makeMove(i,playerTwoSymbol,gameState);
            if(checkWin(newState)){
                return newState;
            }
        }
        // Check for blocking move
        for (int[] i: emptySpaces) {
            char[][] newState = makeMove(i,playerOneSymbol,gameState);
            if (checkWin(newState)) {
                return makeMove(i,playerTwoSymbol,gameState);
            }
        }
        // Move on center space if possible
        for(int[] i:emptySpaces){
            if(Arrays.equals(i,new int[]{1,1})){
                return makeMove(new int []{1,1},playerTwoSymbol,gameState);
            }
        }
        // Move on corner spaces if possible
        ArrayList<int[]> availCorner=new ArrayList<>();
        for(int[] i : emptySpaces){
            if(Arrays.equals(i,new int[]{0,0}) || Arrays.equals(i, new int[]{0,2}) ||
                    Arrays.equals(i, new int[]{2,0}) || Arrays.equals(i,new int[]{2,2})){
                availCorner.add(i);

            }
        }
        if(!availCorner.isEmpty()){
            return makeMove(availCorner.get((int)(Math.random()*emptySpaces.size())),playerTwoSymbol,gameState);
        }
        return makeMove(emptySpaces.get((int)(Math.random()*emptySpaces.size())),playerTwoSymbol,gameState);
        // Move in any available spot
    }

    // Given a game state, return an ArrayList of [row, column] positions that are unclaimed on the board
    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> validMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (gameState[i][j] == emptySpaceSymbol) {
                    int[] move = {i, j};
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }

    // Given player names and the game history, display the past game as in the PDF sample code output
    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {
        // We have the names of the players in the format [playerOneName, playerTwoName]
        // Player one always gets 'X' while player two always gets 'O'
        // However, we do not know yet which player went first, but we'll need to know...
        // Hint for the above: which symbol appears after one turn is taken?

        // Hint: iterate over gameHistory using a loop
        System.out.println("Game history:");

        // Determine which player went first based on first move made
        char firstPlayerSymbol = ' ';
        char[][] firstGameState = gameHistory.get(0);
        for (char[] chars : firstGameState) {
            for (int j = 0; j < firstGameState[0].length; j++) {
                if (chars[j] != emptySpaceSymbol) {
                    firstPlayerSymbol = chars[j];
                    break;
                }
            }
            if (firstPlayerSymbol != emptySpaceSymbol) {
                break;
            }
        }

        // Print the game history
        for (int i = 0; i < gameHistory.size(); i++) {
            char[][] gameState = gameHistory.get(i);
            String playerName = i % 2 == 0 ? playerNames[0] : playerNames[1];
            char playerSymbol = i % 2 == 0 ? 'X' : 'O';

            // Print the board
            System.out.println("Move " + (i + 1) + ": " + playerName + " (" + playerSymbol + ")");
            System.out.println(displayGameFromState(gameState));

            // Print the move made by the player
            if (i == gameHistory.size() - 1) {
                System.out.println(playerName + " has won the game!");
            } else {
                char[][] nextGameState = gameHistory.get(i + 1);
                int moveRow = -1;
                int moveCol = -1;
                for (int row = 0; row < gameState.length; row++) {
                    for (int col = 0; col < gameState[0].length; col++) {
                        if (gameState[row][col] != nextGameState[row][col]) {
                            moveRow = row;
                            moveCol = col;
                            break;
                        }
                    }
                    if (moveRow != -1) {
                        break;
                    }
                }
                System.out.println(playerName + " played " + playerSymbol + " at (" + (moveRow + 1) + "," + (moveCol + 1) + ")");
            }

            System.out.println("__________________________");
        }
    }
}
