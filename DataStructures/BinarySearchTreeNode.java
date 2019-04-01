package alda.tree;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) De ändringar som är tillåtna är dock
 * begränsade av följande:
 * <ul>
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	
	//-------------------------------Constructors-----------------------------
	public BinarySearchTreeNode(T data) {
		this(data, null, null);
	}

	public BinarySearchTreeNode(T data, BinarySearchTreeNode<T> leftChild, BinarySearchTreeNode<T> rightChild){
		this.data = data;
		left = leftChild;
		right = rightChild;	
	}
	
	
	//-------------------------------Methods-----------------------------
	public boolean add(T data) {// bort ser från dubbletter
		int compareResult = data.compareTo(this.data);
		boolean isSuccessful = false;
		
		if(compareResult < 0){
			isSuccessful = addToLeftSubtree(data);
		}else if(compareResult > 0){
			isSuccessful = addToRightSubtree(data);
		}
		
		return isSuccessful;
	}

	private boolean addToLeftSubtree(T data){
		boolean isSuccessful = false;
		if(left != null){
			isSuccessful = left.add(data);
		}else{
			left = new BinarySearchTreeNode<T>(data);
			isSuccessful = true;
		}
		return isSuccessful;
	}

	private boolean addToRightSubtree(T data){
		boolean isSuccessful = false;
		if(right != null){
			 isSuccessful = right.add(data);
		}else{
			right = new BinarySearchTreeNode<T>(data);
			isSuccessful = true;
		}
		return isSuccessful;
	}

	private T findMin() {
		if(left != null){
			left.findMin();
		}
		
		return data;
	}
	
	public BinarySearchTreeNode<T> remove(T data) {
		int compareResult = data.compareTo(this.data);
		BinarySearchTreeNode<T> tempNode = this;
			if(compareResult == 0){
				if(isLeafNode()){
				  tempNode = null;
				}else{
					tempNode = rearrange();
				}
			}else if(compareResult < 0 && left != null  ){
				left = left.remove(data);
		
			}else if( compareResult > 0 && right != null){
				right = right.remove(data);
			}
		
		return tempNode;
	}
	 
	private BinarySearchTreeNode<T> rearrange(){
		BinarySearchTreeNode<T> tempNode;
		if(left != null){
			left.findRightMostChild().right = right;
			tempNode = left;
		}else{
			tempNode = right;
		}
		return tempNode;
	}
	

	private boolean isLeafNode(){
		return (left == null && right == null);
	}

	
	private BinarySearchTreeNode<T> findRightMostChild(){
		BinarySearchTreeNode<T> tempNode = null;
		if(right == null){
			tempNode = this;
		}else{
			tempNode = right.findRightMostChild();
		}
		return tempNode;
	}

	private BinarySearchTreeNode<T> findLeftMostChild(){
		BinarySearchTreeNode<T> tempNode = null;
		if(left == null){
			tempNode = this;
		}else{
			tempNode = left.findLeftMostChild();
		}
		return tempNode;
	}


	public boolean contains(T data) {
		int compareResult = data.compareTo(this.data);
		boolean wasFound = false;
		
		if(compareResult == 0){
			wasFound = true;
		}else if(left != null && compareResult < 0 ){
			wasFound = left.contains(data);
		}else if(right != null && compareResult > 0){
			wasFound = right.contains(data);
		}
	
		return wasFound;
	}

	public int size() {
		int size = 0;
		
			if(left != null){
				size += left.size();
			}
			if(right != null){
				size += right.size();
			}
			size++;
		
		return size;
	}

	public int depth() {		
		int depth = 0;
		int leftDepth = 0, rightDepth = 0;
		
		if(left != null || right != null){
			if(left !=null){
				leftDepth = left.depth();
			}
			if(right !=null){
				rightDepth = right.depth();
			}
			depth += 1;
		}
		depth += Math.max(leftDepth, rightDepth);
		
		return depth;
	}
	
	public String toString() {
		StringBuffer treePrint = new StringBuffer();
		if(left != null){
			treePrint.append(left.toString()+ ", ");
		}
		treePrint.append(data);
		if(right != null){
			treePrint.append( ", " + right.toString());
		}
		return treePrint.toString();
	}
}