
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: Create the grid in the center
 * */

public class GameBoard extends tileGrid {
    private static final long serialVersionUID = 1L;

    public GameBoard(int IDOffset, int tileCount) {
        super(IDOffset, 0, tileCount, tileCount);
    }
    
    public boolean solved() {
        
        for (int i = 0; i < tileWrappers.length; i++) {
            for (int j = 0; j < tileWrappers[i].length; j++) {
                Tile tile = tileWrappers[i][j].getTile();
                if (tile == null || (tile.getTileWrapperID()-16) != tile.getTileID() || tile.getRotation() != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
