
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

public class GameBoard extends tileGrid {

    public GameBoard(int IDOffset, int tileCount) {
        super(IDOffset, 0, tileCount, tileCount);
    }
}
