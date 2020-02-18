import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BST_DupTree_Test {

	static DupTree dtr;
	static List<Integer> al = new ArrayList<Integer>();
	static Random r = new Random();

	@BeforeAll
	public static void setup() {
	 	
		int[] a = new int[25];
		System.out.println("DupTree created in Setup:");
		for(int i=0;i<25; i++){
			
			int randomInt = r.nextInt(25);
			if(dtr == null)
			{
				dtr = new DupTree(randomInt);
			}
			else
			{
				dtr.insert(randomInt);
			}
			
			a[i]=randomInt;
			
			al.add(randomInt);
			Collections.sort(al);
			}

		Iterator tree = dtr.iterator();
		while(tree.hasNext())
			System.out.print(tree.next()+" ");
		System.out.println("\nSorted ArrayList created in Setup:");
		Iterator tset = al.iterator();
		while(tset.hasNext())
			System.out.print(tset.next()+" ");
		
		System.out.println("\n-----------------------------");
	}

	@AfterEach
	void check_invariant() {
		assertTrue(ordered(dtr));
	}
	
	@Test
	void test_insert() {
		System.out.println("Testing DupTree insert...");
		Iterator tree = dtr.iterator();
		Iterator tset = al.iterator();
		System.out.println("Creating ArrayList iterator and Comparing elements pair-wise...");
		while(tree.hasNext() && tset.hasNext())
		{

			assertEquals(tree.next(),tset.next());
		}
		System.out.println("...DupTree insert test passed");
		System.out.println("DupTree invariant maintained");
		System.out.println("-----------------------------");
	}
	
	@Test
	void test_delete() {
		System.out.print("Testing DupTree delete:");
		int randomInt = r.nextInt(25);
		
		if(dtr == null)
		{
			dtr = new DupTree(randomInt);
		}
		else
		{
			dtr.insert(randomInt);
		}
		System.out.print(" inserted value = "+randomInt);
		int c = get_count(dtr,randomInt);
		System.out.print(" with count = "+c);
		dtr.delete(randomInt);
		System.out.print("\nafter DupTree delete:");
		int d =0;
		
		if(dtr != null) {
			d = get_count(dtr,randomInt);
			System.out.println(" value = "+randomInt+", count = "+d);
		}
		else
			System.out.println("value = "+randomInt+ ", count = 0");
		
		
		assertEquals(c-1,d);
		System.out.println("...DupTree delete test passed");
		System.out.println("DupTree invariant maintained");
		System.out.println("-----------------------------");
	}		

	public int get_count(DupTree tr, int v) {
		
		if(tr.find(v) == null)
			return 0;
			return tr.find(v).get_count();
	}
	

	public boolean ordered(Tree tr) {
		
		return (tr.left == null || (tr.value > tr.left.max().value && ordered(tr.left))) && (tr.right == null || (tr.value < tr.right.max().value && ordered(tr.right)));

	}
}