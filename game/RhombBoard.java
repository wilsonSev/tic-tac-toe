package game;

public class RhombBoard extends MnkBoard {

    public RhombBoard(int sideLength, int k, int p) {
        super(2 * sideLength - 1, 2 * sideLength - 1, k, p);
    }

    @Override
    public void fillBoard() {
        int sideLength = m/2 + 1;
        for (int i = 0; i < m; i++) {
            int minY = Math.min(i + 1, m - i);
            for (int j = 0; j < n; j++) {
                int minX = Math.min(j + 1, n - j);
                if (minY + minX <= sideLength){
                    cells[i][j] = Cell.S;
                } else {
                    cells[i][j] = Cell.E;
                }
            }
        }
    }
}
