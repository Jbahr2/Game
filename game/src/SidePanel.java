
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Creates the tiles on the side of the game board.
 *  
 * */
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class SidePanel extends JPanel {

    public SidePanel(int side, ClickSwapper swapper) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 0);
        setBackground(new Color(0, 0, 0, 0));

        // initialize tiles for each player
        for (int i = 0; i < 8; i++) {
            constraints.gridy = i;
            Tile tile = new Tile(Integer.toString(side + i));

            TileWrapper tileWrapper = new TileWrapper(swapper);
            tileWrapper.setTile(tile);

            add(tileWrapper, constraints);
        }
    }
    
}
