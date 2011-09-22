import java.util.HashSet;
import java.util.Stack;
import java.util.Vector;

public class Player {
	private Stack<Board> stack = new Stack<Board>();
	private HashSet<Integer> visited = new HashSet<Integer>();
	Player () {

	}
	public String dfs(Board startState) {
		stack.push(startState);

		while (!stack.isEmpty()) {
			Board currentState = stack.peek();

			if (currentState.isEOG())
				return currentState.path.toString();

			Vector<Direction> moves = currentState.findPossibleMoves();
			System.out.println(moves.size());

			if (moves.size() != 0) {
				for (Direction d : moves) {
					Board nextBoard = new Board(currentState, d);
					//TODO: nextBoard.hashCode(): Gšr en bra hashfunktion fšr states
					if (!visited.contains(nextBoard.hashCode())) {
						visited.add(nextBoard.hashCode());
						nextBoard.addDirectionToPath(d);
						stack.push(nextBoard);
						System.out.println(stack.size());
					}
				}
			} else {
				stack.pop();
			}
		}

		return null;
	}

	//	Push the root node onto a stack.
	//	Pop a node from the stack and examine it.
	//	If the element sought is found in this node, quit the search and return a result.
	//	Otherwise push all its successors (child nodes) that have not yet been discovered onto the stack.
	//	If the stack is empty, every node in the tree has been examined Ð quit the search and return "not found".
	//	If the stack is not empty, repeat from Step 2.
}
