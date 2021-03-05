
public class ArrayStack<T> implements ArrayStackADT<T>{

	private T[] arrayStack;
	private int top;
	public static String sequence;
	
	// creates an empty stack of a default value
	public ArrayStack () {
		sequence = "";
		top = -1;
		arrayStack = (T[])(new Object[14]);	// creates empty stack of initial capacity 10
	}
	
	// creates an empty stack of specified value
	public ArrayStack (int initialCapacity) {	
		sequence = "";
		top = -1;
		arrayStack = (T[])(new Object[initialCapacity]);	// creates empty stack of initial capacity specified in method arguments
	}
	
	// adds element on top of stack
	public void push (T dataItem){
		T[] newStack;
		
		// if stack capacity less than 50
		if (arrayStack.length < 50 && (arrayStack[arrayStack.length-1] != null)) {
			newStack = (T[])(new Object[arrayStack.length+10]);	// creates new stack with 10 more spaces than the old stack
			
			// assigns old elements in array to new array in same spots
			for (int i = 0; i < arrayStack.length; i++) {
				newStack[i] = arrayStack[i];
			}
			arrayStack = newStack;	// stack will point to the new, bigger stack created
		}	
		
		// if stack capacity greater than 50
		if (arrayStack.length >= 50 && (arrayStack[arrayStack.length-1] != null)) {
			newStack = (T[])(new Object[arrayStack.length*2]);	// creates new stack 2 times bigger than old stack
			
			for (int i = 0; i < arrayStack.length; i++) {
				newStack[i] = arrayStack[i];
			}
			arrayStack = newStack;	// stack will point to the new, bigger stack created
		}
		if (top < arrayStack.length) {
			arrayStack[top+1] = dataItem;		// adds the element dataItem on top of stack
			top++;
		}
		
		if (dataItem instanceof MapCell) {
			sequence += "push" + ((MapCell)dataItem).getIdentifier();
		} else {
			sequence += "push" + dataItem.toString();
		}

		
	}
	
	// removes and returns the element on top of the stack
	public T pop () throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Error: Stack is empty");
		}
		T[] newStack;
		
		top--;
		T element = arrayStack[top+1];
		arrayStack[top+1] = null;
		
		if(top < (arrayStack.length/4)) {
			if((arrayStack.length / 2) < 14) {
				newStack = (T[])(new Object[14]);
				for (int i = 0; i < arrayStack.length; i++) {
					if(arrayStack[i] !=null) {
						newStack[i] = arrayStack[i];
					}
				}
				arrayStack = newStack;	// stack will point to the new, smaller stack created
			}else {
				newStack = (T[])(new Object[arrayStack.length / 2]);
				for (int i = 0; i < arrayStack.length; i++) {
					if(arrayStack[i] !=null) {
						newStack[i] = arrayStack[i];
					}
				}
				arrayStack = newStack;	// stack will point to the new, smaller stack created
			}
			
		}
		
		if (element instanceof MapCell) {
			sequence += "pop" + ((MapCell)element).getIdentifier();
		}else {
			sequence += "pop" + element.toString();
		}
		
		return element;
		
	}
	
	// looks at the element at the top of the stack
	public T peek () throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("Error: Stack is empty");
		}
		return arrayStack[top];
	}
	
	// checks if stack is empty
	public boolean isEmpty() {
		return (arrayStack[0] == null);	// if stack is empty, returns true, else returns false
	}
	
	// returns number of elements in stack
	public int size() {
		return top+1;
	}
	
	public int length() {
		return arrayStack.length;
	}
	
	public String toString () {
		String toString = "";
		
		// converts all the elements in the stack to a string
		for (int i = 0; i < arrayStack.length; i++) {
			if(arrayStack[i] != null) {
				if(i != 0) {
					toString = toString + ", " + arrayStack[i];
				}else {
					toString = "Stack: " + arrayStack[i];
				}
			}
		}
		
		return toString;
	}
}
