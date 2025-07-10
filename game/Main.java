package game;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int result;
        Map<String, Player> playerMap = new HashMap<>(Map.of(
                "1", new HumanPlayer(),
                "2", new ExceptionPlayer(),
                "3", new NullPlayer(),
                "4", new CheaterPlayer()
        ));

        Map<String, Boolean> boardMap = new HashMap<>(Map.of(
                "1", false,
                "2", true
        ));

        Map<String, Boolean> regulationsMap = new HashMap<>(Map.of(
                "1", true,
                "2", false
        ));

        Player[] players = new Player[2];
        System.out.println("\033[33m\033[1mWelcome to Tic-Tac-Toe!\033[0m");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                Boolean rhombIsOn, regulationsOn;
                System.out.println("\033[32mNew Game\033[0m");
                boolean errorHappened;
                do {
                    errorHappened = false;
                    System.out.println("\u001B[35mWhich type of board would you like to play on?\u001B[0m");
                    System.out.println("1 - Simple Board");
                    System.out.println("2 - Rhomb Board");
                    String boardType = scanner.nextLine();

                    rhombIsOn = boardMap.get(boardType);
                    if (rhombIsOn == null) {
                        errorHappened = true;
                        System.out.println("\033[31mIncorrect board type\033[0m");
                    }
                } while (errorHappened);

                for (int i = 0; i < 2; i++) {
                    do {
                        errorHappened = false;
                        System.out.println();
                        System.out.println("\u001B[35mAdd player #" + (i + 1) + "\u001B[0m (select the type below):");
                        System.out.println("1 - HumanPlayer");
                        System.out.println("2 - ExceptionPlayer");
                        System.out.println("3 - NullPlayer");
                        System.out.println("4 - CheaterPlayer");
                        String playerType = scanner.nextLine();

                        players[i] = playerMap.get(playerType);
                        if (players[i] == null) {
                            errorHappened = true;
                            System.out.println("\033[31mIncorrect player type\033[0m");
                        }

                    } while (errorHappened);
                }
                final Game game = new Game(false, players[0], players[1]);

                do {
                    errorHappened = false;
                    System.out.println("\u001B[35mDo you wanna play with debut regulations?\033[0m");
                    System.out.println("1 - YES");
                    System.out.println("2 - NO");
                    String regulations = scanner.nextLine();
                    regulationsOn = regulationsMap.get(regulations);
                    if (regulationsOn == null) {
                        errorHappened = true;
                    }

                } while (errorHappened);
                do {
                    errorHappened = false;
                    try {
                        int p = 0;
                        if (regulationsOn) {
                            System.out.println("Enter p:");
                            p = scanner.nextInt();
                        }
                        if (rhombIsOn) {
                            System.out.println("Enter size of the board:");
                            int m = scanner.nextInt();
                            System.out.println("Enter target number of symbols k");
                            int k = scanner.nextInt();
                            scanner.nextLine();
                            result = game.play(new RhombBoard(m, k, p));
                        } else {
                            System.out.println("Enter size of the board (two dimensions separated by space):");
                            int m = scanner.nextInt();
                            int n = scanner.nextInt();
                            System.out.println("Enter target number of symbols k");
                            int k = scanner.nextInt();
                            scanner.nextLine();
                            result = game.play(new MnkBoard(m, n, k, p));
                        }
                        System.out.println("\033[33m\033[1mGame result: " + result + "\033[0m");
                    } catch (InputMismatchException e) {
                        System.out.println("\033[31mInvalid input! Write data using only numbers\033[0m");
                        scanner.nextLine();
                        errorHappened = true;
                    }
                } while (errorHappened);
            }
        }
    }
}