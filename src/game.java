import java.util.Arrays;
import java.util.Scanner;

public class game {

    public static final int AMOUNT_OF_BOATS = 7;
    public static final int MAX_TOTAL_MOVES_UNTIL_END = 50;

    public static void main(String[] args) {

        boolean[][] history = new boolean[10][10];

        field sf = new field(AMOUNT_OF_BOATS, MAX_TOTAL_MOVES_UNTIL_END);

        System.out.println("     0  1  2  3  4  5  6  7  8  9");
        for (int i = 0; i < sf.fieldBoats.length; i++) {
            System.out.printf(" %2d ", i);
            for (int j = 0; j < sf.fieldBoats[i].length; j++) {
                System.out.print(" " + (sf.fieldBoats[i][j] ? "x" : "ø") + " ");
            }
            System.out.println("");
        }

        win winValidation = win.EXCEPTION;

        Scanner sc = new Scanner(System.in);

        while (winValidation == win.EXCEPTION) {

            System.out.println("     0  1  2  3  4  5  6  7  8  9");

            for (int i = 0; i < history.length; i++) {
                System.out.printf(" %2d ", i);
                for (int j = 0; j < history[i].length; j++) {
                    System.out.print(" " + (history[i][j] ? "x" : "ø") + " ");
                }
                System.out.println("");
            }

            int[] location = new int[2];

            Arrays.fill(location, -1);

            int x = -1;
            int y = -1;

            while (x == -1 || y == -1) {
                System.out.println("Where do you wanna shot? Answer with \"X, Y\" (X and Y can be between 0 and 9 (Including 0 and 9))");

                String answer = sc.nextLine();

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
                }
                if (x > 9 || x <= -1 || y > 9 || y <= -1 ) {
                    System.out.println("The value can only be between 0 and 9");
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

            sf.shoot(location);

            winValidation = sf.checkWin();
        }

    }

}
