import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class Player {

	private Rules MASTER_CONTROL_TOWER = new Rules();
	private HashSet<Integer> visited = new HashSet<Integer>();

	private Stack<Board> stack = new Stack<Board>();

	// IDA* variables
	private int costLimit;

	/**
	 * New IDA* implementation. {{{
	 */
	public String idaStar(Board start) {
		// Starting cost limit
		costLimit = heuristicValue(start);

		while (true) {
			String path = limitedDFS(0, start, costLimit, "");

			// Solution found
			if (path != null)
				return path;

			// No solution
			if (costLimit == Integer.MAX_VALUE)
				return  "";
		}
	} // }}}

	protected String limitedDFS(int startCost, Board board, int costLimit, String currentPath) {
		int minCost = startCost + heuristicValue(board);

		// Increase minimum cost if exceeded, then try again
		if (minCost > costLimit) {
			costLimit = minCost;
			return null;
		}

		if (board.isWin()) {
			return currentPath;
		}

		int nextCostLimit = Integer.MAX_VALUE;

		for (Direction move : board.findPossibleMoves()) {
			int newStartCost = startCost + 0; // TODO: + edge cost for this move
			// TODO
		}
	}

	/**
	 * Original DFS implementation. {{{
	 */
	public String dfs(Board startState) {
		stack.push(startState);
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
