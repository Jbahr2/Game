import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDecoder {
     private float[][] x1, x2, y1, y2;
	 private int[] numLines;
	private void filetoByteArray(String path) {
		int numberOfTiles, tileNumber; 
		Path tpath = Paths.get(path);
		byte[] data = null;
		
		try {
			data = Files.readAllBytes(tpath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ByteBuffer buffer = ByteBuffer.wrap(data);
		
		numberOfTiles = buffer.getInt();
			x1 = new float[numberOfTiles][];
			x2 = new float[numberOfTiles][];
			y1 = new float[numberOfTiles][];
			y2 = new float[numberOfTiles][];
			numLines = new int[numberOfTiles];
	       for(int i = 0; i < numberOfTiles; i++)  {
	           tileNumber = buffer.getInt();
	           numLines[i] = buffer.getInt();
	           x1[tileNumber] = new float[numLines[i]];
	           x2[tileNumber] = new float[numLines[i]];
	           y1[tileNumber] = new float[numLines[i]];
	           y2[tileNumber] = new float[numLines[i]];
	           for(int j = 0; j < numLines[i]; j++) {
	               x1[tileNumber][j] = buffer.getFloat();
	               y1[tileNumber][j] = buffer.getFloat();
	               x2[tileNumber][j] = buffer.getFloat();
	               y2[tileNumber][j] = buffer.getFloat();
	           }
	           
	       }
		}
	public void readfile(String path)
	{
		filetoByteArray(path);
	}
	public float[] getX1(int i)
	{
		return x1[i];
	}
	public float[] getX2(int i)
	{
		return x2[i];
	}
	public float[] getY1(int i)
	{
		return y1[i];
	}
	public float[] getY2(int i)
	{
		return y2[i];
	}
	public int getNumLines(int i) 
	{
		return numLines[i];
	}
}
