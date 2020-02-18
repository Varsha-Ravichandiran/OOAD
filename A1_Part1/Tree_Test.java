
public class Tree_Test {
	
	public static void main(String[] args) {
		AbsTree tr = new Tree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

class DupTree_Test {

	public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
	}
}

abstract class AbsTree {
	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null) {
					right = add_node(n);
     				right.parent = this;
			} else
					right.insert(n);
		else if (left == null) {
			left = add_node(n);
     		left.parent = this;
		} else
			left.insert(n);
	}

	public void delete(int n) {  
		AbsTree t = find(n);

		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		}

		int c = t.get_count();
		if (c > 1) {
			t.set_count(c-1);
			return;
		}

		if (t.left == null && t.right == null) { // n is a leaf value
			if (t != this)
				case1(t);
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		}
		if (t.left == null || t.right == null) { // t has one subtree only
			if (t != this) { // check whether t is the root of the tree
				case2(t);
				return;
			} else {
				if (t.right == null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		}
		// t has two subtrees; go with smallest in right subtree of t
		case3R(t);
	}

	protected void case1(AbsTree t) { // remove the leaf
		if(t.parent.left == t)
			t.parent.left = null;
		else
			t.parent.right = null;
		t.parent = null;
	}

	protected void case2(AbsTree t) { // remove internal node	
		if(t.parent.right == t) {
			if(t.right != null) {
				t.right.parent=t.parent;
				t.parent.right= t.right;
				t.parent = null;
				t.right = null;
			}
			else if(t.left != null)
			{
				t.left.parent=t.parent;
				t.parent.right= t.left;
				t.parent = null;
				t.left = null;
		    }	
		}
		else if(t.parent.left == t)
		{
			if(t.right != null) {
				t.right.parent=t.parent;
				t.parent.left= t.right;
				t.parent = null;
				t.right = null;
			}
			else if(t.left != null)
			{
				t.left.parent=t.parent;
				t.parent.left= t.left;
				t.parent = null;
				t.left = null;
		    }			
		}
	}

	protected void case3L(AbsTree t) { // replace t.value and t.count		
		AbsTree maximum = t.left.max();
		t.value = maximum.value;
		if(maximum.left == null && maximum.right == null)
			case1(maximum);
		else
			case2(maximum);			
	}

	protected void case3R(AbsTree t) { // replace t.value
		AbsTree minimum = t.right.min();
		t.value = minimum.value;
		if(minimum.left == null && minimum.right == null)
			case1(minimum);
		else
			case2(minimum);
	}

	private AbsTree find(int n) {
		return findHelper(this , n);		
	}
	
	private AbsTree findHelper(AbsTree root, int  n){
		if(root == null || root.value == n )
			return root;
			
		if(root.value > n)
			return findHelper(root.left, n);
			
		if(root.value < n)
			return findHelper(root.right,n);
			
		return root;		
	}

	public AbsTree min() {
		AbsTree t = this;
		return minHelper(t);
	}
	
	private AbsTree minHelper(AbsTree root){
		if(root == null)
			return root;
		
		if(root.left !=null)
			return minHelper(root.left);
		
		return root;				
	}

	public AbsTree max() {
		AbsTree t = this;
		return maxHelper(t);
	}
	
	private AbsTree maxHelper(AbsTree root){
		if(root == null)
			return root;
		
		if(root.right !=null)
			return maxHelper(root.right);
		
		return root;		
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract void set_count(int v);
}

class Tree extends AbsTree {
	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}

	protected int get_count() {
		return 1;
	}

	protected void set_count(int v) {
		// to be filled by you
	}
}

class DupTree extends AbsTree {
	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	protected int get_count() {
		return count;
	}

	protected void set_count(int v) {
		count=v;
	}

	protected int count;

}
