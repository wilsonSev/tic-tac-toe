package game;

public class NullPlayer implements Player {
    @Override
    public Move move(Position position, Cell cell) {
        return null;
    }
}
