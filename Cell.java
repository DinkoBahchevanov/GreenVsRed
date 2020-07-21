package com.company;

import com.company.Constants.ColourConstants;

public class Cell implements Changeble {

    private String colour;
    private int greenCount;
    private boolean needChange;

    public Cell(String colour) {
        this.setColour(colour);
        this.setStartingGreenCount();
        this.needChange = false;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean needChange() {
        return needChange;
    }

    public int getGreenCount() {
        return greenCount;
    }

    /**
     * Here we check and set the color of the Cell in the first Generation (input data).
     */
    private void setStartingGreenCount() {
        if (this.getColour().equals(ColourConstants.GREEN)) {
            greenCount = 1;
            return;
        }
        greenCount = 0;
    }

    public void setGreenCount(int greenCount) {
        this.greenCount = greenCount;
    }

    /**
     * In this method we check if current cell needs change or not.
     *
     * @param row    - row of current cell
     * @param col    - col of current cell
     * @param grid   - the grid that contains all the Cells
     * @param matrix - the binaryMatrix that holds integer values of 0s and 1s.
     */

    @Override
    public void checkForChange(int row, int col, Cell[][] grid, int[][] matrix) {
        int count = 0;
        if (isInBounds(row, col + 1, grid)) {
            count += matrix[row][col + 1];
        }
        if (isInBounds(row + 1, col + 1, grid)) {
            count += matrix[row + 1][col + 1];
        }
        if (isInBounds(row + 1, col, grid)) {
            count += matrix[row + 1][col];
        }
        if (isInBounds(row + 1, col - 1, grid)) {
            count += matrix[row + 1][col - 1];
        }
        if (isInBounds(row, col - 1, grid)) {
            count += matrix[row][col - 1];
        }
        if (isInBounds(row - 1, col - 1, grid)) {
            count += matrix[row - 1][col - 1];
        }
        if (isInBounds(row - 1, col, grid)) {
            count += matrix[row - 1][col];
        }
        if (isInBounds(row - 1, col + 1, grid)) {
            count += matrix[row - 1][col + 1];
        }

        if (this.colour.equals(ColourConstants.RED)) {
            this.needChange = count == 3 || count == 6;
        } else if (this.colour.equals(ColourConstants.GREEN)) {
            this.needChange = count != 2 && count != 3 && count != 6;
        }

    }

    /**
     * In this method we check if a neighbour cell is in bounds.
     */
    private boolean isInBounds(int row, int col, Cell[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }
}