package poker.game;

import poker.game.exceptions.InvalidDieNumberException;
import poker.game.exceptions.InvalidDieValueException;
import poker.game.exceptions.NoMoreThrowsException;

import java.io.Serializable;
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
public interface Game extends Remote {
    boolean addPlayer(GameParticipant player) throws RemoteException;
    void waitForStateChange(GameState previous)  throws RemoteException;
    GameState getGameState(GameParticipant player) throws RemoteException;
    void leaveGame(GameParticipant player) throws RemoteException;
    //void setPlayerDice(GameParticipant player, int dice[]) throws RemoteException;
    void throwDice(GameParticipant player, Set<Integer> dice) throws RemoteException, NoMoreThrowsException, InvalidDieNumberException;
    void acceptRound(GameParticipant player) throws RemoteException;
}
