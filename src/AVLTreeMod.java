import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Dictionary;
import java.util.HashSet; //Remove before hand in
import java.util.Set; //Remove before hand in


/**
 *
 * AVLTree
 *
 * An implementation of aמ AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTreeMod {
  private IAVLNode root;
  private IAVLNode minKeyNode = null;
  private IAVLNode maxKeyNode = null;
  
  /**
   * Constructs an empty tree with a virtual node as a root.
   */
  public AVLTreeMod() {
	  this.root = new AVLNode(-1, null, -1, null, 0);
  }
  
  /**
   * Constructs a tree with a specific real node as a root.
   * @pre root_node.isRealNode().
   */
  public AVLTreeMod(IAVLNode root_node) {
	  if (root_node == null) {
		  root_node = new AVLNode(-1, null, -1, null, 0);
	  }
	  this.root = root_node;
	  if (root_node.isRealNode()) {
		  if (!(root_node.getRight().isRealNode()) && !(root_node.getLeft().isRealNode())) {
			  this.getRoot().setHeight(0);
			  this.minKeyNode = root_node;
			  this.maxKeyNode = root_node;
			  updateSize(root_node);
		  }
		  else {
			  updateSize(root_node);
			  this.minKeyNode = this.Select(this.root, 0);
			  this.maxKeyNode = this.Select(this.root, this.root.getSize() - 1);
		  }
		  this.getRoot().setParent(null);
	  }
  }

  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
  public boolean empty() {
	  return (this.getRoot() == null);
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
    if (x != null) {
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
	  IAVLNode new_node = new AVLNode(k, i, 0, null, 1);
	  return insert_node(new_node);
   }
   
   /**
    * Receives a node, and inserts it into the tree.
    * @param node
    * @pre node.isRealNode()
    * @return -1 if an item with key node.getKey() already exists in the tree, 
    *         otherwise the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
    */
   public int insert_node(IAVLNode node) {
	  
	  node.setHeight(0);
	  node.setRight(new AVLNode(-1, null, -1, node, 0));
	  node.setLeft(new AVLNode(-1, null, -1, node, 0));
	  updateSize(node);
	  if (this.empty()) { 
		  this.setRoot(node);
		  this.minKeyNode = node;
		  this.maxKeyNode = node;
		  return 0; 
	  }
	  if (this.maxKeyNode.getKey() < node.getKey()) { //Update maxKeyNode field if neccessary. 
		  this.maxKeyNode = node;
	  }
	  if (this.minKeyNode.getKey() > node.getKey()) { //Update minKeyNode field if neccessary. 
		  this.minKeyNode = node;
	  }
	  if (this.search_node_by_key(node.getKey()) != null) { //If the key is already in the tree, we return -1 and do not implement changes to the tree.
		  return -1;
	  }
	  
	  IAVLNode r = this.maxKeyNode;
	  int counter = 0;
	  while(r.getKey() > node.getKey()) {
		  counter++;
		  if ((r.getParent() != null) && (r.getParent().getRight() == r)) {
			  r = r.getParent(); 
              continue;
		  }
          else {
        	  break; //go to left subtree of r with a regular insert.
          }
	  }
	  IAVLNode parent = r;
	  if (r.getKey() <= node.getKey()) { //else ((r.getParent() == null) || (r.getParent().getLeft() == r))
		  r = r.getRight(); //go the to left subtree of r's right son with a regular insert, unless the right son does not exist, and then set as right son.
	  }
	  if (!(r.isRealNode())) {
		  counter++;
		  node.setParent(parent);
		  parent.setRight(node);
		  r.setParent(null);
	  }
	  else { //go to left subtree of r with a regular insert.  
		  while(true) {
			  counter++;
			  if (node.getKey() > r.getKey()) {
				  if (r.getRight().isRealNode()) {
					  r = r.getRight();
					  continue;
				  }
				  else { 
					  counter++;
					  r.setRight(node);
					  node.setParent(r);
					  r = r.getRight();
				  }
			  }
			  else { //node.getKey() < r.getKey()
				  if (r.getLeft().isRealNode()) {
					  r = r.getLeft();
					  continue;
				  }
				  else {
					  counter++;
					  r.setLeft(node);
					  node.setParent(r);
					  r = r.getLeft();
				  }
			  }
		  }  
	  }
	  while(r != null) { //Update node sizes from bottom to top.
		  updateSize(r);
		  r = r.getParent();
	  }
	  rebalance(node);
	  return counter;
   }
	  
	  
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
			  while (node != null) { //Update size attribute all the way to the tree root.
				  updateSize(node);
				  node = node.getParent();
			  }
			  break;
			  
		  }
		  else if (right_rank_difference + left_rank_difference == 1){ //If one rank difference is 1 and the other is 0.
			  node.setHeight(node.getHeight() + 1);
			  rebalancing_action_counter += 1;
		  }
		  else if (two_and_zero_state) {
			  if ((node.getLeft().getHeight() - node.getLeft().getRight().getHeight() == 2)&&
			     (node.getLeft().getHeight() - node.getLeft().getLeft().getHeight() == 1)){
				  node.setHeight(node.getHeight() - 1);
				  rotation(node, 'r'); //Making a single rotation, and afterwards changing ranks accordingly.
				  updateSize(node);
				  updateSize(node.getParent());
				  rebalancing_action_counter += 3; //One for rotation and one for rank update.
			  }
			  else if ((node.getLeft().getHeight() - node.getLeft().getRight().getHeight() == 1)&&
				  (node.getLeft().getHeight() - node.getLeft().getLeft().getHeight() == 2)) { //If node.getLeft().getHeight() - node.getLeft().getRight().getHeight() == 1
				  rotation(node.getLeft(), 'l'); //Making a double rotation, and afterwards changing ranks accordingly.
				  rotation(node, 'r');
				  node.setHeight(node.getHeight() - 1);
				  updateSize(node);
				  node.getParent().setHeight(node.getParent().getHeight() + 1);
				  updateSize(node.getParent());
				  node.getParent().getLeft().setHeight(node.getParent().getLeft().getHeight() - 1);
				  updateSize(node.getParent().getLeft());
				  rebalancing_action_counter += 5; //One for each rotation and three for rank updates.
			  }	
			  else if ((node.getLeft().getHeight() - node.getLeft().getRight().getHeight() == 1)&&
				  (node.getLeft().getHeight() - node.getLeft().getLeft().getHeight() == 1)) {
				  node.getLeft().setHeight(node.getLeft().getHeight() + 1);
				  rotation(node, 'r');
				  updateSize(node);
				  updateSize(node.getParent());	
				  rebalancing_action_counter += 3; //One for rotation and one for rank update.
			  }
		  }
		  else if (zero_and_two_state) {
			  if ((node.getRight().getHeight() - node.getRight().getLeft().getHeight() == 2)&&
				 (node.getRight().getHeight() - node.getRight().getRight().getHeight() == 1)){
				  node.setHeight(node.getHeight() - 1);
				  rotation(node, 'l'); //Making a single rotation, and afterwards changing ranks accordingly.
				  updateSize(node);
				  updateSize(node.getParent());
				  rebalancing_action_counter += 3; //One for rotation and one for rank update.
			  }
			  else if((node.getRight().getHeight() - node.getRight().getLeft().getHeight() == 1)&&
				 (node.getRight().getHeight() - node.getRight().getRight().getHeight() == 2)) {
				  rotation(node.getRight(), 'r'); //Making a double rotation, and afterwards changing ranks accordingly.
				  rotation(node, 'l');
				  node.setHeight(node.getHeight() - 1);
				  updateSize(node);
				  node.getParent().setHeight(node.getParent().getHeight() + 1);
				  updateSize(node.getParent());
				  node.getParent().getLeft().setHeight(node.getParent().getLeft().getHeight() - 1);
				  updateSize(node.getParent().getRight());
				  rebalancing_action_counter += 5; //One for each rotation and three for rank updates.
			  }	
			  else if ((node.getRight().getHeight() - node.getRight().getLeft().getHeight() == 1)&&
				 (node.getRight().getHeight() - node.getRight().getRight().getHeight() == 1)) {
				  node.getRight().setHeight(node.getRight().getHeight() + 1);
				  rotation(node, 'l');
				  updateSize(node);
				  updateSize(node.getParent());	
				  rebalancing_action_counter += 3; //One for rotation and one for rank update.
			  }
		  }
	  }
	  return rebalancing_action_counter;
   }

  /**
   * 
   * @param key - the key that is to be checked whether it is in the tree (as a node key).
   * @return A boolean value, referring to whether the key that was passed is in the tree.
   */
  public IAVLNode search_node_by_key(int key) {
	  IAVLNode r = this.getRoot();
	  while (true) {
		  if (!(r.isRealNode())) {
			  return null;
		  }
		  if (r.getKey() == key) {
			  return r;
		  }
		  if (r.getKey() > key) {
			  r = r.getLeft();			  
		  }
		  else{
			  if (r.getKey() < key) {
				  r = r.getRight();
			  }
		  }
	  }
  }
  
  private static int rankDiffCalc(IAVLNode x, IAVLNode y) {
	  return x.getHeight() - y.getHeight();
  }
  
  private static void updateSize(IAVLNode v) {
	  int leftsize = 0;
	  int rightsize = 0;
	  if (v != null) {
		  if (v.getLeft() != null) { //We call this method when updating the sons of a node. Therefore, one of them may be null.
			  leftsize = v.getLeft().getSize(); 
		  }
		  if (v.getRight() != null) {
			  rightsize = v.getRight().getSize();
		  }
		  v.setSize(leftsize + rightsize + 1);
	  }
  }
  
  private void rotation(IAVLNode local_root, char direction) {
	  if (direction == 'r') {
		  if (local_root == this.getRoot()) {
			  this.setRoot(local_root.getLeft());
		  }
		  IAVLNode a = local_root.getLeft(); // "a" is the left child of local_root
		  IAVLNode d = a.getRight(); // "d" is the right child of the left child of local_root
		  // making node "a" to be the child of local_root.getParent() instead of local_root :
		  a.setParent(local_root.getParent());
		  if (local_root.getParent() != null) {
			  if(local_root.getParent().getRight() == local_root) { // if local_root was right child
				  local_root.getParent().setRight(a); // the right child now is node "a"
			  }
			  else { // local_root was left child
				  local_root.getParent().setLeft(a); // the left child now is node "a"
			  }
		  }
		  // making local_root to be the right child of node "a" :
		  local_root.setParent(a);
		  a.setRight(local_root);
		  // making node "d" to be the left child of local_root :
		  d.setParent(local_root);
		  local_root.setLeft(d);
	  }
	  if (direction == 'l') {
		  if (local_root == this.getRoot()) {
			  this.setRoot(local_root.getRight());
		  }
		  IAVLNode a = local_root.getRight(); // "a" is the right child of local_root
		  IAVLNode d = a.getLeft(); // "d" is the left child of the right child of local_root
		  // making node "a" to be the child of local_root.getParent() instead of local_root :
		  a.setParent(local_root.getParent());
		  if (local_root.getParent() != null) {
			  if(local_root.getParent().getRight() == local_root) { // if local_root was right child
				  local_root.getParent().setRight(a); // the right child now is node "a"
			  }
			  else { // local_root was left child
				  local_root.getParent().setLeft(a); // the left child now is node "a"
			  }
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
	   if (this.empty()) {
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
		   AVLNode v = new AVLNode(-1,null,-1,p,0); // virtual node
		   if(root == x) {
			   root = v; // the parent of v is already null since p = root.getParent() = null
			   // the root was the only real node in the tree, so we have to change max and min to point to the virtual node replacing it
			   IAVLNode maxKeyNode = v;
			   IAVLNode minKeyNode = v;
			   return 0;
		   } // x is not the root :
		   if(p.getRight() == x) {
			   p.setRight(v);
		   }
		   else {
			   p.setLeft(v);
		   }
		   n = balanceRec(p);
		   maxKeyNode = Select(root, size() - 1);
		   minKeyNode = Select(root, 0);
		   return n;
	   }
	   else if(!(r.isRealNode())) { // x has only one child, l		   
		   if(root == x) {
			   root = l;
			   l.setParent(null);
			   maxKeyNode = Select(root, size() - 1);
			   minKeyNode = Select(root, 0);
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
		   maxKeyNode = Select(root, size() - 1);
		   minKeyNode = Select(root, 0);
		   return n;
	   }
	   else if(!(l.isRealNode())) { // x has only one child, r	   
		   if(root == x) {
			   root = r;
			   r.setParent(null);
			   maxKeyNode = Select(root, size() - 1);
			   minKeyNode = Select(root, 0);
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
		   maxKeyNode = Select(root, size() - 1);
		   minKeyNode = Select(root, 0);
		   return n;
		   }
	   
	   else { // x has two children	   
		   int h = x.getHeight();
		   int s = x.getSize() - 1;
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
		   if (r != y) { //Otherwise, y is the right descendant of x, with no left descendant, 
			             //and therefore we don't need to change pointers.
			   y.setRight(r);
			   r.setParent(y);
		   }
		   y.setLeft(l);
		   l.setParent(y);
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
		   maxKeyNode = Select(root, size() - 1);
		   minKeyNode = Select(root, 0);
		   return n;
	   	 }
	  }
	   
   
	   public IAVLNode findSuccessor(IAVLNode v) {
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
		   while(x.getLeft().isRealNode()) { //Get to the node with the minimal key in the subtree of x.
			   x = x.getLeft();
		   }
		   while (x.getSize() < i) { //Get to a subtree the has an O(i) leaves, and contains the i smallest leaves of the subtree.
			   x = x.getParent();
		   }
		   return SelectRecurseUtil(x, i);
	   }
	  
	   private IAVLNode SelectRecurseUtil(IAVLNode x, int i) {
		   int r;
		   if (x.getLeft() == null) {
			   r = 0;
		   }
		   else {
			   r = x.getLeft().getSize();
		   }
		   if(i == r) {
			   return x;
		   }
		   else if(i < r) { // there are more than i elements which are smaller than x
			   return SelectRecurseUtil(x.getLeft(), i); // so the i's element must be in its left subtree
		   }
		   else { // there are less than i elements which are smaller than x
			   return SelectRecurseUtil(x.getRight(), i - r - 1); // so the i's element is the i-r-1 's element in the right subtree
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
  /**
   * public String min()
   *
   * Returns the info of the item with the smallest key in the tree,
   * or null if the tree is empty.
   * 
   */
  public String min()
  {
	   return this.minKeyNode.getValue();
  }
     
   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max()
   {
	   return this.maxKeyNode.getValue();
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
	  return sortedKeyArray(r);
  }
  
  /**
   * @param A node in the tree.
   * @return A sorted array of the keys in it's subtree. 
   * Used as a util for keysToArray method.
   */
  private int[] sortedKeyArray(IAVLNode r) {
	  if (r != null) {
		  if (!(r.isRealNode())){ //If we reached a virtual node, we return an empty array.
			  int[] empty_array = {};
			  return empty_array;
		  }
		  int[] left_subtree_keys = sortedKeyArray(r.getLeft());
		  int[] right_subtree_keys = sortedKeyArray(r.getRight());
		  int new_array_size = r.getSize();  
		  int[] subtree_keys = new int[new_array_size];
		  for (int i = 0; i < r.getLeft().getSize(); i++) {
			  subtree_keys[i] = left_subtree_keys[i];
		  }
		  subtree_keys[r.getLeft().getSize()] = r.getKey();
		  for (int i = 0; i < r.getRight().getSize(); i++) {
			  subtree_keys[i + r.getLeft().getSize() + 1] = right_subtree_keys[i];
		  }
		  return subtree_keys;
	  }
	  else {
		  int[] empty_array = {};
		  return empty_array;
	  }
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
	  IAVLNode r = this.getRoot();
	  return sortInfoRec(r);
  }
  
  /**
   * @param A node in the tree.
   * @return A sorted array of the values in it's subtree. 
   * Used as a util for infoToArray method.
   */
  private String[] sortInfoRec(IAVLNode r) {
	  if (r != null) {
		  if (!(r.isRealNode())){ //If we reached a virtual node, we return an empty array.
			  String[] empty_array = {};
			  return empty_array;
		  }
	  
		  String[] left_subtree_values = sortInfoRec(r.getLeft());
		  String[] right_subtree_values = sortInfoRec(r.getRight());
		  int new_array_size = r.getSize(); 
		  String[] subtree_values = new String[new_array_size];
		  for (int i = 0; i < r.getLeft().getSize(); i++) {
			  subtree_values[i] = left_subtree_values[i];
		  }
		  subtree_values[r.getLeft().getSize()] = r.getValue();
		  for (int i = 0; i < r.getRight().getSize(); i++) {
			  subtree_values[i + r.getLeft().getSize() + 1] = right_subtree_values[i];
		  }
		  return subtree_values;
	  }
	  else {
		  String[] empty_array = {};
		  return empty_array;
	  }
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
	   if (!(root.isRealNode())){
		   return null;
	   }
	   return this.root;
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
	    IAVLNode r = this.search_node_by_key(x);
	    AVLTree larger_keys_tree = new AVLTree(r.getRight()); //Creates the tree of nodes with key > x
	    AVLTree smaller_keys_tree =	new AVLTree(r.getLeft()); //Creates the tree of nodes with key < x
	    IAVLNode parent = r.getParent();
	    IAVLNode grandmother;
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
	    		smaller_keys_tree.join(parent, new AVLTree(join_with));
	    	}
	    	else { //If we came up from the left
	    		parent.getRight().setParent(null);
	    		join_with = parent.getRight();
	    		parent.setLeft(null);
	    		parent.setRight(null);
	    		larger_keys_tree.join(parent, new AVLTree(join_with));
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
	   IAVLNode r = this.root;
	   IAVLNode t_root = t.getRoot();
	   if (((t == null)||t.empty())) { // t is empty.
		   this.insert_node(x); // Assuming insert_node updates maxKeyNode and minKeyNode
		   return this.root.getHeight() + 1;
	   }
	   else if(this.empty()) { // this is an empty tree but t is not empty
		   t.insert_node(x); // Assuming insert_node updates maxKeyNode and minKeyNode
		   this.root = t.getRoot();
		   this.minKeyNode = t.getMinKey();
		   this.maxKeyNode = t.getMaxKey();
		   return this.root.getHeight() + 1 ;
	   }
	   int t_height = t_root.getHeight(); //Height of the tree to be joined to this tree.
	   int h = r.getHeight(); //Height of this tree.
	   if(t_height >= h) { // if rank(t) >= this rank
		   IAVLNode y = t_root;
		   if(r.getKey() < x.getKey()) { // if keys(t) > x.getKey() > keys()
			   // finding y such that y.getHeight <= this rank
			   while(y.getHeight() > h) {
				   y = y.getLeft(); 
			   }
			   join_case_one(x,r,y);
			   this.maxKeyNode = t.getMaxKey();
			   this.minKeyNode = this.getMinKey();
		   }
		   else { // rank(t) >= this tree's rank and keys(t) < x < keys()
			   while(y.getHeight() > h) {
				   y = y.getRight();
			   }
			   join_case_two(x,r,y);
			   this.maxKeyNode = this.getMaxKey();
			   this.minKeyNode = t.getMinKey();
		   }
		   x.setHeight(h + 1); // setting the rank of x
		   if (t_height != h) { //Else, x was alredy defined as the root.
			   this.root = t.getRoot(); // t is higher, so the root is now t.root
		   }
		   this.rebalance(x);
		   return t_height - h + 1 ;
	   }
	   // rank(t) <= this rank :
	   IAVLNode z = r;
	   if(t_root.getKey() < x.getKey()) { // keys(t) < x.getKey() < keys()
		   while(z.getHeight() > t_height) {
			   z = z.getLeft();
		   }
		   join_case_one(x,t_root,z);
		   this.maxKeyNode = t.getMaxKey();
		   this.minKeyNode = this.getMinKey();
	   }
	   else {
		   while(z.getHeight() > t_height) {
			   z = z.getRight();
		   }
		   join_case_two(x,t_root,z);
		   this.maxKeyNode = this.getMaxKey();
		   this.minKeyNode = t.getMinKey();
	   }
	   x.setHeight(t_height + 1); // setting the rank of x
	   
	   
	   this.rebalance(x); // this tree is higher so the root of the joined tree stays this.root
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
	   if (c != null) { //If b is not a root.
		   // make x the left child of c :
		   c.setLeft(x);
	   }
	   else {
		   this.setRoot(x);
	   }
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
	   x.setParent(c);
	   if (c != null) { //If b is not a root.
		   // make x the right child of c :
		   c.setRight(x);
	   }
	   else {
		   this.setRoot(x);
	   }
   }
   
   public IAVLNode getMinKey() {
	   return this.minKeyNode;
   }
   
   public IAVLNode getMaxKey() {
	   return this.maxKeyNode;
   }
   /*
   public boolean double_parent_tester(IAVLNode root) {
	   
   }
   
   public Dictionary<Integer> double_parent_tester_util(IAVLNode root) {
	   
   }
   */
	private static AVLNode EXTERNAL_NODE = new AVLNode(-1, null, -1, null, 0);
	
	public void bfs_print(){
		IAVLNode v = this.root;
		int height = v.getHeight();
		IAVLNode[][] table = new IAVLNode[height+1][(int) Math.pow(2,height)];

		Queue<IAVLNode> q = new ArrayDeque<>();


		q.add(v);

		for (int h=0; h <= height; h++){
			int levelsize = q.size();
			for (int i=0; i<levelsize; i++){
				v = q.remove();
				table[h][i] = v;


				if (v.isRealNode() && v.getLeft().isRealNode())
					q.add(v.getLeft());
				else{
					q.add(EXTERNAL_NODE);
				}
				if (v.isRealNode() && v.getRight().isRealNode())
					q.add(v.getRight());
				else{
					q.add(EXTERNAL_NODE);
				}

			}
		}
		IAVLNode[][] alignedtable = this.aligningPrintTable(table);
		String[][] treetable = this.makeTreeAlike(alignedtable);
		printtreetable(treetable);
	}


	private IAVLNode[][] aligningPrintTable (IAVLNode[][] table){
		int height = this.root.getHeight();
		IAVLNode[][] alignedtable = new IAVLNode[height+1][2*((int) Math.pow(2,height))-1];
		for (int i=0; i<alignedtable.length; i++)
			for (int j=0; j<alignedtable[0].length; j++)
				alignedtable[i][j] = null;


		for (int r=height; r>=0; r--){
			if (r == height){
				for (int i=0; i<table[0].length; i++)
					alignedtable[r][i*2] = table[r][i];
			} else {

				int firstloc = 0;
				int secondloc = 0;
				boolean firstNodeSeen = false;
				int currnode = 0;

				for (int j=0; j<alignedtable[0].length; j++){
					if (alignedtable[r+1][j] != null){
						if (firstNodeSeen){
							secondloc = j;
							alignedtable[r][(firstloc+secondloc)/2] = table[r][currnode++];
							firstNodeSeen = false;
						} else {
							firstloc = j;
							firstNodeSeen = true;
						}
					}
				}
			}
		}

		return alignedtable;
	}

	private String[][] makeTreeAlike (IAVLNode[][] alignedtable){
		int height = this.root.getHeight();
		String[][] treetable = new String[(height+1)*3-2][2*((int) Math.pow(2,height))-1];

		for (int r=0; r<treetable.length; r++){
			if (r%3 == 0){
				for (int j=0; j<treetable[0].length; j++) {
					IAVLNode v = alignedtable[r/3][j];
					if (v != null && v.isRealNode()) {
						String k = "" + v.getKey();
						if (k.length() == 1)
							k = k + " ";
						treetable[r][j] = k;
					} else{
						if (v != null)
							treetable[r][j] = "x ";
						else
							treetable[r][j] = "  ";
					}
				}
			}

			else {
				if (r%3 == 1) {
					for (int j=0; j<treetable[0].length; j++){
						if (!treetable[r-1][j].equals("  "))
							treetable[r][j] = "| ";
						else
							treetable[r][j] = "  ";
					}
				} else { //r%3 == 2
					continue;
				}
			}
		}
		for (int r=0; r<treetable.length; r++){
			if (r%3 == 2){
				boolean write = false;
				for (int j=0; j<treetable[0].length; j++){
					if (!treetable[r+1][j].equals("  ")){
						if (write)
							treetable[r][j] = "__";
						write = !write;
					}
					if (write)
						treetable[r][j] = "__";
					else
						treetable[r][j] = "  ";
				}
			}
		}



		return treetable;
	}

	private void printtreetable (String[][] treetable){
		for (int i=0; i< treetable.length; i++){
			for (int j=0; j< treetable[0].length; j++){
				System.out.print(treetable[i][j]);
				if (j == treetable[0].length-1)
					System.out.print("\n");
			}
		}
	}

	public static boolean isTreeConsistent(IAVLNode root) {
			if (containsLoops(root, new HashSet<Integer>())) {
				System.out.println("Loop");
				return false;
			}
			boolean ret = true;
			if (root.getLeft().isRealNode()) {
				if (root.getLeft().getParent() != root) {
					System.out.println(root.getKey() + " Left son.");
					return false;
				}
				ret = ret && isTreeConsistent(root.getLeft());
			}
			if (root.getRight().isRealNode()) {
				if (root.getRight().getParent() != root) {
					System.out.println(root.getKey() + " Right son.");
					return false;
				}
				ret = ret && isTreeConsistent(root.getRight());
			}
			return ret;
	}

	public static boolean containsLoops(IAVLNode cur, Set<Integer> set) {
			if (cur == null)
				return false;
			if (!cur.isRealNode())
				return false;
			if (set.contains(cur.getKey()))
				return true;
			set.add(cur.getKey());
			boolean leftLoops = containsLoops(cur.getLeft(), set);
			boolean rightLoops = containsLoops(cur.getRight(), set);
			return leftLoops || rightLoops;
	}

	
	
	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public static interface IAVLNode{	
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
    	public void printHeights();
    	public void printSizes();
	}

   /** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
  public static class AVLNode implements IAVLNode{
	  	private int key;
	  	private String value;
	  	private int height;
	  	private int size;
	  	private IAVLNode left = null;
	  	private IAVLNode right = null;
	  	private IAVLNode parent = null;
	  	public AVLNode(int key, String value, int height, IAVLNode parent, int size) {
	  		this.key = key;
	  		assert (!((key == -1)&&(height != -1)) && !((key != -1)&&(height == -1)) && !((key == -1)&&(height != 0))); 
	  		this.value = value;
	  		this.height = height;
	  		this.parent = parent;
	  		this.size = size;
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
			updateSize(this.parent);
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
	    public int getSize() {
	    	return this.size;
	    }
	    public void setSize(int n) {
	    	this.size = n;
	    }
	    
	    public void printHeights() {
			System.out.println("key: " + this.getKey() + " height: " + this.getHeight());
			
			if (this.getLeft().isRealNode()){
				this.getLeft().printHeights();
			}
			if (this.getRight().isRealNode()){
				this.getRight().printHeights();
			}
		}
	    public void printSizes() {
			System.out.println("key: " + this.getKey() + " size: " + this.getSize());
			
			if (this.getLeft().isRealNode()){
				this.getLeft().printSizes();
			}
			if (this.getRight().isRealNode()){
				this.getRight().printSizes();
			}
		}
  }
}
  
