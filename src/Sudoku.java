/**
 *  Author: Andreas Demosthenous
 *  Written: 09/11/2023
 *  Last updated: 26/11/2023
 *
 * Compilation: javac -cp .;./stdlib.jar Board.java UserChoice.java Sudoku.java
 * Execution: java -cp .;./stdlib.jar Sudoku <N> <game-file> *
 *
 * The Java program is a Sudoku game.
 * It enables users to interactively input moves to fill in a Sudoku board, ensuring adherence to game rules.
 * The program includes classes for managing the Sudoku board, user choices, and game logic, providing a console-based interface for playing and saving Sudoku games.
 */
import java.util.Scanner;

/**
 * The Sudoku class represents the main class for playing the Sudoku game.
 */
public class Sudoku {
    static Scanner scanner = new Scanner(System.in);
    /**
     * Plays the Sudoku game using the provided board.
     * N The size of the Sudoku board.
     * B The Sudoku board to play.
     */
    public void play(int N, Board b) {
        // Initialize Scanner to get user input

        // Read the Sudoku board from the file
        b.readBoard();

        // Display the initial state of the Sudoku board
        b.displayBoard();
        OutputDefault(N);

        // Main game loop
        while (true) {
            // Get user input for the move
            UserChoice choice = new UserChoice();
            choice = getUserInput(choice,N, b);
            int i = choice.getRow();
            int j = choice.getColumn();
            int val = choice.getValue();

            // Check if the user wants to save and end the game
            if (i == 0 && j == 0 && val == 0) {
                b.saveBoard();
                break;
            } else if (i >= 1 && i <= N && j >= 1 && j <= N && val >= 0 && val <= N) {
                if (choice.isClear()) {
                    // Clear the cell if the user chose to clear
                    if (b.isValidMove(i, j, 0)) {
                        b.updateBoard(i, j, 0);
                        System.out.println("Cell (" + i + "," + j + ") cleared.");
                    }
                } else {
                    // Make a move and update the board
                    if (b.isValidMove(i, j, val)) {
                        b.updateBoard(i, j, val);
                        System.out.println("Value inserted!");
                    } else {
                        // Invalid move, display the board and try again
                        b.displayBoard();
                        OutputDefault(N);
                        continue;
                    }
                }
            } else {
                // Invalid input for i, j, or val, display the board and try again
                System.out.println("Error: i, j, or val is outside the allowed range [1.." + N + "]!");
                b.displayBoard();
                OutputDefault(N);
                continue;
            }

            // Check if the Sudoku puzzle is completed
            if (b.checkIfSudokuIsFinished()) {
                System.out.println("Game completed!!!");
                b.displayBoard();
                break;
            }

            // Display the updated board
            b.displayBoard();
            OutputDefault(N);
        }

        // Close the Scanner
        scanner.close();
    }

    /**
     * Gets and validates user input for the Sudoku move.
     * scanner The Scanner to read user input.
     * N The size of the Sudoku board.
     * b The Sudoku board.
     * returns the UserChoice object representing the user's move.
     */
    private UserChoice getUserInput(UserChoice choice,int N, Board b) {
        do {
            String input = scanner.next();
            choice = parseInput(input);
            if (choice == null) {
                System.out.println("Error: wrong format of command!");
                b.displayBoard();
                OutputDefault(N);
            }
        } while (choice == null);
        return choice;
    }
    /**
     * Parses the user input string to create a UserChoice object.
     * input The user input string.
     * returns the UserChoice object representing the user's move, or null if the input is invalid.
     */
    public static UserChoice parseInput(String input) {
        if(!input.matches("[0-9]+,[0-9]+=[0-9]+")){
            return null;}
        // Split the input string using '=' or ',' as separators iff the input matches the regex refrence input
        String[] parts = input.split("[,=]");

        // Check if the input has three parts (i, j, and val)
        if (parts.length == 3) {
            int i , j, val;
            try {
                // Parse the string parts to integers
                i = Integer.parseInt(parts[0]);
                j = Integer.parseInt(parts[1]);
                val = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                return null; // Return null if parsing fails
            }

            // Check if it's a clear command
            boolean isClear = val == 0;

            // Create and return a new UserChoice object
            return new UserChoice(i, j, val, isClear);
        } else {
            return null; // Invalid input format
        }
    }
    /**
     * Outputs the default instructions for the user.
     * N The size of the Sudoku board.
     */
    private static void OutputDefault(int N) {
        System.out.println("Enter your command in the following format:");
        System.out.println("+ i,j=val: for entering val at position (i,j)");
        System.out.println("+ i,j=0  : for clearing cell (i,j)");
        System.out.println("+ 0,0=0  : for saving and ending the game");
        System.out.println("Notice: i, j, val numbering is from [1.." + N + "]");
        System.out.print("> ");
    }

    /**
     * The main method to start the Sudoku game.
     */
    public static void main(String[] args) {
        // Check if the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.out.println("Please give the dimension N followed by a <game-file> as the only 2 arguments");
            return;
        }

        int N;

        // Parse and validate the N variable
        try {
            N = Integer.parseInt(args[0]);

            if (N != 4 && N != 9) {
                System.out.println("The allowed value for N is either 4 or 9!");
                System.out.println("Please re-run the program with a valid value for N.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please give the dimension N followed by a <game-file> as the only 2 arguments");
            return;
        }

        // Get the filename from command-line arguments
        Board.filename = args[1];
        // Create a new Sudoku board and start the game
        Board b = new Board(N);
        Sudoku newGame = new Sudoku();
        newGame.play(N, b);
        scanner.close();    // Closes the scanner object
    }
}
