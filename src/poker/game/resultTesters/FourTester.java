package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 15:57:45
 * To change this template use File | Settings | File Templates.
 */
public class FourTester implements ResultTester {
    private boolean wasFound = false;
    private int points = 0;

    public FourTester(int dice[]) {
        int cnt[] = new int[6];
        int pair=-1, triple=-1;

        for (int d : dice) {
            cnt[d-1]++;
        }

        for (int i=0; i<cnt.length; i++) {
            if (cnt[i]==4) {
                this.wasFound = true;
                this.points = i;
            }
        }
    }

    public int getRank() {
        return 80;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}
