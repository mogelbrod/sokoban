import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;
import java.util.PriorityQueue;

public class Player {
	private Rules MASTER_CONTROL_TOWER = new Rules();
	private HashSet<Integer> visited;

	/**
	 * A* implementation. {{{
	 */
	public String aStar(Board start) {
		int cutOffLimit;

		// Initialize starting board
		cutOffLimit = start.f = start.heuristic();
		start.g = 0;

		PriorityQueue<Board> queue = new PriorityQueue<Board>();
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
				possible++;

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
	 * DFS implementation. {{{
	 */
	public String dfs(Board startState) {
		Stack<Board> stack = new Stack<Board>();
		stack.push(startState);

		visited = new HashSet<Integer>();
		visited(startState);

		while (!stack.isEmpty()) {
			Board currentState = stack.peek();

			if (currentState.isWin()) {
				System.out.println(currentState.path);
				return currentState.path;
			}

			Vector<Direction> moves = currentState.findPossibleMoves();

			boolean noUnvisitedChildNodes = true;
			if (moves.size() != 0) {
				for (Direction d : moves) {
					Board nextBoard = new Board(currentState, d);

					if (!visited(nextBoard)) {
						noUnvisitedChildNodes = false;
						if (MASTER_CONTROL_TOWER.check(nextBoard.cells, nextBoard.getWidth())){
							stack.push(nextBoard);
						}	
					} 
				}
			} else {
				stack.pop();
			}
			if (noUnvisitedChildNodes)
				stack.pop();
		}
		return null;
	} // }}}

	/**
	 * Checks if a state has been visited before.
	 * returns false iff not visited else true.
	 */
	private boolean visited(Board board) {
		return !visited.add(board.hashCode());
	}
}
