package MinesweeperGUI;


public interface MSModelInterface {
    boolean isInBounds(int row, int col);
    boolean isGameNotOver();
    boolean isGameWon();
    boolean isGameLost();
    void restartGame(int rows, int cols, int mines);

    void uncoverClearTiles(int row, int col);
    boolean isCovered(int row, int col);
    void revealTile(int row, int col);

    int bombsLeft();
    boolean isBomb(int row, int col);
    int numNeighborsWithBombs(int row, int col);

    boolean isFlagged(int row, int col);
    void toggleFlag(int row, int col);
}