import java.util.ArrayList;
import java.util.Collections;
import java.util.random.*;
public class Q1 {
	
	public static void main(String[] args) {
		for (int i = 1; i <= 5; i++) {
			boolean[] bool_arr = {true,false};
			for (boolean rand:bool_arr) {
				ArrayList<Integer> arrayList = buildArrayList(i, rand);
				System.out.println("random is " + rand + "and i is " + i + "and number of moves is " + buildTreefromArray(arrayList));
			}
		}
	}

	public static int buildTreefromArray(ArrayList<Integer> keys) {
		AVLTreeMod t = new AVLTreeMod();
		int search_cost = 0;
		for(int j = 0 ; j < keys.size(); j++) {
			search_cost += t.insert(keys.get(j),"s");
		}
		return search_cost;
	}
	
	public static ArrayList<Integer> buildArrayList(int i, boolean random) {
		int n = (int) (1000 * Math.pow(2, i));
		ArrayList<Integer> array = new ArrayList<>();
		for(int j = 1; j <= n; j++) {
			array.add(j);
		}
		if(random) {
			Collections.shuffle(array);
			return array;
		}
		Collections.sort(array,(x,y) -> -x.compareTo(y));
		return array;
	}
	
	public int subNum(ArrayList<Integer> a) {
		int count = 0;
		for(int i = 0; i < a.size(); i++) {
			for(int j = i+1; j < a.size(); j++) {
				if(a.get(i) > a.get(j)) {
					count ++;
				}
			}
		}
		return count;
	}
}