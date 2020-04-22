
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
import javax.swing.filechooser.*;
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

    
    
    private SidePanel leftPanel, rightPanel;
    private GameBoard gameBoard;
    private ClickSwapper swapper;

    
    
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
        if ("exit".equals(e.getActionCommand())) {
            System.exit(0);
        }
        if ("reset".equals(e.getActionCommand())) {
            reset();
        }
        if ("file".equals(e.getActionCommand())) {

            // first ask to load or save
            // 0 = load, 1 = save
            Object[] options = { "Load", "Save" };
            int decision = JOptionPane.showOptionDialog(this,
                    "Select an action", "File select",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, // do not use a custom Icon
                    options, // the titles of buttons
                    options[0]); // default button title

            if (decision != JOptionPane.CLOSED_OPTION) {

                JFileChooser fileChooser = new JFileChooser(
                        System.getProperty("user.dir"));

                if (decision == 0) { // loading file
                    int r = fileChooser.showOpenDialog(null);

                    // if the user selects a file
                    if (r == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile()
                                .getAbsolutePath();

                        // need to check if current game should be saved first
                        try {
                            loadGame(filePath);
                        } catch (InvalidPathException | IOException e1) {
                            JOptionPane.showMessageDialog(this, "Failed to read file");
                        }

                    }
                } else if (decision == 1) { // loading file
                    int r = fileChooser.showSaveDialog(null);

                    // if the user selects a file
                    if (r == JFileChooser.APPROVE_OPTION) {
                        String filePath = fileChooser.getSelectedFile()
                                .getAbsolutePath();

                        // save code here

                    }
                }
            }
        }
    }

    /**
     * Establishes the initial board
     */

    public void setUp(String path) {

        swapper = new ClickSwapper();
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
        rightPanel = new SidePanel(0, 8);
        rightPanel.addSwapper(swapper);
        this.add(rightPanel, basic);

        basic.gridx = 2;
        basic.gridy = 1;
        basic.anchor = GridBagConstraints.EAST;
        leftPanel = new SidePanel(8, 8);
        leftPanel.addSwapper(swapper);

        this.add(leftPanel, basic);

        basic.gridx = 1;
        basic.gridy = 1;
        gameBoard = new GameBoard(16, 4);
        gameBoard.addSwapper(swapper);

        add(gameBoard, basic);

        try {
            loadGame("input/default.mze");
        } catch (InvalidPathException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return;

    }

    /**
     * Used by setUp() to configure the buttons on a button bar and add it to
     * the gameBoard
     */
    private void reset() {
        leftPanel.reset();
        rightPanel.reset();
        gameBoard.reset();
        swapper.reset();
    }

    private void loadGame(String path) throws InvalidPathException, IOException {
        FileDecoder filedecoder = new FileDecoder();
        filedecoder.readFile(path);

        leftPanel.setTiles(filedecoder);
        rightPanel.setTiles(filedecoder);
        gameBoard.reset();
    }

    public void addButtons(GridBagConstraints basic) {
        JPanel btnMenu = new JPanel();

        JButton newGameBtn = new JButton("File");
        JButton resetBtn = new JButton("Reset");
        JButton quitBtn = new JButton("Quit");

        btnMenu.setBackground(new Color(254, 211, 48, 255));

        newGameBtn.setActionCommand("file");
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
