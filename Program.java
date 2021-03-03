public class Program {
	private int cityCount;
	private City[] cityArray;
	private CompressedArray array;
	
	public Program(String file, boolean showMap) {
		//Get the city count from the # of lines in the txt file
		cityCount = ((getLines(file) - 1) / 3);
		//Initialize the cityArray with the total amount of cities
		cityArray = new City[cityCount];
		
		MyFileReader input = new MyFileReader(file);//open file reader
		
		for (int i = 0; i < cityCount; i++) {
			if(i==0) {//ignores the first line of the txt file
				input.readString();
			}
			//Set the values to the correct input
			String city = input.readString();
			String x = input.readString();
			String y = input.readString();
			
			cityArray[i] = new City(city, Integer.parseInt(x), Integer.parseInt(y));//Add the city to the cityArray
		}
		
		if(showMap) {//Checks to see if map should be shown
			Map map = new Map();//creates new map object
			//Adds a marker for each city in cityArray
			for (int i = 0; i < cityCount; i++) {
				map.addCity(cityArray[i]);
			}
			
		}	
	}
	
	public City[] getCityList() {
		return cityArray;
	}
	
	private void expandCapacity() {
		cityArray = new City[cityArray.length + 3];//Adds 3 extra slots to cityArray
	}
	
	public double distBetweenCities(City a, City b) {
		int x, y;
		double total;
		//
		x = (a.getX() - b.getX())*(a.getX() - b.getX()); //get distance between x values
		y = (a.getY() - b.getY())*(a.getY() - b.getY()); //get distance between y values
		total = Math.sqrt(x+y);//Use pythagorean theorem to solve for distance
		
		return total;
	}
	
	public void compareDistances() {
		//Create 2D array to store distances
		double[][] distances = new double[getCityList().length][getCityList().length];
		for (int i = 0; i < getCityList().length; i++) {
			for (int x = 0; x < getCityList().length; x++) {
				distances[x][i] = distBetweenCities(cityArray[x], cityArray[i]);//Calculate and store distances between each city in cityArray
			}
		}
		array = new CompressedArray(distances);//Copy and store distances in the compressed array
	}
	
	public CompressedArray getArray() {
		return array;
	}
	
	private int getLines(String file) {
		//Calculate how many lines are in the txt files
		int x = 0;
		MyFileReader input = new MyFileReader(file);

		while (!input.endOfFile()) {
			input.readString();
			x++;
		}
		return x;
	}
}
