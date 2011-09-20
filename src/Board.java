
public class Board {
	// Save string path while searching through the game tree. 
	// Save lastMove to recognize if the player is trying to move back without doing anything.
	// To avoid unnecessary time waste, save player's position.
	String path;
	Move lastMove;
	int playerIndex; 
	
	Board(Board board, Move move) {
		
	}

	public void doMove(Move move) {
		
	}
	
	public Move[] findPossibleMoves() {
		return null;
	}
	
	private boolean tryMove(Move move) {
		return false;
	}

}
