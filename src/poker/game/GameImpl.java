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
public class GameImpl implements Game {
    private GameParticipant players[] = new GameParticipant[2];

    public GameImpl() {
    }

    public boolean addPlayer(GameParticipant player) {
        int i = 0;
        while (i<this.players.length) {
            if (this.players[i] == null) {
                this.players[i] = player;
                return true;
            }
            i++;
        }
        return false;
    }

    public synchronized void waitForStateChange(GameState previous) {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public GameState getGameState() {
        return null;
    }

    public void leaveGame(GameParticipant player) {
    }

    public void setPlayerDice(GameParticipant player, Set<Integer> dices) {
    }
}
