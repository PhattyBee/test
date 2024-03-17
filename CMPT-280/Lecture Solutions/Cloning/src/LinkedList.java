// Exercise 1: Class is redeclared to implement Cloneable

public class LinkedList<I> implements Cursor<I>, Cloneable {
	/**
	 * First node in the list, or null if the list is empty.
	 */
	protected LinkedNode<I> head;

	/**
	 * Number of elements in the list.
	 */
	protected int numEl;

	/** The cursor position */
	protected LinkedNode<I> position;

	/** The position before the cursor position */
	protected LinkedNode<I> prevPosition;

	// Exercise 1: Add public shallow clone() method

	public LinkedList<I> clone() throws CloneNotSupportedException {
		/* This was acceptable for exercise 1 - a shallow clone
		return super.clone();
		*/

		// Exercise 2: A deep clone.  Copy each instance variable,
		// then deep clone the ones that are references to other objects.
		LinkedList<I> newList = (LinkedList<I>) super.clone();
		newList.head = this.head.clone();

		newList.goBefore();

		return newList;
	}


	/**
	 * Create an empty list.
	 */
	public LinkedList() {
		this.head = null;
		// *** Added for exercise 2 ***/
		this.position = null;
		this.prevPosition = null;
		// ***
		this.numEl = 0;
	}




	@Override
	public boolean itemExists() {
		return this.position != null;
	}


	@Override
	public I item() throws RuntimeException {
		if( !itemExists() ) 
			throw new RuntimeException("There is no current item to obtain.");

		return this.position.item();
	}

	@Override
	public void goFirst() throws RuntimeException {
		if( this.isEmpty() ) 
			throw new RuntimeException("Cannot position cursor at first element of an empty list.");

		this.position = this.head;
		this.prevPosition = null;
	}

	@Override
	public void goForth() throws RuntimeException {
		if (after())
			throw new RuntimeException("Cannot advance to next item when already after the end.");

		if (before())
			goFirst();
		else
		{
			this.prevPosition = this.position;
			this.position = this.position.nextNode();
		}				
	}

	@Override
	public boolean after() {
		return (this.position==null) && (this.prevPosition !=null || this.isEmpty());
	}

	@Override
	public boolean before() {
		return (this.prevPosition == null) && (this.position == null);
	}

	@Override
	public void goLast() throws RuntimeException {
		if( this.isEmpty() ) {
			this.position = null;
			this.prevPosition = null;
			return;
		}

		this.position = this.head;
		this.prevPosition = null;
		while(this.position.nextNode() != null) {
			this.prevPosition = this.position;
			this.position = this.position.nextNode();
		}

	}

	@Override
	public void goBefore() {
		this.position = null;
		this.prevPosition = null;
	}

	@Override
	public void goAfter() {
		this.goLast();
		this.goForth();
	}




	public boolean isEmpty() {
		return this.head == null;
	}

	public void insertFirst(I x)  {
		LinkedNode<I> newItem = new LinkedNode<I>(x);
		newItem.setNextNode(this.head);				
		this.head = newItem;
		this.numEl++;
	}

	public void deleteFirst() throws RuntimeException {
		if( this.isEmpty() ) throw new RuntimeException("Error: Cannot delete an item from an empty list.");

		// Unlink the first node.
		LinkedNode<I> oldhead = this.head;
		this.head = this.head.nextNode();
		oldhead.setNextNode(null);
		this.numEl--;
	}

	public I firstItem() throws RuntimeException {
		if( this.isEmpty() ) throw new RuntimeException("Error: firstItem() cannot get element from an empty list.");

		// Return the first item.
		return this.head.item();
	}

	public String toString() {
		LinkedNode<I> saveP = this.position;
		LinkedNode<I> savePP = this.prevPosition;

		String s = "";

		this.goFirst();
		while(this.itemExists()) {
			s += this.item().toString() + ", ";
			this.goForth();
		}

		this.position = saveP;
		this.prevPosition = savePP;

		return s + "\n";
	}

	public static void main(String args[]) throws RuntimeException {
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


		// Test goLast()
		L.goLast();
		if(L.item() != 5.0)
			System.out.println("Error:  goLast() did not set the current item to 5.0.");

		L.goAfter();
		if(!L.after())
			System.out.println("Error: goAfter() failed to put the cursor in the after position.");



		// Test insertFirst when the list is not empty
		L.insertFirst(4.0);
		try {
			x = L.firstItem();
			if( x != 4.0 )
				System.out.println("Error: Expected first list item to be 5.0, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}

		// Test goLast()
		L.goLast();
		if(L.item() != 5.0)
			System.out.println("Error:  goLast() did not set the current item to 5.0.");

		L.goAfter();
		if(!L.after())
			System.out.println("Error: goAfter() failed to put the cursor in the after position.");


		// NOTE: by testing insertFirst() and insertLast() we've effectively
		// tested firstItem() when the list is non-empty
		// "for free".  One might argue that we should fully
		// test firstItem() before relying on it to test insertFirst(), but
		// in this case, we cannot fully test firstItem() without first inserting
		// something into the list.  

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

		for(int i=0; i < 5; i++) {
			L.insertFirst(Math.random());			
		}

		L.goFirst();

		System.out.print("Numbers in the list: ");
		while(L.itemExists()) {
			System.out.print(L.item() + " ");
			L.goForth();
		} 
		System.out.println();




		// Exercise 2:  Clone a list, modify it, make sure original didn't change.

		LinkedList<Double> newList = null;
		try {
			newList = L.clone();
		}
		catch(CloneNotSupportedException e) {
			System.out.println("Hmm... I should be able to clone the list but I couldn't.");
		}

		newList.deleteFirst();

		System.out.println("Here is my clone with the first node removed: ");
		System.out.println(newList);  // uses this.toString()
		System.out.println("Here is me: ");
		System.out.println(L);  // uses this.toString()




	}
}
