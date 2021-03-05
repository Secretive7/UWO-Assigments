import java.io.FileNotFoundException;
import java.io.IOException;

public class StartSearch {
	private Map targetMap;
	private int numArrows;
	private int inertia;
	private int direction;
	private int backtracks;
	private int targetsHit;
	private int cellsTravelled;
	private boolean canTurn;
	private boolean inertiaStart;
	private boolean hasTurned;
	
	public StartSearch(String filename) {
		try {
			targetMap = new Map(filename);
			numArrows = 0;
			direction = 0;
			inertia = 0;
			backtracks = 0;
			targetsHit = 0;
			cellsTravelled = 0;
			canTurn = true;
			inertiaStart = false;
			hasTurned = false;
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MapCell nextCell(MapCell cell) {
		MapCell bestCell = null;
		boolean blocked = false;
		
		if(cell.getNeighbour(direction) == null) {
			blocked = true;
		}else {
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) !=null) {
					if(direction == i && (cell.getNeighbour(i).isBlackHole() || cell.getNeighbour(i).isMarked() || ((direction == 1 || direction == 3) && cell.getNeighbour(i).isVerticalPath()) || ((direction == 0 || direction == 2) && cell.getNeighbour(i).isHorizontalPath()))) {
						blocked = true;
					}
				}
			}
		}
		
		if(!blocked) {
			if(cellsTravelled > 3) {
				inertiaStart = true;
			}
			if(inertiaStart && inertia > 1) {
				canTurn = false;
			}
			bestCell = cell.getNeighbour(direction);
			inertia++;
			return bestCell;
		}else{
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					if(!cell.getNeighbour(i).isMarked() && canTurn) {	
						if(cell.getNeighbour(i).isTarget()) {
							bestCell = cell.getNeighbour(i);
							direction = i;
							inertia = 0;
							if(hasTurned) {
								inertiaStart = true;
							}
							return bestCell;
						}
					}
				}
			}
			
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					if(!cell.getNeighbour(i).isMarked() && canTurn) {
						if(cell.getNeighbour(i).isCrossPath()) {
							bestCell = cell.getNeighbour(i);
							direction = i;
							inertia = 0;
							if(hasTurned) {
								inertiaStart = true;
							}
							return bestCell;
						}
					}
				}
			}
			
			for(int i = 0; i < 4; i++) {
				if(cell.getNeighbour(i) != null) {
					if(!cell.getNeighbour(i).isMarked() && canTurn) {
						if(((i == 0 || i == 2) && cell.getNeighbour(i).isVerticalPath()) || ((i == 1 || i == 3) && cell.getNeighbour(i).isHorizontalPath())) {
							bestCell = cell.getNeighbour(i);
							direction = i;
							inertia = 0;
							if(!cell.isStart()) {
								inertiaStart = true;
							}
							return bestCell;
						}
					}
				}
			}
			
		}
		
		
		return bestCell;
	}
	
	public static void main(String args[]) {
		StartSearch path = new StartSearch(args[0]);
		ArrayStack<MapCell> stack = new ArrayStack<MapCell>();
		MapCell cell = path.targetMap.getStart();
		
		while(path.numArrows < path.targetMap.quiverSize()) {
			
			//Checks if the stack is empty. If empty, setup the stack 
			if(stack.isEmpty()) {
				stack.push(cell);
				cell.markInStack();
				cell.markInitial();
				path.direction = 0;
				path.inertia = 0;
				path.canTurn = true;
				path.inertiaStart = false;
				path.hasTurned = false;
			}
			
			//Gets the current cell from the stack
			MapCell currCell = (MapCell) stack.peek();
			
			//Checks if the current cell is a target
			if(currCell.isTarget()) {
				if(!currCell.isMarked()) {
					currCell.markInStack();
					currCell.markTarget();
					path.targetsHit++;
					path.numArrows++;
					path.cellsTravelled++;
					
					while(stack.size() > 0) {
						if(stack.size() == 2){
							currCell.markOutStack();
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
				if(!path.nextCell(currCell).isTarget()) {
					path.nextCell(currCell).markInStack();
					path.cellsTravelled++;
				}
			}else if(path.backtracks < 3 && !stack.isEmpty() && path.nextCell(currCell) != null) {
				if(!path.nextCell(currCell).isMarked()) {
					stack.pop();
					stack.push(path.nextCell(currCell));
					path.backtracks++;
				}
				
			}else if(!stack.isEmpty()) {
				while(stack.size() > 0) {
					stack.pop();
				}
				path.numArrows++;
			}
		}
		
		System.out.println("Targets Hit: " + path.targetsHit + " Cells Travelled: " + path.cellsTravelled);
		
	}
}
