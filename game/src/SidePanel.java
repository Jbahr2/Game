
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: Creates the tiles on the side of the game board.
 * */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SidePanel extends JPanel {
	private TileWrapper[] tileWrapper = new TileWrapper[8];
    public SidePanel(int side, ClickSwapper swapper, FileDecoder filedecoder) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 0);
        setBackground(new Color(0, 0, 0, 0));
        // initialize tiles for each player
        int Ntile;
        for (int i = 0; i < 8; i++) {
            constraints.gridy = i;
            Ntile = side + i;
            Tile tile = new Tile();
            tile.SetTileInfo(filedecoder.getX1(Ntile), filedecoder.getX2(Ntile), filedecoder.getY1(Ntile),filedecoder.getY2(Ntile),filedecoder.getNumLines(Ntile));
            TileWrapper tileWrapper = new TileWrapper(swapper);
            tileWrapper.setTile(tile);
            tileWrapper.InitializeTile(tile);
            this.tileWrapper[i] = tileWrapper;
            add(tileWrapper, constraints);
        }
    }
    public void resetside()
    {
    	for(int i = 0; i < 8; i++) {
    		tileWrapper[i].setTile(tileWrapper[i].getStartingTile());
    	}
    }
}
