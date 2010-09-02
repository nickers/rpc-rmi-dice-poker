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
public interface Game {
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public GameState waitForGameStateChange() throws RemoteException;

    /**
     * 
     * @param dices
     * @throws RemoteException
     */
    public void throwDices(Set<Integer> dices) throws RemoteException;

    /**
     * 
     * @throws RemoteException
     */
    public void finishRound() throws RemoteException;

    /**
     * 
     * @throws RemoteException
     */
    public void finishGame() throws RemoteException;
}
