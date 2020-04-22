import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class tileGrid extends JPanel {

    private TileWrapper[][] tileWrappers;
    private Object IDoffset;

    public tileGrid(int IDoffset, int spacing, int width, int height) {
        this.IDoffset = IDoffset;
        this.tileWrappers = new TileWrapper[width][height];

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(spacing, 0, 0, 0);
        setBackground(new Color(0, 0, 0, 0));

        for (int i = 0; i < width; i++) {
            constraints.gridx = i;
            for (int j = 0; j < height; j++) {
                constraints.gridy = j;
                tileWrappers[i][j] = new TileWrapper(IDoffset + i + j);
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
                
                Tile tile = filedecoder.getTile(tileWrappers[i][j].ID);

                tileWrappers[i][j].setTile(tile);
                tileWrappers[i][j].setInitialTile(tile);

            }
        }
    }
    
    public boolean checkModified() {
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                if (tileWrappers[i][j].checkModified()) {
                    return true;
                }
            }
        }
        return false;
    }

}
