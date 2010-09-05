package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 14:13:57
 * To change this template use File | Settings | File Templates.
 */
public class SmallStritTester implements ResultTester {
    private boolean wasFound = false;
    private int points = 0;

    public SmallStritTester(int dice[]) {
        int cnt[] = new int[6];
        for (int d : dice) {
            cnt[d-1]++;
        }

        this.wasFound = true;
        for (int i=0; i<5; i++) {
            this.wasFound = this.wasFound && cnt[i]==1;
        }
    }

    public int getRank() {
        return 50;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}
