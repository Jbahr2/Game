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

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TileWrapper extends JPanel {

    private Tile tile;
    private Tile sTile;
    private Color borderColor = new Color(100, 100, 0);

    public TileWrapper(ClickSwapper swapper) {
        super();

        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        setOpaque(true);

        int size = 100;
        setPreferredSize(new Dimension(size, size));

        setBackground(new Color(254, 211, 48, 255));
        tile = null;
        updateBorder();

        addMouseListener(swapper);
    }

    public Tile getTile() {
        return tile;
    }

    public Tile getStartingTile() {
        return sTile;
    }

    public void removeTile() {
        if (tile != null) {
            remove(tile);
            tile = null;
            updateBorder();
        }
    }

    public void resetTile() {
        System.out.println(tile);
        if (sTile != null) {
            remove(tile);
            tile = null;
        }
        if (sTile != null) {
            remove(sTile);
            sTile = null;
        }
    }

    public void setTile(Tile toSet) {
        if (toSet != null) {
            tile = toSet;
            updateBorder();
            add(tile, BorderLayout.CENTER);
        }
    }

    public void InitializeTile(Tile toSet) {
        sTile = toSet;
    }

    public boolean hasTile() {
        return tile != null;
    }

    public void setAsSelected() {
        setBorder(BorderFactory.createLineBorder(borderColor));
    }

    public void setAsUnselected() {
        updateBorder();
    }
    public void rotatetile() {
     tile.rotateTile();   
    }
    public void resetRotation() {
        tile.resetRotation();
    }
    private void updateBorder() {
        if (hasTile()) {
            setBorder(BorderFactory.createEmptyBorder());
        } else {
            setBorder(BorderFactory.createLineBorder(borderColor));
        }
    }
}
