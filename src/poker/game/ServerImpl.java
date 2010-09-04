package poker.game;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
    public Game connectToGame() throws RemoteException {
        Game game = this.onePlayerGames.poll();

        if (game==null) {
            synchronized (this.onePlayerGames) {
                game = this.onePlayerGames.poll();
                if (game==null) {
                    game = new GameImpl();
                    this.onePlayerGames.add(game);
                }
            }
        }

        return game;
    }

    public int gamesCount() throws RemoteException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        String name = "//localhost:1099/Compute";
        try {
            Server game = new ServerImpl();
            Naming.rebind(name, game);
            System.out.println("ComputeEngine ok");
        } catch (Exception e) {
            System.err.println("ComputeEngine excep." + e.getMessage());
            e.printStackTrace();
        }
    }
}
