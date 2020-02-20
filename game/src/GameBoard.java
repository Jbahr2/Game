
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;


public class GameBoard extends JPanel {

	public GameBoard() {
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);

		GridBagConstraints center = new GridBagConstraints();
		center.insets = new Insets(0, 0, 0, 0);
		setBackground(new Color(0, 0, 0, 0));

		// initialize grid of 4x4 tiles
		for (int y = 0; y < 4; y++) {
			center.gridy = y;
			for (int x = 0; x < 4; x++) {
				Tile tile = new Tile("xy");
				center.gridx = x;

				add(tile, center);
			}
		}
	}
}
