
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
}
