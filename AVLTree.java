/**
 *
 * AVLTree
 *
 * An implementation of a׳� AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private IAVLNode root;

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
    return false; // to be replaced by student code
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	if(root == null) {
		return null;
	}
	IAVLNode x = root;
	while(x.isRealNode()) {
		int key = x.getKey();
		if(key == k) {
			return x.getValue();
		}
		else if (k < key) {
			x = x.getLeft();
		}
		else {
			x = x.getRight();
		}
	}
	return null;
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	  return 420;	// to be replaced by student code
   }

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   if (!(root.isRealNode())) {
		   return -1;
	   }
	   IAVLNode x = root;
	   while(x.isRealNode()) {
		   int key = x.getKey();
		   if(key == k) {
			   break;
		   }
		   else if(k < key) {
			   x = x.getLeft();
		   }
		   else {
			   x = x.getRight();
		   }
	   }
	   if(!(x.isRealNode())) { // key not found
		   return -1;
	   }
	   int n;
	   IAVLNode r = x.getRight();
	   IAVLNode l = x.getLeft();
	   IAVLNode p = x.getParent();
	   if(!(r.isRealNode()) && !(l.isRealNode())) { // x is a leaf
		   IAVLNode v = new AVLNode(-1,null,-1,p,0); // virtual node
		   if(root == x) {
			   root = v;
			   return 0;
		   }
		   if(p.getRight() == x) {
			   p.setRight(v);
		   }
		   else {
			   p.setLeft(v);
		   }
		   n = balanceRec(p);
		   return n;
	   }
	   else if(!(r.isRealNode())) { // x has only one child, l
		   if(root == x) {
			   root = l;
			   l.setParent(null);
			   return 0;
		   }
		   // x is not the root :
		   l.setParent(p);
		   if(p.getRight() == x) {
			   p.setRight(l);
		   }
		   else {
			   p.setLeft(l);
			   }
		   n = balanceRec(p);
		   return n;
	   }
	   else if(!(l.isRealNode())) { // x has only one child, r
		   if(root == x) {
			   root = r;
			   r.setParent(null);
			   return 0;
		   }
		   // x is not the root : 
		   r.setParent(p);
		   if(p.getRight() == x) {
			   p.setRight(r);
			   }
		   else {
			   p.setLeft(r);
			   }
		   n = balanceRec(p);
		   return n;
		   }
	   
	   else { // x has two children
		   int h = x.getHeight();
		   int s = x.getSize();
		   IAVLNode y = findSuccessor(x); // there must be a successor since it has two children
		   y.setHeight(h); // it will replace node x, so it gets its height
		   y.setSize(s); // it will replace node x, so it gets its size
		   IAVLNode y_parent = y.getParent();
		   // delete y and replace it by its only child(right child) :
		   y.getRight().setParent(y_parent);
		   if(y_parent.getRight() == y) { // y is a right child
			   y_parent.setRight(y.getRight());
		   }
		   else { // y is a left child
			   y_parent.setLeft(y.getRight());
		   }
		   // delete x and replace it by y :
		   y.setRight(r);
		   y.setLeft(l);
		   if(root == x) {
			   root = y;
			   y.setParent(null);
		   }
		   else {
			   y.setParent(p);
			   if(p.getRight() == x) {
				   p.setRight(y);
				   }
			   else {
				   p.setLeft(y);
			   }
		   }
		   n = balanceRec(y_parent);
		   return n;
	   }

   }
   private IAVLNode findSuccessor(IAVLNode v) {
	   if(v.getRight().isRealNode()) { // v has a right child
		  IAVLNode y = v.getRight();
		  // finding the minimal element in the subtree v.right :
		  while(y.getLeft().isRealNode()) {
			  y = y.getLeft();
		  }
		  return y;
	   }
	   else { // v does not have a right child
		   if(root == v) { // all of the elements in the tree are smaller
			   return null;
		   }
		   IAVLNode z = v.getParent();
		   while(z != root && v == z.getRight()) {
			   v = z;
			   z = z.getParent();
		   }
		   if(z == root) { // the root is the highest ancestor of v such that v is in its right subtree, and v has no right children, hence it is the maximal element in the tree
			   return null;
		   }
		   return z; // z is the lowest ancestor of v such that v is in its left subtree
	   }
   }
   
   
  private int balanceRec(IAVLNode v) {
	  if(v == null) { // the parent of the root is null
		  return 0;
	  }
	  IAVLNode right = v.getRight();
	  IAVLNode left = v.getLeft();
	  int rightRank = right.getHeight();
	  int leftRank = left.getHeight();
	  int r = v.getHeight();
	  int rightRankDiff= r - rightRank;
	  int leftRankDiff = r - leftRank;
	  if((rightRankDiff == 1 && leftRankDiff == 1) || (rightRankDiff == 1 && leftRankDiff == 2) || (rightRankDiff == 2 && leftRankDiff == 1)) {
		  updateSize(v);
		  return 0 + balanceRec(v.getParent());
	  }
	  if(rightRankDiff == 2 && leftRankDiff == 2) {
		  v.setHeight(r - 1);
		  updateSize(v);
		  return 1 + balanceRec(v.getParent());
	  }
	  if(rightRankDiff == 1 && leftRankDiff == 3) {
		  if(rankDiffCalc(right, right.getRight()) == 1 && rankDiffCalc(right, right.getLeft()) == 1) {
			  rotation(v,'l');
			  v.setHeight(r - 1); // demote v
			  right.setHeight(rightRank + 1);// promote previously right child
			  updateSize(v);
			  updateSize(right);
			  return 3 + balanceRec(right.getParent()); //terminal case but we still have to update the sizes
		  }
		  if(rankDiffCalc(right, right.getRight()) == 1 && rankDiffCalc(right, right.getLeft()) == 2) {
			  rotation(v, 'l');
			  v.setHeight(r - 2); // demote v twice
			  updateSize(v);
			  updateSize(right);
			  return 2 + balanceRec(right.getParent()); // right is now the root of this subtree, and we go up
		  }
		  if(rankDiffCalc(right, right.getRight()) == 2 && rankDiffCalc(right, right.getLeft()) == 1) {
			  IAVLNode a = right.getLeft(); // a is the left child of the right child of v
			  int rk_a = a.getHeight();
			  rotation(right, 'r');
			  rotation(v, 'l');
			  a.setHeight(rk_a + 1); // prmote a
			  right.setHeight(rightRank - 1); // demote previously right child of v
			  v.setHeight(r - 2); // demote v twice
			  updateSize(v);
			  updateSize(right);
			  updateSize(a);
			  return 5 + balanceRec(a.getParent()); // a is now the root of this subtree, and we go up
		  }
	  }
	  if(rightRankDiff == 3 && leftRankDiff == 1) {
		  if(rankDiffCalc(left, left.getRight()) == 1 && rankDiffCalc(left, left.getLeft()) == 1) {
			  rotation(v, 'r');
			  v.setHeight(r - 1); // demote v
			  left.setHeight(leftRank + 1); // promote previously left child
			  updateSize(v);
			  updateSize(left);
			  return 3 + balanceRec(left.getParent()); // terminal case but we still have to update the sizes
		  }
		  if(rankDiffCalc(left, left.getRight()) == 2 && rankDiffCalc(left, left.getLeft()) == 1) {
			  rotation(v, 'r');
			  v.setHeight(r - 2); // demote v twice
			  updateSize(v);
			  updateSize(left);
			  return 2 + balanceRec(left.getParent()); // left is now the root of this subtree, and we go up
		  }
		  if(rankDiffCalc(left, left.getRight()) == 1 && rankDiffCalc(left, left.getLeft()) == 2) {
			  IAVLNode b = left.getRight(); // b is the right child of the left child of v
			  int rk_b = b.getHeight();
			  rotation(left, 'l');
			  rotation(v, 'r');
			  b.setHeight(rk_b + 1); // promote b
			  left.setHeight(leftRank - 1); // demote previously left child of v
			  v.setHeight(r - 2); // demote v twice
			  updateSize(v);
			  updateSize(left);
			  updateSize(b);
			  return 5 + balanceRec(b.getParent()); // b is now the root of this subtree, and we go up
		  }
	  }
	  return 0; // never gets here
   }
  private static int rankDiffCalc(IAVLNode x, IAVLNode y) {
	  return x.getHeight() - y.getHeight();
  }
  
  private static void updateSize(IAVLNode v) {
	   v.setSize(v.getLeft().getSize() + v.getRight().getSize() + 1);
  }
  
  private static void rotation(IAVLNode local_root, char direction) {
	  if (direction == 'r') {
		  IAVLNode a = local_root.getLeft(); // "a" is the left child of local_root
		  IAVLNode d = a.getRight(); // "d" is the right child of the left child of local_root
		  // making node "a" to be the child of local_root.getParent() instead of local_root :
		  a.setParent(local_root.getParent());
		  if(local_root.getParent().getRight() == local_root) { // if local_root was right child
			  local_root.getParent().setRight(a); // the right child now is node "a"
		  }
		  else { // local_root was left child
			  local_root.getParent().setLeft(a); // the left child now is node "a"
		  }
		  // making local_root to be the right child of node "a" :
		  local_root.setParent(a);
		  a.setRight(local_root);
		  // making node "d" to be the left child of local_root :
		  d.setParent(local_root);
		  local_root.setLeft(d);
	  }
	  if (direction == 'l') {
		  IAVLNode a = local_root.getRight(); // "a" is the right child of local_root
		  IAVLNode d = a.getLeft(); // "d" is the left child of the right child of local_root
		  // making node "a" to be the child of local_root.getParent() instead of local_root :
		  a.setParent(local_root.getParent());
		  if(local_root.getParent().getRight() == local_root) { // if local_root was right child
			  local_root.getParent().setRight(a); // the right child now is node "a"
		  }
		  else { // local_root was left child
			  local_root.getParent().setLeft(a); // the left child now is node "a"
		  }
		  // making local_root to be the left child of node "a" :
		  local_root.setParent(a);
		  a.setLeft(local_root);
		  // making node "d" to be the right child of local_root :
		  d.setParent(local_root);
		  local_root.setRight(d);
	  }
  }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min()
   {
	   return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   IAVLNode r = root;
	   if(r == null) {
		   return null;
	   }
	   while(r.getRight().isRealNode()) {
		   r = r.getRight();
	   }
	   return r.getValue();
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        return new int[33]; // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
     IAVLNode r = root;
     return sortInfoRec(r);
  }
  
  private String[] sortInfoRec(IAVLNode v) {
	  if(!(v.isRealNode())) {
		  String[] s =  new String[0];
		  return s;
	  }
	  IAVLNode r = v.getRight();
	  IAVLNode l = v.getLeft();
	  int right_size = r.getSize();
	  int left_size = l.getSize();
	  String[] left = sortInfoRec(r);
	  String[] right = sortInfoRec(l);
	  String[] a = new String[right_size + left_size + 1];
	  int i = 0;
	  while(i < left_size) {
		  a[i] = left[i];
		  i++;
	  }
	  a[i] = v.getValue();
	  i += 1;
	  while(i - left_size < right_size) {
		  a[i] = right[i - left_size];
		  i++;
	  }
	  return a;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return root.getSize(); // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot()
   {
	   return root;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return -1;
   }

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
    	public int getSize();
    	public void setSize(int n);
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public class AVLNode implements IAVLNode{
	  private int key;
	  	private String value;
	  	private int height;
	  	private IAVLNode left = null;
	  	private IAVLNode right = null;
	  	private IAVLNode parent = null;
	  	private int size;
	  	  	
	  	public AVLNode(int key, String value, int height, IAVLNode parent, int size) {
	  		this.key = key;
	  		assert (!((key == -1)&&(height != -1)) && !((key != -1)&&(height == -1))); 
	  		this.value = value;
	  		this.height = height;
	  		this.parent = parent;
	  	}
		public int getKey()
		{
			return 423; // to be replaced by student code
		}
		public String getValue()
		{
			return "getValueDefault"; // to be replaced by student code
		}
		public void setLeft(IAVLNode node)
		{
			return; // to be replaced by student code
		}
		public IAVLNode getLeft()
		{
			return null; // to be replaced by student code
		}
		public void setRight(IAVLNode node)
		{
			return; // to be replaced by student code
		}
		public IAVLNode getRight()
		{
			return null; // to be replaced by student code
		}
		public void setParent(IAVLNode node)
		{
			return; // to be replaced by student code
		}
		public IAVLNode getParent()
		{
			return null; // to be replaced by student code
		}
		public boolean isRealNode()
		{
			return true; // to be replaced by student code
		}
	    public void setHeight(int height)
	    {
	      return; // to be replaced by student code
	    }
	    public int getHeight()
	    {
	      return 424; // to be replaced by student code
	    }
	    public int getSize() {
	    	return this.size;
	    }
	    public void setSize(int n) {
	    	this.size = n;
	    }
  }

}
  
