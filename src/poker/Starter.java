package poker;

import poker.gui.PokerUI;
import javax.swing.*;
import poker.game.Server;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-01
 * Time: 15:12:06
 * To change this template use File | Settings | File Templates.
 */
public class Starter {
    public static void main(String[] args) {
        if (System.getSecurityManager() == null){
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            String name = "//localhost:1099/Compute";
            Server serv = (Server) Naming.lookup(name);
            serv.connectToGame().waitForStateChange();
            //serv.connectToGame().waitForGameStateChange().dupa();
            System.out.println("--done--");
        } catch (Exception e) {
            e.printStackTrace();
        }   

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                /*
                System.out.println("show it");
                
                PokerUI gui = new PokerUI();
                
                JFrame frame = new JFrame("CheckBoxDemo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                //Create and set up the content pane.
                JComponent newContentPane = gui.$$$getRootComponent$$$();
                newContentPane.setOpaque(true); //content panes must be opaque
                frame.setContentPane(newContentPane);
        
                //Display the window.
                frame.pack();
                frame.setVisible(true);
                */            
            }
        });
    }
}
