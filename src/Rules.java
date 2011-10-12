import java.util.Vector;


public class Rules {
	protected Symbol UP,DOWN,LEFT,RIGHT;
	protected Symbol[] cells;
	protected int width;


	/*
	 * Main method. Call this to check ALL rules.
	 * Returns true iff _ALL_ rules are accepted.
	 */
	public boolean check(Symbol[] cells,int width){
		this.cells = cells;
		this.width = width;
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
			}	
		}
		return true;
	}

	private void updatePosition(int position){
		UP = cells[position-width];
		DOWN = cells[position+width];
		RIGHT = cells[position+1];
		LEFT = cells[position-1];

	}

	/**
	 * Returns true if the object is in a corner.
	 * @param position - current position of item
	 * @return
	 */
	private boolean corner_rule(int position, int d){


		if (position < 0 || position >= cells.length)
			return false;
		//If a goal is at the position do that move.
		if(cells[position] == Symbol.BOX_GOAL)
			return false;
		
		if(((UP == Symbol.WALL) || (DOWN == Symbol.WALL)) && (LEFT == Symbol.WALL || RIGHT == Symbol.WALL))
			return false;
		
		
		
		if((UP == Symbol.WALL || DOWN == Symbol.WALL) && (RIGHT == Symbol.BOX)){
			if (d == 1)
				return true;
			else
				corner_rule(position+1, d + 1);

		}
		
		
		if((UP == Symbol.WALL || DOWN == Symbol.WALL) && (LEFT == Symbol.BOX)){
			if (d == 1)
				return true;
			else 
				corner_rule(position-1, d + 1);
		}
		
		if((LEFT == Symbol.WALL || RIGHT == Symbol.WALL) && (UP == Symbol.BOX)){
			if(d == 1)
				return true;
			else
				corner_rule(position - width, d + 1);
		}
		
		if((LEFT == Symbol.WALL || RIGHT == Symbol.WALL) && (DOWN == Symbol.BOX)){
			if (d == 1)
				return true;
			else
				corner_rule(position + width, d + 1);
		}
		
		return false;
	}

}
