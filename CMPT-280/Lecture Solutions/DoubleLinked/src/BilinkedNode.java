// Exercise 4 solution:  Extend linked node to Bilinked Node

/**	A node containing an item and references to the next and
 previous nodes.  It includes routines to set the item or set
 either adjacent node. */
public class BilinkedNode<I> extends LinkedNode<I>
{
    /**	The previous node. */
    protected BilinkedNode<I> previousNode;

    /**	Construct a new node with item x. <br>
     Analysis: Time = O(1)
     @param x item placed in the node */
    public BilinkedNode(I x)
    {
        super(x);
    }

    /**	The previous node. <br>
     Analysis: Time = O(1) */
    public BilinkedNode<I> previousNode()
    {
        return previousNode;
    }

    /**	Set the reference to the previous node. <br>
     Analysis: Time = O(1)
     @param x node to be set as the new previous node */
    public void setPreviousNode(BilinkedNode<I> x)
    {
        previousNode = x;
    }

}
