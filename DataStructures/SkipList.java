package alda.radomalgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;


public class SkipList<T extends Comparable<T>> {
	private int size, sizeAtLastRecomposition;
	private Node<T> sentinal;
	private int[] levelSaturation;
	
	//--------------------Constructors------------------------
	public SkipList(){
		this(30);
	
	}
	
	public SkipList(List<T> list){
		this(list.size());
		copyList(list);
	}
	
	public SkipList(int sizeAssessment){
		size = 0;
		levelSaturation = calcLevelSaturation(sizeAssessment);
		sentinal = new Node<T>(null, levelSaturation.length);
	}
	
	
	//--------------------Methods-----------------------------
	/**
	 * 
	 * @param list
	 * calculates the number of levels required for the input list  
	 * 1/2 - level 1 
	 * 1/4 - level 2
	 * etc
	 * 
	 * @return the new level saturation
	 */
	private int[] calcLevelSaturation(int sizeOfList) {
		int size = sizeOfList;
		int numOfLevel = 0;
		while(size > 0){
			size /= 2;
			if(size > 0){
				numOfLevel++;
			}	
		}
		sizeAtLastRecomposition = sizeOfList;
		return new int[numOfLevel];
	}
	private void copyList(List<T> list){
		Iterator<T> ite = list.iterator();
		while(ite.hasNext()){
			reinsert(ite.next(), sentinal);	
		}
	}
	/**
	 * 
	 * 
	 * 
	 * @return true if the list is in need of restructuring, that is if the nodes are unproportionally divided amongst the levels of the list, false otherwise. 
	 */
	
	private boolean isInNeedOfLevelRecomposition(){
		boolean isInNeed = false;
		int suppposedNumOfNodeAtLevel = sizeAtLastRecomposition;
		int numOfNodesAtLevel;
		double sumOfNodeLevelSaturation = 0;
		for(int i = 0; i < levelSaturation.length && !isInNeed; i++){
			suppposedNumOfNodeAtLevel = Math.round(suppposedNumOfNodeAtLevel/2);
			numOfNodesAtLevel = levelSaturation[i];
			sumOfNodeLevelSaturation += numOfNodesAtLevel/(double)suppposedNumOfNodeAtLevel;
		}
		
		sumOfNodeLevelSaturation = (sumOfNodeLevelSaturation/(double) levelSaturation.length);	
		System.out.println("saturationLevel: " + sumOfNodeLevelSaturation);
		if((sumOfNodeLevelSaturation < 0.9||sumOfNodeLevelSaturation > 2) && size > 30){
			isInNeed = true;
		}
		return isInNeed;
	}
	private void recomposeList(){
		levelSaturation = calcLevelSaturation(size);
		Node<T> tempSentinal = new Node<T>(null, levelSaturation.length);
		Iterator<Node<T>> tempList = iterator();
		size = 0;
		while(tempList.hasNext()){
			reinsert(tempList.next().data, tempSentinal);
		}
		sentinal = tempSentinal;
	}
	
	public boolean insert (T element){
		boolean wasInserted = false;
		if(element != null){
			int nodeNumOfLevels = coinFlip();
			Node<T> newNode = new Node<T>(element, nodeNumOfLevels);
			wasInserted = sentinal.insert(newNode);
			if(wasInserted){
				size++;
				levelSaturation[nodeNumOfLevels-1]++;
				if(size/(double)sizeAtLastRecomposition > 1.2 && isInNeedOfLevelRecomposition()){
					recomposeList();
				}
			}
		}	
		return wasInserted;
		 
	}
	private boolean reinsert(T element, Node<T> listSentinal){
		boolean wasInserted = false;
		if(element != null){
			int nodeNumOfLevels = coinFlip();
			Node<T> newNode = new Node<T>(element, nodeNumOfLevels);
			wasInserted =listSentinal.insert(newNode);
			if(wasInserted){
				size++;
				levelSaturation[nodeNumOfLevels-1]++;
			}
		}
		return wasInserted;
	}
	
	
	private int coinFlip(){
		Random rnd = new Random();
		int numOfFlips = 0;
		int headOrTail = 0;
		while(headOrTail == 0){
			numOfFlips++;
			headOrTail = rnd.nextInt(2);
		}
		if(numOfFlips > levelSaturation.length){
			numOfFlips = levelSaturation.length;
		}
		
		return numOfFlips;
	}
	
	public boolean contains(T element){
		  return sentinal.hasLinkTo(element);
	 }

	public boolean remove(T element){
		boolean wasRemoved = sentinal.remove(element);
		if(wasRemoved){
			size--;
			if(size/(double)sizeAtLastRecomposition < 0.8 && isInNeedOfLevelRecomposition()){
				recomposeList();
			}
		}
		return wasRemoved;
	}
	
	public int size(){
		return size;
	}

	public Iterator<Node<T>> iterator(){
		return new SkipListIterator();
	}

	public String toString(){
		Iterator<Node<T>> tempSkipList = iterator();
		StringBuffer output = new StringBuffer();
		output.append("[");
		while(tempSkipList.hasNext()){
			output.append(tempSkipList.next().toString());
			if(tempSkipList.hasNext()){
				output.append(", ");
			}
		}
		output.append("]");
		return output.toString();
	}
	
	//-------------------InnerClass---------------------------
	
	private class SkipListIterator implements Iterator<Node<T>>{
		Node<T> currentNode;
		public SkipListIterator(){
			currentNode = sentinal;
		}
		
		@Override
		public boolean hasNext() {
			boolean hasNext = false;
			if(currentNode.links.get(0) != null){
				hasNext = true;
			}	
			return hasNext;
		}

		@Override
		public Node<T> next() throws NoSuchElementException{
			
			currentNode = currentNode.links.get(0);
			if(currentNode == null){
				throw new NoSuchElementException();
			}
			return currentNode;
		}
		
	}
	
	
	
	private class Node<T extends Comparable<T>> implements Comparable<T> {
		T data;
		ArrayList<Node<T>> links;
		
		public Node(T data, int numOfLevels){
			this.data = data;
			links = new ArrayList<Node<T>>(numOfLevels);
			for(int i = 0; i < numOfLevels; i++){
				links.add(null);
			}
		}
		
		public boolean insert(Node<T> newNode){
			boolean insertionComplete = false;
			int linkIndex = links.size()-1;
			while(!insertionComplete){
				if((linkIndex <= newNode.links.size()-1) && (links.get(linkIndex) == null || links.get(linkIndex).compareTo(newNode.data) > 0)){
					newNode.links.set(linkIndex, links.get(linkIndex));
					links.set(linkIndex, newNode);	
					if(linkIndex == 0){
						insertionComplete = true;
					}	
				}else if (linkIndex <= newNode.links.size()-1 && links.get(linkIndex) != null){
					insertionComplete = links.get(linkIndex).insert(newNode);
				}
				linkIndex--;
			}
			return insertionComplete;
		}

		public boolean hasLinkTo(T element){
			boolean elementFound = false;
			int compRes;
			for(int i = links.size()-1; i >= 0 && !elementFound; i--){
				if(links.get(i) != null){
					compRes = links.get(i).compareTo(element);
					if(compRes == 0){
						elementFound = true;
					}else if(compRes < 0){
						elementFound = links.get(i).hasLinkTo(element);
					}
				}
			}	
			return elementFound;
		}
		
		public boolean remove(T nodeToDelete) {
			boolean wasDeleted = false;
			int compRes;
			if(nodeToDelete != null){
				for (int i = links.size() - 1; i >= 0 && !wasDeleted; i--) {

					if (links.get(i) != null) {
						compRes = links.get(i).compareTo(nodeToDelete);
						if (compRes == 0) {
							if (i == 0) {
								levelSaturation[links.get(i).links.size()-1]--;
								wasDeleted = true;
							}
							Node<T> tempNode = links.get(i).links.get(i);
							links.set(i, tempNode);
							
						} else if (compRes < 0) {
							wasDeleted = links.get(i).remove(nodeToDelete);
							i = -1;
						}
					}
				}
			}
			return wasDeleted;
		}
		
		@Override
		public int compareTo(T arg0){
			return	data.compareTo(arg0);
		}

		
		

		public String toString(){
			return data.toString()+"["+links.size()+"]";
		}
		
	}
	


	
	
}
