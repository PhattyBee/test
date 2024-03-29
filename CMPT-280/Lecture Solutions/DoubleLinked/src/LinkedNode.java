public class LinkedNode<I> {
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

}
