
public class ArrayedList<I> implements ListADT<I> {
	protected I[] listElements;
	
	/**
	 * Offset of the array cell to the left of the 
	 */
	protected int listTail;
	
	/**
	 * size of the array listElements; maximum number of elements in the list
	 */
	protected int capacity; 
		
	@SuppressWarnings("unchecked")
	public ArrayedList(int capacity) {
		this.listTail = 0;
		this.capacity = capacity;
		this.listElements = (I[]) new Object[capacity];
	}

	// *** Exercise 4 ***
	public boolean isEmpty() {
		return this.listTail == 0;
	}

	public boolean isFull() {
		return this.listTail==capacity;
	}

	// *** Exercise 5 ***
	public void insertFirst(I x) {
		if(this.isFull()) throw new RuntimeException("Error: Cannot insert an item into a full list.");
		
		this.listElements[this.listTail] = x;
		this.listTail = this.listTail + 1;
	}

	// *** Exercise 6 ***
	public void deleteFirst()  {
		if(isEmpty()) throw new RuntimeException("Error: cannot delete an item from an empty list.");
		
		this.listTail = this.listTail - 1;
	}
	
	// *** Exercise 7 ***
	public I firstItem()  {
		if(this.isEmpty()) throw new RuntimeException("Error: cannot obtain an item from an empty list.");
		
		return this.listElements[this.listTail-1];
	}



}
