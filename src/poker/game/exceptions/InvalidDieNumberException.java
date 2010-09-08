package poker.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-08
 * Time: 19:05:38
 * To change this template use File | Settings | File Templates.
 */
public class InvalidDieNumberException extends GameException {
    public InvalidDieNumberException() {
        super("Podany numer kości jest nieprawidłowy.");
    }
}
