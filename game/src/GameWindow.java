
/* *
 * @author Kim Buckner
 * Date: Jan 13, 2019
 *
 * This is the actual "game". Will have to make some major changes.
 *
 * Description: This contains the code that sets up the window, and
 * handles actions performed.
 * 
 * Edited by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.InvalidPathException;

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
    private SidePanel leftPanel, rightPanel;
    private GameBoard gameBoard;
    private FileDecoder filedecoder;

    /**
     * Constructor sets the window name using super(), changes the layout, which
     * you really need to read up on, and maybe you can see why I chose this
     * one.
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
     *          BTW can ask the event for the name of the object generating
     *          event. The odd syntax for non-java people is that "exit" for
     *          instance is converted to a String object, then that object's
     *          equals() method is called.
     */

    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand()))
            System.exit(0);
        if ("reset".equals(e.getActionCommand()))
            reset();
        if ("file".equals(e.getActionCommand())) /* Parker */
            fileMenu(); /* Parker */
    }

    /**
     * Establishes the initial board
     */

    public void setUp(String path) {
        // actually create the array for elements, make sure it is big enough

        // Need to play around with the dimensions and the gridx/y values
        // These constraints are going to be added to the pieces/parts I
        // stuff into the "GridBag".
        // YOU CAN USE any type of constraints you like. Just make it work.
        FileDecoder filedecoder = new FileDecoder();
        tryUpdateFiledecoder(filedecoder, path);
        this.filedecoder = filedecoder;

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
        rightPanel = new SidePanel(0, swapper, filedecoder);
        this.add(rightPanel, basic);

        // want to be able to add swapper outside of constructor call for panels
        // and
        // board, more elegant

        basic.gridx = 2;
        basic.gridy = 1;
        basic.anchor = GridBagConstraints.EAST;
        leftPanel = new SidePanel(1, swapper, filedecoder);
        this.add(leftPanel, basic);

        basic.gridx = 1;
        basic.gridy = 1;
        gameBoard = new GameBoard(swapper, filedecoder);
        add(gameBoard, basic);

        return;

    }

    private void tryUpdateFiledecoder(FileDecoder filedecoder, String path) {
        try {
            filedecoder.readfile(path);
        } catch (InvalidPathException e) {
            JOptionPane.showMessageDialog(this, "Failed to find file");
            System.exit(ERROR);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read file");
            System.exit(ERROR);
        }
    }

    /**
     * Used by setUp() to configure the buttons on a button bar and add it to
     * the gameBoard
     */
    private void reset() {
        leftPanel.resetside(filedecoder);
        rightPanel.resetside(filedecoder);
        gameBoard.resetboard();
    }

    private void newgame(String path) {
        FileDecoder filedecoder = new FileDecoder();
        tryUpdateFiledecoder(filedecoder, path);
        this.filedecoder = filedecoder;
        leftPanel.newgame(filedecoder);
        rightPanel.newgame(filedecoder);
        gameBoard.resetboard();
    }

    private void fileMenu() {
        /* Load or save pop up */
        Object[] options = { "Save", "Load", "Cancel" };
        int n = JOptionPane.showOptionDialog(this, "Save or Load Game?",
                "Save or Load Menu", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
        FileDialog selector = new FileDialog(this);
        String fileName = null;
        if (n == 0) {
            /* User pressed Save */
            selector.setTitle("Save");
            selector.setMode(FileDialog.SAVE);
            selector.setDirectory("input");
            selector.setVisible(true);
            fileName = selector.getFile();
            /* statement checks if user canceled */
            if(fileName != null) {
                /*write magicNumber (0xca, 0xfe, 0xed) */
                /* numTiles */
                    /* placement */
                    /* rotation */
                    /* numLines */
                        /* line data */
            }

        } else if (n == 1) {
            /* User pressed Load */
            selector.setTitle("Load");
            selector.setMode(FileDialog.LOAD);
            selector.setDirectory("input");
            selector.setVisible(true);
            fileName = selector.getFile();
            /* statement checks if user canceled */
            if (fileName != null) {
                loadGame(selector.getDirectory() + fileName);
            }
        } else {
            /* User pressed Cancel */
            return;
        }

    }
    
    private void loadGame(String path) {
        FileDecoder filedecoder = new FileDecoder();
        tryUpdateFiledecoder(filedecoder, path);
        this.filedecoder = filedecoder;
        leftPanel.newgame(filedecoder);
        rightPanel.newgame(filedecoder);
        gameBoard.resetboard();
    }

    public void addButtons(GridBagConstraints basic) {
        JPanel btnMenu = new JPanel();

        JButton fileBtn = new JButton("File"); /* Parker */
        JButton resetBtn = new JButton("Reset");
        JButton quitBtn = new JButton("Quit");

        btnMenu.setBackground(new Color(254, 211, 48, 255));

        fileBtn.setActionCommand("file"); /* Parker */
        fileBtn.addActionListener(this);

        resetBtn.setActionCommand("reset");
        resetBtn.addActionListener(this);

        quitBtn.setActionCommand("exit");
        quitBtn.addActionListener(this);

        btnMenu.add(fileBtn);
        btnMenu.add(resetBtn);
        btnMenu.add(quitBtn);
        this.add(btnMenu, basic);

        return;
    }

};
