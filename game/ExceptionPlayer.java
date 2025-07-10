package game;

public class ExceptionPlayer implements Player {
    @Override
    public Move move(final Position position, final Cell cell) {
        throw new RuntimeException();
    }
}
