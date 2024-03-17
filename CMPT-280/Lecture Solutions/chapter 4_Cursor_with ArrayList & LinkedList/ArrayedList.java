// ***
// Exercise 5 - Add "implements" clause for cursor interface
// ***
public class ArrayedList<I> implements Cursor<I> {

	/**
	 * Array of list items.
	 */
	protected I[] listElements;

	/**
	 * size of the array listElements; maximum number of elements in the list
	 */
	protected int capacity;

	/**
	 * Number of items in the array.
	 */
	protected int listTail;


	// *** Exercise 4 Solution ***

	/**
	 * Cursor position is an array offset.  In a non-empty list,
	 * the cursor positioned at an element if the stored offset is
	 * between 0 listTail-1.  If the list is empty, then the cursor is
	 * not positioned at any element by definition.
	 */
	protected int position;
	
	// *** End exercise 4 solution
	
	
	@SuppressWarnings("unchecked")
	public ArrayedList(int capacity) {
		this.capacity = capacity;
		this.listElements = (I[]) new Object[capacity];
		this.listTail = 0;
		/** Exercise 4 - need to initialize position. */
		this.position = -1;
	}

	public void insertFirst(I x) {
		if(this.isFull())
			throw new RuntimeException("Error: Cannot insert an item into a full list.");

		for(int i=listTail; i > 0; i--)
			this.listElements[i] = this.listElements[i-1];
		this.listElements[0] = x;
		this.listTail++;
	}

	public void deleteFirst()  {
		if(isEmpty())
			throw new RuntimeException("Error: cannot delete an item from an empty list.");

		for(int i = 0; i < this.listTail-1; i++) {
			this.listElements[i] = this.listElements[i+1];
		}
		this.listTail--;
	}

	public I firstItem()  {
		if(this.isEmpty()) throw new RuntimeException("Error: cannot obtain an item from an empty list.");

		return this.listElements[0];
	}
	
	
	// *** Exercise 5 Solutions ***

	@Override
	public boolean itemExists() {
		if( !this.isEmpty() && this.position < this.listTail )
			return true;
		else 
			return false;
	}

	@Override
	public I item() throws RuntimeException {
		if(!this.itemExists()) throw new RuntimeException("No item at cursor.");
		
		return this.listElements[this.position];
	}

	@Override
	public void goFirst() throws RuntimeException {
		if( this.isEmpty() ) throw new RuntimeException("No first element in an empty list.");
		this.position = 0;
	}

	@Override
	public void goForth() throws RuntimeException {
		if( this.after() )
			throw new RuntimeException("Already after the end.");
		else if( this.before() ) this.position = 0;
		else this.position ++;
	}

	@Override
	public void goLast() throws RuntimeException {
		if( this.isEmpty() ) throw new RuntimeException("No last element in a empty list.");
		else this.position = this.listTail-1;
	}

	@Override
	public void goBefore() {
		this.position = -1;
	}

	@Override
	public void goAfter() {
		this.position = this.capacity;
	}

	@Override
	public boolean before() {
		return this.isEmpty() || this.position < 0;
	}

	@Override
	public boolean after() {
		return this.isEmpty() || this.position >= this.capacity;
	}	
	
	// *** end Exercise 5 solutions ***
	
	
	
	public boolean isEmpty() {
		return this.listTail == 0;
	}

	public boolean isFull() {
		return this.listTail == this.capacity;
	}

	
	
	
	public static void main(String args[]) {
		ArrayedList<Double> L = new ArrayedList<Double>(5);
		Double x;

		// Newly created list should be empty.
		if( !L.isEmpty() ) 
			System.out.println("Error: Newly created list should be empty, but is not.");

		// Newly created list should not be full.
		if( L.isFull() ) 
			System.out.println("Error: isFull() returned true for a non-full list.");

	
		// Test firstItem() on empty list (exception expected)
		try {
			x = L.firstItem();
			System.out.println("Error: Expected exception did not occur when calling firstItem() on empty list.");
		}
		catch(RuntimeException e) {
			// RuntimeException expected, so do nothing.
		}

		
		// Test insertFirst when list is empty (no exception expected)
		try {
			L.insertFirst(5.0);
		}
		catch (RuntimeException e) {
			// Not expected.
		}
		
		
		// See if the state of the list is now correct.
		try {
			x = L.firstItem();
			if( x != 5.0 )
				System.out.println("Error: Expected first list item to be 5.0, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}
		
		
		// Test that isEmpty is now false
		if( L.isEmpty() ) 
			System.out.println("Error: isEmpty() returned true when the list is not empty.");
		

		
		// Test insertFirst when the list is not empty
		try {
			L.insertFirst(4.0);
		}
		catch (RuntimeException e) {
			// Not expected.
		}
		try {
			x = L.firstItem();
			if( x != 4.0 )
				System.out.println("Error: Expected first list item to be 5.0, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}
		
	
		// NOTE: by testing insertFirst() and insertLast() we've effectively
		// tested firstItem() when the list is non-empty
		// "for free".  One might argue that we should fully
		// test firstItem() before relying on it to test insertFirst(), but
		// in this case, we cannot fully test firstItem() without first inserting
		// something into the list.  
	
		
		
		// Add a third, fourth and fifth element so we can test
		// things when the list is full (no exception expecgted).
		try {
			// We could test the state after all of these, but we've
			// already tested all execution paths of insertFirst(), so it
			// should be fine.
			L.insertFirst(3.0);
			L.insertFirst(2.0);
			L.insertFirst(1.0);
		}
		catch (RuntimeException e) {
			// Not expected, do nothing.
		}
		
		// Test insertFirst() when list is full (exception expected!).
		try {
			L.insertFirst(0.0);
			System.out.println("Error: insertFirst() did not throw an exception when the list is full.");
		}
		catch(RuntimeException e) {
			// Expected, do nothing.
		}
		
		// Test isFull() when the list is full.
		if( !L.isFull() )
			System.out.println("Error: isFull() returned false when the list is full.");
	
		// test deleteFirst() when the list is full.
		try {
			L.deleteFirst();
			x = L.firstItem();
			if( x != 2.0 )
				System.out.println("Error: after deleting from full list, the first item should be 2.0, got: " + x );
			
			// Delete 3.0 and 4.0 without checking state.
			L.deleteFirst();
			L.deleteFirst();
		}
		catch(RuntimeException e) {
			System.out.println("Error: got unexpected exception while testing deleteFirst() on a full list.");
		}
		
		
		
		
		// Test deleteFirst() with more than one item.
		
		try {
			L.deleteFirst();
			// Verify the new expected state -- 5.0 should be the new first 
			x = L.firstItem();
			if( x != 5.0 )
				System.out.println("Error: Expected first list item to be 5.0, got: " + x);			
		}
		catch (RuntimeException e) {
			System.out.println("Error: unexpected exception occured while testing deleteFirst().");
		}
		
		
		// Test deleteFirst() with one item.
		try {
			L.deleteFirst();
			// Verify the new expected state -- The list should now be empty. 
			if(!L.isEmpty()) {
				System.out.println("Error: after testing deleteFirst() the list should be empty, but it is not.");
			}
		}
		catch (RuntimeException e) {
			System.out.println("Error: unexpected exception occured while testing deleteFirst().");
		}

		// Test deleteFirst when the list is empty (exception expected).
		try {
			L.deleteFirst();
			System.out.println("Error: An exception was expected and not caught while calling deleteFirst() on empty list.");
		}
		catch (RuntimeException e) {
			// RuntimeException expected, do nothing.
		}

	}


}
