package poker.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:11:18
 * To change this template use File | Settings | File Templates.
 */
public class GameImpl extends UnicastRemoteObject implements Game {
    public GameImpl() throws RemoteException {
        System.out.println("Game::init()");
    }

    public GameState waitForGameStateChange() throws RemoteException {
        return new GameState();
    }

    public void throwDices(Set<Integer> dices) throws RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void finishRound() throws RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void finishGame() throws RemoteException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
