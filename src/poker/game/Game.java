package poker.game;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:10:43
 * To change this template use File | Settings | File Templates.
 */
public interface Game  {
    boolean addPlayer(GameParticipant player);
    void waitForStateChange(GameState previous) ;
    GameState getGameState();
    void leaveGame(GameParticipant player);
    void setPlayerDice(GameParticipant player, Set<Integer> dices);
}
