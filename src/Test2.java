import java.util.Arrays;

import AVLTree.IAVLNode;

public class Test2{

	public static void main (String[] args) {
		AVLTree tree = new AVLTree();		
		tree.insert_node(new AVLTree.AVLNode(50, "1", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(10, "2", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(55, "3", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(5, "4", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(20, "5", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(52, "6", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(60, "7", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(2, "8", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(11, "9", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(40, "10", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(54, "11", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(58, "12", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(65, "13", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(7, "14", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(1, "15", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(4, "16", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(30, "17", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(45, "18", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(62, "19", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(70, "20", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(13, "21", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(35, "22", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(48, "23", 0, null, 0));
		
		tree.bfs_print();
		
		System.out.println("The tree is consistent: " + AVLTree.isTreeConsistent(tree.getRoot()));
		/*
		AVLTree.IAVLNode node = tree.search_node_by_key(40);
		AVLTree[] result = tree.split(40);
		System.out.println("\n Smaller keys tree: ");
		result[0].bfs_print();
		System.out.println("\n Larger keys tree: ");
		result[1].bfs_print();
		result[0].join(node, result[1]);
		tree = result[0];
		
		
		System.out.println("\n Tree number 1 after split and join: ");
		tree.bfs_print();
		*/		
		printError(tree.getRoot().getSize() == 23, "Problem N.1 with size");
		//printError(tree.getRoot().getHeight() == 4, "Problem N.1 with height");
		printError(tree.max() == "20", "Problem N.1 with max");
		printError(tree.min() == "15", "Problem N.1 with min");
		
		int[] sorted_keys = {1,2,4,5,7,10,11,13,20,30,35,40, 45,48,50,52,54,55,58,60,62,65,70};
		String[] values_sorted_by_keys = new String[sorted_keys.length];
		for (int i = 0; i< sorted_keys.length; i++) {
			values_sorted_by_keys[i] = tree.search_node_by_key(sorted_keys[i]).getValue();
		}
		
		printError(Arrays.equals(tree.keysToArray(), sorted_keys), "Problem N.1 with keysToArray");
		printError(Arrays.equals(tree.infoToArray(), values_sorted_by_keys), "Problem N.1 with infoToArray");
		
		System.out.println("root is" + tree.getRoot().getKey());
		System.out.println("successor is" + tree.findSuccessor(tree.getRoot()).getKey());
		tree.delete(50);
		System.out.println("\n Tree number 2: ");
		tree.bfs_print();	
		
		System.out.println(tree.getRoot().getRight().getLeft().getKey());
		
		//printError(tree.findSuccessor(tree.getRoot().getRight().getLeft()).getKey() == 54, "Problem N.1 with findSuccessor");
		//printError(tree.findSuccessor(tree.getRoot().getLeft().getRight()).getKey() == 30, "Problem N.2 with findSuccessor");
		
		tree.delete(20);
		System.out.println("\n Tree number 3: ");
		tree.bfs_print();	
		
		tree.delete(13);
		System.out.println("\n Tree number 4: ");
		tree.bfs_print();
		
		
		/*
		public IAVLNode search_node_by_key(int key) 
		public String search(int k)
		public int insert(int k, String i) 
		public int delete(int k)
		private IAVLNode findSuccessor(IAVLNode v)
		private IAVLNode Select(IAVLNode x, int i)
		min()
		max()
		public int[] keysToArray()
		private int[] sortedKeyArray(IAVLNode r) 
		public String[] infoToArray()
		private String[] sortInfoRec(IAVLNode r)
		public int size()
		public IAVLNode getRoot()
		public AVLTree[] split(int x)
		public int join(IAVLNode x, AVLTree t)
		*/
	}
	
	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	
}