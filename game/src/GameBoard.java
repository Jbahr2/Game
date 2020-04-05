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
	private TileWrapper[] tileWrapper;
    public GameBoard(ClickSwapper swapper, FileDecoder filedecoder) {
    	int Gridnum = (int)Math.sqrt(filedecoder.getTileNum());
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        tileWrapper = new TileWrapper[(int)Math.pow(Gridnum, 2)];
        
        GridBagConstraints center = new GridBagConstraints();
        setBackground(new Color(0, 0, 0, 0));

        // initialize grid of 4x4 tiles
        for (int y = 0; y < Gridnum; y++) {
            center.gridy = y;
            for (int x = 0; x < Gridnum; x++) {
                TileWrapper tileWrapper = new TileWrapper(swapper);
                this.tileWrapper[y * Gridnum + x] = tileWrapper;
                tileWrapper.InitializeTile(tileWrapper.getTile());
                center.gridx = x;
                add(tileWrapper, center);
            }
        }
    }
    public void resetboard()
    {
    	for(int i = 0; i < tileWrapper.length; i++)
    		tileWrapper[i].removeTile();
    }
}
