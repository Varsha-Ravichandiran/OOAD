import static org.junit.jupiter.api.Assertions.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BST_Tree_Test {

	
	static Tree tr;
	static TreeSet<Integer> ts = new TreeSet<Integer>();
	static Random r = new Random();

	@BeforeAll
	public static void setup() {
		int[] a = new int[25];
		int[] b = new int[25];
		
		System.out.println("Tree created in Setup:");
		for(int i=0;i<25; i++){
			
			int randomInt = r.nextInt(25);
			if(tr == null)
			{
				tr = new Tree(randomInt);
			}
			else
			{
				tr.insert(randomInt);
			}
			ts.add(randomInt);
			}
		Iterator tree = tr.iterator();
		while(tree.hasNext())
			System.out.print(tree.next()+" ");
		System.out.println("\nTreeSet created in Setup:");
		Iterator tset = ts.iterator();
		while(tset.hasNext())
			System.out.print(tset.next()+" ");
		System.out.println("\n-----------------------------");
		
			
	}		 

	@AfterEach
	void check_invariant() {
		
		assertTrue(ordered(tr));
	}
		
	@Test
	void test_insert() {
		System.out.println("Testing Tree insert...");
		Iterator tree = tr.iterator();
		Iterator tset = ts.iterator();
		System.out.println("Creating TreeSet iterator and Comparing elements pair-wise...");
		while(tree.hasNext() && tset.hasNext())
		{
			assertEquals(tree.next(),tset.next());
		}
		System.out.println("...Tree insert test passed");
		System.out.println("Tree invariant maintained");
			 
	}
		
	public boolean ordered(Tree tr) {
		return (tr.left == null || (tr.value > tr.left.max().value && ordered(tr.left))) && (tr.right == null || (tr.value < tr.right.max().value && ordered(tr.right)));

			
	}

}