package Core.Jood.core;

public class Position {
    public int row, col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public static final int ROW = Chessboard.ROW;
    public static final int COL = Chessboard.COL;

    public boolean valid() {
        return this.row <= ROW && this.row >= 1 && this.col <= COL && this.col >= 1;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
}