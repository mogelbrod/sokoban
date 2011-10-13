import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.channels.*;

public class Test {
	public static void main(String[] args) {
		try {
			Board b = new Board(readFile("testboard.txt"), 8, 5);
			b.write();
			for (Direction dir : b.findPossibleMoves())
				System.out.println(dir);
		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private static String readFile(String path) throws IOException {
		FileInputStream stream = new FileInputStream(new File(path));
		try {
			FileChannel fc = stream.getChannel();
			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			return Charset.defaultCharset().decode(bb).toString();
		} finally {
			stream.close();
		}
	}
}
