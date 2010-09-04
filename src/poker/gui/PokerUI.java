package poker.gui;

import poker.game.GameParticipant;
import poker.game.GameState;
import poker.game.Server;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: nickers
 * Date: 2010-09-01
 * Time: 16:21:33
 * To change this template use File | Settings | File Templates.
 */
public class PokerUI implements ActionListener {

    private class ListenerThread extends Thread {
        private GameParticipant part = null;
        private PokerUI gui = null;
        public ListenerThread(GameParticipant part, PokerUI gui) {
            this.part = part;
            this.gui = gui;
        }
        @Override
        public void run() {
            while(!this.isInterrupted()) {
                try {
                    System.out.println(" - wait for change");
                    GameState state = part.waitForGameStateChange();

                    System.out.println(" + round accepted: " + state.player.acceptedRound);

                    // my dice values
                    JToggleButton myDice[] = {gui.die1, gui.die2, gui.die3, gui.die4, gui.die5};
                    for (int i=0; i<myDice.length; i++) {
                        myDice[i].setText(String.format("%d",state.player.dice[i]));
                    }

                    // can i throw dice again
                    gui.throwDice.setEnabled(!state.player.acceptedRound);
                    System.out.println("Enabled: " + state.player.acceptedRound);

                    // round accepted (by user or automaticly)
                    gui.acceptRound.setEnabled(!state.player.acceptedRound);

                    // enemy dice values
                    JToggleButton enemyDice[] = {gui.enemyDice1, gui.enemyDice2, gui.enemyDice3, gui.enemyDice4, gui.enemyDice5};
                    for (int i=0; i<enemyDice.length; i++) {
                        enemyDice[i].setText(String.format("%d",state.enemy.dice[i]));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private GameParticipant game = null;
    private Thread gameStacheListener = null;
    private Server serv = null;

    public PokerUI() {
        initComponents();
        setMyFunctions();
    }

    public void actionPerformed(ActionEvent act) {
        try {
            System.out.println("Command received: " + act.getActionCommand());

            // start a new game
            if ("new_game".equals(act.getActionCommand())) {
                this.stopGame();
                this.startGame();
            }

            // throw dice
            if ("throw_dice".equals(act.getActionCommand()) && game!=null) {
                Set<Integer> dice = new TreeSet<Integer>();
                JToggleButton d[] = {this.die1, this.die2, this.die3, this.die4, this.die5};
                for (int i=0; i<d.length; i++){
                    if (d[i].isSelected()) dice.add(i);
                }
                if (!dice.isEmpty()) {
                    game.throwDices(dice);
                }
            }

            // accept round (dice in actual state)
            if ("accept_round".equals(act.getActionCommand())) {
                game.finishRound();
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void setMyFunctions() {
        try {
            String name = "//localhost:1099/Compute";
            this.serv = (Server) Naming.lookup(name);
        } catch (Exception e) {
            System.err.println("Can't connect to game server.");
        }

        this.newGame.setActionCommand("new_game");
        this.newGame.addActionListener(this);

        this.throwDice.setActionCommand("throw_dice");
        this.throwDice.addActionListener(this);

        this.acceptRound.setActionCommand("accept_round");
        this.acceptRound.addActionListener(this);

    }

    private void stopGame() {
        if (this.game!=null) {
            try {
                this.game.finishGame();
            } catch(Exception e) {
                e.printStackTrace();
            }
            this.game = null;

            try {
                this.gameStacheListener.interrupt();
                this.gameStacheListener.join(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.gameStacheListener = null;
        }
    }

    private void startGame() {
        try {
            this.game = serv.connectToGame();
            this.gameStacheListener = new ListenerThread(this.game, this);
            this.gameStacheListener.start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public JPanel getMainPanel(){
        return this.mainPanel;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - unknown
        mainPanel = new JPanel();
        menuPanel = new JPanel();
        JToolBar toolBar1 = new JToolBar();
        newGame = new JButton();
        gamePanel = new JPanel();
        youPanel = new JPanel();
        JPanel panel1 = new JPanel();
        die1 = new JToggleButton();
        die5 = new JToggleButton();
        die4 = new JToggleButton();
        die2 = new JToggleButton();
        die3 = new JToggleButton();
        JLabel label1 = new JLabel();
        throwDice = new JButton();
        acceptRound = new JButton();
        oponentPanel = new JPanel();
        JPanel panel2 = new JPanel();
        enemyDice1 = new JToggleButton();
        enemyDice5 = new JToggleButton();
        enemyDice4 = new JToggleButton();
        enemyDice3 = new JToggleButton();
        enemyDice2 = new JToggleButton();
        JLabel label2 = new JLabel();

        //======== mainPanel ========
        {
            mainPanel.setPreferredSize(new Dimension(500, 300));

            // JFormDesigner evaluation mark
            mainPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), mainPanel.getBorder())); mainPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            mainPanel.setLayout(new BorderLayout());

            //======== menuPanel ========
            {
                menuPanel.setLayout(new BorderLayout());

                //======== toolBar1 ========
                {
                    toolBar1.setRollover(false);
                    toolBar1.setFloatable(false);
                    toolBar1.putClientProperty("JToolBar.isRollover", false);
                    toolBar1.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), ""));

                    //---- button1 ----
                    newGame.setText("Nowa gra");
                    newGame.setMnemonic('N');
                    toolBar1.add(newGame);
                }
                menuPanel.add(toolBar1, BorderLayout.CENTER);
            }
            mainPanel.add(menuPanel, BorderLayout.NORTH);

            //======== gamePanel ========
            {
                gamePanel.setLayout(new GridBagLayout());

                //======== youPanel ========
                {
                    youPanel.setEnabled(false);
                    youPanel.setBackground(Color.black);
                    youPanel.setLayout(new GridBagLayout());

                    //======== panel1 ========
                    {
                        panel1.setBackground(new Color(51, 102, 0));
                        panel1.setBorder(new TitledBorder(new LineBorder(Color.white), ""));
                        panel1.setLayout(new GridBagLayout());

                        //---- die1 ----
                        die1.setText("...");
                        die1.setMaximumSize(new Dimension(50, 50));
                        die1.setRolloverEnabled(true);
                        die1.setMinimumSize(new Dimension(50, 50));
                        die1.setPreferredSize(new Dimension(50, 50));
                        panel1.add(die1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.NORTH, GridBagConstraints.NONE,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- die5 ----
                        die5.setText("...");
                        die5.setMaximumSize(new Dimension(50, 50));
                        die5.setRolloverEnabled(true);
                        die5.setOpaque(true);
                        die5.setMinimumSize(new Dimension(50, 50));
                        die5.setPreferredSize(new Dimension(50, 50));
                        panel1.add(die5, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- die4 ----
                        die4.setText("...");
                        die4.setMaximumSize(new Dimension(50, 50));
                        die4.setMinimumSize(new Dimension(50, 50));
                        die4.setPreferredSize(new Dimension(50, 50));
                        panel1.add(die4, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- die2 ----
                        die2.setText("...");
                        die2.setMaximumSize(new Dimension(50, 50));
                        die2.setMinimumSize(new Dimension(50, 50));
                        die2.setPreferredSize(new Dimension(50, 50));
                        panel1.add(die2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- die3 ----
                        die3.setText("...");
                        die3.setMaximumSize(new Dimension(50, 50));
                        die3.setMinimumSize(new Dimension(50, 50));
                        die3.setPreferredSize(new Dimension(50, 50));
                        panel1.add(die3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    youPanel.add(panel1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 15, 15));

                    //---- label1 ----
                    label1.setFont(label1.getFont().deriveFont(Font.BOLD, 26f));
                    label1.setText("TY");
                    label1.setForeground(Color.white);
                    youPanel.add(label1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

                    //---- throwDice ----
                    throwDice.setText("Rzut");
                    throwDice.setVerticalAlignment(0);
                    throwDice.setPreferredSize(new Dimension(75, 50));
                    youPanel.add(throwDice, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));

                    //---- acceptRound ----
                    acceptRound.setText(" OK ");
                    acceptRound.setVerticalAlignment(0);
                    acceptRound.setPreferredSize(new Dimension(75, 50));
                    youPanel.add(acceptRound, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                gamePanel.add(youPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));

                //======== oponentPanel ========
                {
                    oponentPanel.setBackground(Color.black);
                    oponentPanel.setLayout(new GridBagLayout());

                    //======== panel2 ========
                    {
                        panel2.setBackground(new Color(51, 102, 0));
                        panel2.setBorder(new TitledBorder(new LineBorder(Color.white), ""));
                        panel2.setLayout(new GridBagLayout());

                        //---- enemyDice1 ----
                        enemyDice1.setPreferredSize(new Dimension(50, 50));
                        enemyDice1.setMinimumSize(new Dimension(50, 50));
                        enemyDice1.setSelected(false);
                        enemyDice1.setMaximumSize(new Dimension(50, 50));
                        enemyDice1.setText("...");
                        panel2.add(enemyDice1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- enemyDice5 ----
                        enemyDice5.setPreferredSize(new Dimension(50, 50));
                        enemyDice5.setMinimumSize(new Dimension(50, 50));
                        enemyDice5.setSelected(false);
                        enemyDice5.setMaximumSize(new Dimension(50, 50));
                        enemyDice5.setText("...");
                        panel2.add(enemyDice5, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- enemyDice4 ----
                        enemyDice4.setPreferredSize(new Dimension(50, 50));
                        enemyDice4.setMinimumSize(new Dimension(50, 50));
                        enemyDice4.setMaximumSize(new Dimension(50, 50));
                        enemyDice4.setText("...");
                        panel2.add(enemyDice4, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- enemyDice3 ----
                        enemyDice3.setPreferredSize(new Dimension(50, 50));
                        enemyDice3.setMinimumSize(new Dimension(50, 50));
                        enemyDice3.setMaximumSize(new Dimension(50, 50));
                        enemyDice3.setText("...");
                        panel2.add(enemyDice3, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- enemyDice2 ----
                        enemyDice2.setPreferredSize(new Dimension(50, 50));
                        enemyDice2.setMinimumSize(new Dimension(50, 50));
                        enemyDice2.setMaximumSize(new Dimension(50, 50));
                        enemyDice2.setText("...");
                        panel2.add(enemyDice2, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    oponentPanel.add(panel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(5, 5, 5, 5), 15, 15));

                    //---- label2 ----
                    label2.setFont(label2.getFont().deriveFont(Font.BOLD, 26f));
                    label2.setText("Przeciwnik");
                    label2.setForeground(Color.white);
                    oponentPanel.add(label2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.WEST, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0));
                }
                gamePanel.add(oponentPanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            mainPanel.add(gamePanel, BorderLayout.CENTER);
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - unknown
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel gamePanel;
    private JPanel youPanel;
    private JToggleButton die1;
    private JToggleButton die2;
    private JToggleButton die3;
    private JToggleButton die4;
    private JToggleButton die5;
    private JButton throwDice;
    private JButton acceptRound;
    private JPanel oponentPanel;
    private JToggleButton enemyDice1;
    private JToggleButton enemyDice2;
    private JToggleButton enemyDice3;
    private JToggleButton enemyDice4;
    private JToggleButton enemyDice5;
    private JButton newGame;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
