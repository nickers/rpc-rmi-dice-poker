package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-04
 * Time: 23:55:21
 * To change this template use File | Settings | File Templates.
 */
public class NothingTester implements ResultTester{
    private int points = 0;
    public NothingTester(int dice[]) {
        for (int d : dice) {
            this.points += d;
        }
    }

    public int getRank() {
        return 0;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean isValid() {
        return false;
    }
}
