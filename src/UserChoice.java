/**
 * The UserChoice class represents a user's choice for a Sudoku move.
 */
public class UserChoice {
    private int row;         // Row index for the move
    private int column;         // Column index for the move
    private int val;       // Value to be placed in the cell
    private boolean isClear; // Flag indicating if the user wants to clear the cell

    /**
     * Constructor to create a UserChoice object with specified values.
     * i Row index for the move.
     * j Column index for the move.
     * val Value to be placed in the cell.
     * isClear Flag indicating if the user wants to clear the cell.
     */
    public UserChoice(int i, int j, int val, boolean isClear) {
        this.row = i;
        this.column = j;
        this.val = val;
        this.isClear = isClear;
    }
    /**
     * Default constructor to create a UserChoice object with predetermined values.
     **/
    public UserChoice(){
        this.row = 0;
        this.column = 0;
        this.val = 1;
        this.isClear = false;
    }

    /**
     * Gets the row index for the move.
     * return The row index.
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column index for the move.
     * return The column index.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Gets the value to be placed in the cell.
     * return The value.
     */
    public int getValue() {
        return val;
    }


    /**
     * Checks if the user wants to clear the cell.
     * return true if the user wants to clear the cell, false otherwise.
     */
    public boolean isClear() {
        return isClear;
    }
}
