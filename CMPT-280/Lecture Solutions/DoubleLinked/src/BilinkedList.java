
public class BilinkedList<I> extends LinkedList<I>
{
	/***** Exercise 5 solutions *****/

    protected LinkedNode<I> tail;

    /**	Construct an empty list.
     Analysis: Time = O(1) */
    public BilinkedList()
    {
        super();
    }

    /**
     * Create a BilinkedNode280 this Bilinked list.  This routine should be
     * overridden for classes that extend this class that need a specialized node.
     * @param item - element to store in the new node
     * @return a new node containing item
     */
    protected BilinkedNode<I> createNewNode(I item)
    {
        return new BilinkedNode<I>(item);
    }


    /***** End exercise 5 solutions *****/




    /***** Exercise 6 Solutions *****/

    public void insertLast(I x)
    {
        // If the list is already empty, just use insert first to save coding a special case.
        if (this.isEmpty())
            this.insertFirst(x);
        else
        {
            // Otherwise, make a new node and insert it at after the last node

            BilinkedNode<I> temp = this.createNewNode(x);

            // Make current tail point to new node.
            this.tail.setNextNode(temp);

            // Make new node's previous reference point to current tail.
            temp.setPreviousNode((BilinkedNode<I>)this.tail);

            // Make the new node the new tail.
            this.tail = temp;

            // If the cursor was in the after position, have set prevPosition to refer to the new last node.
            if (this.after())
                this.prevPosition = this.tail;

            this.numEl++;
        }
    }


    public void deleteLast() throws RuntimeException
    {
        // If the list is empty, throw an exception.
        if (this.isEmpty())
            throw new RuntimeException("Cannot delete last item of an empty list.");

        // If the list has only one element, use deleteFirst() to avoid coding another special case.
        if (this.head==this.tail)
            deleteFirst();
        else
        {
            // Otherwise delete the last node and update prevPosition and position if necessary
            // We know there are at least two nodes...

            // If the cursor is in the after position, the prevPosition must be updated to point to the
            // current tail's predecessor.
            if (this.prevPosition==this.tail) {
                this.prevPosition = ((BilinkedNode<I>)this.tail).previousNode();

            }
            // If the cursor is on the last element, we have to move the cursor to the
            // previous element.
            else if (this.position==this.tail) {
                this.prevPosition = ((BilinkedNode<I>)this.prevPosition).previousNode();
                this.position = ((BilinkedNode<I>)this.position).previousNode();
            }

            // Set the tail to be the current tail's predecessor.
            this.tail = ((BilinkedNode<I>)this.tail).previousNode();

            // If the list is not empty as a result, set the current tail's successor reference to null
            // (instead of the old tail).
            if (this.tail!=null)
                this.tail.setNextNode(null);

            this.numEl--;
        }
    }

    @Override
    public void goLast() throws RuntimeException
    {
        if(this.isEmpty()) throw new RuntimeException("Cannot move to the end of an empty list.");

        this.position = this.tail;
        this.prevPosition = ((BilinkedNode<I>)this.position).previousNode();
    }


    /***** End Exercise 6 Solutions *****/


}
