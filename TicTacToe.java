//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    static char emptySpaceSymbol = ' ';
    static char playerOneSymbol = 'X';
    static char playerTwoSymbol = 'O';

    public TicTacToe() {
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean quit = false;
        ArrayList<char[][]> gameHistory = new ArrayList();

        for(String[] playerNames = new String[]{"", ""}; !quit; System.out.println()) {
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
            if (option.equals("1")) {
                System.out.println("Starting a one player game...");
                System.out.print("Input player name: ");
                playerNames[0] = scan.next();
                playerNames[1] = "Computer";
                gameHistory = runOnePlayerGame(playerNames);
            } else if (option.equals("2")) {
                System.out.println("Starting a two player game...");
                System.out.print("Player one's name: ");
                playerNames[0] = scan.nextLine();
                System.out.print("Player two's name: ");
                playerNames[1] = scan.nextLine();
                gameHistory = runTwoPlayerGame(playerNames);
            } else if (option.equalsIgnoreCase("D")) {
                if (gameHistory.isEmpty()) {
                    System.out.println("No match has been played yet.");
                } else {
                    System.out.println("Last match: " + gameHistory);
                    runGameHistory(playerNames, gameHistory);
                }
            } else if (option.equalsIgnoreCase("I")) {
                System.out.println(gameInstructions());
            } else if (option.equalsIgnoreCase("Q")) {
                System.out.println("Quitting game...");
                quit = true;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Thank you for playing!");
    }

    private static String gameInstructions() {
        System.out.println("Instructions:");
        System.out.println("Each player takes turns by placing symbols 'X' or 'O' on the board.");
        System.out.println("The first player to get three of their symbols in a row wins!");
        System.out.println("place your inputs by row and col.");
        System.out.println("please look at the board below.");
        return "   0   1   2\n0    |   |  \n\n  ---*---*---\n1    |   |  \n\n  ---*---*---\n2    |   |  \n\n";
    }

    private static String displayGameFromState(char[][] state) {
        StringBuilder gameString = new StringBuilder();

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
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

    private static char[][] getInitialGameState() {
        return new char[][]{{emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}, {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}, {emptySpaceSymbol, emptySpaceSymbol, emptySpaceSymbol}};
    }

    private static ArrayList<char[][]> runTwoPlayerGame(String[] playerNames) {
        boolean gameOver = false;
        int currentPlayer = coinToss(playerNames);
        char[][] gameState = getInitialGameState();
        ArrayList<char[][]> gameHistory = new ArrayList();
        gameHistory.add(getInitialGameState());

        while(!gameOver) {
            String boardString = displayGameFromState(gameState);
            System.out.println(boardString);
            char currentPlayerSymbol;
            if (currentPlayer == 0) {
                currentPlayerSymbol = playerOneSymbol;
            } else {
                currentPlayerSymbol = playerTwoSymbol;
            }

            gameState = runPlayerMove(playerNames[currentPlayer], currentPlayerSymbol, gameState);
            gameHistory.add(gameState);
            if (checkWin(gameState)) {
                System.out.println(displayGameFromState(gameState));
                System.out.println(playerNames[currentPlayer] + " wins!\n");
                gameOver = true;
            } else if (checkDraw(gameState)) {
                System.out.println(displayGameFromState(gameState));
                System.out.println("It's a draw!\n");
                gameOver = true;
            }

            if (currentPlayer == 0) {
                currentPlayer = 1;
            } else {
                currentPlayer = 0;
            }
        }

        return gameHistory;
    }

    private static ArrayList<char[][]> runOnePlayerGame(String[] playerNames) {
        boolean gameOver = false;
        int currentPlayer = coinToss(playerNames);
        char[][] gameState = getInitialGameState();
        ArrayList<char[][]> gameHistory = new ArrayList();
        gameHistory.add(getInitialGameState());

        while(!gameOver) {
            String boardString = displayGameFromState(gameState);
            System.out.println(boardString);
            char currentPlayerSymbol;
            if (currentPlayer == 0) {
                currentPlayerSymbol = playerOneSymbol;
            } else {
                currentPlayerSymbol = playerTwoSymbol;
            }

            if (currentPlayer == 0) {
                gameState = runPlayerMove(playerNames[currentPlayer], currentPlayerSymbol, gameState);
            } else {
                System.out.println(playerNames[1] + "'s turn.");
                gameState = getCPUMove(gameState);
            }

            gameHistory.add(gameState);
            if (checkWin(gameState)) {
                System.out.println(displayGameFromState(gameState));
                System.out.println(playerNames[currentPlayer] + " wins!\n");
                gameOver = true;
            } else if (checkDraw(gameState)) {
                System.out.println(displayGameFromState(gameState));
                System.out.println("It's a draw!\n");
                gameOver = true;
            }

            if (currentPlayer == 0) {
                currentPlayer = 1;
            } else {
                currentPlayer = 0;
            }
        }

        return gameHistory;
    }

    private static int coinToss(String[] playerNames) {
        System.out.println("Coin toss to see who goes first...");
        double num = Math.random();
        if (num > 0.5) {
            System.out.println(playerNames[1] + " plays first!");
            return 1;
        } else {
            System.out.println(playerNames[0] + " plays first!");
            return 0;
        }
    }

    private static char[][] runPlayerMove(String playerName, char playerSymbol, char[][] currentState) {
        boolean validMove = false;
        int[] currentMove = new int[]{0, 0};

        while(!validMove) {
            currentMove = getInBoundsPlayerMove(playerName);
            validMove = checkValidMove(currentMove, currentState);
            if (!validMove) {
                System.out.println("That space is invalid, try again.");
            }
        }

        return makeMove(currentMove, playerSymbol, currentState);
    }

    private static int[] getInBoundsPlayerMove(String playerName) {
        Scanner sc = new Scanner(System.in);
        System.out.println(playerName + "'s turn.");
        int row = 0;
        int col = 0;
        boolean inside = false;

        while(true) {
            while(!inside) {
                System.out.print(playerName + ", enter your row: ");
                row = sc.nextInt();
                System.out.print(playerName + ", enter your column: ");
                col = sc.nextInt();
                if (row < 3 && row >= 0 && col < 3 && col >= 0) {
                    inside = true;
                } else {
                    System.out.println("Invalid input, please try again.");
                }
            }

            return new int[]{row, col};
        }
    }

    private static boolean checkValidMove(int[] move, char[][] state) {
        return Character.toString(state[move[0]][move[1]]).equals(" ");
    }

    private static char[][] makeMove(int[] move, char symbol, char[][] currentState) {
        char[][] newState = new char[currentState.length][];

        for(int i = 0; i < currentState.length; ++i) {
            newState[i] = Arrays.copyOf(currentState[i], currentState[i].length);
        }

        newState[move[0]][move[1]] = symbol;
        return newState;
    }

    private static boolean checkWin(char[][] state) {
        int i;
        for(i = 0; i < 3; ++i) {
            if ((state[i][0] == playerOneSymbol || state[i][0] == playerTwoSymbol) && state[i][0] == state[i][1] && state[i][1] == state[i][2]) {
                return true;
            }
        }

        for(i = 0; i < 3; ++i) {
            if ((state[0][i] == playerOneSymbol || state[0][i] == playerTwoSymbol) && state[0][i] == state[1][i] && state[1][i] == state[2][i]) {
                return true;
            }
        }

        return (state[1][1] == playerOneSymbol || state[1][1] == playerTwoSymbol) && (state[0][0] == state[1][1] && state[1][1] == state[2][2] || state[2][0] == state[1][1] && state[1][1] == state[0][2] || state[1][0] == state[1][1] && state[1][1] == state[1][2] || state[0][1] == state[1][1] && state[1][1] == state[2][1]);
    }

    private static boolean checkDraw(char[][] state) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                if (state[i][j] == emptySpaceSymbol) {
                    return false;
                }
            }
        }

        return true;
    }

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

    private static ArrayList<int[]> getValidMoves(char[][] gameState) {
        ArrayList<int[]> validMoves = new ArrayList();

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                if (gameState[i][j] == emptySpaceSymbol) {
                    int[] move = new int[]{i, j};
                    validMoves.add(move);
                }
            }
        }

        return validMoves;
    }

    private static void runGameHistory(String[] playerNames, ArrayList<char[][]> gameHistory) {
        System.out.println("Game history:");
        char firstPlayerSymbol = ' ';
        char[][] firstGameState = (char[][])gameHistory.get(0);
        char[][] var4 = firstGameState;
        int var5 = firstGameState.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            char[] chars = var4[var6];

            for(int j = 0; j < firstGameState[0].length; ++j) {
                if (chars[j] != emptySpaceSymbol) {
                    firstPlayerSymbol = chars[j];
                    break;
                }
            }

            if (firstPlayerSymbol != emptySpaceSymbol) {
                break;
            }
        }

        for(int i = 0; i < gameHistory.size(); ++i) {
            char[][] gameState = (char[][])gameHistory.get(i);
            String playerName = i % 2 == 0 ? playerNames[0] : playerNames[1];
            char playerSymbol = i % 2 == 0 ? 88 : 79;
            System.out.println("Move " + (i + 1) + ": " + playerName + " (" + playerSymbol + ")");
            System.out.println(displayGameFromState(gameState));
            if (i == gameHistory.size() - 1) {
                System.out.println(playerName + " has won the game!");
            } else {
                char[][] nextGameState = (char[][])gameHistory.get(i + 1);
                int moveRow = -1;
                int moveCol = -1;

                for(int row = 0; row < gameState.length; ++row) {
                    for(int col = 0; col < gameState[0].length; ++col) {
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
