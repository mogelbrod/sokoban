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
		int cutOff;

		// Initialize starting board
		cutOff = start.f = start.h = heuristicValue(start);
		start.g = 0;

		stack = new Stack<Board>();
		stack.push(start);

		visited = new HashSet<Integer>();
		visited(start.hashCode());

		while (true) {
			while (!stack.isEmpty()) {
				Board board = stack.pop();

				if (board.isWin())
					return board.getPath();

				if (board.f <= cutOff) {
					// Examine successors to current board
					for (Direction dir : board.findPossibleMoves()) {
						Board succ = new Board(board, dir);
						succ.estimateValue(board.g);

						stack.push(succ);
					}
				}
			}
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
