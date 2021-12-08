import java.util.Arrays;

import AVLTree.IAVLNode;

public class Test3{

	public static void main (String[] args) {
		AVLTree tree1 = new AVLTree();
		AVLTree tree2 = new AVLTree();
		
		
		tree1.insert_node(new AVLTree.AVLNode(50, "1", 0, null, 0));
		tree1.insert_node(new AVLTree.AVLNode(40, "2", 0, null, 0));
		tree1.insert_node(new AVLTree.AVLNode(55, "3", 0, null, 0));
		tree1.insert_node(new AVLTree.AVLNode(28, "4", 0, null, 0));
		tree1.insert_node(new AVLTree.AVLNode(30, "5", 0, null, 0));
		tree1.insert_node(new AVLTree.AVLNode(52, "6", 0, null, 0));
		
		tree2.insert_node(new AVLTree.AVLNode(2, "7", 0, null, 0));
		tree2.insert_node(new AVLTree.AVLNode(10, "8", 0, null, 0));
		tree2.insert_node(new AVLTree.AVLNode(20, "9", 0, null, 0));
		tree2.insert_node(new AVLTree.AVLNode(15, "10", 0, null, 0));
		tree2.insert_node(new AVLTree.AVLNode(17, "11", 0, null, 0));
		tree2.insert_node(new AVLTree.AVLNode(22, "12", 0, null, 0));
		
		System.out.println("The tree is consistent: " + AVLTree.isTreeConsistent(tree1.getRoot()));
		System.out.println("The tree is consistent: " + AVLTree.isTreeConsistent(tree2.getRoot()));
		System.out.println("tree1: ");
		tree1.bfs_print();
		
		System.out.println("\n tree2: ");
		tree2.bfs_print();
		
		tree1.join(new AVLTree.AVLNode(25, "13", 0, null, 0), tree2);
		
		System.out.println("The tree is consistent: " + AVLTree.isTreeConsistent(tree1.getRoot()));

		
		System.out.println("after join: ");
		tree1.bfs_print();
		System.out.println(tree1.getRoot().getSize());
		
		AVLTree[] split_results = tree1.split(25);
		
		System.out.println("Split result number 1: ");
		split_results[0].bfs_print();
		
		System.out.println("Split result number 2: ");
		split_results[1].bfs_print();
	}
	
	public static void printError(boolean condition, String message) {
		if (!condition) {
			throw new RuntimeException("[ERROR] " + message);
		}
	}
	
	
}