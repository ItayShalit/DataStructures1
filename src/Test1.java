import java.util.Arrays;

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
		printError(tree.is_key_in_tree(11), "Problem N.1 with key in tree.");
		printError(tree.is_key_in_tree(4), "Problem N.2 with key in tree.");
		printError(tree.is_key_in_tree(15), "Problem N.3 with key in tree.");
		printError(!tree.is_key_in_tree(20), "Problem N.4 with key in tree.");
		int[] sorted_keys = {4,5,8,10,11,13,15,16};
		String[] values_sorted_by_keys = {"8","6","7","2","5","1","3","4"};
		printError(tree.keysToArray() == sorted_keys, "Problem N.2 with keysToArray");
		printError(tree.infoToArray() == values_sorted_by_keys, "Problem N.2 with infoToArray");
		printError(tree.size() == 8, "Problem N.1 with size");		
		
		System.out.println("\n Tree number 1: ");
		tree.print2D();
		
		tree.insert_node(new AVLTree.AVLNode(3, "9", 0, null, 0)); //Now right rotation should occur.
		
		System.out.println("\n Tree number 2: ");
		tree.print2D();
		
		tree.insert_node(new AVLTree.AVLNode(12, "10", 0, null, 0));
		
		System.out.println("\n Tree number 3: ");
		tree.print2D();
		
		printError(tree.max() == "4", "Problem N.1 with max.");
		printError(tree.min() == "9", "Problem N.1 with min.");
		
		tree.insert_node(new AVLTree.AVLNode(19, "11", 0, null, 0));

		System.out.println("\n Tree number 4: ");
		tree.print2D();
		
		tree.insert_node(new AVLTree.AVLNode(1, "12", 0, null, 0));
		
		System.out.println("\n Tree number 5: ");
		tree.print2D();
		
		tree.insert_node(new AVLTree.AVLNode(14, "13", 0, null, 0));
		
		System.out.println("\n Tree number 6: ");
		tree.print2D();
		
		tree.delete(13);
		
		System.out.println("\n Tree number 7: ");
		tree.print2D();
		
		printError(tree.getRoot().getRight().getKey() == 15, "General problem N.2");	
		printError(tree.getRoot().getRight().getSize() == 6, "Problem N.3 with size");	
		
		
		tree.delete(10);
		
		System.out.println("\n Tree number 8: ");
		tree.print2D();
		
		tree.delete(16);
		
		System.out.println("\n Tree number 9: ");
		tree.print2D();		
		
		tree.delete(11);
		
		System.out.println("\n Tree number 10: ");
		tree.print2D();	
		
		printError(tree.max() == "11", "Problem N.2 with max.");
		printError(tree.min() == "12", "Problem N.2 with min.");
		
		printError(tree.getRoot().getLeft().getKey() == 3, "General problem N.1");	
		printError(tree.getRoot().getLeft().getSize() == 4, "Problem N.2 with size");	
		
		AVLTree[] splittingResult = tree.split(9);
		System.out.println("Smaller keys tree: ");
		splittingResult[0].print2D();	
		System.out.println("Larger keys tree: ");
		splittingResult[1].print2D();	
	}
	
	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	
}