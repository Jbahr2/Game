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
public class GameBoard extends JPanel {
	private TileWrapper[] tileWrapper = new TileWrapper[16];
    public GameBoard(ClickSwapper swapper) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints center = new GridBagConstraints();
        setBackground(new Color(0, 0, 0, 0));

        // initialize grid of 4x4 tiles
        int id = 0;
        for (int y = 0; y < 4; y++) {
            center.gridy = y;
            for (int x = 0; x < 4; x++) {
                TileWrapper tileWrapper = new TileWrapper(swapper);
                this.tileWrapper[id] = tileWrapper;
                tileWrapper.InitializeTile(tileWrapper.getTile());
                center.gridx = x;
                add(tileWrapper, center);
                id++;
            }
        }
    }
    public void resetboard()
    {
    	for(int i = 0; i < 16; i++) {
    		tileWrapper[i].removeTile();
    	}
    }
}
