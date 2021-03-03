public class CompressedArray {
	private int origArraySize;
	private double[] array;
	private int count = 0;
	
	public CompressedArray(double[][] db) {
		//Set array size to given array length
		origArraySize = db.length;
		//Set the compressed array length based on the given array length
		if(origArraySize == 2) {
			array = new double[1];
		}else if(origArraySize == 3) {
			array = new double[3];
		}else if(origArraySize == 4) {
			array = new double[6];
		}else if(origArraySize == 5) {
			array = new double[10];
		}else if(origArraySize == 6) {
			array = new double[15];
		}else if(origArraySize == 7) {
			array = new double[21];
		}else if(origArraySize == 8) {
			array = new double[28];
		}else if(origArraySize == 9) {
			array = new double[36];
		}
		
		//Compress the given 2D array to a linear array
		for(int i = 0; i < origArraySize; i++) {
			for(int x = 0; x < origArraySize; x++) {
				//Checks if the value is above the triangle
				if(i <= x) {
					break;
				}else {
					//If value is below the triangle, add it to the linear array
					array[count] = db[i][x];
					count++;
				}	
			}
		}
	}
	
	public int getLength() {
		return array.length;
	}
	
	public double getElement(int i) {
		return array[i]; //Returns the value at i in the array
	}
	
	public boolean equals(CompressedArray a) {
		//Checks if the 2 arrays have the same length
		if(a.getLength() == this.array.length) {
			//Checks that every value is the same in both arrays
			for(int i = 0; i < this.array.length; i++) {
				if(a.getElement(i) == this.array[i]) {
					continue;
				}else {
					return false;
				}
			}
		}
		return true;
	}
	
	public String toString() {
		String out = "\n";
		for(int i = 0; i < getLength(); i++) {
			out += String.format("%8.2f", getElement(i)); //Formats the string
			if(i == 0 | i == 2 | i == 5 | i == 9 | i == 14 | i == 20 | i == 27 | i== 35 | i == 44 | i == 54) { //Checks if a new line should be added
				out += "\n";
			}
		}
		return out;
	}
}
