package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-05
 * Time: 00:07:54
 * To change this template use File | Settings | File Templates.
 */
public class TwoPairsTester implements ResultTester {
    private int points = 0;
    private boolean wasFound = false;

    public TwoPairsTester(int dice[]) {
        int a,b;
        a=b=-1;

        int cnt[] = new int[6];
        for (int d : dice) {
            cnt[d-1]++;
        }

        for (int i=0; i<cnt.length; i++) {
            if (cnt[i]==2) {
                a=b;
                b=i;
            }
        }

        this.wasFound = (a>-1);
        this.points = b*10 + a;
    }

    public int getRank() {
        return 30;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return this.wasFound;
    }
}
