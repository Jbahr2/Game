
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
    private int TileCount, side;

    public SidePanel(int side, ClickSwapper swapper, FileDecoder filedecoder) {
        // Splits up the tiles to each side, the 2nd part is checking if there
        // is an uneven number of tiles and putting the extra tile on the right
        // side;
        TileCount = filedecoder.getTileNum() / 2
                + ((filedecoder.getTileNum() % 2) * side);

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
            Tile tile = new Tile();
            TileWrapper tileWrapper = new TileWrapper(swapper);
            tileWrapper.setTile(tile);
            tileWrapper.InitializeTile(tile);
            this.tileWrapper[i] = tileWrapper;
            add(tileWrapper, constraints);
        }
        updateTiles(filedecoder);
    }

    private void updateTiles(FileDecoder filedecoder) {
        for (int i = 0; i < TileCount; i++) {
            tileWrapper[i].setTile(filedecoder.getTile(side * TileCount + i));
            tileWrapper[i].updateTile(filedecoder);
            tileWrapper[i].resetRotation(filedecoder);
        }
    }

    public void resetside(FileDecoder filedecoder) {
        for (int i = 0; i < tileWrapper.length; i++) {
            tileWrapper[i].setTile(tileWrapper[i].getStartingTile());
            tileWrapper[i].resetRotation(filedecoder);
        }
    }

    public void newgame(FileDecoder filedecoder) {
        resetside(filedecoder);
        updateTiles(filedecoder);
    }

}
