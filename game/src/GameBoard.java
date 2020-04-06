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
    	int Gridnum = (int)Math.floor(Math.sqrt(filedecoder.getTileNum()));
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        tileWrapper = new TileWrapper[filedecoder.getTileNum()];
        
        //if tile count is not a square number it calculates the overflow and accounts for it else it returns 0
        int tileoverflowY = (int)(Math.ceil((filedecoder.getTileNum() - Math.pow(Gridnum,2)) / Gridnum));
        int tileoverflowX = ((int)(filedecoder.getTileNum() - Math.pow(Gridnum,2)) % Gridnum);
        
        GridBagConstraints center = new GridBagConstraints();
        setBackground(new Color(0, 0, 0, 0));
        // initialize grid of X by X tiles
        for (int y = 0; y < (Gridnum + tileoverflowY); y++) {
            center.gridy = y;
            for (int x = 0; x < (Gridnum + (((y + 1 >= (Gridnum + tileoverflowY)) && (tileoverflowY > 0) && (tileoverflowX != 0)) ? (tileoverflowX - Gridnum) : 0)); x++) {
            	System.out.println(x);
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
