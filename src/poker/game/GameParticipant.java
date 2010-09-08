package poker.game;

import poker.game.exceptions.InvalidDieNumberException;
import poker.game.exceptions.InvalidDieValueException;
import poker.game.exceptions.NoMoreThrowsException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-03
 * Time: 12:59:47
 * To change this template use File | Settings | File Templates.
 */
public interface GameParticipant extends Remote {
    public GameState waitForGameStateChange() throws RemoteException;
    public void throwDices(Set<Integer> dices) throws RemoteException, NoMoreThrowsException, InvalidDieNumberException;
    public void finishRound() throws RemoteException;
    public void finishGame() throws RemoteException;
    GameState getGameState() throws RemoteException;
}
