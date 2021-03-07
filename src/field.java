public class field {

    private final int fieldLength = 10;
    private int boatsALife = 0, shootCounter = 0;
    private boat[] boats = new boat[7];
    public boolean[][] fieldBoats = new boolean[10][10];
    private final int totalMoves;

    field(int amountOfBoats, int totalMoves) {
        this.totalMoves = totalMoves;
        this.boats = new boat[amountOfBoats];
        generateBoats(amountOfBoats);
    }

    void generateBoats(int amount) {
        for (int i = 0; i < amount; i++) {
            int length = (int) Math.floor(Math.random() * 2) + 1; // Generate random boat length between 1 and 3 (including 1 and 3)

            int[][] location = new int[length][2];

            int horOrVer = (int) (Math.random() * 3) + 1;

            boolean horizontal = horOrVer % 2 == 1;

            int x, y;
            do {
                x = this.getRandomFieldNumber();
                y = this.getRandomFieldNumber();
            } while (!validBoatLocation(new int[]{ x, y }, length));

            location[0] = new int[]{x, y};
            fieldBoats[x][y] = true;

            if (x > this.fieldLength - length - 1 && horOrVer == 1) {
                horOrVer = 3;
            } else if (x < length && horOrVer == 3) {
                horOrVer = 1;
            } else if (y > this.fieldLength - length - 1 && horOrVer == 2) {
                horOrVer = 4;
            } else if (y < length && horOrVer == 4) {
                horOrVer = 2;
            }

            for (int j = 1; j < length; j++) {
                if (horizontal) {
                    x = horOrVer == 3 ? x - j : x + j;
                } else {
                    y = horOrVer == 4 ? y - j : y + j;
                }
                location[j] = new int[]{ x, y };
                fieldBoats[x][y] = true;
            }

            boats[i] = new boat(location, length);
        }
        boatsALife = this.boats.length;
    }

    public void shoot(int[] location) {

        boolean missed = true;

        for (int i = 0; i < this.boats.length && missed; i++) {
            if (this.boats[i].getAlive()) continue;
            hitStatus hit = this.boats[i].hit(location);
            if (hit == hitStatus.DEAD) {
                boatsALife--;
                missed = false;
                System.out.println("Killed! " + (boatsALife) + " boats remaining. \uD83C\uDF89");
            }
            if (hit == hitStatus.HBNO) {
                missed = false;
                System.out.println("Hit! :D");
            }
        }

        if (missed)
            System.out.println("Missed :/");

        this.shootCounter++;
    }

    public win checkWin() {
        win res = win.EXCEPTION;
        if (this.shootCounter >= this.totalMoves || this.boatsALife == 0)
            if (this.boatsALife == 0) res = win.PLAYER;
            else res = win.AI;
        return res;
    }

    private int getRandomFieldNumber() {
        return (int) Math.round(Math.random() * (fieldLength - 1));
    }

    /*private boolean validBoatLocation(int[] loc, int length) {
        boolean res = true;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (this.fieldBoats[loc[0] + i][loc[1] + j] || this.fieldBoats[loc[0] - i][loc[1] - j]) {
                    res = false;
                    break;
                }
            }
            if (!res) break;
        }
        return res;
    }*/

    private boolean validBoatLocation(int[] loc, int length) {
        boolean res = true;
        for (int i = 0; i < length; i++) {

            if (loc[0] + i < this.fieldBoats.length) {
                if (this.fieldBoats[loc[0] + i][loc[1]]) res = false;
            }
            if (loc[0] - i >= 0) {
                if (this.fieldBoats[loc[0] - i][loc[1]]) res = false;
            }
            if (loc[1] + i < this.fieldBoats.length) {
                if (this.fieldBoats[loc[0]][loc[1] + i]) res = false;
            }
            if (loc[1] - i >= 0) {
                if (this.fieldBoats[loc[0]][loc[1] - i]) res = false;
            }
        }
        return res;
    }
}
