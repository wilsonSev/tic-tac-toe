package game;

public class CheaterPlayer implements Player {
    public Move move(Position position, Cell cell) {
        Board board = (Board) position;
        final Move cheaterMove = new Move(0, 0, cell);
        board.makeMove(cheaterMove);
        return null;
    }
}
