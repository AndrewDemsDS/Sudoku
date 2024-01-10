import java.util.InputMismatchException;
import java.lang.IllegalArgumentException;

/**
 * The Board class represents a Sudoku board with methods for displaying, reading, and validating the puzzle.
 */
public class Board {
    // The size of the Sudoku board
    public final int N;

    // 2D array to store the Sudoku values
    public int[][] tableau;

    // Filename to read and save the Sudoku board
    public static String filename;

    /**
     * Constructor to initialize the Sudoku board with size N and a filename.
     * N The size of the Sudoku board.
     */
    public Board(int N) {
        this.N = N;
        tableau = new int[N][N];
    }
    /**
     * Default constructor to initialize the Sudoku board with size N and a filename.
     */
    public Board(){
        this.N = 9;
        tableau = new int[N][N];
    }

    /**
     * Displays the Sudoku board with proper formatting.
     */
    public void displayBoard() {
        // Calculate the size of subgroups in the Sudoku board
        int subgridSize = (int) Math.sqrt(N);

        // Iterate through each row of the Sudoku board
        for (int i = 0; i < N; i++) {
            // Print horizontal lines to separate subgroups sqrt(N)
            if (i % subgridSize == 0) {
                for (int k = 0; k < subgridSize; k++) {
                    System.out.print("+");
                    for (int j = 0; j < N + 7; j++)
                        System.out.print("-");
                }
                System.out.println("+");
            }

            // Print vertical lines and Sudoku values for each column
            System.out.print("| ");
            for (int j = 0; j < N; j++) {
                if (tableau[i][j] < 0) {
                    System.out.printf(" (%-1d) ", Math.abs(tableau[i][j]));
                } else if (tableau[i][j] == 0) {
                    System.out.print("  .  ");
                } else {
                    System.out.printf("  %-1d  ", tableau[i][j]);
                }

                // Print vertical lines to separate subgroups
                if ((j + 1) % subgridSize == 0 && j != N - 1) {
                    System.out.print("| ");
                }
            }
            System.out.print("|");
            System.out.println();
        }

        // Print horizontal lines to complete the Sudoku board
        for (int k = 0; k < subgridSize; k++) {
            System.out.print("+");
            for (int j = 0; j < N + 7; j++)
                System.out.print("-");
        }
        System.out.println("+");
    }

    /**
     * Reads the Sudoku board from a file and populates the 2D array.
     */
    public void readBoard() {
        if (!checkValidity())
            System.exit(0);
        In fin = new In(filename);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                tableau[i][j] = fin.readInt();
            }
        fin.close();
    }

    /**
     * Checks the validity of a Sudoku puzzle by examining the rules.
     * - All numbers in the puzzle must be integers within the range [-N, N], where N is the size of the puzzle.
     * - Each row and column must not contain the same number more than once.
     * - Each sqrt(N) x sqrt(N) subgrid must not contain the same number more than once.
     * Returns true if the Sudoku puzzle is valid, false otherwise.
     */
    public boolean checkValidity() {
        // Open the input file using the default constructor
        In fin =  null;
        //  Check if the file exists
        try {
            fin = new In(filename);
        }catch (IllegalArgumentException e){
            System.out.print("File " + filename + " not found in the root directory");
            System.exit(0);
        }
        //  Test if the fin object is null

        if(fin.isEmpty()){
            System.out.println("File " + filename + " not found in the root directory ");
            System.exit(0);
        }
        // Initialize counters
        int count = 0;
        int limit = N * N;
        // Read integers from the file
        while (!fin.isEmpty()) {
            int temp = 0;

            // Attempt to read an integer from the file
            try {
                temp = fin.readInt();
            } catch (InputMismatchException e) {
                // Handle the case where a non-integer value is detected in the file
                System.out.println("Not an integer value detected in the file");
                System.exit(0);
            }

            // Check if the number is within the valid range
            if (temp > N || temp < -N) {
                System.out.println("Error: Illegal number in the input file!");
                return false;
            }

            // Increment the count of read values
            count++;
        }

        // Check if the number of values in the file matches the expected limit
        if (count < limit) {
            System.out.println("Error: Missing values from the file!");
            fin.close();
            return false;
        }

        // Close the file after reading the first time
        fin.close();

        // Open the input file again for reading the Sudoku values
        In fin2 = new In(filename);

        // Create a 2D array to store Sudoku values
        int[][] temp = new int[N][N];

        // Read Sudoku values from the file into the 2D array
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                temp[i][j] = fin2.readInt();
            }

        // Close the file after reading the Sudoku values
        fin2.close();

        return !sudokuIsValid(temp);
    }

    /**
     * Checks if a Sudoku puzzle is valid based on the rules.
     * Table 2D array representing the Sudoku puzzle.
     * Returns true if the Sudoku puzzle is valid, false otherwise.
     */
    public boolean sudokuIsValid(int[][] table) {
        // Arrays to check the validity of rows and columns

        // Check the validity of rows and columns
        for (int i = 0; i < N; i++) {
            int[] repeatCheckRow = new int[N];
            int[] repeatCheckCol = new int[N];

            for (int j = 0; j < N; j++) {
                if (table[i][j] != 0) {
                    repeatCheckRow[Math.abs(table[i][j]) - 1]++;
                }

                if (table[j][i] != 0) {
                    repeatCheckCol[Math.abs(table[j][i]) - 1]++;
                }
            }

            // Check if the same number appears more than once in a row
            for (int k = 0; k < N; k++) {
                if (repeatCheckRow[k] > 1) {
                    System.out.println("Error: This is not a valid Sudoku! Same row rule not met!");
                    return true;
                }

                if (repeatCheckCol[k] > 1) {
                    System.out.println("Error: This is not a valid Sudoku! Same column rule not met!");
                    return true;
                }
            }
        }
        int subgridSize = (int) Math.sqrt(N);

        for (int startRow = 0; startRow < N; startRow += subgridSize) {
            for (int startCol = 0; startCol < N; startCol += subgridSize) {
                int[] repeatCheck = new int[N];

                for (int i = startRow; i < startRow + subgridSize; i++) {
                    for (int j = startCol; j < startCol + subgridSize; j++) {
                        int value = Math.abs(table[i][j]);

                        if (value != 0) {
                            if (repeatCheck[value - 1] > 0) {
                                System.out.println("Error: This is not a valid Sudoku! Same box rule not met!");
                                return true; // Duplicate found
                            }
                            repeatCheck[value - 1]++;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Creates a copy of the current Sudoku board.
     * returns A copy of the Sudoku board.
     */
    private int[][] getTable() {
        int[][] temp = new int[N][N];
        for (int i = 0; i < temp.length; i++) {
            System.arraycopy(tableau[i], 0, temp[i], 0, temp.length);
        }
        return temp;
    }

    /**
     * Checks if a move is valid in the Sudoku board.
     * i The row index of the move.
     * j The column index of the move.
     * val The value to be placed in the cell.
     * returns true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int i, int j, int val) {
        // Check if the cell is empty
        int[][] temp = getTable();
        //Check if the cell has a negative value
        if(tableau[i - 1][j - 1]<0){
            System.out.println("Error: cell is already occupied!");
            return false;}
        if (tableau[i - 1][j - 1] == 0) {
            // Check the row for duplicates
            if (val == 0)
                return true;
            temp[i - 1][j - 1] = val;//
            for (int x = 0; x < N; x++) {
                if (Math.abs(temp[i - 1][x]) == val && x != j - 1) {
                    System.out.println("Error: Illegal value insertion! Same row rule not met!");
                    return false;
                }
            }

            // Check the column for duplicates
            for (int x = 0; x < N; x++) {
                if (Math.abs(temp[x][j - 1]) == val && x != i - 1) {
                    System.out.println("Error: Illegal value insertion! Same column rule not met!");
                    return false;
                }
            }

            // Check the subgrid for duplicates
            int subgridSize = (int) Math.sqrt(N);
            int startRow = (i - 1) / subgridSize * subgridSize;
            int startCol = (j - 1) / subgridSize * subgridSize;

            for (int x = startRow; x < startRow + subgridSize; x++) {
                for (int y = startCol; y < startCol + subgridSize; y++) {
                    if (Math.abs(temp[x][y]) == val && x != i - 1 && y != j - 1) {
                        System.out.println("Error: Illegal value insertion! Same box rule not met!");
                        return false;
                    }
                }
            }

            return true; // Valid move
        } else {
            if (tableau[i - 1][j - 1] != 0 && val != 0) {
                System.out.println("Error: cell is already occupied!");
                return false;
            } else return true;
        }
    }

    /**
     * Updates the Sudoku board with a valid move.
     * i The row index of the move.
     * j The column index of the move.
     * val The value to be placed in the cell.
     */
    public void updateBoard(int i, int j, int val) {
        tableau[i - 1][j - 1] = val;
    }

    /**
     * Saves the current state of the board to an output file.
     */
    public void saveBoard() {
        String filePath = "out-" + filename;

        Out fout = new Out(filePath);

        // Write Sudoku values to the output file
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fout.print(tableau[i][j] + " ");
            }
            fout.println();
        }

        fout.close();
        System.out.println("Saving game to " + filePath);
        System.out.println("bye!");
    }

    /**
     * Checks if the Sudoku board is fully filled.
     * returns true if the Sudoku board is fully filled, false otherwise.
     */
    public boolean checkIfSudokuIsFinished() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (tableau[i][j] == 0)
                    return false;
        return true;
    }
}
