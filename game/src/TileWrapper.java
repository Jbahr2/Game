/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: This is the wrapper used hold the tiles.
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TileWrapper extends JPanel {

    public int ID;
    private Tile tile;
    private Tile sTile;
    private Color borderColor = new Color(100, 100, 0);

    public TileWrapper(int ID) {
        super();

        this.ID = ID;

        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        setOpaque(true);

        int size = 100;
        setPreferredSize(new Dimension(size, size));

        setBackground(new Color(254, 211, 48, 255));
        tile = null;
        updateBorder();
    }

    public void addSwapper(ClickSwapper swapper) {
        addMouseListener(swapper);
    }

    public void setTile(Tile toSet) {
        removeTile();
        if (toSet != null) {
            tile = toSet;
            updateBorder();
            add(tile, BorderLayout.CENTER);
        }
    }

    public Tile getTile() {
        return tile;
    }

    public void removeTile() {
        if (tile != null) {
            remove(tile);
            tile = null;
            updateBorder();
        }
    }

    public void rotateTile() {
        tile.rotate();
    }

    public boolean hasTile() {
        return tile != null;
    }

    public void setInitialTile(Tile toSet) {
        sTile = toSet;
    }

    public void reset() {
        removeTile();
        setTile(sTile);
        if (tile != null) {
            tile.reset();
        }
        updateBorder();
    }

    public boolean checkModified() {
        return tile != sTile || (tile != null && tile.modified());
    }

    public byte[] getByteArray() {
        if (tile != null) {
            byte[] tileBytes = tile.getByteArray();
            ByteBuffer bytes = ByteBuffer.allocate(4 + tileBytes.length);

            bytes.putInt(ID); // placement
            bytes.put(tile.getByteArray());

            return bytes.array();

        }
        return null;

    }

    // border updating

    public void setAsSelected() {
        setBorder(BorderFactory.createLineBorder(borderColor, 3));
    }

    public void setAsUnselected() {
        updateBorder();
    }

    public void illegalBorder() {
        setBorder(BorderFactory.createLineBorder((new Color(250, 0, 0)), 3));
    }

    private void updateBorder() {
        if (hasTile()) {
            setBorder(BorderFactory.createEmptyBorder());
        } else {
            setBorder(BorderFactory.createLineBorder(borderColor, 3));
        }
    }

}
