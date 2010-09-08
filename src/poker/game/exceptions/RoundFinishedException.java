package poker.game.exceptions;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-08
 * Time: 21:49:50
 * To change this template use File | Settings | File Templates.
 */
public class RoundFinishedException extends GameException{
    public RoundFinishedException() {
        super("Już zaakceptowałeś Twój układ kości w tej rundzie. Musisz zaczekać do następnej rundy.");
    }
}
