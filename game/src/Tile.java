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
import java.awt.BasicStroke;
import javax.swing.*;

@SuppressWarnings("serial")
public class Tile extends JPanel {

    private float x1[], x2[], y1[], y2[];
    private int numLines;
    private double degree, InitialDegree;

    public Tile() {
        super();
        setOpaque(true);
        setBackground(new Color(69, 170, 242));
    }

    public void SetTileInfo(float[] x1, float[] x2, float[] y1, float[] y2,
            int numLines) {
        this.x1 = new float[numLines];
        this.x2 = new float[numLines];
        this.y1 = new float[numLines];
        this.y2 = new float[numLines];
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.numLines = numLines;
        degree = ((int) (Math.random() * 4)) * 90;
        InitialDegree = degree;
    }

    public void rotateTile() {
        degree = (degree + 90) % 360;
        this.repaint();
    }

    public void resetRotation() {
        degree = InitialDegree;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D G2D = (Graphics2D) g;
        G2D.setColor(Color.BLACK);
        G2D.setStroke(new BasicStroke(2.5f));
        double centerx = this.getX() + this.getWidth() / 2;
        double centery = this.getY() + this.getHeight() / 2;
        float x, y, xx, yy;
        for (int i = 0; i < numLines; i++) {
            // Calculates rotation of maze around the center axis of the tile.
            x = (float) (((x1[i] - centerx) * Math.cos(Math.toRadians(degree))
                    - (y1[i] - centery) * Math.sin(Math.toRadians(degree)))
                    + centerx);
            y = (float) ((((y1[i] - centery) * Math.cos(Math.toRadians(degree))
                    + (x1[i] - centerx) * Math.sin(Math.toRadians(degree))))
                    + centery);
            xx = (float) ((((x2[i] - centerx) * Math.cos(Math.toRadians(degree))
                    - (y2[i] - centery) * Math.sin(Math.toRadians(degree))))
                    + centerx);
            yy = (float) ((((y2[i] - centery) * Math.cos(Math.toRadians(degree))
                    + (x2[i] - centerx) * Math.sin(Math.toRadians(degree))))
                    + centery);
            G2D.draw(new Line2D.Float(x, y, xx, yy));
        }
    }
}
