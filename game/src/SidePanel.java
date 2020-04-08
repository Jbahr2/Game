
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
    private TileWrapper[] tileWrapper;
    private Tile[] tile;
    private int TileCount, Ntile, side;

    public SidePanel(int side, ClickSwapper swapper, FileDecoder filedecoder) {
        // Splits up the tiles to each side, the 2nd part is checking if there
        // is an uneven number of tiles and putting the extra tile on the right
        // side;
        TileCount = filedecoder.getTileNum() / 2
                + ((filedecoder.getTileNum() % 2) * side);
        tile = new Tile[TileCount];
        this.side = side;
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        tileWrapper = new TileWrapper[TileCount];
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 0);
        setBackground(new Color(0, 0, 0, 0));
        // initialize tiles for each player
        for (int i = 0; i < TileCount; i++) {
            constraints.gridy = i;
            Ntile = side * TileCount + i;
            Tile tile = new Tile();
            TileWrapper tileWrapper = new TileWrapper(swapper);
            tileWrapper.setTile(tile);
            tileWrapper.InitializeTile(tile);
            this.tileWrapper[i] = tileWrapper;
            this.tile[i] = tile;
            add(tileWrapper, constraints);
        }
        updatetiles(filedecoder);
    }

    private void updatetiles(FileDecoder filedecoder) {
        for (int i = 0; i < TileCount; i++) {
            Ntile = side * TileCount + i;
            tile[i].SetTileInfo(filedecoder.getX1(Ntile),
                    filedecoder.getX2(Ntile), filedecoder.getY1(Ntile),
                    filedecoder.getY2(Ntile), filedecoder.getNumLines(Ntile));
        }
    }

    public void resetside() {
        for (int i = 0; i < tileWrapper.length; i++)
            tileWrapper[i].setTile(tileWrapper[i].getStartingTile());
    }

    public void newgame(FileDecoder filedecoder) {
        resetside();
        updatetiles(filedecoder);
    }

}
