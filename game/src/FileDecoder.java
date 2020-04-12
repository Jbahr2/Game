
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
    private float[][] x1, x2, y1, y2;
    private int[] numLines, degree;
    private int numberOfTiles;
    List<Integer> ranTile = new ArrayList<Integer>();

    private void filetoByteArray(String path)
            throws IOException, InvalidPathException {
        int tileNumber;
        Path tpath = Paths.get(path);
        byte[] data = Files.readAllBytes(tpath);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        numberOfTiles = buffer.getInt();
        x1 = new float[numberOfTiles][];
        x2 = new float[numberOfTiles][];
        y1 = new float[numberOfTiles][];
        y2 = new float[numberOfTiles][];
        numLines = new int[numberOfTiles];
        degree = new int[numberOfTiles];
        for (int i = 0; i < numberOfTiles; i++) {
            tileNumber = buffer.getInt();
            numLines[i] = buffer.getInt();
            x1[tileNumber] = new float[numLines[i]];
            x2[tileNumber] = new float[numLines[i]];
            y1[tileNumber] = new float[numLines[i]];
            y2[tileNumber] = new float[numLines[i]];
            ranTile.add(tileNumber);
            degree[i] = ((int) (Math.random() * 4)) * 90;
            for (int j = 0; j < numLines[i]; j++) {
                x1[tileNumber][j] = buffer.getFloat();
                y1[tileNumber][j] = buffer.getFloat();
                x2[tileNumber][j] = buffer.getFloat();
                y2[tileNumber][j] = buffer.getFloat();
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

    public float[] getX1(int i) {
        return x1[i];
    }

    public float[] getX2(int i) {
        return x2[i];
    }

    public float[] getY1(int i) {
        return y1[i];
    }

    public float[] getY2(int i) {
        return y2[i];
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
}
