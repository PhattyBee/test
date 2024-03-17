
// Exercise 2

public class ArrayedStack<I> extends Stack<I> {


	public ArrayedStack(int capacity) {
		stackItems = new ArrayedList<I>(capacity);
	}
	

}
