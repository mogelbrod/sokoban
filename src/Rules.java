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
		
//		for (int numBox : boxes) {
//			updatePosition(numBox);
//			if(corner_rule(numBox))
//				return false;	
//		}
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
	private boolean corner_rule(int position){


		//If a goal is at the position do that move.
		if(cells[position] == Symbol.BOX_GOAL)
			return false;


		/* Case
		 * ###
		 *  $#
		 *   #
		 */
		if((UP == Symbol.WALL || UP == Symbol.BOX) && (RIGHT == Symbol.WALL || RIGHT == Symbol.BOX))
			return true;

		/*Case
		 *   #
		 *  $#
		 * ### 
		 */
		if((DOWN == Symbol.WALL || DOWN == Symbol.BOX) && (RIGHT == Symbol.WALL || RIGHT == Symbol.BOX))
			return true;


		/*Case
		 * #
		 * #$
		 * ### 
		 */
		if((DOWN == Symbol.WALL || DOWN == Symbol.BOX) && (LEFT == Symbol.WALL || LEFT == Symbol.BOX))
			return true;


		/*Case
		 * ###
		 * #$
		 * # 
		 */
		if((LEFT == Symbol.WALL || LEFT == Symbol.BOX) && (UP == Symbol.WALL || UP == Symbol.BOX))
			return true;


		return false;
	}
	
}
