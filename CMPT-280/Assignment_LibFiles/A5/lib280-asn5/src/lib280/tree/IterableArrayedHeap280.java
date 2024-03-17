package lib280.tree;

import lib280.exception.ContainerEmpty280Exception;

public class IterableArrayedHeap280<I extends Comparable<? super I>> extends ArrayedHeap280<I> {

	/**
	 * Create an iterable heap with a given capacity.
	 * @param cap The maximum number of elements that can be in the heap.
	 */
	public IterableArrayedHeap280(int cap) {
		super(cap);
	}



	// TODO
	// Add iterator() and deleteAtPosition() methods here.


	public ArrayedBinaryTreeIterator280<I> iterator(){
		ArrayedBinaryTreeIterator280 iterater = new ArrayedBinaryTreeIterator280(this);
		return iterater;
	}
	public void deleteAtPosition(ArrayedBinaryTreeIterator280 iter) {

/**
 * New code added here
 * passing an Iterator object reference to the method, and use that iterator reference for the heap to perform the Deletion process instead
 * of delete the first top item.
 *
 */
		if (this.isEmpty())
			throw new ContainerEmpty280Exception("Cannot delete an item from an empty heap.");

		int currentNode = iter.currentNode;

		if (this.count > 1) {
			if (currentNode == this.count) {
				this.items[currentNode] = null;

			} else {

				this.items[currentNode] = this.items[count];
			}
			this.count--;


			// If we deleted the last remaining item, make the the current item invalid, and we're done.
			if (this.count == 0) {
				this.currentNode = 0;
				return;
			}

			// Propagate the new root down the lib280.tree.
			int n = 1;

			// While offset n has a left child...
			while (findLeftChild(n) <= count) {
				// Select the left child.
				int child = findLeftChild(n);

				// If the right child exists and is larger, select it instead.
				if (child + 1 <= count && items[child].compareTo(items[child + 1]) < 0)
					child++;

				// If the parent is smaller than the root...
				if (items[n].compareTo(items[child]) < 0) {
					// Swap them.
					I temp = items[n];
					items[n] = items[child];
					items[child] = temp;
					n = child;

				} else return;


			}


		}

	}
}
