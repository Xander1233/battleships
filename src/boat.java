public class boat {

    private boolean alive = true;
    private int length;
    private int[][] location = new int[1][2];
    private boolean[] hits = new boolean[1];
    private int counterHits = 0;

    boat(int[][] location, int length) {
        this.length = length;
        this.location = location;
        this.hits = new boolean[this.length];
        this.counterHits = this.length;
    }

    public hitStatus hit(int[] location) {
        boolean validLocation = false;
        int locationIndex = -1;
        for (int i = 0; i < this.location.length; i++)
            if (this.location[i][0] == location[0] && this.location[i][1] == location[1]) {
                validLocation = true;
                locationIndex = i;
                break;
            }
        if (!validLocation) return hitStatus.BADREQUEST;

        this.hits[locationIndex] = true;
        counterHits--;

        if (counterHits == 0) {
            alive = false;
            return hitStatus.DEAD;
        }
        return hitStatus.HBNO;
    }

    public boolean getAlive() {
        return this.alive;
    }

}
