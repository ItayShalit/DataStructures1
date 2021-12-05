/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
  private IAVLNode root;
  private IAVLNode minKeyNode = null;
  private IAVLNode maxKeyNode = null;
  
  /**
   * Constructs an empty tree with a virtual node as a root.
   */
  public AVLTree() {
	  self.root = AVLNode(-1, null, -1, null, 0);
  }
  /**
   * Constructs a tree with a specific node as a root.
   */
  public AVLTree(IAVLNode root_node) {
	  this.root = root_node;
	  this.getRoot().setHeight(0);
	  this.getRoot().setRight(AVLNode(-1, null, -1, this.getRoot(), 0));
	  this.getRoot().setLeft(AVLNode(-1, null, -1, this.getRoot(), 0));
	  this.minKeyNode = node;
	  this.maxKeyNode = node;
	  updateSize(root_node);
  }

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
	  return !(this.getRoot().isRealNode());
  }

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k)
  {
	return "searchDefaultString";  // to be replaced by student code
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
	  IAVLNode new_node = AVLNode(k, i, 0, null, 1);
	  return insert_node(new_node);
   
   /**
    * Receives a node, and inserts it into the tree.
    * @param node
    * @pre node.isRealNode()
    * @return -1 if an item with key node.getKey() already exists in the tree, 
    *         otherwise the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
    */
   private int insert_node(IAVLNode node) {
	  node.setRight(new AVLNode(-1, null, -1, r, 0));
	  node.setLeft(new AVLNode(-1, null, -1, r, 0));
	  if (this.isEmpty()) { 
		  this.setRoot(node);
		  this.setHeight(0);
		  this.getRoot().setRight(AVLNode(-1, null, -1, this.getRoot(), 0));
		  this.getRoot().setLeft(AVLNode(-1, null, -1, this.getRoot(), 0));
		  this.minKeyNode = node;
		  this.maxKeyNode = node;
		  updateSize(this.getRoot());
		  return 0; //Does setting the new root's height should be counted as a rebalancing action?
	  }
	  if (this.maxKeyNode().getKey() < node.getKey()) { //Update maxKeyNode field if neccessary. 
		  this.maxKeyNode = node;
	  }
	  if (this.minKeyNode().getKey() > node.getKey()) { //Update minKeyNode field if neccessary. 
		  this.minKeyNode = node;
	  }
	  IAVLNode r = this.getRoot();
	  if (this.is_key_in_tree(r.key)) { //If the key is already in the tree, we return -1 and do not implement changes to the tree.
		  return -1;
	  }
	  while (true) {
		  int current_node_key = r.getKey();
		  if (r.getKey() > k) {
			  if (!(r.getLeft().isRealNode())) {
				  r.setLeft(node);
				  r.getLeft().setParent(r);
				  r = r.getLeft();
				  break;
			  }
			  r = r.getLeft();
		  }
		  if (r.getKey() < k) {
			  if (!(r.getRight().isRealNode())) {
				  r.setRight(node);
				  r.getRight().setParent(r);
				  r = r.getRight();
				  break;
			  }
			  r = r.getRight();
		  }
	  }
	  new_node_pointer = r;
	  while(r != null) { //Update node sizes from bottom to top.
		  updateSize(r);
		  r = r.getParent();
	  }
	  return rebalance(new_node_pointer);
  /**
   * Receives a node in the tree and rebalances the tree from the node and up.
   * @param node - A node within the tree.
   * @return The number of rebalancing actions needed.
   */
  private int rebalance(IAVLNode node) {
	  int right_rank_difference = 0;
	  int left_rank_difference = 0;
	  int rebalancing_action_counter = 0;
	  while(node.getParent() != null) {
		  node = node.getParent();
		  right_rank_difference = node.getHeight() - node.getRight().getHeight(); 
		  left_rank_difference = node.getHeight() - node.getLeft().getHeight(); 
		  boolean one_and_two_state = (right_rank_difference == 1)&&(left_rank_difference == 2);
		  boolean two_and_one_state = (right_rank_difference == 2)&&(left_rank_difference == 1);
		  boolean one_and_one_state = (right_rank_difference == 1)&&(left_rank_difference == 1);
		  boolean zero_and_two_state = (right_rank_difference == 0)&&(left_rank_difference == 2);
		  boolean two_and_zero_state = (right_rank_difference == 2)&&(left_rank_difference == 0);
		  if (one_and_two_state||two_and_one_state||one_and_one_state) { //Tree is fixed.
			  break;
		  }
		  if (right_rank_difference + left_rank_difference == 1){ //If one rank difference is 1 and the other is 0.
			  node.setHeight(node.getHeight() + 1);
			  rebalancing_action_counter += 1;
			  continue;
		  }
		  if (two_and_zero_state) {
			  if (node.getLeft().getHeight() - node.getLeft().getRight().getHeight() == 2){
				  node.getLeft().setHeight(node.getLeft().getHeight() + 1);
				  rotation(node, 'r'); //Making a single rotation, and afterwards changing ranks accordingly.
				  updateSize(node);
				  node = node.getParent();
				  updateSize(node);
				  rebalancing_action_counter += 2; //One for rotation and one for rank update.
			  }
			  else { //If r.getLeft().getHeight() - r.getLeft().getRight().getHeight() == 1
				  rotation(node.getLeft(), 'l'); //Making a double rotation, and afterwards changing ranks accordingly.
				  rotation(node, 'r');
				  node.setHeight(node.getHeight() - 1);
				  updateSize(node);
				  node.getParent().setHeight(node.getParent().getHeight() + 1);
				  updateSize(node.getParent());
				  node.getParent().getLeft().setHeight(node.getParent().getLeft().getHeight() - 1);
				  updateSize(node.getParent().getLeft());
				  rebalancing_action_counter += 4; //One for rotation and three for rank updates.
			  }	  
			  break;
		  }
		  if (zero_and_two_state) {
			  if (node.getRight().getHeight() - node.getRight().getLeft().getHeight() == 2){
				  node.getRight().setHeight(node.getRight().getHeight() + 1);
				  rotation(node, 'l'); //Making a single rotation, and afterwards changing ranks accordingly.
				  updateSize(node);
				  node = node.getParent();
				  updateSize(node);
				  rebalancing_action_counter += 2; //One for rotation and one for rank update.
			  }
			  else { //If r.getRight().getHeight() - r.getRight().getLeft().getHeight() == 1
				  rotation(node.getRight(), 'r'); //Making a double rotation, and afterwards changing ranks accordingly.
				  rotation(node, 'l');
				  node.setHeight(node.getHeight() - 1);
				  updateSize(node);
				  node.getParent().setHeight(node.getParent().getHeight() + 1);
				  updateSize(node.getParent());
				  node.getParent().getLeft().setHeight(node.getParent().getLeft().getHeight() - 1);
				  updateSize(node.getParent().getRight());
				  rebalancing_action_counter += 4; //One for rotation and three for rank updates.
			  }	
			  break;
		  }
	  }
	  return rebalancing_action_counter;
   }

  /**
   * 
   * @param key - the key that is to be checked whether it is in the tree (as a node key).
   * @return A boolean value, referring to whether the key that was passed is in the tree.
   */
  private boolean is_key_in_tree(int key) {
	  IAVLNode r = this.getRoot();
	  while (true) {
		  if (!(r.isRealNode())) {
			  return false;
		  }
		  if (r.getKey() == k) {
			  return true;
		  }
		  if (r.getKey() > k) {
			  r = r.getLeft();			  
		  }
		  if (r.getKey() < k) {
			  r = r.getRight();
		  }
	  }
  }
  
  private void rotation(IAVLNode local_root, char direction) {
	  if (direction == 'r') {
		  local_root.getLeft().setParent(local_root.getParent());
		  local_root.setParent(local_root.getLeft());
		  local_root.setLeft(local_root.getParent().getRight());
		  local_root.getParent().setRight(local_root);
	  }
	  if (direction == 'l') {
		  local_root.getRight().setParent(local_root.getParent());
		  local_root.setParent(local_root.getRight());
		  local_root.setRight(local_root.getParent().getLeft());
		  local_root.getParent().setLeft(local_root);
	  }
  }
  
  /**
   * Defines a real - node root for the tree, assuming that it is empty. 
   * @pre !(this.getRoot().isRealNode()); 
   */
  private void setRoot(IAVLNode new_root) {
	  this.root = new_root;
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
	   return 421;	// to be replaced by student code
   }  
   
  /**
   * public String min()
   *
   * Returns the info of the item with the smallest key in the tree,
   * or null if the tree is empty.
   * 
   */
  public String min()
  {
	   return this.minKeyNode().getValue();
  }
     
   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return this.maxKeyNode().getValue();
   }
   

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {	 
	  IAVLNode r = this.getRoot();
	  return sorted_key_array(r);
  }
  
  /**
   * @param A node in the tree.
   * @return A sorted array of the keys in it's subtree. 
   * Used as a util for keysToArray method.
   */
  private int[] sortedKeyArray(IAVLNode r) {
	  if (!(r.isRealNode())){ //If we reached a virtual node, we return an empty array.
		  return new int[0] ;
	  }
	  int[] left_subtree_keys = sorted_key_array(r.getLeft());
	  int[] right_subtree_keys = sorted_key_array(r.getRight());
	  int new_array_size = 1 + left_subtree_keys.length + right_subtree_keys.length;
	  int[] subtree_keys = new int[new_array_size];
	  for (int i = 0; i < left_subtree_keys.length; i++) {
		  subtree_keys[i] = left_subtree_keys[i];
	  }
	  subtree_keys[left_subtree_keys.length] = r.getKey();
	  for (int i = 0; i < right_subtree_keys.length; i++) {
		  subtree_keys[i + left_subtree_keys.length + 1] = right_subtree_keys[i];
	  }
	  return subtree_keys;
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
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size()
   {
	   return root.getSize();
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   
   public IAVLNode getRoot()
   {
	   return null;
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
	    IAVLNode r = this.getRoot();
	    while(r.getKey() != x) { //Navigating to the node with key x.
	    	if (r.getKey() > x) {
	    		r = r.getLeft();
	    	}
	    	else {
	    		r = r.getRight();
	    	}
	    }
	    AVLTree larger_keys_tree = new AVLTree(r.getRight()); //Creates the tree of nodes with key > x
	    AVLTree smaller_keys_tree =	new AVLTree(r.getLeft()); //Creates the tree of nodes with key < x
	    larger_keys_tree.getRoot().setParent(null);
	    smaller_keys_tree.getRoot().setParent(null);
	    IAVLNode parent = r.getParent();
	    while(parent != null) {
	    	grandmother = parent.getParent();
    		r.setParent(grandmother);
    		parent.setParent(null);
    		if (grandmother != null) { //Creating a direct connection between r and the grandmother, because the parent moves to one of the new trees.
	    		if (grandmother.getRight() == parent){
	    			grandmother.setRight(r);
	    		}
	    		else {
	    			grandmother.setLeft(r);
	    		}
    		}
    		IAVLNode join_with;
	    	if (parent.getRight() == r) { //If we came up to parent from the right
	    		parent.getLeft().setParent(null);
	    		join_with = parent.getLeft();
	    		parent.setLeft(null);
	    		parent.setRight(null);
	    		smaller_keys_tree.join(parent, join_with);
	    	}
	    	else { //If we came up from the left
	    		parent.getRight().setParent(null);
	    		join_with = parent.getRight();
	    		parent.setLeft(null);
	    		parent.setRight(null);
	    		larger_keys_tree.join(parent, join_with);
	    	}
	    	parent = grandmother;
	    }
	    AVLTree[] result = {smaller_keys_tree, larger_keys_tree};
	    return result;
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
	   return null;
   }
   
   private static void print2DUtil(IAVLNode node, int space)
   {
		int COUNT = 10;
	    // Base case
	    if (!(node.IsRealNode()){
	    	System.out.print("\n");
		    for (int i = COUNT; i < space; i++)
		        System.out.print(" ");
		    System.out.print(node.getKey() + "\n");
		    return;
	    }
	 
	    // Increase distance between levels
	    space += COUNT;
	 
	    // Process right child first
	    print2DUtil(node.getRight(), space);
	 
	    // Print current node after space
	    // count
	    System.out.print("\n");
	    for (int i = COUNT; i < space; i++)
	        System.out.print(" ");
	    System.out.print(node.getKey() + "\n");
	 
	    // Process left child
	    print2DUtil(root.getLeft(), space);
	}
	 
	// Wrapper over print2DUtil()
	public static void print2D()
	{
	    // Pass initial space count as 0
	    print2DUtil(this.getRoot, 0);
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
	  	
	  	  	
	  	public AVLNode(int key, String value, int height, IAVLNode parent) {
	  		this.key = key;
	  		assert (!((key == -1)&&(height != -1)) && !((key != -1)&&(height == -1)) && !((key == -1)&&(height != 0))); 
	  		this.value = value;
	  		this.height = height;
	  		this.parent = parent;
	  	}
	  	
		public int getKey(){
			return this.key;
		}
		
		public String getValue()
		{
			if (this.key == -1) {
				return null;
			}
			return this.value; // to be replaced by student code
		}
		public void setLeft(IAVLNode node)
		{
			this.left = node;
			updateSize(this);
		}
		public IAVLNode getLeft()
		{
			return this.left; 
		}
		public void setRight(IAVLNode node)
		{
			this.right = node;
			updateSize(this);
		}
		public IAVLNode getRight()
		{
			return this.right; 
		}
		public void setParent(IAVLNode node)
		{
			this.parent = node;
		}
		public IAVLNode getParent()
		{
			return this.parent;
		}
		public boolean isRealNode()
		{
			return this.key != -1; 
		}
	    public void setHeight(int height)
	    {
	    	this.height = height;
	    }
	    public int getHeight()
	    {
	      return this.height;
	    }
  }

}
  
