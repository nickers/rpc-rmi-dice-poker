package poker.game.resultTesters;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-04
 * Time: 23:51:50
 * To change this template use File | Settings | File Templates.
 */
public interface ResultTester {
    int getRank();
    int getPoints();
    boolean isValid();
}
