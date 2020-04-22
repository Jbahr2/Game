
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
    private int[] numLines, degree, sdegree;
    private int numberOfTiles;
    List<Integer> ranTileID = new ArrayList<Integer>();

    private void filetoByteArray(String path)
            throws IOException, InvalidPathException {

        Path tpath = Paths.get(path);
        byte[] data = Files.readAllBytes(tpath);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        numberOfTiles = buffer.getInt();
        lines = new float[numberOfTiles][][];

        numLines = new int[numberOfTiles];
        degree = new int[numberOfTiles];
        sdegree = new int[numberOfTiles];
        
        for (int i = 0; i < numberOfTiles; i++) {
            int tileNumber = buffer.getInt(); // not yet used
            
            numLines[i] = buffer.getInt();
            lines[tileNumber] = new float[numLines[i]][4];

            ranTileID.add(tileNumber);
            degree[i] = (int) (Math.random() * 4);
            sdegree = degree;
            
            for (int j = 0; j < numLines[i]; j++) {
                lines[tileNumber][j][0] = buffer.getFloat();
                lines[tileNumber][j][1] = buffer.getFloat();
                lines[tileNumber][j][2] = buffer.getFloat();
                lines[tileNumber][j][3] = buffer.getFloat();
            }
        }
        
        // Shuffles Tiles around randomly in array using FisherYates shuffle
        // algorithm
        for (int i = ranTileID.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Collections.swap(ranTileID, i, j);
        }
    }

    public void readFile(String path) throws InvalidPathException, IOException {
        filetoByteArray(path);
    }

    public float[][] getLines(int i) {
        return lines[i];
    }


    public int getDegree(int i) {
        return degree[i];
    }
    
    public int getInitialDegree(int i) {
        return sdegree[i];
    }

    public int getNumLines(int i) {
        return numLines[i];
    }

    public int getTile(int i) {
        return ranTileID.get(i);
    }

    public int getTileNum() {
        return numberOfTiles;
    }
}
