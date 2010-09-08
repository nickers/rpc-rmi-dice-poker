package poker.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-08
 * Time: 21:48:59
 * To change this template use File | Settings | File Templates.
 */
public class GameAlreadyFinishedException extends GameException {
    public GameAlreadyFinishedException() {
        super("Nie można wykonać tej opracji, gdyż gra została już zakończona.");
    }
}
