package poker.game;

import poker.game.exceptions.InvalidDieNumberException;
import poker.game.exceptions.InvalidDieValueException;
import poker.game.exceptions.NoMoreThrowsException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.Unreferenced;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-03
 * Time: 16:54:04
 * To change this template use File | Settings | File Templates.
 */
public class GameParticipantImpl extends UnicastRemoteObject implements GameParticipant, Unreferenced {
    private Game game = null;
    private GameState gameState = null;

    public GameParticipantImpl(Game game) throws RemoteException {
        this.game = game;
        this.gameState = new GameState();
    }

    public GameState waitForGameStateChange() throws RemoteException {
        GameState gs = null;
        synchronized (this) {
            gs = this.gameState;
        }
        this.game.waitForStateChange(gs);
        synchronized (this) {
            this.gameState = this.game.getGameState(this);
        }
        return this.gameState;
    }

    public void throwDices(Set<Integer> dice) throws RemoteException, NoMoreThrowsException, InvalidDieNumberException {
        game.throwDice(this, dice);
    }

    public void finishRound() throws RemoteException {
        this.game.acceptRound(this);
    }

    public void finishGame() throws RemoteException {
        this.game.leaveGame(this);
    }

    public synchronized GameState getGameState() throws RemoteException {
        return this.gameState;
    }

    public void unreferenced() {
        System.out.println("Game unreferenced!");   
        try {
            this.finishGame();
        } catch (RemoteException e) {
            // but who really cares?
            e.printStackTrace();
        }
    }
}
