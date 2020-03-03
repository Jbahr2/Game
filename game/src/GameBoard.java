
/* *
 * author: Jacob, Jason, Parker, Sam, Trevor
 * date: 3/3/2020
 * 
 * Create the grid in the center
 * */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class GameBoard extends JPanel {

    public GameBoard() {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints center = new GridBagConstraints();
        center.insets = new Insets(0,0,0,0);
        setBackground(new Color(0, 0, 0, 0));

        // initialize grid of 4x4 tiles
        for (int y = 0; y < 4; y++) {
            center.gridy = y;
            for (int x = 0; x < 4; x++) {
                Tile tile = new Tile();
                tile.setBorder(BorderFactory.createLineBorder(new Color(119, 140, 163), 1));
                center.gridx = x;
                add(tile, center);
            }
        }
    }
}
