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
    
    public Tile() {
        super();
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(100, 100,100, 100));//Worked on some
        setPreferredSize(new Dimension(100, 100));
        setBackground(new Color(254, 211, 48));
    }
}
