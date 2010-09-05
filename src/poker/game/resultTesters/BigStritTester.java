package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 14:17:35
 * To change this template use File | Settings | File Templates.
 */
public class BigStritTester implements ResultTester {
    private boolean wasFound = false;
    private int points = 0;

    public BigStritTester(int dice[]) {
        int cnt[] = new int[6];
        for (int d : dice) {
            cnt[d-1]++;
        }

        this.wasFound = true;
        for (int i=1; i<6; i++) {
            this.wasFound = this.wasFound && cnt[i]==1;
        }
    }

    public int getRank() {
        return 60;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}

