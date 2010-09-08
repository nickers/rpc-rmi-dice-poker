package poker.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-08
 * Time: 19:04:12
 * To change this template use File | Settings | File Templates.
 */
public class NoMoreThrowsException extends GameException{
    public NoMoreThrowsException() {
        super("Wykorzystałeś już wszystkie rzuty w tej rundzie.");
    }
}
