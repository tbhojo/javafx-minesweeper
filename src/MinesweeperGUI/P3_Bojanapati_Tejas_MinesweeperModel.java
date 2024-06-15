package MinesweeperGUI;

import java.util.Random;

/*
    Name:       Tejas Bojanapati
    Date:       3/31/24
    Period:     3
    
    Is this lab fully working? Yes
    If not, explain:
    If resubmitting, explain:
 */
public class P3_Bojanapati_Tejas_MinesweeperModel implements P3_Bojanapati_Tejas_MSModelInterface {
    private int rows;
    private int cols;
    private int numMines;
    private int numFlags;
    private boolean[][] isCovered;
    private boolean[][] isFlagged;
    private boolean[][] isBomb;
    private int[][] numNeighborBombs;

    public P3_Bojanapati_Tejas_MinesweeperModel() {
        restartGame(10, 10, 10);
    }
    public P3_Bojanapati_Tejas_MinesweeperModel(int rows, int cols, int mines) {
        restartGame(rows, cols, mines);
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public int getNumMines(){
        return numMines;
    }

    @Override
    public boolean isInBounds(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    @Override
    public boolean isGameOver() {
        return isGameLost() || isGameWon();
    }

    @Override
    public boolean isGameWon() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isCovered[i][j] && !isBomb[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean isGameLost() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!isCovered[i][j] && isBomb[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void restartGame(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = mines;
        this.numFlags = 0;

        this.isCovered = new boolean[rows][cols];
        this.isFlagged = new boolean[rows][cols];
        this.isBomb = new boolean[rows][cols];
        this.numNeighborBombs = new int[rows][cols];

        resetArray();
    }

    public void resetArray() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                isCovered[i][j] = true;
                isFlagged[i][j] = false;
                isBomb[i][j] = false;
                numNeighborBombs[i][j] = 0;
            }
        }
    }

    public void placeMines(int row, int col) {
        Random rand = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int randRow = rand.nextInt(rows);
            int randCol = rand.nextInt(cols);
            if (!isBomb[randRow][randCol] && (randRow != row || randCol != col)) {
                isBomb[randRow][randCol] = true;
                updateNeighborCounts(randRow, randCol);
                minesPlaced++;
            }
        }
    }

    private void updateNeighborCounts(int row, int col) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int newRow = row + dr;
                int newCol = col + dc;
                if (isInBounds(newRow, newCol)) {
                    numNeighborBombs[newRow][newCol]++;
                }
            }
        }
    }

    @Override
    public void uncoverClearTiles(int row, int col) {
        if (!isInBounds(row, col) || !isCovered[row][col] || isBomb(row, col) || isFlagged(row, col)) {
            return;
        }

        revealTile(row, col);

        if (numNeighborBombs[row][col] != 0){
            return;
        }

        uncoverClearTiles(row - 1, col); // up
        uncoverClearTiles(row, col + 1); // right
        uncoverClearTiles(row + 1, col); // down
        uncoverClearTiles(row, col - 1); // left
        uncoverClearTiles(row - 1, col + 1); // top right
        uncoverClearTiles(row - 1, col - 1); // top left
        uncoverClearTiles(row + 1, col + 1); // bottom right
        uncoverClearTiles(row + 1, col - 1); // bottom left
    }

    @Override
    public boolean isCovered(int row, int col) {
        return isInBounds(row, col) && isCovered[row][col];
    }

    @Override
    public void revealTile(int row, int col) {
        if (!isInBounds(row, col) || isFlagged[row][col]) {
            return;
        }
        isCovered[row][col] = false;
    }

    @Override
    public int bombsLeft() {
        return numMines - numFlags;
    }

    @Override
    public boolean isBomb(int row, int col) {
        return isInBounds(row, col) && isBomb[row][col];
    }

    @Override
    public int numNeighborsWithBombs(int row, int col) {
        return isInBounds(row, col) ? numNeighborBombs[row][col] : 0;
    }

    @Override
    public boolean isFlagged(int row, int col) {
        return isInBounds(row, col) && isFlagged[row][col];
    }

    @Override
    public void toggleFlag(int row, int col) {
        if (isInBounds(row, col) && isCovered[row][col]) {
            isFlagged[row][col] = !isFlagged[row][col];
            if (isFlagged[row][col]) {
                numFlags++;
            } else {
                numFlags--;
            }
        }
    }
}