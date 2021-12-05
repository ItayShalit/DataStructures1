/**
 *
 * AVLTree
 *
 * An implementation of a׳� AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	private IAVLNode root = new AVLNode(-1,null,-1,null,0);
	private IAVLNode max_key_node = new AVLNode(-1,null,-1,null,0);
	private IAVLNode min_key_node = new AVLNode(-1,null,-1,null,0);

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
			   root = v; // the parent of v is already null since p = root.getParent() = null
			   // the root was the only real node in the tree, so we have to change max and min to point to the virtual node replacing it
			   max_key_node = v;
			   max_key_node = v;
			   return 0;
		   } // x is not the root :
		   if(p.getRight() == x) {
			   p.setRight(v);
		   }
		   else {
			   p.setLeft(v);
		   }
		   n = balanceRec(p);
		   max_key_node = Select(root, size() - 1);
		   min_key_node = Select(root, 0);
		   return n;
	   }
	   else if(!(r.isRealNode())) { // x has only one child, l
		   if(root == x) {
			   root = l;
			   l.setParent(null);
			   max_key_node = Select(root, size() - 1);
			   min_key_node = Select(root, 0);
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
		   max_key_node = Select(root, size() - 1);
		   min_key_node = Select(root, 0);
		   return n;
	   }
	   else if(!(l.isRealNode())) { // x has only one child, r
		   if(root == x) {
			   root = r;
			   r.setParent(null);
			   max_key_node = Select(root, size() - 1);
			   min_key_node = Select(root, 0);
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
		   max_key_node = Select(root, size() - 1);
		   min_key_node = Select(root, 0);
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
		   max_key_node = Select(root, size() - 1);
		   min_key_node = Select(root, 0);
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
   
   /**
    * precondition : the tree is not empty, and 0 <= i <= size() -1
    */
   private IAVLNode Select(IAVLNode x, int i) { // return the i's element in the tree which has x as a root
	   int r = x.getLeft().getSize();
	   if(i == r) {
		   return x;
	   }
	   else if(i < r) { // there are more than i elements which are smaller than x
		   return Select(x.getLeft(), i); // so the i's element must be in its left subtree
	   }
	   else { // there are less than i elements which are smaller than x
		   return Select(x.getRight(), i - r - 1); // so the i's element is the i-r-1 's element in the right subtree
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
	   IAVLNode r = this.root;
	   IAVLNode t_root = t.getRoot();
	   if(!(r.isRealNode()) && !(t_root.isRealNode())) {
		   this.root = x;
		   x.setSize(1);
		   x.setHeight(0); // x is a leaf
		   x.setParent(null); // x is the root and has no parent
		   IAVLNode v = new AVLNode(-1,null,-1,x,0); // virtual node
		   x.setRight(v);
		   IAVLNode w = new AVLNode(-1,null,-1,x,0); // virtual node
		   x.setLeft(w);
		   return 1;
	   }
	   if(!(r.isRealNode())) { // this is an empty tree but t is not empty
		   t.insert_node(x); // Assuming insert_node updates max_key_node and min_key_node
		   this.root = t.getRoot();
		   this.min_key_node = t.getMinKey();
		   this.max_key_node = t.getMaxKey();
		   return this.root.getHeight() + 1 ;
	   }
	   if(!(t_root.isRealNode())) { // t is empty but this tree is not empty
		   this.insert_node(x); // Assuming insert_node updates max_key_node and min_key_node
		   return this.root.getHeight() + 1;
	   }
	   int t_height = t_root.getHeight();
	   int h = r.getHeight();
	   if(t_height >= h) { // if rank(t) >= this rank
		   IAVLNode y = t_root;
		   if(r.getKey() < x.getKey()) { // if keys(t) > x.getKey() > keys()
			   // finding y such that y.getHeight <= this rank
			   while(y.getHeight() > h) {
				   y = y.getLeft(); 
			   }
			   join_case_one(x,r,y);
		   }
		   else { // rank(t) >= this tree's rank and keys(t) < x < keys()
			   while(y.getHeight() > h) {
				   y = y.getRight();
			   }
			   join_case_two(x,r,y);
		   }
		   x.setHeight(h + 1); // setting the rank of x
		   this.root = t.getRoot(); // t is higher, so the root is now t.root
		   this.rebalance(x);
		   t.max_key_node = this.Select(root, size());
		   t.min_key_node = this.Select(root, 0);
		   return t_height - h + 1 ;
	   }
	   // rank(t) <= this rank :
	   IAVLNode z = r;
	   if(t_root.getKey() < x.getKey()) { // keys(t) < x.getKey() < keys()
		   while(z.getHeight() > t_height) {
			   z = z.getLeft();
		   }
		   join_case_one(x,t_root,z);
	   }
	   else {
		   while(z.getHeight() > t_height) {
			   z = z.getRight();
		   }
		   join_case_two(x,t_root,z);
	   }
	   x.setHeight(t_height + 1); // setting the rank of x
	   this.rebalance(x); // this tree is higher so the root of the joined tree stays this.root
	   t.max_key_node = this.Select(root, size());
	   t.min_key_node = this.Select(root, 0);
	   return h - t_height + 1 ;
   }
   /** 
    * precondition: if b is in tree T and a in tree S then rank(T) >= rank(S) && keys(S) < x.getKey() < keys(T) && b.getHeight() <= a.getHeight()
     */
   private void join_case_one(IAVLNode x, IAVLNode a, IAVLNode b) {
	   IAVLNode c = b.getParent(); 
	  // make b the right child of x
	   b.setParent(x);
	   x.setRight(b);
	   // make a the left child of x :
	   a.setParent(x);
	   x.setLeft(a);
	   // make x the left child of c
	   x.setParent(c);
	   c.setLeft(x);
   }
   /**
    * precondition: if b is in tree T and a is in tree S then rank(T) >= rank(S) && keys(S) > x.getKey() > keys(T) && b.getHeight() <= a.getHeight()
    */
   private void join_case_two(IAVLNode x, IAVLNode a, IAVLNode b) {
	   IAVLNode c = b.getParent();
	   // make b the left child of x :
	   b.setParent(x);
	   x.setLeft(b);
	   // make a the right child of x :
	   a.setParent(x);
	   x.setRight(a);
	   // make x the right child of c :
	   x.setParent(c);
	   c.setRight(x);
   }
   
   public IAVLNode getMinKey() {
	   return this.min_key_node;
   }
   
   public IAVLNode getMaxKey() {
	   return this.max_key_node;
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
  
