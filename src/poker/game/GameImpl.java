package poker.game;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import poker.game.GameState.GameParticipantState;
import poker.game.exceptions.GameException;
import poker.game.exceptions.InvalidDieNumberException;
import poker.game.exceptions.InvalidDieValueException;
import poker.game.exceptions.NoMoreThrowsException;
import poker.game.resultTesters.*;

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

    /**
     *
     * @throws RemoteException
     */
    public GameImpl() throws RemoteException {
        this.players.setSize(2);
        for (int i=0; i<this.playersState.length; i++) {
            this.playersState[i] = this.gameState.new GameParticipantState();
        }
    }

    /**
     *
     * @param player
     * @return
     * @throws RemoteException
     */
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

    /**
     *
     * @param previous
     * @throws RemoteException
     */
    public synchronized void waitForStateChange(GameState previous)  throws RemoteException{
        try {
            if (this.gameState.version==previous.version) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    /**
     *
     * @param player
     * @return
     * @throws RemoteException
     */
    public GameState getGameState(GameParticipant player)  throws RemoteException{
        GameState gs = this.gameState.clone();
        gs.player = this.getParticipantState(player);
        gs.enemy = (this.playersState[0]==gs.player ? this.playersState[1] : this.playersState[0]);
        return gs;
    }

    /**
     *
     * @param player
     * @throws RemoteException
     */
    public void leaveGame(GameParticipant player)  throws RemoteException{
        for (int i=0; i<this.players.size(); i++) {
            if (this.players.get(i)!=null && player.equals(this.players.get(i).get())) {
                this.players.set(i, null);
                break;
            }
        }
        this.gameState.gameFinished = true;
        this.gameStateChanged();
    }

    /**
     *
     * @param player
     * @param dice
     * @throws RemoteException
     */
    private void setPlayerDice(GameParticipant player, int dice[]) throws RemoteException {
        GameParticipantState part = this.getParticipantState(player);
        if (!part.acceptedRound && part.roundNumber<this.gameState.roundsMax) {
            synchronized(part) {
                part.dice = dice;
                part.roundNumber++;
                //NO AUTO ACCEPTING//part.acceptedRound = (part.roundNumber>=this.gameState.roundsMax);
                if (!this.playGame()) {
                    this.gameStateChanged();
                }
            }
        }
    }

    /**
     *
     * @param player
     * @throws RemoteException
     */
    public void acceptRound(GameParticipant player) throws RemoteException {
        this.getParticipantState(player).acceptedRound = true;
        if (!this.playGame()) {
            this.gameStateChanged();
        }
    }

    /**
     *
     * @param player
     * @return
     */
    private GameParticipantState getParticipantState(GameParticipant player) {
        for (int i=0; i<this.players.size(); i++) {
            if (this.players.get(i)!=null && player.equals(this.players.get(i).get())) {
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

        for (GameParticipantState state : this.playersState ) {
            if (!state.acceptedRound)
                return false;
        }

        Class<?> testersClasses[] = {NothingTester.class, PairTester.class, TwoPairsTester.class, TripleTester.class,
                SmallStritTester.class, BigStritTester.class, FullTester.class, FourTester.class, PokerTester.class };

        Map<GameParticipantState,ResultTester> res = new HashMap<GameParticipantState,ResultTester>();
        for (GameParticipantState state : this.playersState ) {
            int dice[] = state.dice;

            if (dice!=null && dice[0]!=0) {

                ResultTester lastTester = null;
                for (Class<?> clazz : testersClasses) {
                    try {
                        Class<?> argsType[] = new Class<?>[] { int[].class };
                        Constructor<?> c = clazz.getConstructor(argsType);
                        Object argsList[] = new Object[] { dice };
                        ResultTester t = (ResultTester)c.newInstance(argsList);
                        if (t.isValid() && (lastTester==null || lastTester.getRank()<t.getRank())) {
                            lastTester = t;
                        }
                    } catch (Exception e) {
                        System.err.println("Can't instantiate game result tester class.");
                        e.printStackTrace();
                    }
                } // end for (class<?>)
                
                res.put(state, lastTester);
            } // end if (dice!=null...
        }

        assert(res.size()==2);

        ResultTester a,b;
        a = res.get(this.playersState[0]);
        b = res.get(this.playersState[1]);

        if (a.getRank()>b.getRank()) {
            // A -
            this.playersState[0].score++;
        } else if (a.getRank()<b.getRank()) {
            // B -
            this.playersState[1].score++;
        } else {
            // x ?
            if (a.getPoints()>b.getPoints()) {
                // x A
                this.playersState[0].score++;
            } else if (a.getPoints()<b.getPoints()) {
                // x B
                this.playersState[1].score++;
            }
            else {
                // x x - remis
            }
        }

        //for (GameParticipantState state : this.playersState) {
        for (WeakReference<GameParticipant> player : this.players) {
            try {
                GameParticipantState state = this.getParticipantState(player.get());
                state.acceptedRound = false;
                state.roundNumber = 0;

                Set<Integer> s = new TreeSet<Integer>();
                for (int i=0; i<state.dice.length; i++)
                    s.add(i);
                try {
                    this.throwDice(player.get(), s);
                } catch (GameException e) {
                    e.printStackTrace();
                }
            } catch (NullPointerException e) {
                System.err.println("Probably player disconnected.");
                e.printStackTrace();
            } catch (RemoteException e) {
                // do not care this time
                e.printStackTrace();
            }
        }

        //for (ResultTester t : testers) {
        //    System.out.println(String.format("%-45s: %2d : %2d : %s", t.getClass().getName(), t.getRank(), t.getPoints(), t.isValid()));
        //}

        this.gameStateChanged();

        return true;
    }

    /**
     *
     */
    private void gameStateChanged() {
        this.gameState.changed();
        synchronized(this) {
            this.notifyAll();
        }
    }

    /**
     *
     * @param player
     * @param dice
     * @throws RemoteException
     */
    public void throwDice(GameParticipant player, Set<Integer> dice) throws RemoteException, NoMoreThrowsException, InvalidDieNumberException {
        GameParticipantState part = this.getParticipantState(player);
        if (part.acceptedRound || !(part.roundNumber<this.gameState.roundsMax)) {
            throw new NoMoreThrowsException();
        }

        Random r = new Random();
        //int newDice[] = this.gameState.player.dice.clone();
        int newDice[] = this.getParticipantState(player).dice.clone();
        for (Integer dieNr : dice) {
            if (dieNr<newDice.length && dieNr>=0) {
                newDice[dieNr] = r.nextInt(6)+1; // <1;6>
            } else {
                throw new InvalidDieNumberException();
            }
        }

        this.setPlayerDice(player, newDice);
    }
}
