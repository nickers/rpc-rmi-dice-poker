package poker.game;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import java.util.Vector;

import poker.game.GameState.GameParticipantState;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:11:18
 * To change this template use File | Settings | File Templates.
 */
public class GameImpl extends UnicastRemoteObject implements Game {
    private Vector<WeakReference<GameParticipant>> players = new Vector<WeakReference<GameParticipant>>(2);
    private GameState.GameParticipantState playersState[] = new GameState.GameParticipantState[2];
    private GameState gameState = new GameState();

    public GameImpl() throws RemoteException {
        this.players.setSize(2);
        for (int i=0; i<this.playersState.length; i++) {
            this.playersState[i] = this.gameState.new GameParticipantState();
        }
    }

    public boolean addPlayer(GameParticipant player) throws RemoteException{
        int i = 0;
        while (i<this.players.size()) {
            if (this.players.get(i) == null) {
                this.players.set(i, new WeakReference<GameParticipant>(player));
                return true;
            }
            i++;
        }
        this.gameStateChanged();
        return false;
    }

    public synchronized void waitForStateChange(GameState previous)  throws RemoteException{
        try {
            if (this.gameState.version==previous.version) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public GameState getGameState(GameParticipant player)  throws RemoteException{
        GameState gs = this.gameState.clone();
        gs.player = this.getParticipantState(player);
        gs.enemy = (this.playersState[0]==gs.player ? this.playersState[1] : this.playersState[0]);
        return gs;
    }

    public void leaveGame(GameParticipant player)  throws RemoteException{
        for (int i=0; i<this.players.size(); i++) {
            if (this.players.get(i).get().equals(player)) {
                this.players.set(i, null);
                break;
            }
        }
        this.gameStateChanged();
    }

    public void setPlayerDice(GameParticipant player, int dice[]) throws RemoteException {
        System.out.println("dice set: " + dice.toString());
        GameParticipantState part = this.getParticipantState(player);
        if (!part.acceptedRound && part.roundNumber<this.gameState.roundsMax) {
            synchronized(part) {
                part.dice = dice;
                part.roundNumber++;
                part.acceptedRound = (part.roundNumber>=this.gameState.roundsMax);
                if (!this.playGame()) {
                    this.gameStateChanged();
                }
            }
        }
    }

    public void acceptRound(GameParticipant player) throws RemoteException {
        this.getParticipantState(player).acceptedRound = true;
        if (!this.playGame()) {
            this.gameStateChanged();
        }
    }

    private GameParticipantState getParticipantState(GameParticipant player) {
        for (int i=0; i<this.players.size(); i++) {
            if (player.equals(this.players.get(i).get())) {
                return this.playersState[i];
            }
        }
        return null;
    }

    /**
     * try to compute round results, true if done, false otherwise.
     * @return
     */
    private boolean playGame() {
        return false;
    }

    private void gameStateChanged() {
        this.gameState.changed();
        synchronized(this) {
            this.notifyAll();
        }
    }
}
