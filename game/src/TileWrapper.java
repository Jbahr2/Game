import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TileWrapper extends JPanel {

    private Tile tile;

    private Color borderColor = new Color(100, 100, 0);

    public TileWrapper(ClickSwapper swapper) {
        super();

        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        setOpaque(true);

        int size = 50;
        setPreferredSize(new Dimension(size, size));

        setBackground(new Color(254, 211, 48, 255));
        tile = null;
        updateBorder();
        
        addMouseListener(swapper);
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

    public void setTile(Tile toSet) {
        if (toSet != null) {
            tile = toSet;
            updateBorder();
            add(tile, BorderLayout.CENTER);
        }
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
    
    private void updateBorder() {
        if (hasTile()) {
            setBorder(BorderFactory.createEmptyBorder());
        } else {
            setBorder(BorderFactory.createLineBorder(borderColor));
        }
    }
}