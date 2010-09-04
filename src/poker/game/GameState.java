package poker.game;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:29:31
 * To change this template use File | Settings | File Templates.
 */
public class GameState implements Serializable, Cloneable {

    /** GameState internal **/
    public int version = 0;

    /* game params */
    public final int diceAmount = 5;
    public final int roundsMax = 3;
    public boolean gameFinished = false;

    public class GameParticipantState implements Serializable, Cloneable {
        public int score = 0;
        public int dice[] = new int[diceAmount];
        public int roundNumber = 0;
        public boolean acceptedRound = false;

        @Override
        protected GameParticipantState clone() {
            GameParticipantState c = new GameParticipantState();
            c.score  = this.score;
            c.dice = this.dice.clone();
            c.roundNumber = this.roundNumber;
            c.acceptedRound = this.acceptedRound;
            return c;
        }
    };
    
    /* THIS player info */
    public GameParticipantState player = new GameParticipantState();
    
    /* second player info */
    public GameParticipantState enemy = new GameParticipantState();
    
    public GameState() {
    }
    
    public synchronized void changed() {
        version++;
    }

    @Override
    protected GameState clone() {
        GameState c = new GameState();
        c.gameFinished = this.gameFinished;
        c.player = this.player.clone();
        c.enemy = this.enemy.clone();
        return c;
    }

}
