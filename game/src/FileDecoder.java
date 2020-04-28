
/* *
 * Authored by Group H
 * Class: Software Design 3011
 * Last Edited: 04/07/2020
 * 
 * Description: Read raw data from file and convert them into readable text.
 * */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import java.util.*;

public class FileDecoder {
    private float[][][] lines;
    private int[] numLines, degree;
    private int numberOfTiles, magicNumber;
    List<Integer> ranTile = new ArrayList<Integer>();

    private void filetoByteArray(String path)
            throws IOException, InvalidPathException {

        Path tpath = Paths.get(path);
        byte[] data = Files.readAllBytes(tpath);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        magicNumber = buffer.getInt();
        numberOfTiles = buffer.getInt();
        lines = new float[numberOfTiles][][];

        numLines = new int[numberOfTiles];
        degree = new int[numberOfTiles];
        
        for (int i = 0; i < numberOfTiles; i++) {
            int tileNumber = buffer.getInt(); // not yet used
            degree[i] = buffer.getInt();
            numLines[i] = buffer.getInt();
            lines[tileNumber] = new float[numLines[i]][4];

            ranTile.add(tileNumber);
            for (int j = 0; j < numLines[i]; j++) {
                lines[tileNumber][j][0] = buffer.getFloat();
                lines[tileNumber][j][1] = buffer.getFloat();
                lines[tileNumber][j][2] = buffer.getFloat();
                lines[tileNumber][j][3] = buffer.getFloat();
            }
        }
        
        // Shuffles Tiles around randomly in array using FisherYates shuffle
        // algorithm
        for (int i = ranTile.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Collections.swap(ranTile, i, j);
        }
    }

    public void readfile(String path) throws InvalidPathException, IOException {
        filetoByteArray(path);
    }

    public float[][] getLines(int i) {
        return lines[i];
    }


    public int getDegree(int i) {
        return degree[i];
    }

    public int getNumLines(int i) {
        return numLines[i];
    }

    public int getTile(int i) {
        return ranTile.get(i);
    }

    public int getTileNum() {
        return numberOfTiles;
    }
    
    public int getMagicNum() {
        return magicNumber; /* Parker */
    }

}
