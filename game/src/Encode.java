import java.nio.file.Paths;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
public class Encode {
	 float[] x1 = new float[16];
	 float[] x2 = new float[16];
	 float[] y1 = new float[16];
	 float[] y2 = new float[16];
	 int numberOfTiles, tileNumber, numLines;
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
			tileNumber = buffer.getInt();
			System.out.println(tileNumber + " 2");
			numLines = buffer.getInt();
			System.out.println(numLines + " 3");
		for(int j = 0; j < numberOfTiles; j++) {
			x1[j] = buffer.getFloat();
			System.out.println(x1[j]);
			y1[j] = buffer.getFloat();
			System.out.println(y1[j]);
			x2[j] = buffer.getFloat();
			System.out.println(x2[j]);
			y2[j] = buffer.getFloat();
			System.out.println(y2[j]);
		}
		}
}
