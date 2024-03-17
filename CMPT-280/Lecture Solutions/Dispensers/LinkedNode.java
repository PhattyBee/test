// *** Exercise 1 ***
public class LinkedNode<I> {
	protected I item;
	protected LinkedNode<I> nextNode;
	
	public LinkedNode(I x)
	{
		this.setItem(x);
		this.setNextNode(null);
	}
	
	// Getters/setters are automatically generated using eclipse.
	// Use: Source menu -> Generate Getters and Setters

	public I item() {
		return this.item;
	}
	
	public void setItem(I item) {
		this.item = item;
	}
	
	public LinkedNode<I> nextNode() {
		return this.nextNode;
	}
	
	public void setNextNode(LinkedNode<I> nextNode) {
		this.nextNode = nextNode;
	}

}
