
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameWindow extends JFrame implements ActionListener {
    /**
     * because it is a serializable object, need this or javac complains <b>a
     * lot</b>, the ID can be any integer.
     */
    public static final long serialVersionUID = 1;

    private long timer;
    private SidePanel leftPanel, rightPanel;
    private GameBoard gameBoard;
    private JLabel timerDisplay;
    private ClickSwapper swapper;
    private FileDecoder filedecoder;
    private boolean modified, isBadGame;

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
            quit();
        }
        if ("reset".equals(e.getActionCommand())) {
            reset();
        }
        if ("file".equals(e.getActionCommand())) {
            loadOrSave();
        }
    }

    /**
     * Establishes the initial board
     */
    public void setUp(String path) {

        swapper = new ClickSwapper(this);
        addMouseListener(swapper);

        GridBagConstraints basic = new GridBagConstraints();
        basic.insets = new Insets(5, 5, 5, 5);

        
        ScheduledExecutorService executorService = Executors
                .newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new IncrementTimer(), 0, 1,
                TimeUnit.SECONDS);

        // button and timer setup
        basic.anchor = GridBagConstraints.CENTER;
        basic.gridx = 1;
        basic.gridy = 0;
        timerDisplay = new JLabel();
        this.add(timerDisplay, basic);
        updateTimerDisplay();
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
        if (isBadGame == true) {
            return;
        }
        leftPanel.reset();
        rightPanel.reset();
        gameBoard.reset();
        swapper.reset();

        timer = filedecoder.getTime();
        updateTimerDisplay();
        updateModified(false);
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
        if (isBadGame == true) {
            JOptionPane.showMessageDialog(this,
                    "File cannot be save: Invalid Format"); // change wording
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

        // get path

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
            updateModified(false);
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

            ByteBuffer bytes = ByteBuffer.allocate(4 + 8 + 4 + tileByteCount);

            bytes.put(playedFlag);
            bytes.putInt(tiles.length);
            bytes.putLong(timer);

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
        updateModified(false);

        filedecoder = new FileDecoder();
        filedecoder.readFile(filePath);

        timer = filedecoder.getTime();
        updateTimerDisplay();

        leftPanel.setTiles(filedecoder);
        rightPanel.setTiles(filedecoder);
        gameBoard.setTiles(filedecoder);
        reset();

    }

    private void quit() {
        checkToSave();
        System.exit(0);

    }

    private void checkToSave() {
        if (modified) {
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


    class IncrementTimer implements Runnable {
        public void run() {
            if (modified) {
                timer++;
                updateTimerDisplay();
            }
        }
    };

    private void updateTimerDisplay() {
        int s = (int) (timer % 60);
        int m = (int) (timer / 60) % 60;
        int h = (int) (timer / 3600);
        
        String sString = "" + s;
        if (s <= 9) {
            sString = "0" + s;
        }
        String mString = "" + m;
        if (m <= 9) {
            mString = "0" + m;
        }
        String hString = "" + h;

        String displayTime = hString + ":" + mString + ":" + sString;
        timerDisplay.setText(displayTime);
    }

    public void updateModified(boolean b) {
        modified = b;
    }

    public void checkSolved() {
        if (gameBoard.solved()) {
            JOptionPane.showMessageDialog(this, "You have Won! \nTime: " + timerDisplay.getText());

        }
    }
};
