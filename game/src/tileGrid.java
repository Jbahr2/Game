import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 04/25/2020
 * 
 * Description: General format for the tile holders, creates the gameboard and grid.
 * */
public abstract class tileGrid extends JPanel {
    private static final long serialVersionUID = 1L;
    
    
    protected TileWrapper[][] tileWrappers;

    public tileGrid(int IDoffset, int spacing, int width, int height) {
        this.tileWrappers = new TileWrapper[width][height];

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(spacing, spacing, 0, 0);
        setBackground(new Color(0, 0, 0, 0));

        for (int j = 0; j < height; j++) {
            constraints.gridy = j;
            for (int i = 0; i < width; i++) {
                constraints.gridx = i;
                int ID = IDoffset + i + j * width;
                tileWrappers[i][j] = new TileWrapper(ID);
                add(tileWrappers[i][j], constraints);
            }
        }
    }

    public void addSwapper(ClickSwapper swapper) {
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                tileWrappers[i][j].addSwapper(swapper);
            }
        }
    }

    public void reset() {
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                tileWrappers[i][j].reset();
            }
        }
    }

    public void setTiles(FileDecoder filedecoder) {
        reset();
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {

                Tile tile = filedecoder.getTile(tileWrappers[i][j].getID());

                tileWrappers[i][j].setTile(tile);
                tileWrappers[i][j].setInitialTile(tile);

            }
        }
    }
    public void clearGrid() {
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                tileWrappers[i][j].removeTile();
            }
        }
    }
    
}
