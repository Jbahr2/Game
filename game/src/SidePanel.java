import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class SidePanel extends JPanel {

	public SidePanel() {
		setAlignmentX(CENTER_ALIGNMENT);

		setBackground(new Color(0, 0, 0, 0));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		for (int i = 0; i < 8; i++) {
			Tile tile = new Tile("R");
			tile.setBackground(new Color(69, 170, 242));
			add(tile);
			add(Box.createRigidArea(new Dimension(8, 8)));
		}
	}
}
