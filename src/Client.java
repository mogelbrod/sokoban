import java.io.*;
import java.net.*;
import java.util.Date;
import java.lang.StringBuilder;

public class Client {
	public static void main(String[] pArgs) {
		if (pArgs.length < 3) {
			String[] args = new String[3];
			args[0] = "130.237.218.85";
			args[1] = pArgs[0];
			args[2] = pArgs[1];
			pArgs = args;
		}

		try {
			Socket lSocket = new Socket(pArgs[0], Integer.parseInt(pArgs[1]));
			PrintWriter lOut = new PrintWriter(lSocket.getOutputStream());
			BufferedReader lIn = new BufferedReader(new InputStreamReader(lSocket.getInputStream()));

			lOut.println(pArgs[2]);
			lOut.flush();

			String lLine = lIn.readLine();

			//read number of rows
			int height = Integer.parseInt(lLine);
			int width = 0;
			StringBuilder sb = new StringBuilder();

			//read each row
			for (int i = 0; i < height; i++) {
				lLine = lIn.readLine();
				if (lLine != null){
					if (lLine.length() > width)
						width = lLine.length();
				}
				sb.append(lLine).append('\n');
			}

			Board board = new Board(sb.toString(), width, height);
			board.write();

			Player p = new Player();
			String solution = p.aStar(board);

			//send the solution to the server
			System.out.println(solution);
			lOut.println(solution);
			lOut.flush();

			//read answer from the server
			lLine = lIn.readLine();

			System.out.println(lLine);
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
	}
}
