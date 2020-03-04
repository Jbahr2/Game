/* *
 * author: Group H
 * date: 3/3/2020
 * 
 * Tile class
 * utilizes panels now instead of labels
 * 
 * */

import java.awt.Color;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {
    
    boolean empty = false;
    
    public Tile(String name) {
        super();
        setPreferredSize(new Dimension(100, 100));
        setOpaque(true);
        setBackground(new Color(254, 211, 48));

        add(new JLabel(name));
    }
}
