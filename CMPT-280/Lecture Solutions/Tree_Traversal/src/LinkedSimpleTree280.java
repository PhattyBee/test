import java.util.LinkedList;

/**	An implementation of the SimpleTree280 interface with functions to access and
 set the root node and the root subtrees.  It also has functions to
 access the root item, test for empty, and to wipe out all the items. */
public class LinkedSimpleTree280<I> implements SimpleTree280<I>, Cloneable
{
	/**	Root node of the lib280.tree. */
	protected BinaryNode280<I> rootNode;

	/**	Create an empty lib280.tree. <br>
	 Analysis: Time = O(1) */
	public LinkedSimpleTree280()
	{
		rootNode = null;
	}

	// *** Exercise 1 Solution ***
    //this is a pre-order traversal on a DFS

	public void printDepthFirst() {
		if(!this.isEmpty())
			printDepthFirst(this.rootNode);
	}

	protected void printDepthFirst(BinaryNode280<I> root) {
		System.out.println(root.item());

		if( root.leftNode() != null )
			this.printDepthFirst(root.leftNode());
		if( root.rightNode() != null)
			this.printDepthFirst(root.rightNode());
	}

	// *** End of Exercise 1 Solution ***


	// *** Exercise 2 Solution ***

	public void printInorder() {
		if(!this.isEmpty())
			printInorder(this.rootNode);
	}

	protected void printInorder(BinaryNode280<I> root) {

		if( root.leftNode() != null )
			this.printInorder(root.leftNode());

		System.out.println(root.item());

		if( root.rightNode() != null)
			this.printInorder(root.rightNode());
	}

	// *** End of Exercise 2 Solution ***


	// *** Exercise 3 Solution ***

	public void printPostorder() {
		if(!this.isEmpty())
			printPostorder(this.rootNode);
	}

	protected void printPostorder(BinaryNode280<I> root) {

		if( root.leftNode() != null )
			this.printPostorder(root.leftNode());

		if( root.rightNode() != null)
			this.printPostorder(root.rightNode());

		System.out.println(root.item());

	}

	// *** End of Exercise 3 Solution ***

	// *** Exercise 4 solution ***

	public int numberOfNodes() {
		if(this.isEmpty()) return 0;
		else return numberOfNodes(this.rootNode);
	}

	protected int numberOfNodes(BinaryNode280<I> root) {
		int leftCount, rightCount;

		if( root.leftNode() == null )
			leftCount = 0;
		else leftCount = numberOfNodes(root.leftNode());

		if( root.rightNode() == null )
			rightCount = 0;
		else rightCount = numberOfNodes(root.rightNode());

		return 1 + leftCount + rightCount;
	}

	// *** End of exercise 4 solution ***

	// *** Exercise 5 solution ***

	public int treeHeight() {
		if(this.isEmpty()) return 0;
		else return treeHeight(this.rootNode);
	}

	protected int treeHeight(BinaryNode280<I> root) {
		int leftHeight, rightHeight;

		if( root.leftNode() == null )
			leftHeight = 0;
		else leftHeight = treeHeight(root.leftNode());

		if( root.rightNode() == null )
			rightHeight = 0;
		else rightHeight = treeHeight(root.rightNode());

		return 1 + Math.max(leftHeight, rightHeight);
	}

	// *** End of exercise 5 solution ***


	// *** Exercise 6 solution ***

	public void printLevelOrder() {
		if(this.isEmpty()) {
			System.out.println("<Empty>");
			return;
		}

		LinkedList<BinaryNode280<I>> L = new LinkedList<BinaryNode280<I>>();
		L.addLast(this.rootNode);

		while(!L.isEmpty()) {
			BinaryNode280<I> p = L.removeFirst();
			System.out.println(p.item());
			if( p.leftNode() != null ) L.addLast(p.leftNode());
			if( p.rightNode() != null) L.addLast(p.rightNode());
		}
	}

	// *** End exercise 6 solution ***


	/**	Create a lib280.tree from a root and two subtrees. <
	 Analysis: Time = O(1)
	 @param lt lib280.tree to initialize as the left subtree.  If null, the left subtree is empty.
	 @param r item to initialize as the root item
	 @param rt lib280.tree to initialize as the right subtree.  If null, the right subtree is empty. */
	public LinkedSimpleTree280(LinkedSimpleTree280<I> lt, I r, LinkedSimpleTree280<I> rt)
	{
		rootNode = createNewNode(r);
		try {
			setRootLeftSubtree(lt);
			setRootRightSubtree(rt);
		}
		catch(Exception e) {
			// This should never happen because we add a root node
			// before setting the subtrees, so it can never be empty.
		}
	}

	/**	Create a new node that is appropriate to this lib280.tree.  This method should be
	 overidden for classes that extend this class and need a specialized node,
	 i.e., a descendant of BinaryNode280.
	 Analysis: Time = O(1)
	 @param item    The item to be placed in the new node */
	protected BinaryNode280<I> createNewNode(I item)
	{
		return new BinaryNode280<I>(item);
	}

	/**	Is the lib280.tree empty?.
	 Analysis: Time = O(1)  */
	public boolean isEmpty()
	{
		return rootNode == null;
	}

	/**	Is the lib280.tree full?.
	 Analysis: Time = O(1) */
	public boolean isFull()
	{
		return false;
	}

	/**	Return the root node.
	 Analysis: Time = O(1) */
	protected BinaryNode280<I> rootNode()
	{
		return rootNode;
	}

	/**	Set root node to new node.
	 Analysis: Time = O(1)
	 @param newNode node to become the new root node */
	protected void setRootNode(BinaryNode280<I> newNode)
	{
		rootNode = newNode;
	}

	/**	Contents of the root item.
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 */
	public I rootItem() throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot access the root of an empty lib280.tree.");

		return rootNode.item();
	}

	/**	Set contents of the root to x.
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 @param x item to become the new root item
	 */
	public void setRootItem(I x) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set the root of an empty lib280.tree.");

		rootNode.setItem(x);
	}

	/**	Left subtree of the root.
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 */
	public LinkedSimpleTree280<I> rootLeftSubtree() throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty lib280.tree.");

		LinkedSimpleTree280<I> result = this.clone();
		result.clear();
		result.setRootNode(rootNode.leftNode());
		return result;
	}

	/**	Right subtree of the root.
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 */
	public LinkedSimpleTree280<I> rootRightSubtree() throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot return a subtree of an empty lib280.tree.");

		LinkedSimpleTree280<I> result = this.clone();
		result.clear();
		result.setRootNode(rootNode.rightNode());
		return result;
	}

	/**	Set the left subtree to t (set isEmpty if t == null).
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 @param t lib280.tree to become the rootLeftSubtree()
	 */
	public void setRootLeftSubtree(LinkedSimpleTree280<I> t) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set subtree of an empty lib280.tree.");

		if (t != null)
			rootNode.setLeftNode(t.rootNode);
		else
			rootNode.setLeftNode(null);
	}

	/**	Set the right subtree to t (set isEmpty if t == null).
	 Analysis: Time = O(1)
	 @precond !isEmpty()
	 @param t lib280.tree to become the rootRightSubtree()
	 */
	public void setRootRightSubtree(LinkedSimpleTree280<I> t) throws ContainerEmpty280Exception
	{
		if (isEmpty())
			throw new ContainerEmpty280Exception("Cannot set subtree of an empty lib280.tree.");

		if (t != null)
			rootNode.setRightNode(t.rootNode);
		else
			rootNode.setRightNode(null);
	}

	/**	Remove all items from the lib280.tree.
	 Analysis: Time = O(1) */
	public void clear()
	{
		setRootNode(null);
	}



	/**	A shallow clone of this lib280.tree.
	 Analysis: Time = O(1)
	 */
	@SuppressWarnings("unchecked")
	public LinkedSimpleTree280<I> clone()
	{
		// This is a safe way of implementing a shallow clone
		// that checks for the possibility that there is a
		// superclass which is not Object, that is NOT
		// cloneable.
		try
		{
			return (LinkedSimpleTree280<I>) super.clone();
		} catch(CloneNotSupportedException e)
		{
			/*	Should not occur: SimpleList280 extends Cloneable and Object implements clone(). */
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		LinkedSimpleTree280<Double> T = new LinkedSimpleTree280<Double>();
		T.printLevelOrder();
	}

}
