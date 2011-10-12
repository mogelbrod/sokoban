import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class Player {

	private Rules MASTER_CONTROL_TOWER = new Rules();
	private HashSet<Integer> visited;
	private Stack<Board> stack;

	/**
	 * New IDA* implementation. {{{
	 */
	public String idaStar(Board start) {
		int cutOffLimit;

		// Initialize starting board
		cutOffLimit = start.f = start.heuristic();
		start.g = 0;

		Stack<Board> stack = new Stack<Board>();
		stack.push(start);

		Stack<Board> next = new Stack<Board>();

		visited = new HashSet<Integer>();
		visited(start.hashCode());

		while (true) {
			while (!stack.isEmpty()) {
				Board board = stack.pop();

				if (board.isWin())
					return board.getPath();

				if (board.f <= cutOffLimit) {
					// Examine successors to current board
					for (Direction dir : board.findPossibleMoves()) {
						Board succ = new Board(board, dir);
						succ.g = board.g + 1;
						succ.f = succ.heuristic() + succ.g;

						stack.push(succ);
					}
				} else {
					next.push(board);
				}
			} // while stack has elements

			if (next.isEmpty())
				return "";

			stack.clear();

			// Calculate new cut off limit
			cutOffLimit = Integer.MIN_VALUE;
			for (Board b : next) {
				if (b.f < cutOffLimit)
					cutOffLimit = b.f;

				// Queue next candidates
				stack.push(b);
			}

			next.clear();
		}
	} // }}}

	/**
	 * Original DFS implementation. {{{
	 */
	public String dfs(Board startState) {
		stack = new Stack<Board>();
		stack.push(startState);
		visited = new HashSet<Integer>();
		visited(startState.hashCode());

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

					if(!visited(nextBoard.hashCode())){
						noUnvisitedChildNodes = false;
						if(MASTER_CONTROL_TOWER.check(nextBoard.cells, nextBoard.getWidth())){
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
	 * @param: hashCode of Board.cells.hashCode()
	 * returns false iff not visited else true.
	 */
	private boolean visited(int hashCode) {
		//.add returns true if hashCode was added (meaning hashCode haven't been added before)
		return !visited.add(hashCode);
	}
}
