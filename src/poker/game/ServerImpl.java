package poker.game;

import poker.game.exceptions.GameException;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-02
 * Time: 01:08:14
 * To change this template use File | Settings | File Templates.
 */
public class ServerImpl extends UnicastRemoteObject implements Server {

    /**
     * Games with one player connected.
     */
    private Queue<Game> onePlayerGames = new ConcurrentLinkedQueue<Game>();

    public ServerImpl() throws RemoteException {
        System.out.println("ServerImpl -> default constructor");
    }

    /**
     *
     * @return Game instance to which player should connect.
     * @throws RemoteException
     */
    public GameParticipant connectToGame() throws RemoteException {
        System.out.print("Server::connectToGame()...");
        Game game = this.onePlayerGames.poll();

        if (game==null) {
            synchronized (this.onePlayerGames) {
                game = this.onePlayerGames.poll();
                if (game==null) {
                    System.out.print("new game created...");
                    game = new GameImpl();
                    this.onePlayerGames.add(game);
                }
            }
        }

        System.out.println("connected");
        GameParticipant part = new GameParticipantImpl(game);
        game.addPlayer(part);

        Set<Integer> set = new TreeSet<Integer>();
        for (int i=0; i<part.getGameState().diceAmount; i++) {
            set.add(i);
        }
        try {
            part.throwDices(set);
        } catch (GameException e) {
            e.printStackTrace();
            return null;
        }

        return part;
    }

    public int gamesCount() throws RemoteException {
        return 0;
    }
    
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        String name = "//localhost:1099/Game";
        try {
            Server game = new ServerImpl();
            Naming.rebind(name, game);
            System.out.println("Game server started");
        } catch (Exception e) {
            System.err.println("Problems with server." + e.getMessage());
            e.printStackTrace();
        }
    }
}
