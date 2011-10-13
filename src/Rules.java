import java.util.Vector;

public class Rules {
	protected Board board;
	protected Symbol ABOVE, UNDER, TOLEFT, TORIGHT;
	protected Symbol[] cells;

	// protected int width;

	/*
	 * Main method. Call this to check ALL rules.
	 * Returns true iff _ALL_ rules are accepted.
	 */
	public boolean check(Board board) {
		this.board = board;
		this.cells = board.cells;

		Vector<Integer> boxes = new Vector<Integer>();
		for (int i = 0; i < cells.length; i++) {
			if(cells[i] == Symbol.BOX)
				boxes.add(i);
		}

		if(boxes.size() > 0){
			for (Integer numBox : boxes) {
				updatePosition(numBox);
				if (corner_rule(numBox, 0))
					return false;
				// if (side_rule(numBox))
				// return false;
			}	
		}
		return true;
	}

	private void updatePosition(int position){
		int width = board.width;
		ABOVE = cells[position - width];
		UNDER = cells[position + width];
		TORIGHT = cells[position + 1];
		TOLEFT = cells[position - 1];

	}

	private boolean side_rule(int position) {
		int width = board.width;
		int height = board.height;

		if (ABOVE == Symbol.WALL || UNDER == Symbol.WALL) {
			for (int i = 0; i < width; i++) {
				int t = position - (position % width) + i;
				if (cells[t] == Symbol.GOAL)
					return false;
			}

			return true;
		} else if (TORIGHT == Symbol.WALL || TOLEFT == Symbol.WALL) {
			for (int i = 0; i < height; i++) {
				int t = position - (position / width - (height - 1 - i)) * width;
				if (cells[t] == Symbol.GOAL)
					return false;
			}
			return true;
		}

		return false;
	}
	/**
	 * Returns true if the object is in a corner.
	 * @param position - current position of item
	 * @return
	 */
	private boolean corner_rule(int position, int d){

		int width = board.width;
		if (position < 0 || position >= cells.length)
			return false;

		if (cells[position] == Symbol.GOAL)
			return false;
		//If a goal is at the position do that move.
		
		if (((ABOVE == Symbol.WALL) || (UNDER == Symbol.WALL))
				&& (TOLEFT == Symbol.WALL || TORIGHT == Symbol.WALL))
			return true;
		
		
		
		if ((ABOVE == Symbol.WALL || UNDER == Symbol.WALL)
				&& (TORIGHT == Symbol.BOX)) {
			if (d == 1)
				return true;
			else
				corner_rule(position+1, d + 1);

		}
		
		
		if ((ABOVE == Symbol.WALL || UNDER == Symbol.WALL)
				&& (TOLEFT == Symbol.BOX)) {
			if (d == 1)
				return true;
			else 
				corner_rule(position-1, d + 1);
		}
		
		if ((TOLEFT == Symbol.WALL || TORIGHT == Symbol.WALL)
				&& (ABOVE == Symbol.BOX)) {
			if(d == 1)
				return true;
			else
				corner_rule(position - width, d + 1);
		}
		
		if ((TOLEFT == Symbol.WALL || TORIGHT == Symbol.WALL)
				&& (UNDER == Symbol.BOX)) {
			if (d == 1)
				return true;
			else
				corner_rule(position + width, d + 1);
		}
		
		return false;
	}

}
