import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.swing.JPanel;

public class tileGrid extends JPanel {

    private TileWrapper[][] tileWrappers;

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

    public byte[] getByteArray() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                if (tileWrappers[i][j].hasTile()) {
                    try {
                        bytes.write(tileWrappers[i][j].getByteArray());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }

        return bytes.toByteArray();
    }

}
