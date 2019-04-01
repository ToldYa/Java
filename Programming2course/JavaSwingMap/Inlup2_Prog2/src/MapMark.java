import javax.swing.*;
import java.awt.*;

public class MapMark extends JComponent {
	private Place markedPlace;
	private boolean isMarked = false;
	private int markPointX, markPointY;
	private int[] xValues  = {0,20,10}, yValues = {0,0,20}; 
	
	//-------------------Constructor--------------------
	public MapMark(Place markedPlace){
		this.markedPlace = markedPlace;
		markPointX = markedPlace.getPosition().getXCoordinate();
		markPointY = markedPlace.getPosition().getYCoordinate();
		setCorrectBounds();
	}
	//-------------------Methods------------------------
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		if(!isMarked){
			g2D.setColor(markedPlace.getCategory().getCategoryColor());
			g2D.fillPolygon(xValues, yValues, 3);
			
		}else{
			g2D.setColor(markedPlace.getCategory().getCategoryColor());
			g2D.fillPolygon(xValues, yValues, 3);
			
			g2D.setColor(Color.MAGENTA);
			g2D.fillOval(5, 0, 10, 10);
		}
		
		
		
		
	
	}
	public String toString(){
		return markedPlace.toString();
	}
	private void setCorrectBounds(){
		int xPos = markPointX - 10;
		int yPos = markPointY - 20;
		setBounds(xPos,yPos,20,20);
	}
	public Place getMarkedPlace(){
		return markedPlace;
	}
	
	public boolean isMarked(){
		return isMarked;
	}
	public void setIsMarked(){
		isMarked = !isMarked;
	}
	public boolean equals(Object other){
		boolean isEqual = false;
		if(other instanceof MapMark){
			MapMark tempMark = (MapMark)other;
			isEqual = markedPlace.equals(tempMark.getMarkedPlace());
		}
			
	
		return isEqual;
	}
	public int hashCode(){
		return markedPlace.hashCode() + 1;
	}
	
}
