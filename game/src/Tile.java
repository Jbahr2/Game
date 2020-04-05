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

	private float x1[],x2[],y1[],y2[];
	private int numLines;
    public Tile() {
        super();
        setOpaque(true);
        setBackground(new Color(69, 170, 242));
    }
    public void SetTileInfo(float[] x1, float[] x2, float[] y1, float[] y2, int numLines) 
    {
        this.x1 = new float[numLines];
        this.x2 = new float[numLines];
        this.y1 = new float[numLines];
        this.y2 = new float[numLines];
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.numLines = numLines;
    }
    @Override public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D G2D = (Graphics2D)g;
    	G2D.setColor(Color.BLACK);
    	G2D.setStroke(new BasicStroke(2.5f));
    	for(int i = 0; i < numLines; i++)
    	G2D.draw(new Line2D.Float(x1[i], y1[i], x2[i], y2[i]));
    }
}
