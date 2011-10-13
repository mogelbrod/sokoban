import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Vector;

public class Player {
	private HashSet<Integer> visited;

	/**
	 * A* implementation. {{{
	 */
	public String aStar(Board start) {
		// Initialize starting board
		start.f = start.heuristic();
		start.g = 0;

		PriorityQueue<Board> queue = new PriorityQueue<Board>(8192);
		queue.add(start);

		visited = new HashSet<Integer>();

		int possible = 0, queued = 0, examined = 0;

		while (!queue.isEmpty()) {
			Board board = queue.poll();
			examined++;

			if (board.isWin()) {
				System.out.println(
						  "Examined: " + examined +
						"\nQueued:   " + queued +
						"\nPossible: " + possible
				);
				return board.getPath();
			}

			// Examine successors to current board
			for (Direction dir : board.findPossibleMoves()) {
				Board succ = new Board(board, dir);

				if (visited(succ))
					continue;

				possible++;
				int h = succ.heuristic();
				if (h == Integer.MAX_VALUE)
					continue;

				succ.g = board.g + 1;
				succ.f = succ.g + h;
				queue.add(succ);
				queued++;
			}
		} // while queue has elements

		return "";
	} // }}}

	/**
	 * IDA* implementation. {{{
	 */
	public String idaStar(Board start) {
		int cutOffLimit;

		// Initialize starting board
		cutOffLimit = start.f = start.heuristic();
		start.g = 0;

		LinkedList<Board> stack = new LinkedList<Board>();
		stack.push(start);

		PriorityQueue<Board> queue = new PriorityQueue<Board>();

		visited = new HashSet<Integer>();

		while (true) {
			//System.out.println("cutoff: " + cutOffLimit + ", stack: "+ stack.size());
			while (!stack.isEmpty()) {
				Board board = stack.pop();

				if (board.isWin())
					return board.getPath();

				if (board.f <= cutOffLimit) {
					// Examine successors to current board
					for (Direction dir : board.findPossibleMoves()) {
						Board succ = new Board(board, dir);

						if (visited(succ))
							continue;

						int h = succ.heuristic();
						if (h == Integer.MAX_VALUE)
							continue;

						succ.g = board.g + 1;
						succ.f = succ.g + h;
						stack.push(succ);
					}
				} else {
					queue.add(board);
				}
			} // while stack has elements

			//System.out.println("queue: " + queue.size());

			if (queue.isEmpty())
				return "";

			stack.clear();

			// Calculate new cut off limit
			cutOffLimit = Integer.MAX_VALUE;
			for (Board b : queue) {
				if (b.f < cutOffLimit)
					cutOffLimit = b.f;

				// Queue next candidates
				stack.addLast(b);
			}

			queue.clear();
		}
	} // }}}

	/**
	 * Checks if a state has been visited before.
	 * returns false iff not visited else true.
	 */
	private boolean visited(Board board) {
		return !visited.add(board.hashCode());
	}
}
