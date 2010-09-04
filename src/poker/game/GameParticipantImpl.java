package poker.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-03
 * Time: 16:54:04
 * To change this template use File | Settings | File Templates.
 */
public class GameParticipantImpl extends UnicastRemoteObject implements GameParticipant {
    private Game game = null;
    private GameState gameState = null;

    public GameParticipantImpl() throws RemoteException {
    }

    public GameParticipantImpl(Game game) throws RemoteException {
        this.game = game;
    }

    public GameState waitForGameStateChange() throws RemoteException {
        this.game.waitForStateChange(this.gameState);
        synchronized (this) {
            this.gameState = this.game.getGameState(this);
        }
        return this.gameState;
    }

    public void throwDices(Set<Integer> dice) throws RemoteException {
        Random r = new Random();
        int newDice[] = this.gameState.player.dice.clone();
        for (Integer dieNr : dice) {
            if (dieNr<newDice.length) {
                newDice[dieNr] = r.nextInt(6);
            }
        }
        game.setPlayerDice(this, newDice);
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
}
