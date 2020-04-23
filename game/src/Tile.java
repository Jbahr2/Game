/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 * Description: movable tile with identifier
 * */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.awt.BasicStroke;
import javax.swing.*;

@SuppressWarnings("serial")
public class Tile extends JPanel {

    // 0 = x1, 1 = y1, 2 = x2, 3 = y2
    private float[][] lines;
    // degree: 0 = no rotation, 1 = 90, etc...
    private int initialDegree, degree, tileID;

    public Tile(float[][] lines, int degree, int tileID) {
        super();
        this.lines = lines;
        this.degree = degree;
        this.initialDegree = degree;
        this.tileID = tileID;
        setOpaque(true);
        setBackground(new Color(69, 170, 242));
    }
    
    public void rotate() {
        degree = (degree + 1) % 4;
        this.repaint();
    }

    public void reset() {
        degree = initialDegree;
    }

    public int getTileID() {
        return tileID;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D G2D = (Graphics2D) g;
        G2D.setColor(Color.BLACK);
        G2D.setStroke(new BasicStroke(2.5f));

        G2D.rotate(Math.toRadians(degree * 90), 50, 50);

        // draw lines
        for (int i = 0; i < lines.length; i++) {
            G2D.draw(new Line2D.Double(lines[i][0], lines[i][1], lines[i][2],
                    lines[i][3]));
        }
    }
    
    public byte[] getByteArray() {
        ByteBuffer bytes = ByteBuffer.allocate(4+4+4*4*lines.length);
        
        bytes.putInt(degree); // rotation
        bytes.putInt(lines.length); // tile count

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[0].length; j++) {
                bytes.putFloat(lines[i][j]);
            }
        }
        
        return bytes.array();

    }
}
