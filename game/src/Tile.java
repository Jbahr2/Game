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
        
    public Tile(String name) {
        super();
        setOpaque(true);
        setBackground(new Color(69, 170, 242));
        add(new JLabel(name));
    }
}
