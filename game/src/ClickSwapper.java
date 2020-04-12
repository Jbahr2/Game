
/* *
 * Edited by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: Handles the mouse input and moving tiles.
 * */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

class ClickSwapper extends MouseAdapter {

    TileWrapper selected = null;

    public ClickSwapper() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        if (source instanceof TileWrapper) {
            TileWrapper clickedTile = (TileWrapper) source;

            if (SwingUtilities.isLeftMouseButton(e)) { // moving tile
                if (selected == null && clickedTile.hasTile()) {
                    // selecting a new tile
                    select(clickedTile);
                } else if (selected != null && !clickedTile.hasTile()) {
                    // moving to an empty tile
                    moveTile(selected, clickedTile);
                    deselct();
                } else if (selected == clickedTile) {
                    deselct();
                } else {
                    illegalMove();
                }
            } else if (SwingUtilities.isRightMouseButton(e)
                    && clickedTile.hasTile()) {
                clickedTile.rotatetile();
            }
        } else {
            illegalMove();
        }
    }

    public void moveTile(TileWrapper tile1, TileWrapper tile2) {
        tile2.setTile(tile1.getTile());
        tile1.removeTile();
    }

    private void deselct() {
        if (selected != null) {
            selected.setAsUnselected();
            selected = null;
        }
    }

    private void illegalMove() {
        if (selected != null)
            selected.illegalBorder();
    }

    private void select(TileWrapper tile) {
        selected = tile;
        selected.setAsSelected();
    }
}