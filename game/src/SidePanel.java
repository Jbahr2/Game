
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

public class SidePanel extends JPanel {
    public SidePanel(int side, ClickSwapper swapper, float x1[][], float x2[][], float y1[][], float y2[][], int[] numLines, int numberOfTiles, int tileNumber) {
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 0, 0, 0);
        setBackground(new Color(0, 0, 0, 0));
        // initialize tiles for each player
        for (int i = 0; i < 8; i++) {
            constraints.gridy = i;
            int Ntile = side + i;
            Tile tile = new Tile(x1[Ntile], x2[Ntile], y1[Ntile],y2[Ntile],numLines[Ntile]);
            TileWrapper tileWrapper = new TileWrapper(swapper);
            tileWrapper.setTile(tile);

            add(tileWrapper, constraints);
        }
    }
    
}
