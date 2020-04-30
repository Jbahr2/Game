
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

import javax.annotation.processing.FilerException;

import java.util.*;

public class FileDecoder {
    private int magicNumber;
    private int original = -889274641;
    private int played = -889266451;
    private Tile tiles[];

    private void filetoByteArray(String path)
            throws IOException, InvalidPathException {

        Path tpath = Paths.get(path);
        byte[] data = Files.readAllBytes(tpath);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        magicNumber = buffer.getInt();

        /* Ensure file is legit */
        if ((magicNumber != original) && (magicNumber != played)) {
            /* File is wack yo */
            throw new FilerException("File type incorrect");
        }
        
        int numberOfTiles = buffer.getInt();
        
        int[] tempId = new int[numberOfTiles];
        Random rng = new Random();
        if(magicNumber == original) {
            Vector<Integer> randomInts = new Vector<Integer>();
            
            for(int a = 0; a < numberOfTiles; a++) {
                randomInts.add(a);
            }
            
            for(int b = numberOfTiles; b > 0; b--) {
                int picked = rng.nextInt(b);
                tempId[numberOfTiles - b] = (int)randomInts.get(picked);
                randomInts.remove(picked);
            }
        }
        
        tiles = new Tile[numberOfTiles];

        for (int i = 0; i < numberOfTiles; i++) {
            
            int wrapperID = buffer.getInt(); // not yet used
            if(magicNumber == original) {wrapperID = tempId[i];}
            
            int degree = buffer.getInt();
            if(magicNumber == original) {degree = rng.nextInt(4);}
            System.out.println(degree);
            
            int numLines = buffer.getInt();
            float[][] lines = new float[numLines][4];

            for (int j = 0; j < numLines; j++) {
                lines[j][0] = buffer.getFloat();
                lines[j][1] = buffer.getFloat();
                lines[j][2] = buffer.getFloat();
                lines[j][3] = buffer.getFloat();
            }
            
            tiles[i] = new Tile(lines, degree, wrapperID);
        }

    }

    public void readfile(String path) throws InvalidPathException, IOException {
        filetoByteArray(path);
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getMagicNum() {
        return magicNumber; /* Parker */
    }

}
