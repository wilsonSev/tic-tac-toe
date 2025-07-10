package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            try {
                out.println("Position");
                out.println(position);
                out.println("\033[34m" + cell + "\033[0m" + "'s move");
                out.println("Enter row and column");
                final Move move = new Move(in.nextInt(), in.nextInt(), cell);
                if (position.isValid(move)) {
                    return move;
                }
                out.println("\033[31mMove " + move + " is invalid. Please try again\033[0m");
            } catch (InputMismatchException e) {
                out.println("\033[31mInvalid input! Write only numbers\033[0m");
                in.nextLine();
            }
        }
    }
}
