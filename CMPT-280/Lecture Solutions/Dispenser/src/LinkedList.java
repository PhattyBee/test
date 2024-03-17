// *** Exercise 1 ***
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

	// *** Exercise 2 ***
	
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


}
