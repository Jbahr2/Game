
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

    private Tile[] tiles;

    public void readFile(String path) throws InvalidPathException, IOException {

        tiles = new Tile[16];

        Path tpath = Paths.get(path);
        byte[] data = Files.readAllBytes(tpath);

        ByteBuffer buffer = ByteBuffer.wrap(data);

        // not played is -889274641
        // played -889266451
        int playedHex = buffer.getInt();
        boolean played;
        if (playedHex == -889274641) {
            played = false;
        } else if (playedHex == -889266451) {
            played = true;
        } else {
            throw new FilerException("File type incorrect");
        }
        
        int numberOfTiles = buffer.getInt();

        // create bin of IDs and rotations to grab from for randomization
        ArrayList<Integer> randomIDs = new ArrayList<Integer>(); 
        ArrayList<Integer> randomRotations = new ArrayList<Integer>(); 

        for (int i = 0; i < numberOfTiles; i++) {
            randomIDs.add(i);
            randomRotations.add(i % 4);
        }
        Collections.shuffle(randomIDs); 
        Collections.shuffle(randomRotations); 


        for (int i = 0; i < numberOfTiles; i++) {
            int tileID = buffer.getInt();
            int degree = buffer.getInt();

            int numLines = buffer.getInt();

            float[][] lines = new float[numLines][4];

            for (int j = 0; j < numLines; j++) {
                for (int k = 0; k < 4; k++) {
                    lines[j][k] = buffer.getFloat();
                }
            }

            if (played) {
                tiles[i] = new Tile(lines, degree, tileID);
            } else {
                tileID = randomIDs.remove(0);
                degree = randomRotations.remove(0);
                tiles[i] = new Tile(lines, degree, tileID);
            }
        }
    }

    public Tile getTile(int wrapperID) {
        for (int j = 0; j < tiles.length; j++) {
            if (tiles[j].getTileID() == wrapperID) {
                return tiles[j];
            }
        }
        return null;
    }

    public int getTilesSize() {
        return tiles.length;
    }
    
    public Tile[] getTiles() {
        return tiles;
    }
}
