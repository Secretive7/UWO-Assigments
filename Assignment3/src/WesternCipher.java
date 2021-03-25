public class WesternCipher {
	CircularArrayQueue<Character> eq;
	CircularArrayQueue<Character> dq;
	
	public WesternCipher() {
		eq = new CircularArrayQueue<>();
		eq = new CircularArrayQueue<>();
	}
	
	public WesternCipher(int size) {
		eq = new CircularArrayQueue<>(size);
		eq = new CircularArrayQueue<>(size);
	}
	
	public String encode(String input) {
		
	CircularArrayQueue<Integer> keyQueue1 = new CircularArrayQueue<>();
	CircularArrayQueue<Integer> keyQueue2 = new CircularArrayQueue<>();
	
	for (int i = 0; i < input.length(); i++) {
		keyQueue1.enqueue(new Integer(input.charAt(i)));
		keyQueue2.enqueue(new Integer(input.charAt(i)));
	}
	
	Integer keyValue;
	String encoded = "";
	
	for (int i = 0; i < input.length(); i++) {
		keyValue = keyQueue1.dequeue();
		encoded += (char) ((int) input.charAt(i) + keyValue.intValue());
		keyQueue1.enqueue(keyValue);
	}
	return encoded;
}
	
	public String decode(String encoded) {
		CircularArrayQueue<Integer> keyQueue1 = new CircularArrayQueue<>();
		CircularArrayQueue<Integer> keyQueue2 = new CircularArrayQueue<>();
		
		for (int i = 0; i < encoded.length(); i++) {
			keyQueue1.enqueue(new Integer(encoded.charAt(i)));
			keyQueue2.enqueue(new Integer(encoded.charAt(i)));
		}
		
		Integer keyValue;
		String decoded = "";
		
		for (int scan = 0; scan < encoded.length(); scan++) {
			keyValue = keyQueue2.dequeue();
			decoded += (char) ((int) encoded.charAt(scan) -
					keyValue.intValue());
			keyQueue2.enqueue(keyValue);
		}
		return decoded;
	}
}
