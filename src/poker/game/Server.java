package poker.game;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:08:56
 * To change this template use File | Settings | File Templates.
 */
public interface Server extends Remote {
    public GameParticipant connectToGame() throws RemoteException;
    public int gamesCount() throws RemoteException;
}
