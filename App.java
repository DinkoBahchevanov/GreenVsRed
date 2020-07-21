package com.company;

import com.company.constants.ColourConstants;
import com.company.constants.NumberConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class App {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Cell[][] grid;
    private static int[][] binaryMatrix;

    /**
     * This is the main(logic) method where we invoke all methods that we need
     * for the program to run successfully.
     *
     * @throws IOException
     */
    public void run() throws IOException {
        grid = initGrid();

        int[] finalInput = Arrays.stream(reader.readLine().split(", "))
                .mapToInt(Integer::parseInt).toArray();

        int colOfSelectedCell = finalInput[0];
        int rowOfSelectedCell = finalInput[1];
        int generationCount = finalInput[2];

        //Validation of final input (cell coordinates and number of generations)
        while (rowOfSelectedCell < 0 || rowOfSelectedCell >= grid.length
                || colOfSelectedCell < 0 || colOfSelectedCell >= grid[rowOfSelectedCell].length) {

            System.out.print("Invalid input, enter again: ");
            finalInput = Arrays.stream(reader.readLine().split(", "))
                    .mapToInt(Integer::parseInt).toArray();

            colOfSelectedCell = finalInput[0];
            rowOfSelectedCell = finalInput[1];
            generationCount = finalInput[2];

        }

        for (int i = 0; i < generationCount; i++) {
            activateNewGeneration();
            if (grid[rowOfSelectedCell][colOfSelectedCell].getColour().equals(ColourConstants.GREEN)) {
                grid[rowOfSelectedCell][colOfSelectedCell]
                        .setGreenCount(grid[rowOfSelectedCell][colOfSelectedCell].getGreenCount() + 1);
            }
        }

        System.out.println(grid[rowOfSelectedCell][colOfSelectedCell].getGreenCount());
    }

    /**
     * In this method we check for every cell in the grid if it needs
     * change or not. Finally, we invoke the doChange method,
     * which activates the new Generation.
     */
    private void activateNewGeneration() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].checkForChange(i, j, grid, binaryMatrix);
            }
        }
        doChange();
    }

    /**
     * In this method we change colour of every cell in the grid
     * if it needs change or not. Respectively, we do change
     * the binaryMatrix (which contains integer values of 0s and 1s).
     */
    private void doChange() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j].needChange()) {
                    if (grid[i][j].getColour().equals(ColourConstants.RED)) {
                        grid[i][j].setColour(ColourConstants.GREEN);
                        binaryMatrix[i][j] = 1;
                        continue;
                    }
                    grid[i][j].setColour(ColourConstants.RED);
                    binaryMatrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * In this method we initialize the grid. First we read rows and cols
     * and after that we fill the grid .
     *
     * @throws IOException
     */
    private Cell[][] initGrid() throws IOException {
        int[] coordinates = Arrays.stream(reader.readLine().split(", "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int col = coordinates[0];
        int row = coordinates[1];

        while (col > row || row > 1000) {
            System.out.print("Invalid input of coordinates for row and col, enter again: ");
            coordinates = Arrays.stream(reader.readLine().split(", "))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            col = coordinates[0];
            row = coordinates[1];
        }
        return fillGrid(row, col);
    }

    /**
     * In this method we fill the grid and binaryMatrix with the given input.
     *
     * @param row - rows of grid and matrix
     * @param col - cols of grid and matrix
     * @throws IOException
     */
    private Cell[][] fillGrid(int row, int col) throws IOException {
        grid = new Cell[row][col];
        binaryMatrix = new int[row][col];

        for (int i = 0; i < grid.length; i++) {
            String[] inputRow = reader.readLine().split("");
            for (int j = 0; j < grid[i].length; j++) {
                binaryMatrix[i][j] = Integer.parseInt(inputRow[j]);

                if (inputRow[j].equals(NumberConstants.ZERO)) {
                    grid[i][j] = new Cell(ColourConstants.RED);
                    continue;
                }
                grid[i][j] = new Cell(ColourConstants.GREEN);
            }
        }
        return grid;
    }
}
