public class LinkedList<I> implements ListADT<I> {
	/**
	 * First node in the list, or null if the list is empty.
	 */
	protected LinkedNode<I> head;
	
	/**
	 * Number of elements in the list.
	 */
	protected int numEl;
	
	/**
	 * Create an empty list.
	 */
	public LinkedList() {
		this.head = null;
		this.numEl = 0;
	}

	public boolean isEmpty() {
		return this.head == null;
	}

	public boolean isFull() { return false; }
	
	public void insertFirst(I x)  {
		LinkedNode<I> newItem = new LinkedNode<I>(x);
		newItem.setNextNode(this.head);				
		this.head = newItem;
		this.numEl++;
	}

	public void deleteFirst() {
		if( this.isEmpty() ) throw new RuntimeException("Error: Cannot delete an item from an empty list.");
				
		// Unlink the first node.
		LinkedNode<I> oldhead = this.head;
		this.head = this.head.nextNode();
		oldhead.setNextNode(null);
		this.numEl--;
	}

	public I firstItem()  {
		if( this.isEmpty() ) throw new RuntimeException("Error: firstItem() cannot get element from an empty list.");
		
		// Return the first item.
		return this.head.item();
	}

	// *** Exercise 3 ***
	public static void main(String args[]) {
		LinkedList<Double> L = new LinkedList<Double>();
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
			System.out.println("Error: firstItem() caused an unexpected exception while testing results of insertFirst().");
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



		// NOTE: by testing insertFirst() and insertLast() we've effectively
		// tested firstItem() when the list is non-empty
		// "for free".  One might argue that we should fully
		// test firstItem() before relying on it to test insertFirst(), but
		// in this case, we cannot fully test firstItem() without first inserting
		// something into the list.

		// Test deleteFirst() with more than 2 items

		try {
			L.deleteFirst();
			// Verify the new expected state -- 4.0 should be the new first
			x = L.firstItem();
			if( x != 4.0 )
				System.out.println("Error: Expected first list item to be 4.0, got: " + x);
		}
		catch (RuntimeException e) {
			System.out.println("Error: unexpected exception occured while testing deleteFirst().");
		}


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

	}
}
