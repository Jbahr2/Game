import java.awt.Color;

import javax.swing.*;

public class Tile extends JLabel {
	public Tile(String text) {
		super(text);
		setOpaque(true);
		setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
		setBackground(new Color(254, 211, 48));
	}
}
