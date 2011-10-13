public enum Symbol {
	WALL, PLAYER, PLAYER_GOAL, BOX, BOX_GOAL, GOAL, FLOOR, CORNER, PLAYER_CORNER;

	public static Symbol fromChar(char c) {
		switch (c) {
			case '#': return Symbol.WALL;
			case '@': return Symbol.PLAYER;
			case '+': return Symbol.PLAYER_GOAL;
			case '$': return Symbol.BOX;
			case '*': return Symbol.BOX_GOAL;
			case '.': return Symbol.GOAL;
			case ' ': return Symbol.FLOOR;
			case '_': return Symbol.CORNER;
		}
		return Symbol.WALL;
	}
	
	public String toString() {
		switch (this) {
			case WALL: return "#";
			case PLAYER_CORNER:
			case PLAYER: return "@";
			case PLAYER_GOAL: return "+";
			case BOX: return "$";
			case BOX_GOAL: return "*";
			case GOAL: return ".";
			case FLOOR: return " ";
			case CORNER: return " ";
		}
		return "";
	}
	
	public char toChar() {
		switch (this) {
			case WALL: return '#';
			case PLAYER_CORNER:
			case PLAYER: return '@';
			case PLAYER_GOAL: return '+';
			case BOX: return '$';
			case BOX_GOAL: return '*';
			case GOAL: return '.';
			case FLOOR: return ' ';
			case CORNER: return ' ';
		}
		return ' ';
	}
}
