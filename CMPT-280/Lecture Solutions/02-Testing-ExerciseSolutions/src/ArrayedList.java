
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

		this.capacity = capacity;
		this.listTail = 0;
		// Workaround for an array of a generic type.   Causes a warning that can be suppressed.
		// with @SuppressWarnings directive, above.
		this.listElements = (I[]) new Object[capacity];

		// This does not work!
		//this.listElements = new I[capacity];
	}

	public boolean isEmpty() {
		return this.listTail == 0;
	}

	public boolean isFull() {
		return this.listTail==capacity;
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


	// *** Exercise 4 ***
	public static void main(String args[]) {
		ArrayedList<Double> L = new ArrayedList<Double>(5);
		Double x;

		// Newly created list should be empty.
		if( !L.isEmpty() )
			System.out.println("Error: Newly created list should be empty, but is not.");


		// Test firstItem() on empty list (exception expected)
		try {
			x = L.firstItem();
			System.out.println("Error: Expected exception did not occur when calling firstItem() on empty list.");
		}
		catch(RuntimeException e) {
			// RuntimeException expected, so do nothing.
		}


		// Test insertFirst when list is empty
		L.insertFirst(5.0);


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


		// Test insertFirst when the list has one element
		L.insertFirst(4.0);
		try {
			x = L.firstItem();
			if( x != 4.0 )
				System.out.println("Error: Expected first list item to be 4.0, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}


		// Test insertFirst when the list has more than one element
		L.insertFirst(18.0);
		try {
			x = L.firstItem();
			if( x != 18.0 )
				System.out.println("Error: Expected first list item to be 18.0, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}


		// Add 2 more items to make the list full.
		L.insertFirst(42.0);
		L.insertFirst(19.0);

		// Now test if the list is full.
		if(!L.isFull()) {
			System.out.println("Error: List should be full, but isFull() says it is not.");
		}

		// Test isEmpty() when the list is full.
		if(L.isEmpty()) {
			System.out.println("Error: List is not empty, but isEmpty() says it is.");
		}

		// Test insertion into full list.
		try {
			L.insertFirst(99.0);
			// If no exception occurred, then error!
			System.out.println("Error: Insertion on a full list did not cause an exception.");
		}
		catch(Exception e) {
			// Exception expected... do nothing.
		}



		// NOTE: by testing insertFirst() and insertLast() we've effectively
		// tested firstItem() when the list is non-empty
		// "for free".  One might argue that we should fully
		// test firstItem() before relying on it to test insertFirst(), but
		// in this case, we cannot fully test firstItem() without first inserting
		// something into the list.

		// Test deleteFirst() when the list is full.

		try {
			L.deleteFirst();
			// Verify the new expected state -- 42.0 should be the new first
			x = L.firstItem();
			if( x != 42.0 )
				System.out.println("Error: Expected first list item to be 42.0, got: " + x);
		}
		catch (RuntimeException e) {
			System.out.println("Error: unexpected exception occured while testing deleteFirst().");
		}

		// The list should now no longer be full.
		if(L.isFull()) {
			System.out.println("Error: The list is not full, but isFull() says that it is.");
		}

		// Test deleteFirst() with more than 2 items

		try {
			L.deleteFirst();
			// Verify the new expected state -- 19.0 should be the new first
			x = L.firstItem();
			if( x != 18.0 )
				System.out.println("Error: Expected first list item to be 18.0, got: " + x);
		}
		catch (RuntimeException e) {
			System.out.println("Error: unexpected exception occured while testing deleteFirst().");
		}

		// Delete one more item so there are 2 left.
		L.deleteFirst();

		// Test deleteFirst() with 2 items

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

		// Test isFull() when the list is empty.
		if( L.isFull() ) {
			System.out.println("Error: The list is not full, but isFull() says that it is.");
		}

	}
}
