package poker.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-08
 * Time: 19:02:20
 * To change this template use File | Settings | File Templates.
 */
public class InvalidDieValueException extends GameException {
    public InvalidDieValueException() {
        super("Wartość podana na kości jest nieprawidłowa.");
    }

    public InvalidDieValueException(int value) {
        super(String.format("Wartość podana na kości (%d) jest nieprawidłowa.", value));
    }

    public InvalidDieValueException(String msg) {
        super(msg);
    }
}
