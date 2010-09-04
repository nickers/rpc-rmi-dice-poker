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
        this.gameState = new GameState();
    }

    public GameState waitForGameStateChange() throws RemoteException {
        GameState gs = null;
        synchronized (this) {
            gs = this.gameState;
        }
        this.game.waitForStateChange(gs);
        synchronized (this) {
            System.out.println("GamePart::old GS: " + this.gameState + ", v=" + this.gameState.version);
            this.gameState = this.game.getGameState(this);
            System.out.println("GamePart::new GS: " + this.gameState + ", v=" + this.gameState.version);
        }
        return this.gameState;
    }

    public void throwDices(Set<Integer> dice) throws RemoteException {
        Random r = new Random();
        int newDice[] = this.gameState.player.dice.clone();
        for (Integer dieNr : dice) {
            if (dieNr<newDice.length) {
                newDice[dieNr] = r.nextInt(6)+1; // <1;6>
                System.out.println(String.format(" rolling for die %d: %d", dieNr, newDice[dieNr]));
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
