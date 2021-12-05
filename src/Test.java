public class Test{

	public static void main (String[] args) {
		AVLTree tree = new AVLTree();
		key,value,height,parent,size
		tree.insert(AVLNode(13, 1, 0, null, 0));
		tree.insert(AVLNode(10, 2, 0, null, 0));
		tree.insert(AVLNode(15, 3, 0, null, 0));
		tree.insert(AVLNode(16, 4, 0, null, 0));
		tree.insert(AVLNode(11, 5, 0, null, 0));
		tree.insert(AVLNode(5, 6, 0, null, 0));
		tree.insert(AVLNode(8, 7, 0, null, 0));
		tree.insert(AVLNode(4, 8, 0, null, 0));
		
		System.out.println("\n Tree number 1: ");
		tree.print2D();
		
		tree.insert(new AVLNode(3, 9, 0, null, 0)); //Now right rotation should occur.
		
		System.out.println("\n Tree number 2: ");
		tree.print2D();
		
		tree.insert(new AVLNode(12, 10, 0, null, 0));
		
		System.out.println("\n Tree number 3: ");
		tree.print2D();
		
		tree.insert(new AVLNode(19, 11, 0, null, 0));

		System.out.println("\n Tree number 4: ");
		tree.print2D();
		
		tree.insert(new AVLNode(1, 12, 0, null, 0));
		
		System.out.println("\n Tree number 5: ");
		tree.print2D();
		
		tree.insert(new AVLNode(14, 13, 0, null, 0));
		
		System.out.println("\n Tree number 6: ");
		tree.print2D();
		
		tree.delete(13);
		
		System.out.println("\n Tree number 7: ");
		tree.print2D();
		
		tree.delete(10);
		
		System.out.println("\n Tree number 8: ");
		tree.print2D();
		
		tree.delete(16);
		
		System.out.println("\n Tree number 9: ");
		tree.print2D();		

	}
	
	
}