package lib280.list;


import lib280.base.BilinearIterator280;
import lib280.base.CursorPosition280;
import lib280.base.Pair280;
import lib280.exception.*;

/**	This list class incorporates the functions of an iterated 
	dictionary such as has, obtain, search, goFirst, goForth, 
	deleteItem, etc.  It also has the capabilities to iterate backwards 
	in the list, goLast and goBack. */
public class BilinkedList280<I> extends LinkedList280<I> implements BilinearIterator280<I>
{
	/* 	Note that because firstRemainder() and remainder() should not cut links of the original list,
		the previous node reference of firstNode is not always correct.
		Also, the instance variable prev is generally kept up to date, but may not always be correct.  
		Use previousNode() instead! */

	/**	Construct an empty list.
		Analysis: Time = O(1) */
	public BilinkedList280()
	{
		super();
	}

	/**
	 * Create a BilinkedNode280 this Bilinked list.  This routine should be
	 * overridden for classes that extend this class that need a specialized node.
	 * @param item - element to store in the new node
	 * @return a new node containing item
	 */
	protected BilinkedNode280<I> createNewNode(I item)
	{
		// TODO

		return new BilinkedNode280<I>(item);
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insertFirst(I x) 
	{
		// TODO

		BilinkedNode280<I> newItem = createNewNode(x);
		newItem.setNextNode(this.head);

		if( !this.isEmpty() && this.position == this.head ) this.prevPosition = newItem;

		if( this.isEmpty() ) this.tail = newItem;
		this.head = newItem;
	}

	/**
	 * Insert element at the beginning of the list
	 * @param x item to be inserted at the beginning of the list 
	 */
	public void insert(I x) 
	{
		this.insertFirst(x);
	}

	/**
	 * Insert an item before the current position.
	 * @param x - The item to be inserted.
	 */
	public void insertBefore(I x) throws InvalidState280Exception {
		if( this.before() ) throw new InvalidState280Exception("Cannot insertBefore() when the cursor is already before the first element.");
		
		// If the item goes at the beginning or the end, handle those special cases.
		if( this.head == position ) {
			insertFirst(x);  // special case - inserting before first element
		}
		else if( this.after() ) {
			insertLast(x);   // special case - inserting at the end
		}
		else {
			// Otherwise, insert the node between the current position and the previous position.
			BilinkedNode280<I> newNode = createNewNode(x);
			newNode.setNextNode(position);
			newNode.setPreviousNode((BilinkedNode280<I>)this.prevPosition);
			prevPosition.setNextNode(newNode);
			((BilinkedNode280<I>)this.position).setPreviousNode(newNode);
			
			// since position didn't change, but we changed it's predecessor, prevPosition needs to be updated to be the new previous node.
			prevPosition = newNode;			
		}
	}
	
	
	/**	Insert x before the current position and make it current item. <br>
		Analysis: Time = O(1)
		@param x item to be inserted before the current position */
	public void insertPriorGo(I x) 
	{
		this.insertBefore(x);
		this.goBack();
	}

	/**	Insert x after the current item. <br>
		Analysis: Time = O(1) 
		@param x item to be inserted after the current position */
	public void insertNext(I x) 
	{
		if (isEmpty() || before())
			insertFirst(x); 
		else if (this.position==lastNode())
			insertLast(x); 
		else if (after()) // if after then have to deal with previous node  
		{
			insertLast(x); 
			this.position = this.prevPosition.nextNode();
		}
		else // in the list, so create a node and set the pointers to the new node 
		{
			BilinkedNode280<I> temp = createNewNode(x);
			temp.setNextNode(this.position.nextNode());
			temp.setPreviousNode((BilinkedNode280<I>)this.position);
			((BilinkedNode280<I>) this.position.nextNode()).setPreviousNode(temp);
			this.position.setNextNode(temp);
		}
	}

	/**
	 * Insert a new element at the end of the list
	 * @param x item to be inserted at the end of the list 
	 */
	public void insertLast(I x) 
	{
		// TODO
		if (this.isEmpty())
			this.insertFirst(x);

		else{

			BilinkedNode280 newItem = this.createNewNode(x);

			this.tail.setNextNode(newItem);

			newItem.setPreviousNode((BilinkedNode280) tail);

			this.tail = newItem;

			if (this.after()) {
				this.prevPosition = tail;
			}

		}

	}

	/**
	 * Delete the item at which the cursor is positioned
	 * @precond itemExists() must be true (the cursor must be positioned at some element)
	 */
	public void deleteItem() throws NoCurrentItem280Exception
	{
		// TODO

		if (this.isEmpty()){
			throw new NoCurrentItem280Exception ("Nothing can be deleted.");
		}
		if (this.position == this.head){
			deleteFirst();
		}

		if (this.position == this.tail){
			deleteLast();
		}
		else{
			if (this.itemExists()){
				delete(this.position.item());
			}
		}

	}

	
	@Override
	public void delete(I x) throws ItemNotFound280Exception {
		if( this.isEmpty() ) throw new ContainerEmpty280Exception("Cannot delete from an empty list.");

		// Save cursor position
		LinkedIterator280<I> savePos = this.currentPosition();
		
		// Find the item to be deleted.
		search(x);
		if( !this.itemExists() ) throw new ItemNotFound280Exception("Item to be deleted wasn't in the list.");

		// If we are about to delete the item that the cursor was pointing at,
		// advance the cursor in the saved position, but leave the predecessor where
		// it is because it will remain the predecessor.
		if( this.position == savePos.cur ) savePos.cur = savePos.cur.nextNode();
		
		// If we are about to delete the predecessor to the cursor, the predecessor 
		// must be moved back one item.
		if( this.position == savePos.prev ) {
			
			// If savePos.prev is the first node, then the first node is being deleted
			// and savePos.prev has to be null.
			if( savePos.prev == this.head ) savePos.prev = null;
			else {
				// Otherwise, Find the node preceding savePos.prev
				LinkedNode280<I> tmp = this.head;
				while(tmp.nextNode() != savePos.prev) tmp = tmp.nextNode();
				
				// Update the cursor position to be restored.
				savePos.prev = tmp;
			}
		}
				
		// Unlink the node to be deleted.
		if( this.prevPosition != null)
			// Set previous node to point to next node.
			// Only do this if the node we are deleting is not the first one.
			this.prevPosition.setNextNode(this.position.nextNode());
		
		if( this.position.nextNode() != null )
			// Set next node to point to previous node 
			// But only do this if we are not deleting the last node.
			((BilinkedNode280<I>)this.position.nextNode()).setPreviousNode(((BilinkedNode280<I>)this.position).previousNode());
		
		// If we deleted the first or last node (or both, in the case
		// that the list only contained one element), update head/tail.
		if( this.position == this.head ) this.head = this.head.nextNode();
		if( this.position == this.tail ) this.tail = this.prevPosition;
		
		// Clean up references in the node being deleted.
		this.position.setNextNode(null);
		((BilinkedNode280<I>)this.position).setPreviousNode(null);
		
		// Restore the old, possibly modified cursor.
		this.goPosition(savePos);
		
	}
	/**
	 * Remove the first item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteFirst() throws ContainerEmpty280Exception
	{
		// TODO
		if (this.isEmpty()){
			throw new ContainerEmpty280Exception("Can not delete an empty list.");

		}
		else{
			delete(this.head.item());

		}

	}

	/**
	 * Remove the last item from the list.
	 * @precond !isEmpty() - the list cannot be empty
	 */
	public void deleteLast() throws ContainerEmpty280Exception {
		// TODO

		if (this.isEmpty()) {
			throw new ContainerEmpty280Exception("Can not delete an empty list.");

		} else {
			delete(this.tail.item());
		}
	}
	
	/**
	 * Move the cursor to the last item in the list.
	 * @precond The list is not empty.
	 */
	public void goLast() throws ContainerEmpty280Exception
	{
		// TODO
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Can not go to the last because it is empty.");
		else{

			this.position = this.tail;
			this.prevPosition = ((BilinkedNode280<I>)this.position).previousNode();

		}

	}
  
	/**	Move back one item in the list. 
		Analysis: Time = O(1)
		@precond !before() 
	 */
	public void goBack() throws BeforeTheStart280Exception
	{
		// TODO
		if (before()){throw new BeforeTheStart280Exception("Can not go back, its already at the beginning.");}

		else{

			this.position = this.prevPosition;

			this.prevPosition = ((BilinkedNode280<I>)this.prevPosition).previousNode();
		}


	}

	/**	Iterator for list initialized to first item.
		Analysis: Time = O(1) 
	*/

	public BilinkedIterator280<I> iterator()
	{
		return new BilinkedIterator280<I>(this);
	}

	/**	Go to the position in the list specified by c. <br>
		Analysis: Time = O(1) 
		@param c position to which to go */
	@SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 c)
	{
		if (!(c instanceof BilinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a BilinkedIterator280<I>");
		BilinkedIterator280<I> lc = (BilinkedIterator280<I>) c;
		this.position = lc.cur;
		this.prevPosition = lc.prev;
	}

	/**	The current position in this list. 
		Analysis: Time = O(1) */
	public BilinkedIterator280<I> currentPosition()
	{
		return  new BilinkedIterator280<I>(this, this.prevPosition, this.position);
	}

	
  
	/**	A shallow clone of this object. 
		Analysis: Time = O(1) */
	public BilinkedList280<I> clone() throws CloneNotSupportedException
	{
		return (BilinkedList280<I>) super.clone();
	}


	/* Regression test. */
	public static void main(String[] args) {
		// TODO

		//test createNewNode()

		BilinkedList280<Integer> list = new BilinkedList280<Integer>();

		if (!list.isEmpty()){
			System.out.println("Error: Newly created list should be empty, but is not");
		}

		//test insertFirst() when list is empty

		list.insertFirst(10);

		try{
			int x = list.firstItem();
			if (x != 10)
				System.out.println("Error: Expected first list item to be 10, got: " + x);
		}catch(RuntimeException e) {
			System.out.println("Error: firstItem() caused an enexpected exception while testing results of insertFirst().");
		}

		// Test insertFirst when the list has one element
		list.insertFirst(40);
		try {
			int x = list.firstItem();
			if( x != 40 )
				System.out.println("Error: Expected first list item to be 40, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: insertFirst() caused an unexpected exception while testing results of insertFirst().");
		}

		//test insertLast()

		list.insertLast(100);
		try {
			int x = list.lastItem();
			if( x != 100 )
				System.out.println("Error: Expected first list item to be 100, got: " + x);
		}
		catch(RuntimeException e) {
			System.out.println("Error: insertLast() caused an unexpected exception while testing results of insertFirst().");
		}

		list.position = list.tail;
		//test deleteItem() move the cursor to the last item and then delete it.
		// test if the last item is 10 (the second last node before deleting)
		list.deleteItem();
		try{
			int x = list.lastItem();
			if (x != 10)
				System.out.println("deleteItem() Error: Expected first list item to be 40, got: " + x);
		}catch (RuntimeException e){
			System.out.println("Error: deleteItem() caused an unexpected exception while testing results of deleteItem().");
		};

		//test deleteFirst(),now there are 40,10 in the list
		// we test deleteFirst and see if the first item is 40 after the method.

		list.deleteFirst();
		try{
			int x = list.firstItem();
			if (x != 10)
				System.out.println("deleteFirst() Error: Expected first list item to be 40, got: " + x);
		}catch (RuntimeException e){
			System.out.println("Error: deleteFirst() caused an unexpected exception while testing results of deleteFirst()");
		};

		//deleteLast(), now only 10 in the list, so after the deleteLast(), the list shoudl be empty,
		// test if its empty.
		list.deleteLast();

		try{
			if (!list.isEmpty()){
				System.out.println("deleteLast() error: The list is expected to be empty, but it is not.");
			}
		}catch (RuntimeException e){
			System.out.println("Error: deleteLast() caused an unexpected exception while testing results of deleteLast()");};

		//test goLast(), add two more integers to the list for testing.
		list.insertLast(99);
		list.insertLast(999);
		list.goLast();

		try{
			int x = list.lastItem();
			if (x != 999)
				System.out.println("goLast() Error: Expected first list item to be 999, got: " + x);
		}catch (RuntimeException e){
			System.out.println("Error: goLast() caused an unexpected exception while testing results of deleteFirst()");
		};

		//test goBack()
		//Right now the list is 10,99,999, we move the cursor from the last one to the second last node which is 99 and give
		//it a test.
		list.position = list.tail;
		list.goBack();

		try{
			int x = list.position.item;
			if (x != 99)
				System.out.println("goBack() Error: Expected first list item to be 99, got: " + x);
		}catch (RuntimeException e){
			System.out.println("Error: goBack() caused an unexpected exception while testing results of deleteFirst()");
		};
	}
}