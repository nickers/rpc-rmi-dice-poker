package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 14:18:35
 * To change this template use File | Settings | File Templates.
 */
public class FullTester implements ResultTester {
    private boolean wasFound = false;
    private int points = 0;

    public FullTester(int dice[]) {
        int cnt[] = new int[6];
        int pair=-1, triple=-1;

        for (int d : dice) {
            cnt[d-1]++;
        }

        this.wasFound = true;
        for (int i=0; i<cnt.length; i++) {
            if (cnt[i]==2) {
                pair = i;
            } else if (cnt[i]==3) {
                triple = i;
            }
        }
        this.wasFound = ((triple>=0) && (pair>=0));
        if (this.wasFound) {
            this.points = triple*10 + pair;
        }
    }

    public int getRank() {
        return 70;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}
