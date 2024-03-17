// Exercise 1:  Class must be redeclared to implement the Cloneable interface.

public class LinkedNode<I> implements Cloneable {
	protected I item;
	protected LinkedNode<I> nextNode;
	
	public LinkedNode(I x)
	{
		setItem(x);
	}
	
	// Getters/setters are automatically generated using eclipse.
	// Use: Source menu -> Generate Getters and Setters

	public I item() {
		return item;
	}
	
	public void setItem(I item) {
		this.item = item;
	}
	
	public LinkedNode<I> nextNode() {
		return nextNode;
	}
	
	public void setNextNode(LinkedNode<I> nextNode) {
		this.nextNode = nextNode;
	}

	// Exercise 1: Add a public shallow clone() method.
	@SuppressWarnings("unchecked")
	public LinkedNode<I> clone() throws CloneNotSupportedException {
		/* This was acceptable for exercise 1 - a shallow clone
		return super.clone();
		*/

		// Exercise 2: A deep clone.  Copy each instance variable,
		// clone()-ing the ones that are references to other objects.
		LinkedNode<I> newNode = (LinkedNode<I>)super.clone();
		if(newNode.nextNode != null)
			newNode.nextNode = this.nextNode.clone();

		// Clone the item if we can.  Types that encapsulate primitive types like
		// Double, Integer, etc. are not cloneable because they are immutable.
		// But since they are immutable, this causes no problems for our list.
		try {
			//newNode.item = this.item.clone();   We can't do this because we can't require a generic type to be
			// cloneable, so there's no guarantee that a public clone method exists.  (You'd think we could
			// restrict I so that it implements cloneable, i.e. <I extends Cloneable>, but this does not work.)
			// Moreover, Integer, Double, etc. are not cloneable, so if the item is one of these types
			// we can't even shallow clone it.   But, such types are immutable, so
			// if we can't clone them, it is OK.   We use the following magical incantation to try to
			// call the possibly protected clone() method in a way that bypasses the compiler's objections.
			// If an exception arises, we do nothing and assume that the object was immutable and don't clone anything.
			newNode.item = (I) this.item.getClass().getMethod("clone").invoke(this.item);
		} catch (Exception e) {
		}
		return newNode;
	}

}
