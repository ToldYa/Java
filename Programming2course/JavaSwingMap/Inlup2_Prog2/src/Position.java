
public class Position {
	private int xCoordinate, yCoordinate;
	
	//--------------Constructor-----------------
	public Position(int x, int y){
		xCoordinate = x;
		yCoordinate = y;
		
	}
	
	//--------------Methods---------------------
	public int getXCoordinate() {
		return xCoordinate;
	}
	public int getYCoordinate() {
		return yCoordinate;
	}
	
	public boolean equals(Object other){
		boolean isEqual = false;
		if(other instanceof Position){
			isEqual = isEqual(other);
		}	
		return isEqual;
	}
	private boolean isEqual(Object other){
		boolean isEqual = false;
		if(other instanceof Position && other.hashCode() == hashCode()){
			Position tempPos = (Position)other;
			isEqual = (xCoordinate == tempPos.getXCoordinate() && yCoordinate == tempPos.getYCoordinate());
		}
		return isEqual;	
	}

	public int hashCode(){
		int temp = yCoordinate + ((xCoordinate+1)/2);
		return xCoordinate + (temp*temp);
	}
	public String prepareForFile(){
		return xCoordinate +"," + yCoordinate;
	}
	public String toString(){
		return xCoordinate +"." + yCoordinate;
	}
}
