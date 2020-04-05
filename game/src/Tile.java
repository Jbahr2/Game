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
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.BasicStroke;
import javax.swing.*;

public class Tile extends JPanel {
	 float[][] x1 = new float[16][];
	 float[][] x2 = new float[16][];
	 float[][] y1 = new float[16][];
	 float[][] y2 = new float[16][];
	 int numberOfTiles, tileNumber; 
	 int[] numLines = new int[16];
	public static int id = 0;
	public int[] tilenum = new int[16];
	public void filetoByteArray() {
		Path tpath = Paths.get("src\\default.mze");
		byte[] data = null;
		
		try {
			data = Files.readAllBytes(tpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		numberOfTiles = buffer.getInt();
		System.out.println(numberOfTiles + " 1");

	       for(int i = 0; i < numberOfTiles; i++)  {
	           tileNumber = buffer.getInt();
	           System.out.print("Tile " + tileNumber + ": ");
	           numLines[i] = buffer.getInt();
	           System.out.println(numLines[i] + " lines");
	           x1[tileNumber] = new float[numLines[i]];
	           x2[tileNumber] = new float[numLines[i]];
	           y1[tileNumber] = new float[numLines[i]];
	           y2[tileNumber] = new float[numLines[i]];
	           for(int j = 0; j < numLines[i]; j++) {
	               x1[tileNumber][j] = buffer.getFloat();
	               y1[tileNumber][j] = buffer.getFloat();
	               x2[tileNumber][j] = buffer.getFloat();
	               y2[tileNumber][j] = buffer.getFloat();
	               
	               System.out.println("\t" + j + ": " + x1[tileNumber][j] + " " + y1[tileNumber][j] + " " + x2[tileNumber][j] +" " + y2[tileNumber][j]);
	           }
	           
	       }
		}
    public Tile(String name) {
        super();
        filetoByteArray();
        setOpaque(true);
        setBackground(new Color(69, 170, 242));
        add(new JLabel(name));
        System.out.println("Name: " + name);
        int i = Integer.parseInt(name);
        tilenum[i] = i;
        System.out.println("Name int: " + i);
    }
    @Override public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D G2D = (Graphics2D)g;
    	G2D.setColor(Color.BLACK);
    	G2D.setStroke(new BasicStroke(2.5f));
    	int tnum = id % 15;
    	System.out.println(tnum);
    	for(int i = 0; i < numLines[tnum]; i++)
    	G2D.draw(new Line2D.Float(x1[tnum][i], y1[tnum][i], x2[tnum][i], y2[tnum][i]));
    	id++;
    }
	
}
