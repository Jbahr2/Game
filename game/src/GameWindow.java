
/**
 * @author Kim Buckner
 * Date: Jan 13, 2019
 *
 * This is the actual "game". Will have to make some major changes.
 * This is just a "hollow" shell.
 *
 * When you get done, I should see the buttons at the top in the "play" area
 * (NOT a pull-down menu). The only one that should do anything is Quit.
 *
 * Should also see something that shows where the 4x4 board and the "spare"
 * tiles will be when we get them stuffed in.
 *
 * This COULD be part of a package but I choose to make the starting point NOT a
 * package. However all other added elements should certainly sub-packages.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {
    /**
     * because it is a serializable object, need this or javac complains <b>a
     * lot</b>, the ID can be any integer.
     */
    public static final long serialVersionUID = 1;

    /*
     * This is so I can try changing the starting point easily. Can certainly be
     * left out altogether.
     */
    private int startAt = 1;

    /**
     * Constructor sets the window name using super(), changes the layout, which you
     * really need to read up on, and maybe you can see why I chose this one.
     *
     * @param s
     */

    public GameWindow(String s) {
        super(s);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
    }

    /**
     * For the buttons
     * 
     * @param e is the ActionEvent
     * 
     *          BTW can ask the event for the name of the object generating event.
     *          The odd syntax for non-java people is that "exit" for instance is
     *          converted to a String object, then that object's equals() method is
     *          called.
     */

    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand()))
            System.exit(0);
        // if ("reset".equals(e.getActionCommand()))

        // if ("new".equals(e.getActionCommand()))

    }

    /**
     * Establishes the inital board
     */

    public void setUp() {
        // actually create the array for elements, make sure it is big enough

        // Need to play around with the dimensions and the gridx/y values
        // These constraints are going to be added to the pieces/parts I
        // stuff into the "GridBag".
        // YOU CAN USE any type of constraints you like. Just make it work.

        ClickSwapper swapper = new ClickSwapper();
        addMouseListener(swapper);
        
        GridBagConstraints basic = new GridBagConstraints();
        basic.insets = new Insets(5, 5, 5, 5);

        // This is really a constant in the GrdiBagConstraints. This way we
        // don't need to know what type/value it is

        basic.fill = GridBagConstraints.BOTH;

        basic.anchor = GridBagConstraints.NORTH;
        basic.fill = GridBagConstraints.HORIZONTAL;
        basic.gridx = 1;
        basic.gridy = 0;
        this.addButtons(basic);

        basic.gridx = 0;
        basic.gridy = 1;
        basic.anchor = GridBagConstraints.WEST;
        SidePanel rightPanel = new SidePanel(1, swapper);
        this.add(rightPanel, basic);

        basic.gridx = 2;
        basic.gridy = 1;
        basic.anchor = GridBagConstraints.EAST;
        SidePanel leftPanel = new SidePanel(9, swapper);
        this.add(leftPanel, basic);

        basic.gridx = 1;
        basic.gridy = 1;
        GameBoard gameBoard = new GameBoard(swapper);
        add(gameBoard, basic);

        return;

    }

    /**
     * Used by setUp() to configure the buttons on a button bar and add it to the
     * gameBoard
     */

    public void addButtons(GridBagConstraints basic) {
        JPanel btnMenu = new JPanel();

        JButton newGameBtn = new JButton("New Game");
        JButton resetBtn = new JButton("Reset");
        JButton quitBtn = new JButton("Quit");

        btnMenu.setBackground(new Color(254, 211, 48, 255));

        newGameBtn.setActionCommand("new");
        newGameBtn.addActionListener(this);

        resetBtn.setActionCommand("reset");
        resetBtn.addActionListener(this);

        quitBtn.setActionCommand("exit");
        quitBtn.addActionListener(this);

        btnMenu.add(newGameBtn);
        btnMenu.add(resetBtn);
        btnMenu.add(quitBtn);
        this.add(btnMenu, basic);

        return;
    }
};
