package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 00:01:18
 * To change this template use File | Settings | File Templates.
 */
public class PairTester implements ResultTester {
    private int points = 0;
    private boolean wasFound = false;

    public PairTester(int dice[]) {
        int cnt[] = new int[6];
        for (int d : dice) {
            cnt[d-1]++;
        }

        for (int i=0; i<cnt.length; i++) {
            if (cnt[i]==2) {
                this.wasFound = true;
                this.points = i;
            }
        }
    }

    public int getRank() {
        return 10;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}
