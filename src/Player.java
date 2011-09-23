import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class Player {

	private Rules MASTER_CONTROL_TOWER = new Rules();
	private Stack<Board> stack = new Stack<Board>();
	private HashSet<Integer> visited = new HashSet<Integer>();

	Player () {
	}

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
					Board nextBoard = currentState.clone().move(d);

					if(!visited(nextBoard.hashCode())){
						noUnvisitedChildNodes = false;
						if(MASTER_CONTROL_TOWER.check(nextBoard.cells, nextBoard.getWidth())){
							nextBoard.addDirectionToPath(d);
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
	}


	/*
	 * Checks if a state has been visited before.
	 * @param: hashCode of Board.cells.hashCode()
	 * returns false iff not visited else true
	 * 
	 */
	private boolean visited(int hashCode){
		//.add returns true if hashCode was added (meaning hashCode haven't been added before)
		if(visited.add(hashCode))
			return false;
		else
			return true;
	}

}
