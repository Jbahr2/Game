
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

    private TileWrapper selected = null;
    private GameWindow window;
    
    
    public ClickSwapper(GameWindow window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        
        // make sure what we clicked is a tile wrapper
        if (source instanceof TileWrapper) {
            TileWrapper clickedTile = (TileWrapper) source;

            // on left click, we move a tile
            if (SwingUtilities.isLeftMouseButton(e)) {
                if (selected == null && clickedTile.hasTile()) {
                    // selecting a new tile
                    select(clickedTile);
                } else if (selected != null && !clickedTile.hasTile()) {
                    // moving to an empty tile
                    moveTile(selected, clickedTile);
                    deselect();
                } else if (selected == clickedTile) {
                    // deselect on clicking on the already selected tile
                    deselect();
                } else {
                    illegalMove();
                }
            } else if (SwingUtilities.isRightMouseButton(e) // right click rotates a tile
                    && clickedTile.hasTile()) {
                rotateTile(clickedTile);
            }
        } else {
            illegalMove();
        }

    }

    private void rotateTile(TileWrapper tile) {
        tile.rotateTile();
        window.updateModified(true);
        window.checkSolved();
    }

    public void reset() {
        deselect();
    }
    
    /**
     * moves a tile from one wrapper to an empty one
     */
    private void moveTile(TileWrapper tile1, TileWrapper tile2) {
        window.updateModified(true);
        tile2.setTile(tile1.getTile());
        tile1.removeTile();
        window.checkSolved();

    }

    private void deselect() {
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