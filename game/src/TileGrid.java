import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class TileGrid extends JPanel {

    private TileWrapper[][] tileWrapper;
    private int TileCount, side;
    
    
    public TileGrid(FileDecoder fileDecoder, ClickSwapper swapper, int width, int height, int start) {
        
        tileWrapper = new TileWrapper[width][height];
        
        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);
        
        GridBagConstraints constraints = new GridBagConstraints();
        
        
        for (int i = 0; i < height; i++) {
            for(int x = 0; x < width; x++) {
                constraints.gridy = i;
                constraints.gridx = x;
                Tile tile = new Tile();
                TileWrapper tileWrapper = new TileWrapper(swapper, start);
                tileWrapper.setTile(tile);
                tileWrapper.InitializeTile(tile);
                this.tileWrapper[i][x] = tileWrapper;
                add(tileWrapper, constraints);
                start++;
            }
        }
    }
    
    public void fillTileWrappers(FileDecoder fileDecoder) {
        
    }

    public void reset() {
        for (int i = 0; i < tileWrapper.length; i++) {
            for(int j = 0; j < tileWrapper[i].length; j++) {
                tileWrapper[i][j].reset();
            }
        }
    }

    public void newGame(FileDecoder filedecoder) {
        reset(); // why not
    }
    
}