import javax.swing.*;
public class CategoryListModel extends DefaultListModel<Category> {
	
	public	CategoryListModel(){
		super();
	}
	

	public void addSorted(Category newCategory){
		int atIndex = binSortNew(newCategory);
		add(atIndex, newCategory);
	}
	private int binSortNew(Category newC){
		boolean isAtIndex = false;
		int lastIndex = size()-1, firstIndex = 0, compareResult = 0, compareI = 0;
		
		while(lastIndex >= firstIndex && !isAtIndex){
			compareI = (int)((lastIndex + firstIndex) / 2);
			compareResult = newC.getCategoryName().compareTo(getElementAt(compareI).getCategoryName());
			if(compareResult > 0){
				firstIndex = compareI + 1;
			}else if (compareResult < 0){
				lastIndex = compareI -1;
			}else{
				isAtIndex = true;
			}
		}
		if(compareResult > 0 || isAtIndex){
			compareI++;
		}
		return compareI;
	}
	
	public int binGetIndexOf(String categoryName){
		boolean isAtIndex = false;
		int lastIndex = size()-1, firstIndex = 0, compareResult = 0, compareI = 0;
		
		while(lastIndex >= firstIndex && !isAtIndex){
			compareI = (int)((lastIndex + firstIndex) / 2);
			compareResult = categoryName.compareTo(getElementAt(compareI).getCategoryName());
			if(compareResult > 0){
				firstIndex = compareI + 1;
			}else if (compareResult < 0){
				lastIndex = compareI -1;
			}else{
				isAtIndex = true;
			}
		}
		if(!isAtIndex){
			compareI = -1;
		}
		
		return compareI;
	}

	private int sortNew(Category newCategory) {
		int index = 0;
		while(index < size() && newCategory.getCategoryName().compareTo(getElementAt(index).getCategoryName()) > 0){
			index++;
			
		}
		return index;
	}
	public int getIndexOf(String categoryName){//gör om till binsök
		int index = -1;
		for(int i = 0; i < size(); i++){
			
			if(getElementAt(i).getCategoryName().equals(categoryName)){
				index = i;
				i = size();
			}	
		}
		return index;
	}
}
