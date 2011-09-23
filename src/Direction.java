public enum Direction {
	UP, RIGHT, DOWN, LEFT;

	public String toString() {
		switch (this) {
			case UP:    return "U";
			case RIGHT: return "R";
			case DOWN:  return "D";
			case LEFT:  return "L";
		}
		return "";
	}
}
