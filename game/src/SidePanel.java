
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: Creates the tiles on the side of the game board.
 * */
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SidePanel extends TileGrid {
    private TileWrapper[] tileWrapper;
    private int TileCount, side;
    

    public SidePanel(FileDecoder fileDecoder, ClickSwapper swapper, int width, int height, int start) {
        super(fileDecoder, swapper, width, height, start);
    }



}
