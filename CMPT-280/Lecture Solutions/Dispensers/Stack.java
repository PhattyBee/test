// Exercise 1

public abstract class Stack<I>  {

	protected ListADT<I> stackItems;

	public I top() {
		return stackItems.firstItem();
	}

	public void pop() throws RuntimeException {
		stackItems.deleteFirst();
	}

	public void push(I x) throws RuntimeException {
		stackItems.insertFirst(x);
	}

}
