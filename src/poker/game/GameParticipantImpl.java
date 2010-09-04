package poker.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-03
 * Time: 16:54:04
 * To change this template use File | Settings | File Templates.
 */
public class GameParticipantImpl extends UnicastRemoteObject implements GameParticipant {
    public GameParticipantImpl() throws RemoteException {
    }

    public GameState waitForGameStateChange() throws RemoteException {
        return null;
    }

    public void throwDices(Set<Integer> dices) throws RemoteException {
    }

    public void finishRound() throws RemoteException {
    }

    public void finishGame() throws RemoteException {
    }
}
