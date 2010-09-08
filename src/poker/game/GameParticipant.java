package poker.game;

import poker.game.exceptions.*;

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
    public GameState waitForGameStateChange() throws RemoteException, GameAlreadyFinishedException;
    public void throwDices(Set<Integer> dices) throws RemoteException, NoMoreThrowsException, InvalidDieNumberException, GameAlreadyFinishedException, RoundFinishedException;
    public void finishRound() throws RemoteException, GameAlreadyFinishedException, RoundFinishedException;
    public void finishGame() throws RemoteException, GameAlreadyFinishedException;
    GameState getGameState() throws RemoteException, GameAlreadyFinishedException;
}
