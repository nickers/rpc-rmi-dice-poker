package poker;

import poker.game.GameParticipant;
import poker.game.GameState;
import poker.gui.PokerUI;
import javax.swing.*;
import poker.game.Server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-01
 * Time: 15:12:06
 * To change this template use File | Settings | File Templates.
 */
public class Starter extends Thread {
    private GameParticipant p = null;

    public void run() {
        try {
        GameState gs = p.getGameState();
        while(true) {
            p.waitForGameStateChange();
            System.out.println(" -- game state changed :: " + p.getGameState().version);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exec() {
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            String name = "//localhost:1099/Compute";
            Server serv = (Server) Naming.lookup(name);
            this.p = serv.connectToGame();
            System.out.println("connected");

            this.start();

            Set<Integer> s = new TreeSet<Integer>();
            s.add(1);
            s.add(0);
            this.p.throwDices(s);
            System.out.println("Dice throwed");
            Thread.sleep(10000);
            this.p.finishGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                PokerUI gui = new PokerUI();

                JFrame frame = new JFrame("Dice poker game with RMI in background");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Create and set up the content pane.
                JComponent newContentPane = gui.getMainPanel();
                newContentPane.setOpaque(true); //content panes must be opaque
                frame.setContentPane(newContentPane);

                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });

//        Starter s = new Starter();
//        s.exec();
    }
}
