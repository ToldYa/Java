
import java.awt.*;
public class Category {
	private String categoryName;
	private Color categoryColor;
	
	
	//------------------------Constructor----------------------------
	public Category(String categoryName, Color categoryColor){
		this.categoryName = categoryName;
		this.categoryColor = categoryColor;
		
	}
	//------------------------Methods--------------------------------
	
	public String getCategoryName(){
		return categoryName;
	}
	public Color getCategoryColor(){
		return categoryColor;
	}
	
	
	public void setCategoryColor(Color newColor){
		categoryColor = newColor;
	}
	
	public String toString(){
		return categoryName;
	}
	
	public boolean equals(Object other){
		boolean isEqual = false;
		if(other instanceof Category){
			Category tempCat = (Category) other;
			isEqual = categoryName.equals(tempCat.getCategoryName()) && categoryColor.equals(tempCat.getCategoryColor());
		}
		return isEqual;
	}
	
	public int hashCode(){
		char[] tempCatName = categoryName.toCharArray();
		int tempCode = tempCatName[0] * 10000 + ((categoryColor.getRGB()/2) * categoryColor.getRGB());
		
		return tempCode + calculateNameHash(tempCatName);
	}
	private int calculateNameHash(char[] tempArr){
		int arrayValue = 0;
		for(int i = 0; i < tempArr.length; i++){
			arrayValue += tempArr[i];
		}
		
		return arrayValue;
	}
}
