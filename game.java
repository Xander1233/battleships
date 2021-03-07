import java.util.Arrays;
import java.util.Scanner;

public class game {

    public static final int AMOUNT_OF_BOATS = 7;
    public static final int MAX_TOTAL_MOVES_UNTIL_END = 50;

    public static void main(String[] args) {

        int counter = 1; // Count the tries;

        boolean[][] history = new boolean[10][10];

        field sf = new field(AMOUNT_OF_BOATS, MAX_TOTAL_MOVES_UNTIL_END);

        win winValidation = win.EXCEPTION;

        Scanner sc = new Scanner(System.in);

        System.out.println();
        System.out.println("                         __/___            ");
        System.out.println("                   _____/______|           ");
        System.out.println("           _______/_____\\_______\\_____   ");
        System.out.println("           \\        Battleships       |   ");
        System.out.println("     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();

        while (winValidation == win.EXCEPTION) {

            System.out.println();
            System.out.println("      0  1  2  3  4  5  6  7  8  9");
            System.out.println("    --------------------------------");

            for (int i = 0; i < history.length; i++) {
                System.out.printf(" %2d |", i);
                for (int j = 0; j < history[i].length; j++) {
                    System.out.print(" " + (history[i][j] ? "X" : "ø") + " ");
                }
                System.out.println("|");
            }
            System.out.println("    --------------------------------");

            int[] location = new int[2];

            Arrays.fill(location, -1);

            int x = -1;
            int y = -1;

            while (x == -1 || y == -1) {
                System.out.println("\nWhere do you wanna shot? Answer with \"X, Y\" (X and Y can be between 0 and 9 (Including 0 and 9))");
                System.out.print("Location (Format: X, Y) > ");
                String answer = sc.nextLine();
                System.out.println();

                char[] answerChars = answer.toCharArray();

                boolean includesKomma = false;
                for (int i = 0; i < answerChars.length; i++) {
                    if (answerChars[i] == 0x2C) {
                        includesKomma = true;
                        i = answerChars.length;
                    }
                }

                if (!includesKomma) {
                    System.out.println("The answer must include a ','. Try it again.");
                    continue;
                }
                String[] answerSplitted = answer.split(" *, *");
                try {
                    x = Integer.parseInt(answerSplitted[0]);
                    y = Integer.parseInt(answerSplitted[1]);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("You need to specify both axes! Try it again.");
                    x = -1;
                    y = -1;
                    continue;
                } catch (NumberFormatException e) {
                    System.out.println("You need to specify both axes with valid number (0 - 9)! Try it again.");
                    x = -1;
                    y = -1;
                    continue;
                }
                if (x > 9 || x <= -1 || y > 9 || y <= -1 ) {
                    System.out.println("The value can only be between 0 and 9 (Including 0 and 9)");
                    x = -1;
                    y = -1;
                    continue;
                }
                if (history[x][y]) {
                    x = -1;
                    y = -1;
                    System.out.println("You already shot there.");
                }
            }

            location[0] = x;
            location[1] = y;
            history[x][y] = true;
            counter++;

            sf.shoot(location);

            winValidation = sf.checkWin();

            if (winValidation.equals(win.EXCEPTION)) System.out.println("\nTry " + counter + "/" + MAX_TOTAL_MOVES_UNTIL_END + ". Make sure to destroy all boats before you reach the Max. amount of tries!");
        }

        if (winValidation.equals(win.AI)) {
            System.out.println("No more tries left. You lost :c");
        } else {
            System.out.println("You destroyed all boats. You won!!! :D");
        }

    }

}
