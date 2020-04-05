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

public class GameBoard extends JPanel {

    public GameBoard(ClickSwapper swapper) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints center = new GridBagConstraints();
        setBackground(new Color(0, 0, 0, 0));

        // initialize grid of 4x4 tiles
        for (int y = 0; y < 4; y++) {
            center.gridy = y;
            for (int x = 0; x < 4; x++) {
                TileWrapper tileWrapper = new TileWrapper(swapper);
                center.gridx = x;
                add(tileWrapper, center);
            }
        }
    }
}
