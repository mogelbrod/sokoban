public enum Symbol {
	WALL, PLAYER, PLAYER_GOAL, BOX, BOX_GOAL, GOAL, FLOOR;

	public static Symbol fromChar(char c) {
		switch (c) {
			case '#': return Symbol.WALL;
			case '@': return Symbol.PLAYER;
			case '+': return Symbol.PLAYER_GOAL;
			case '$': return Symbol.BOX;
			case '*': return Symbol.BOX_GOAL;
			case '.': return Symbol.GOAL;
			case ' ': return Symbol.FLOOR;
		}
		return Symbol.WALL;
	}
	
	public String toString() {
		switch (this) {
			case WALL: return "#";
			case PLAYER: return "@";
			case PLAYER_GOAL: return "+";
			case BOX: return "$";
			case BOX_GOAL: return "*";
			case GOAL: return ".";
			case FLOOR: return " ";
		}
		return "";
	}
}
