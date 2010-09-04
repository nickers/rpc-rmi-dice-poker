package poker.game;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;
import poker.game.GameState.GameParticipantState;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:11:18
 * To change this template use File | Settings | File Templates.
 */
public class GameImpl extends UnicastRemoteObject implements Game {
    private GameParticipant players[] = new GameParticipant[2];
    private GameState.GameParticipantState playersState[] = new GameState.GameParticipantState[2];
    private GameState gameState = new GameState();

    public GameImpl() throws RemoteException {
        for (int i=0; i<this.playersState.length; i++) {
            this.playersState[i] = this.gameState.new GameParticipantState();
        }
    }

    public boolean addPlayer(GameParticipant player) throws RemoteException{
        int i = 0;
        while (i<this.players.length) {
            if (this.players[i] == null) {
                this.players[i] = player;
                return true;
            }
            i++;
        }
        this.gameState.changed();
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
        return this.gameState.clone();
    }

    public void leaveGame(GameParticipant player)  throws RemoteException{
        this.gameState.changed();
    }

    public void setPlayerDice(GameParticipant player, int dice[]) throws RemoteException {
        GameState.GameParticipantState partState = null;
        for (int i=0; i<this.players.length; i++) {
            if (this.players[i]==player) {

            }
        }
        this.gameState.changed();
    }
}
