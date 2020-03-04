import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

class ClickSwapper extends MouseAdapter {

    Tile selected = null;

    public ClickSwapper() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source instanceof Tile) {

            Tile clickedTile = (Tile) source;

            if (selected == null) {
                setSelected(clickedTile);
            } else if (!clickedTile.empty) { // check if clicked tile isnt null
                swapTiles(selected, clickedTile);
                removeSelected();
            } else {
                removeSelected();
            }
        } else {
            removeSelected();
        }
    }

    private void swapTiles(Tile tile1, Tile tile2) {

    }

    private void removeSelected() {
        if (selected != null) {
            selected.setBorder(BorderFactory.createEmptyBorder());
            selected = null;
        }
    }

    private void setSelected(Tile tile) {
        selected = tile;
        selected.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 0)));
    }
}