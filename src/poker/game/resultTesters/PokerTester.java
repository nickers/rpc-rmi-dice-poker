package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 15:59:29
 * To change this template use File | Settings | File Templates.
 */
public class PokerTester implements ResultTester {
    private boolean wasFound = false;
    private int points = 0;

    public PokerTester(int dice[]) {
        int cnt[] = new int[6];
        for (int d : dice) {
            cnt[d-1]++;
        }

        for (int i=0; i<cnt.length; i++) {
            if (cnt[i]==5) {
                this.points = i;
                this.wasFound = true;
            }
        }
    }

    public int getRank() {
        return 90;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}

