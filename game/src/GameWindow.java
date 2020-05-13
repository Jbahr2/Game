
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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.InvalidPathException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameWindow extends JFrame implements ActionListener {
    /* *
     * because it is a serializable object, need this or javac complains <b>a
     * lot</b>, the ID can be any integer.
     * */
    public static final long serialVersionUID = 1;

    private SidePanel leftPanel, rightPanel;
    private GameBoard gameBoard;
    private ClickSwapper swapper;
    private FileDecoder filedecoder;
    private boolean isBadGame;
    private int seconds;
    public JLabel displayTime;

    public GameWindow(String s) {
        super(s);
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        seconds = 0;
    }

    /* *
     * For the buttons
     * 
     * @param e is the ActionEvent
     * 
     *          BTW can ask the event for the name of the object generating
     *          event. The odd syntax for non-java people is that "exit" for
     *          instance is converted to a String object, then that object's
     *          equals() method is called.
     * */

    public void actionPerformed(ActionEvent e) {
        if ("exit".equals(e.getActionCommand())) {
            quit();
        }
        if ("reset".equals(e.getActionCommand())) {
            reset();
        }
        if ("file".equals(e.getActionCommand())) {
            loadOrSave();
        }
        if ("clock".equals(e.getActionCommand())) {;
            updateTimer();
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
        
        // timer setup
        basic.anchor = GridBagConstraints.NORTH;
        basic.fill = GridBagConstraints.CENTER;
        basic.gridx = 1;
        basic.gridy = 0;
        displayTime = new JLabel();
        Timer timer = new Timer(1000, this);
        timer.setActionCommand("clock");
        timer.start();
        this.addTimer(timer, displayTime, basic);
        
        // button setup
        basic.fill = GridBagConstraints.HORIZONTAL;
        basic.gridx = 1;
        basic.gridy = 1;
        this.addButtons(basic);

        // side panels setup
        basic.gridx = 0;
        basic.gridy = 2;
        basic.anchor = GridBagConstraints.WEST;
        rightPanel = new SidePanel(0, 8);
        rightPanel.addSwapper(swapper);
        this.add(rightPanel, basic);

        basic.gridx = 2;
        basic.gridy = 2;
        basic.anchor = GridBagConstraints.EAST;
        leftPanel = new SidePanel(8, 8);
        leftPanel.addSwapper(swapper);
        this.add(leftPanel, basic);

        // center panel setup
        basic.gridx = 1;
        basic.gridy = 2;
        gameBoard = new GameBoard(16, 4);
        gameBoard.addSwapper(swapper);
        add(gameBoard, basic);

        // start with initial file
        try {
            loadGame("input/default.mze");
        } catch (InvalidPathException | IOException e) {
            // TODO Auto-generated catch block
            loadDialog();
        }
        return;
    }

    private void reset() {
        if(isBadGame == true) {
            return;
        }
        seconds = 0;
        leftPanel.reset();
        rightPanel.reset();
        gameBoard.reset();
        swapper.reset();
    }

    private void loadOrSave() {
        // first ask to load or save
        // 0 = load, 1 = save
        Object[] options = { "Load", "Save" };
        int decision = JOptionPane.showOptionDialog(this, "Select an action",
                "File load", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, // do not use a custom Icon
                options, // the titles of buttons
                options[0]); // default button title

        if (decision == 0) { // loading file
            checkToSave();
            loadDialog();
        } else if (decision == 1) { // loading file
            saveDialog();
        }
    }

    private void saveDialog() {
        // get path
        if(isBadGame == true) {
            JOptionPane.showMessageDialog(this, "File cannot be save: Invalid Format"); //change wording 
            return;
        }
        JFileChooser fileChooser = new JFileChooser(
                System.getProperty("user.dir"));
        int r = fileChooser.showSaveDialog(null);

        if (r != JFileChooser.CANCEL_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            File saveFile = new File(filePath);

            if (saveFile.exists()) {
                Object[] options = { "Overwrite", "Pick new file path" };
                int decision = JOptionPane.showOptionDialog(this,
                        "That file already exists", "File save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, // do not use a custom Icon
                        options, // the titles of buttons
                        options[0]); // default button title

                if (decision == 0) {
                    saveGame(filePath);
                } else if (decision == 1) {
                    saveDialog();
                }
            } else {
                saveGame(filePath);
            }
        }
    }

    private void loadDialog() {
        
        JFileChooser fileChooser = new JFileChooser(
                System.getProperty("user.dir"));
        int r = fileChooser.showOpenDialog(null);

        if (r != JFileChooser.CANCEL_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try {
                loadGame(filePath);
            } catch (InvalidPathException e) {
                JOptionPane.showMessageDialog(this, "Failed to find file");
            } catch (IOException e) {
                leftPanel.clearGrid();
                rightPanel.clearGrid();
                gameBoard.clearGrid();
                isBadGame = true; 
                JOptionPane.showMessageDialog(this, "File has incorrect type");
                loadDialog();
            }
        }
    }
    

    private void saveGame(String filePath) {
        try {
            swapper.resetModified();
            FileOutputStream saveFile = new FileOutputStream(filePath, false);

            byte[] playedFlag = { (byte) 0xca, (byte) 0xfe, (byte) 0xde,
                    (byte) 0xed };
            
            Tile[] tiles = filedecoder.getTiles();            
            byte[][] tileBytes = new byte[tiles.length][];
            int tileByteCount = 0;
            
            for (int i = 0; i < tileBytes.length; i++) {
                tileBytes[i] = tiles[i].getByteArray();
                tileByteCount += tileBytes[i].length;
            }

            ByteBuffer bytes = ByteBuffer.allocate(4+4+tileByteCount);
            
            bytes.put(playedFlag);
            bytes.putInt(tiles.length);
            
            for (int i = 0; i < tileBytes.length; i++) {
                for (int j = 0; j < tileBytes[i].length; j++) {
                    bytes.put(tileBytes[i][j]);
                }
            }

            saveFile.write(bytes.array());
            saveFile.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private void loadGame(String filePath)
            throws InvalidPathException, IOException {
        isBadGame = false;
        swapper.resetModified();

        filedecoder = new FileDecoder();
        filedecoder.readFile(filePath);

        leftPanel.setTiles(filedecoder);
        leftPanel.reset();
        rightPanel.setTiles(filedecoder);
        rightPanel.reset();
        gameBoard.setTiles(filedecoder);
        gameBoard.reset();
    }

    private void quit() {
        checkToSave();
        System.exit(0);

    }

    private void checkToSave() {
        if (swapper.getModified()) {
            Object[] options = { "Save", "Don't save" };
            int decision = JOptionPane.showOptionDialog(this,
                    "Would you like to save first?", "File select",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, // do not use a custom Icon
                    options, // the titles of buttons
                    options[0]); // default button title

            if (decision == 0) {
                saveDialog();
            }

        }
    }

    /**
     * Used by setUp() to configure the buttons on a button bar and add it to
     * the gameBoard
     */
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
    
    public void addTimer(Timer timer, JLabel displayTime, GridBagConstraints basic) {
        JPanel background = new JPanel();
        
        
        
        
        background.setBackground(new Color(254, 211, 48, 255));
        
        displayTime.setText("0 : 0 : 0");
        
        background.add(displayTime);
        
        this.add(background, basic);
        
    }
    
    public void updateTimer() {
        seconds++;
        
        int hours = seconds / 3600;
        int minutes = seconds / 60;
        displayTime.setText("" + hours + " : "+minutes+" : "+(seconds%60));
    }

};
