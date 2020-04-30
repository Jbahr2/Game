
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: Create the grid in the center
 * */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameBoard extends TileGrid {
    private TileWrapper[] tileWrapper;

    public GameBoard(FileDecoder filedecoder, ClickSwapper swapper, int width, int height, int start) {
        super(filedecoder, swapper, width, height, start);
        }

}
