import java.awt.Color;
import java.util.Comparator;

public abstract class Place implements Comparable<Place> {
	private String name;
	private Position pos;
	private Category category;
	
	
	//------------------------Constructor--------------------
	public Place(String name, Position pos){
		this(name, pos, new Category("None", Color.BLACK));
		
	}
	public Place(String name, Position pos, Category category){
		this.name = name;
		this.pos = pos;
		this.category = category;
	}
	
	//------------------------Methods------------------------
	public String getName(){
		return name;
	}
	public Position getPosition(){
		return pos;
	}
	public Category getCategory(){
		return category;
	}
	
	
	public String toString(){
		return name + "{" +pos+"}";
	}
	public abstract String prepareForFile();
	
	public boolean equals(Object other){
		boolean isEqual = false;
		if(other instanceof Place){
			Place tempPlace = (Place)other;
			isEqual = pos.equals(tempPlace.getPosition());
		}
		return isEqual;
		
	}
	public int hashCode(){
		int tempValue = pos.getYCoordinate() + ((pos.getXCoordinate()+1)/2);
		return (pos.getXCoordinate() + (tempValue*tempValue)) ;
	}
	@Override
	public int compareTo(Place tempPlace){
		return name.compareTo(tempPlace.getName());
	}

	

}
