package poker.game;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:29:31
 * To change this template use File | Settings | File Templates.
 */
public class GameState implements Serializable {
    /* game params */
    public final int diceAmount = 5;
    public final int roundsMax = 3;
    public boolean gameFinished = false;
    
    /* THIS player info */
    public int playerScore = 0;
    public int playerDice[] = new int[diceAmount];
    public int playerRoundNumber = 0;
    public boolean playerAcceptedRound = false;
    
    /* second player info */
    public int enemyScore = 0;
    public int enemyDice[] = new int[diceAmount];
    public int enemyRoundNumber = 0;
    public boolean enemyAcceptedRound = false;
    
    public GameState() {
    }
    
    public void dupa() {
        System.out.println("dupa");
    }
}
