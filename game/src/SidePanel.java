
/* *
 * @author: Group H
 * date: 3/3/2020
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

    public SidePanel(int side) {
        setAlignmentX(CENTER_ALIGNMENT);

        setBackground(new Color(0, 0, 0, 0));

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        /* initialize a tile counter */
        int count = 0;
        if(side == 1)
            count = 8; /* set tile counter to 8 for right sidePanel */

        // initialize tiles for each player
        for (int i = 0; i < 8; i++) {
            Tile tile = new Tile();
            tile.add(new JLabel(""+(count+i)));
            tile.setBackground(new Color(69, 170, 242));
            add(tile);
            add(Box.createRigidArea(new Dimension(8, 8)));
        }
    }
}
