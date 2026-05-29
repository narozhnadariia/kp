package com.kp.game.core;

import java.util.Random;

public class Board {
    private TileType[][] grid;
    private Random random;

    public Board(int rows, int cols) {
        grid = new TileType[rows][cols];
        random = new Random();
    }

    public void fillBottomRowWithBananas() {
        int bottomRow = grid.length - 1;

        for (int col = 0; col < grid[bottomRow].length; col++) {
            grid[bottomRow][col] = TileType.BANANA;
        }
    }

    public void fillRandom() {
        TileType[] values = TileType.values();

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                TileType tile;

                do {
                    tile = values[random.nextInt(values.length)];
                } while (createsMatch(row, col, tile));

                grid[row][col] = tile;
            }
        }
    }

    private boolean createsMatch(int row, int col, TileType tile) {
        // check horizontal: two same tiles on the left
        if (col >= 2 &&
                grid[row][col - 1] == tile &&
                grid[row][col - 2] == tile) {
            return true;
        }

        // check vertical: two same tiles above
        if (row >= 2 &&
                grid[row - 1][col] == tile &&
                grid[row - 2][col] == tile) {
            return true;
        }

        return false;
    }

    public TileType getRandomTile() {//воно генерує кожного разу нову фішку для ходу
        TileType[] values = TileType.values();
        return  values[random.nextInt(values.length)];
    }
//наступні три методи коли я ставлю фішку вони зсувають ті фішки які були в тому напрямку якому я напишу
    public void shiftRowLeft(int row,TileType newTile) {
        for (int col = grid[row].length - 1; col >0; col--) {
            grid[row][col] = grid[row][col-1];
        }
        grid[row][0] = newTile;
    }
    public void shiftRowRight(int row,TileType newTile) {
        for (int col = 0; col < grid[row].length-1; col++) {
            grid[row][col] = grid[row][col+1];
        }
        grid[row][grid[row].length - 1] = newTile;
    }
    public void shiftColTop(int col,TileType newTile) {
        for (int row = grid.length - 1 ; row >0; row--) {
            grid[row][col] = grid[row-1][col];
        }
        grid[0][col] = newTile;
    }

    public int culculateHorizontalScore() {
        int totalScore = 0;

        for (int row = 0; row < grid.length; row++) {
            int count = 1;

            for (int col = 1; col < grid[row].length; col++) {
                if (grid[row][col] == grid[row][col - 1]) {
                    count++;
                } else {
                    if (count >= 3) {
                        totalScore += grid[row][col - 1].getPoints(count);
                    }
                    count = 1;
                }
            }

            if (count >= 3) {
                totalScore += grid[row][grid[row].length - 1].getPoints(count);
            }
        }

        return totalScore;
    }

    public int calculateVerticalScore() {
        int totalScore = 0;

        for (int col = 0; col < grid[0].length; col++) {
            int count = 1;

            for (int row = 1; row < grid.length; row++) {
                if (grid[row][col] == grid[row - 1][col]) {
                    count++;
                } else {
                    if (count >= 3) {
                        totalScore += grid[row - 1][col].getPoints(count);
                    }
                    count = 1;
                }
            }

            if (count >= 3) {
                totalScore += grid[grid.length - 1][col].getPoints(count);
            }
        }
        return totalScore;

    }


    public void clearMatchesAndFill() {//коли склалась комбінація то тут фішки спускаються вниз і нові заповняються
        boolean[][] toClear = new boolean[grid.length][grid[0].length];

        // горизонтальні
        for (int row = 0; row < grid.length; row++) {
            int count = 1;

            for (int col = 1; col < grid[row].length; col++) {
                if (grid[row][col] == grid[row][col - 1]) {
                    count++;
                } else {
                    if (count >= 3) {
                        for (int k = col - count; k < col; k++) {
                            toClear[row][k] = true;
                        }
                    }
                    count = 1;
                }
            }

            if (count >= 3) {
                for (int k = grid[row].length - count; k < grid[row].length; k++) {
                    toClear[row][k] = true;
                }
            }
        }

        // вертикальні
        for (int col = 0; col < grid[0].length; col++) {
            int count = 1;

            for (int row = 1; row < grid.length; row++) {
                if (grid[row][col] == grid[row - 1][col]) {
                    count++;
                } else {
                    if (count >= 3) {
                        for (int k = row - count; k < row; k++) {
                            toClear[k][col] = true;
                        }
                    }
                    count = 1;
                }
            }

            if (count >= 3) {
                for (int k = grid.length - count; k < grid.length; k++) {
                    toClear[k][col] = true;
                }
            }
        }

        // очищення
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (toClear[row][col]) {
                    grid[row][col] = null;
                }
            }
        }

        // падіння вниз
        for (int col = 0; col < grid[0].length; col++) {
            int writeRow = grid.length - 1;

            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][col] != null) {
                    grid[writeRow][col] = grid[row][col];
                    if (writeRow != row) {
                        grid[row][col] = null;
                    }
                    writeRow--;
                }
            }
            while (writeRow >= 0) {
                grid[writeRow][col] = getRandomTile();
                writeRow--;
            }
        }
    }

    public void printBoard () {
        TileType[][] grid = getGrid();

        for (TileType[] tileTypes : grid) {
            for (int col = 0; col < tileTypes.length; col++) {
               System.out.print((tileTypes[col] != null ? tileTypes[col].getSymbol() : " " )+ "\t");
            }
            System.out.println();
        }
    }

    public int calculateTotalScore() {
        return culculateHorizontalScore() + calculateVerticalScore();
    }

    public Board copy() {
        Board copyBoard = new Board(grid.length, grid[0].length);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                copyBoard.grid[row][col] = this.grid[row][col];
            }
        }

        return copyBoard;
    }
    public int simulateMoveScore(Direction direction, int index) {
        Board copyBoard = this.copy();
        TileType newTile = copyBoard.getRandomTile();

        if (direction == Direction.LEFT) {
            copyBoard.shiftRowLeft(index, newTile);
        } else if (direction == Direction.RIGHT) {
            copyBoard.shiftRowRight(index, newTile);
        } else if (direction == Direction.TOP) {
            copyBoard.shiftColTop(index, newTile);
        }

        return copyBoard.culculateHorizontalScore();
    }

    // бомба
//    public void explodeRandomArea() {
//        int centerRow = random.nextInt(grid.length);
//        int centerCol = random.nextInt(grid[0].length);
//
//        for (int row = centerRow - 1; row <= centerRow + 1; row++) {
//            for (int col = centerCol - 1; col <= centerCol + 1; col++) {
//                if (row >= 0 && row < grid.length && col >= 0 && col < grid[0].length) {
//                    grid[row][col] = null;
//                }
//            }
//        }
//
//        fillEmptyCells();
//    }
//
//    private void fillEmptyCells() {
//        for (int col = 0; col < grid[0].length; col++) {
//            int writeRow = grid.length - 1;
//
//            for (int row = grid.length - 1; row >= 0; row--) {
//                if (grid[row][col] != null) {
//                    grid[writeRow][col] = grid[row][col];
//
//                    if (writeRow != row) {
//                        grid[row][col] = null;
//                    }
//
//                    writeRow--;
//                }
//            }
//
//            while (writeRow >= 0) {
//                grid[writeRow][col] = getRandomTile();
//                writeRow--;
//            }
//        }
//    }
//

    // поміняти плити місцями
//    public void swapTiles(int row1, int col1, int row2, int col2) {
//        TileType temp = grid[row1][col1];
//        grid[row1][col1] = grid[row2][col2];
//        grid[row2][col2] = temp;
//    }
    //


    public int getRowsSum() {
        return grid.length;
    }

    public int getColsSum() {
        return grid[0].length;
    }

    public TileType[][] getGrid() {
        return grid;
    }

    public TileType getTile(int row, int col) {
        return grid[row][col];
    }

    public void setTile(int row, int col, TileType tile) {
        grid[row][col] = tile;
    }
}