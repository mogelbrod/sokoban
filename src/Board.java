<<<<<<< HEAD
	public class Board {
=======
public class Board {
	// Dimensions of board.
	protected int width, height;

	protected Symbol[] cells;

>>>>>>> 3221a60c5d8d866cdb631dd0379792906ac082f1
	// Save string path while searching through the game tree.
	// To avoid unnecessary time waste, save player's position.
	String path;
	int playerIndex;

	Board(Board board, Direction dir) {
	}

	public void doMove(Direction dir) {
	}

	public Direction[] findPossibleMoves() {
		return null;
	}

	// Returns true if an object (player or box) can be moved from a
	// position towards a specified direction.
	private boolean canMove(int pos, Direction dir) {
		switch (dir) {
			case UP:
				pos -= this.width;
				if (pos < 0) return false;
				break;
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				break;
		}
		return false;
	}
}
