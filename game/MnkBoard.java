package game;

import java.util.Arrays;
import java.util.Map;

public class MnkBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.S, ' '
    );

    protected final Cell[][] cells;
    protected Cell turn;
    protected final int m, n, k;
    protected int emptyCells = 0;
    protected int pCounter;
    protected int iCounter = 1;

    public MnkBoard(int m, int n, int k, int pCounter) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[m][n];
        fillBoard();
        turn = Cell.X;
        this.pCounter = pCounter;
    }

     public void fillBoard() {
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
    }

    @Override
    public Position getPosition() {
        return new Position() {
            @Override
            public boolean isValid(final Move move) {
                return MnkBoard.this.isValid(move);
            }

            @Override
            public Cell getCell(final int r, final int c) {
                return cells[r][c];
            }

            @Override
            public String toString() {
                return MnkBoard.this.toString();
            }
        };
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private boolean checkDiagonal(Move move, int direction) {
        int counter = 0;
        for (int i = 1; i <= k - 1; i++) {
            boolean isUpperBound = false;
            boolean isLowerBound = false;
            if ((move.getRow() + i < m) && (move.getColumn() + i * direction < n) && (move.getColumn() + i * direction >= 0)) {
                if (cells[move.getRow() + i][move.getColumn() + i * direction] == move.getValue()) {
                    counter++;
                } else {
                    isLowerBound = true;
                }
            }

            if (((move.getRow() - i) >= 0) && ((move.getColumn() - i * direction) >= 0) && ((move.getColumn() - i * direction) < n)) {
                if (cells[move.getRow() - i][move.getColumn() - i * direction] == move.getValue()) {
                    counter++;
                } else {
                    isUpperBound = true;
                }
            }
            if (counter == k - 1){
                return true;
            }

            if (isUpperBound && isLowerBound) {
                break;
            }
        }
        return false;
    }

    @Override
    public Result makeMove(final Move move) {

        cells[move.getRow()][move.getColumn()] = move.getValue();
        emptyCells++;
        int iStart = Math.max(move.getRow() - k + 1, 0);
        int iEnd = Math.min(move.getRow() + k, m);
        int jStart = Math.max(move.getColumn() - k + 1, 0);
        int jEnd = Math.min(move.getColumn() + k, m);

        //Checking the column
        int counter = 0;
        for (int i = iStart; i < iEnd; i++) {
            if (cells[i][move.getColumn()] == move.getValue()) {
                counter++;
                if (counter == k) {
                    System.out.println(this);
                    return Result.WIN;
                }
            } else {
                counter = 0;
            }
        }

        //Checking the row
        counter = 0;
        for (int i = jStart; i < jEnd; i++) {
            if (cells[move.getRow()][i] == move.getValue()) {
                counter++;
                if (counter == k) {
                    System.out.println(this);
                    return Result.WIN;
                }
            } else {
                counter = 0;
            }
        }

        //Checking the diagonals
        if (checkDiagonal(move, 1) || checkDiagonal(move, -1)) {
            System.out.println(this);
            return Result.WIN;
        }

        if (emptyCells == n * m){
            System.out.println(this);
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        pCounter--;
        iCounter++;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        boolean checker = true;
        if (pCounter > 0) {
            int height = -m % 2 + 2 * iCounter;
            int width = -n % 2 + 2 * iCounter;

            int centerRow = m / 2;
            int centerCol = n / 2;

            int rowMin = centerRow - (height / 2);
            int rowMax = centerRow + (height / 2);
            int colMin = centerCol - (width / 2);
            int colMax = centerCol + (width / 2);
            checker = move.getRow() >= rowMin && move.getRow() <= rowMax
                    && move.getColumn() >= colMin && move.getColumn() <= colMax;
        }
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == MnkBoard.this.getCell() && checker;
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        int lm = String.valueOf(m).length();
        final StringBuilder sb = new StringBuilder(" ".repeat(lm + 3));

        for (int i = 0; i < n; i++) {
            String el = String.format("%-" + (3) + "s", i);
            sb.append(el);
        }
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            String el = String.format("%-" + (lm + 3) + "s", r);
            sb.append(el);
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append("  ");
            }
        }
        return sb.toString();
    }
}
