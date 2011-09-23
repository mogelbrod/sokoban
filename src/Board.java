import java.util.Vector;

public class Board {
	// Dimensions of board.
	protected final int width, height;
	protected Symbol[] cells;
	
	// Save string path while searching through the game tree.
	protected String path;

	// Current player position
	protected int playerPos;

	Board(String boardRep, int width, int height) {
		this.width = width;
		this.height = height;
		path = "";

		cells = new Symbol[width*height];

		int rowMul = 0;
		for (String row : boardRep.split("\n")) {
			for (int k = 0; k < row.length(); k++) {
				cells[rowMul+k] = Symbol.fromChar(row.charAt(k));
				if (cells[rowMul+k] == Symbol.PLAYER)
					playerPos = rowMul+k;
			}
			rowMul += width;
		}
	}

	Board(Symbol[] cells, int width, int height) {
		this.cells = cells;
		this.width = width;
		this.height = height;
	}

	Board(Board board, Direction dir) {
		this.width = board.width;
		this.height = board.height;
		this.path = board.path;
		this.cells = new Symbol[board.cells.length];
		System.arraycopy(board.cells, 0, this.cells, 0, cells.length);
		this.playerPos = board.playerPos;
		updateBoard(dir);
	}

	public void addDirectionToPath(Direction dir) {
		switch (dir) {
		case UP:
			path += "U";
			break;
		case DOWN:
			path += "D";
			break;
		case LEFT:
			path += "L";
			break;

		case RIGHT:
			path += "R";
			break;
		}
	}

	private boolean playerOnGoal() {
		return cells[playerPos] == Symbol.PLAYER_GOAL;
	}
	
	private boolean playerOnFloor() {
		return cells[playerPos] == Symbol.PLAYER;
	}
	
	private boolean boxOnFloor(int i) {
		return cells[i] == Symbol.BOX;
	}
	
	private boolean boxOnGoal(int i) {
		return cells[i] == Symbol.BOX_GOAL;
	}
	
	private boolean isGoal(int pos) {
		return cells[pos] == Symbol.GOAL;
	}
	
	private boolean isFloor(int pos){
		return cells[pos] == Symbol.FLOOR;
	}
	private void moveBox(int toPos) {
		if (isGoal(toPos))
			cells[toPos] = Symbol.BOX_GOAL;
		else if (isEmptyCell(toPos))
			cells[toPos] = Symbol.BOX;
	}
	
	private void updateBoard(Direction dir) {
		if (playerOnGoal())
			cells[playerPos] = Symbol.GOAL;
		else if (playerOnFloor())
			cells[playerPos] = Symbol.FLOOR;
		
		int newPlayerPos = translatePos(playerPos, dir);
		int moveBoxToIndex = translatePos(newPlayerPos, dir);
		
		if (boxOnFloor(newPlayerPos)) {
			moveBox(moveBoxToIndex);
			cells[newPlayerPos] = Symbol.PLAYER;
		} else if(boxOnGoal(newPlayerPos)) {
			moveBox(moveBoxToIndex);
			cells[newPlayerPos] = Symbol.PLAYER_GOAL;
		} else if(isFloor(newPlayerPos)) {
			cells[newPlayerPos] = Symbol.PLAYER;
		} else if(isGoal(newPlayerPos)) {
			cells[newPlayerPos] = Symbol.PLAYER_GOAL;
		}
			
		playerPos = newPlayerPos;
//		write();
	}

	/**
	 * Returns true if the cell at the specified position is empty,
	 * and a valid target for movement.
	 */


	//DO FUCKING MOVE!
	//	public Board move(Direction dir) {
	//		Board board = new Board(cells.clone(), width, height);
	//		// TODO: Do move
	//		return board;
	//	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public Symbol[] getCells() {
		return this.cells;
	}

	/**
	 * Returns a string representation of this board.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cells.length; i++) {
			if (i > 0 && i % width == 0) sb.append('\n');
			if (cells[i] != null)
				sb.append(cells[i]);
			else
				sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Outputs the string representation of this board to the console.
	 */
	public void write() {
		System.out.println(toString());
	}

	/**
	 * Returns a Vector instance with all possible player moves
	 * (as Direction instances) that are valid on this board.
	 */
	public Vector<Direction> findPossibleMoves() {
		Vector<Direction> moves = new Vector<Direction>(4);

		for (Direction dir : Direction.values()) {
			int to = translatePos(playerPos, dir);
			if (isEmptyCell(to) ||
					((at(to) == Symbol.BOX  || at(to) == Symbol.BOX_GOAL) &&
					isEmptyCell(translatePos(to, dir))))
				moves.add(dir);
		}
		return moves;
	}


	/**
	 * Returns true if the cell at the specified position is empty,
	 * and a valid target for movement.
	 */
	public boolean isEmptyCell(int pos) {
		Symbol c = at(pos);
		if (c == Symbol.FLOOR || c == Symbol.GOAL)
			return true;
		return false;
	}

	/**
	 * Returns a position translated in a specified direction.
	 */
	public int translatePos(int pos, Direction dir) {
		switch (dir) {
		case UP:
			return (pos - this.width);
		case DOWN:
			return (pos + this.width);
		case LEFT:
			return (pos - 1);
		case RIGHT:
			return (pos + 1);
		}
		return pos;
	}

	/**
	 * Returns the symbol located at the specified position.
	 */
	public Symbol at(int pos) {
		if (pos < 0 || pos >= cells.length)
			return Symbol.WALL;
		return cells[pos];
	}

	/**
	 * Returns the symbol located in the specified direction from a position.
	 */
	public Symbol at(int pos, Direction dir) {
		return at(translatePos(pos, dir));
	}


	public boolean isWin(){
		for (Symbol s : cells)
			if (s == Symbol.BOX) return false;
				return true;
	}

	/**
	 * Returns true if this board represents a finished game,
	 * where all boxes are placed on goals.
	 */
	public boolean isEOG() {
		for (Symbol s : cells)
			if (s == Symbol.BOX) return false;
				return true;
	}

	@Override
	public int hashCode() {
		int h = 0;
		for(int i = 0; i < cells.length; i++) {
			h ^= ( h << 5 ) + ( h >> 2 ) + cells[i].toChar();
		}
		return h;
	}
}
