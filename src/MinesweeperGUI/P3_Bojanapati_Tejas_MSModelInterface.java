package MinesweeperGUI;

/*
        Name:       Tejas Bojanapati
        Date:       3/31/24
        Period:     3

        Is this lab fully working? Yes
        If not, explain:
        If resubmitting, explain:
*/
public interface P3_Bojanapati_Tejas_MSModelInterface {
    boolean isInBounds(int row, int col);
    boolean isGameOver();
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