public class City {
	private String name;
	private int x;
	private int y;
	private CityMarker marker;
	
	public City(String name1, int x1, int y1) {
		//Initialize variables to variables given
		name = name1;
		x = x1;
		y = y1;
		marker = new CityMarker();
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public CityMarker getMarker() {
		return marker;
	}
	
	public void setName(String name1) {
		name = name1;
	}
	
	public void setX(int x1) {
		x = x1;
	}
	
	public void setY(int y1) {
		y = y1;
	}
	
	public void setMarker(CityMarker marker1) {
		marker = marker1;
	}
	
	public String toString() {
		return name;
	}
	
}
