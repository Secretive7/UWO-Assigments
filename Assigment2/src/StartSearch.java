import java.io.FileNotFoundException;
import java.io.IOException;

public class StartSearch {
	private Map targetMap;
	private int numArrows;
	private int inertia;
	private int direction;
	private int targetsHit;
	private int cellsTravelled;
	private int cellsTravelledPerArrow;
	private boolean canTurn;
	private boolean inertiaStart;
	
	public StartSearch(String filename) {
		try {
			//Create map object and initialize variables
			targetMap = new Map(filename);
			numArrows = 0;
			direction = 0;
			inertia = 0;
			targetsHit = 0;
			cellsTravelled = 0;
			canTurn = true;
			inertiaStart = false;
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MapCell nextCell(MapCell cell) {
		//Create and initialize instance variables
		MapCell bestCell = null;
		boolean blocked = false;
		
		//Once the arrow has move 3 cells, start inertia
		if(cellsTravelledPerArrow == 1) {
			inertiaStart = true;
		}
		
		//If inertia is started and more the arrow has travelled more than 3 cells without turning, don't allow turning anymore
		if(inertiaStart && (inertia > 3)) {
			canTurn = false;
		}
				
		//Checks if the cell in the direction the arrow is moving is null, if it is null, set the blocked bool to true
		if(cell.getNeighbour(direction) == null) {
			blocked = true;
		}else {
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) !=null) {
					//Checks if the cell in the direction the arrow is moving is unmoveable to, if that is true, set the blocked bool to true
					if(direction == i && (cell.getNeighbour(i).isBlackHole() || cell.getNeighbour(i).isMarked() || ((direction == 1 || direction == 3) && cell.getNeighbour(i).isVerticalPath()) || ((direction == 0 || direction == 2) && cell.getNeighbour(i).isHorizontalPath()))) {
						blocked = true;
						break;
					}
				}
			}
		}
		
		//If the arrow isn't blocked, keep going in the same direction
		if(!blocked) {
			bestCell = cell.getNeighbour(direction);
			return bestCell;
		}else{
			
			//For loop to check each direction and prioritizes the lowest index
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					
					//If the arrow can turn to a target, it will prioritize it
					if(!cell.getNeighbour(i).isMarked() && canTurn) {	
						if(cell.getNeighbour(i).isTarget()) {
							bestCell = cell.getNeighbour(i);
							direction = i; //Set direction to the new direction
							inertia = 0; //reset inertia
							return bestCell;
						}
					}
				}
			}
			
			//For loop to check each direction and prioritizes the lowest index
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					
					//If the arrow can turn to a cross path, it will be 2nd priority
					if(!cell.getNeighbour(i).isMarked() && canTurn) {
						if(cell.getNeighbour(i).isCrossPath() && ((cell.isVerticalPath() && (direction == 1 || direction == 3)) || (cell.isHorizontalPath() && (direction == 0 || direction == 2)) || cell.isCrossPath() || cell.isStart())) {
							bestCell = cell.getNeighbour(i);
							direction = i; //Set direction to the new direction
							inertia = 0; //reset inertia
							return bestCell;
						}
					}
				}
			}
			
			//For loop to check each direction and prioritizes the lowest index
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					
					//If the arrow can turn to a vertical / horizontal path, it will be 3rd priority
					if(!cell.getNeighbour(i).isMarked() && canTurn) {
						if(((i == 0 || i == 2) && cell.getNeighbour(i).isVerticalPath()) || ((i == 1 || i == 3) && cell.getNeighbour(i).isHorizontalPath())) {
							bestCell = cell.getNeighbour(i);
							direction = i; //Set direction to the new direction
							inertia = 0; //reset inertia
							return bestCell;
						}
					}
				}
			}
			
		}
		
		return bestCell;
	}
	
	public static void main(String args[]) {
		
		//Create the needed objects
		StartSearch path = new StartSearch(args[0]);
		ArrayStack<MapCell> stack = new ArrayStack<MapCell>();
		MapCell cell = path.targetMap.getStart(); //Create the starting cell
		
		while(path.numArrows < path.targetMap.quiverSize()) {
			
			//Checks if the stack is empty. If empty, setup the stack 
			if(stack.isEmpty()) {
				//Adds the initial cell to the stack and marks it accordingly
				stack.push(cell);
				cell.markInStack();
				cell.markInitial();
				path.direction = 0;
				path.inertia = 0;
				path.cellsTravelledPerArrow = 0;
				path.canTurn = true;
				path.inertiaStart = false;
			}
			
			//Gets the current cell from the stack
			MapCell currCell = (MapCell) stack.peek();
			
			//Checks if the current cell is a target
			if(currCell.isTarget()) {
				if(!currCell.isMarked()) {
					//If the target hasn't been hit, add it to the stack
					currCell.markInStack();
					currCell.markTarget();
					path.targetsHit++;
					path.numArrows++;
					path.cellsTravelled++;
					path.cellsTravelledPerArrow++;
					
					//Once a new target is hit, pop / reset the stack
					while(stack.size() > 0) {
						if(stack.size() == 2){
							currCell.markOutStack();
							path.inertia = 0;
						}
						stack.pop();
						if(stack.size() > 0) {
							currCell = (MapCell) stack.peek();
						}
					}
				}
			}

			//Checks if there is an available cell to move to
			if(path.nextCell(currCell) != null) {
				stack.push(path.nextCell(currCell));
				if(path.inertiaStart) {
					path.inertia++;
				}
				
				//Checks that the next cell is not a target
				if(!path.nextCell(currCell).isTarget()) {
					path.nextCell(currCell).markInStack();
					path.cellsTravelled++;
					path.cellsTravelledPerArrow++;
				}
			}else if(!stack.isEmpty()) { //If there are no cells to move to, pop / reset the stack
				while(stack.size() > 0) {
					stack.pop();
				}
				path.numArrows++;
			}
		}
		
		System.out.println("Targets Hit: " + path.targetsHit + " Cells Travelled: " + path.cellsTravelled);
		
	}
}
