package ConsoleMinesweeper;

import java.util.Scanner;

/*
    Name:       Tejas Bojanapati
    Date:       3/20/23
    Period:     3
    
    Is this lab fully working? Yes
    If not, explain:
    If resubmitting, explain:
 */
public class P3_Bojanapati_Tejas_MinesweeperController {
    private P3_Bojanapati_Tejas_MinesweeperModel model;
    private Scanner scanner;
    boolean firstMove = true;

    public P3_Bojanapati_Tejas_MinesweeperController(int rows, int cols, int mines) {
        this.model = new P3_Bojanapati_Tejas_MinesweeperModel();
        this.model.restartGame(rows, cols, mines);
        this.scanner = new Scanner(System.in);
    }

    public void printBoard() {
        int rows = model.getRows();
        int cols = model.getCols();

        System.out.print("  ");
        int k = 0;
        for (int i = 0; i < cols; i++) {
            System.out.print(k++ + " ");
            if (k == 10){
                k = 0;
            }
        }

        System.out.print("\t  ");
        k = 0;
        for (int i = 0; i < cols; i++) {
            System.out.print(k++ + " ");
            if (k == 10){
                k = 0;
            }
        }
        System.out.println();

        k = 0;
        for (int i = 0; i < rows; i++) {
            System.out.print(k + " ");
            for (int j = 0; j < cols; j++) {
                if (model.isCovered(i, j) && !model.isFlagged(i, j)) {
                    System.out.print("_ ");
                } else if (model.isFlagged(i, j)) {
                    System.out.print("F ");
                } else if (model.isBomb(i, j)) {
                    System.out.print("* ");
                } else {
                    int numBombs = model.numNeighborsWithBombs(i, j);
                    if (numBombs == 0){
                        System.out.print("  ");
                    } else {
                        System.out.print(numBombs + " ");
                    }
                }
            }

            System.out.print("\t");

            System.out.print(k++ + " ");
            if (k == 10){
                k = 0;
            }
            for (int j = 0; j < cols; j++) {
                if (model.isBomb(i, j)) {
                    System.out.print("* ");
                } else {
                    int numBombs = model.numNeighborsWithBombs(i, j);
                    if (numBombs == 0){
                        System.out.print("  ");
                    } else {
                        System.out.print(numBombs + " ");
                    }
                }
            }
            System.out.println();
        }

        System.out.print("There are " + model.bombsLeft() + " mines left.");
    }

    public void play() {
        System.out.println("        _ ");
        System.out.println("  /\\/\\ (_)_ __   ___  _____      _____  ___ _ __   ___ _ __ ");
        System.out.println(" /    \\| | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|");
        System.out.println("/ /\\/\\ \\ | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |   ");
        System.out.println("\\/    \\/_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|   ");
        System.out.println("                                           |_|\n");
        printBoard();

        while (!model.isGameOver()) {
            System.out.print("\nWould you like to flag a cell or reveal a cell?\nEnter 'f' or 'r' > ");
            char action = scanner.nextLine().charAt(0);

            System.out.print("Enter row: ");
            int row = scanner.nextInt();
            System.out.print("Enter column: ");
            int col = scanner.nextInt();
            scanner.nextLine();

            if (action == 'f') {
                if (!model.isCovered(row, col)) {
                    System.out.println("You cannot flag a revealed cell.");
                    continue;
                }
                model.toggleFlag(row, col);
            } else if (action == 'r') {
                if (model.isFlagged(row, col)) {
                    System.out.println("You cannot reveal a flagged cell.");
                    continue;
                }

                if (firstMove) {
                    model.placeMines(row, col);
                    firstMove = false;
                }

                if (model.isBomb(row, col)) {
                    model.revealTile(row, col);
                } else if (model.numNeighborsWithBombs(row, col) == 0) {
                    model.uncoverClearTiles(row, col);
                } else {
                    model.revealTile(row, col);
                }

                if (model.isGameLost()) {
                    System.out.println();
                    printBoard();
                    System.out.println("\nSorry, you lose :(");
                    break;
                } else if (model.isGameWon()) {
                    System.out.println();
                    printBoard();
                    System.out.println("\nYou Win!!!");
                    break;
                }
            }
            System.out.println();
            printBoard();
        }

        System.out.println("\nGoodbye. Thanks for playing!");
        scanner.close();
    }
}