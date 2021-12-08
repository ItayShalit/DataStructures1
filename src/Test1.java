import java.util.Arrays;

import AVLTree.IAVLNode;

public class Test1{

	public static void main (String[] args) {
		AVLTree tree = new AVLTree();
		
		printError(tree.empty(), "Tree is not classified as empty.");
		int[] empty_array = {};
		int[] keys_array = tree.keysToArray();
		printError(Arrays.equals(tree.keysToArray(), new int[0]), "Problem N.1 with keysToArray");
		printError(Arrays.equals(tree.infoToArray(), new String[0]), "Problem N.1 with infoToArray");
		
		
		tree.insert_node(new AVLTree.AVLNode(13, "1", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(10, "2", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(15, "3", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(16, "4", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(11, "5", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(5, "6", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(8, "7", 0, null, 0));
		tree.insert_node(new AVLTree.AVLNode(4, "8", 0, null, 0));
		
		printError(tree.search(11) == "5", "Problem N.1 with search.");
		printError(tree.search(13) == "1", "Problem N.2 with search.");
		printError(tree.search(8) == "7", "Problem N.3 with search.");
		printError(tree.search(8) == "7", "Problem N.3 with search.");
		printError(tree.search(17) == null, "Problem N.3 with search.");
		printError(tree.search_node_by_key(11) != null, "Problem N.1 with key in tree.");
		printError(tree.search_node_by_key(4) != null, "Problem N.2 with key in tree.");
		printError(tree.search_node_by_key(15) != null, "Problem N.3 with key in tree.");
		printError(tree.search_node_by_key(20) == null, "Problem N.4 with key in tree.");
		int[] sorted_keys = {4,5,8,10,11,13,15,16};
		String[] values_sorted_by_keys = {"8","6","7","2","5","1","3","4"};
		printError(Arrays.equals(tree.keysToArray(), sorted_keys), "Problem N.2 with keysToArray");
		printError(Arrays.equals(tree.infoToArray(), values_sorted_by_keys), "Problem N.2 with infoToArray");
		printError(tree.getRoot().getSize() == 8, "Problem N.1 with size");		
		
		System.out.println("\n Tree number 1: "+ tree.max());
		tree.bfs_print();
		
		tree.insert_node(new AVLTree.AVLNode(3, "9", 0, null, 0)); //Now right rotation should occur.
		
		System.out.println("\n Tree number 2: "+ tree.max());
		tree.bfs_print();
		
		printError(tree.getRoot().getHeight() == 3, "Problem N.1 with height");	
		
		System.out.println(tree.getRoot().getKey());
		
		tree.insert_node(new AVLTree.AVLNode(12, "10", 0, null, 0));
		
		System.out.println(tree.getRoot().getKey());
		
		System.out.println("\n Tree number 3: "+ tree.max());
		tree.bfs_print();
		
		
		printError(tree.max() == "4", "Problem N.1 with max.");
		printError(tree.min() == "9", "Problem N.1 with min.");
		
		tree.insert_node(new AVLTree.AVLNode(19, "11", 0, null, 0));

		System.out.println("\n Tree number 4: "+ tree.max());
		tree.bfs_print();
		
		tree.insert_node(new AVLTree.AVLNode(1, "12", 0, null, 0));
		
		System.out.println("\n Tree number 5: "+ tree.max());
		tree.bfs_print();
		
		printError(tree.getRoot().getHeight() == 3, "Problem N.1 with height");	
		
		tree.insert_node(new AVLTree.AVLNode(14, "13", 0, null, 0));
		
		System.out.println("\n Tree number 6: "+ tree.max());
		tree.bfs_print();
				
		printError(tree.getRoot().getHeight() == 4, "Problem N.4 with height");
		
		
		tree.delete(13);
		
		System.out.println("\n Tree number 7: "+ tree.max());
		tree.bfs_print();
		
		System.out.println(tree.getRoot().getRight().getSize());
		printError(tree.getRoot().getRight().getKey() == 14, "General problem N.2");	
		printError(tree.getRoot().getRight().getSize() == 6, "Problem N.3 with size");	
		
		tree.delete(10);
		
		System.out.println("\n Tree number 8: "+ tree.max());
		tree.bfs_print();
		
		
		
		tree.delete(16);
		
		System.out.println("\n Tree number 9: "+ tree.max());
		tree.bfs_print();		
		
		//tree.getRoot().printSizes();
		
		printError(tree.getRoot().getHeight() == 3, "Problem N.2 with height");
		
		tree.delete(11);
		
		System.out.println("\n Tree number 10: " + tree.max());
		tree.bfs_print();	
		
		printError(tree.max() == "11", "Problem N.2 with max.");
		printError(tree.min() == "12", "Problem N.2 with min.");
		
		printError(tree.getRoot().getLeft().getKey() == 5, "General problem N.1");	
		printError(tree.getRoot().getLeft().getSize() == 5, "Problem N.2 with size");	
		
		AVLTree[] splittingResult = tree.split(8);
		System.out.println("Smaller keys tree: ");
		splittingResult[0].bfs_print();	
		System.out.println("Larger keys tree: ");
		splittingResult[1].bfs_print();	
	}
	
	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	
}