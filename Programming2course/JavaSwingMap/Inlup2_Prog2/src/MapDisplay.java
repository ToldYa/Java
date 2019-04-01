import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MapDisplay extends JPanel {
	
	private ImageIcon map;
	
	
	//---------------------Consturctor---------------------
	public MapDisplay(){
		setLayout(null);
		
	}
	
	//---------------------Methods-------------------------
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(map != null){
			g.drawImage(map.getImage(), 0, 0, this);
		}
	}
	public void setMapDisplay(ImageIcon newMap){
		map = newMap;
		Image tempMap = newMap.getImage();
		setPreferredSize(new Dimension (tempMap.getWidth(this),tempMap.getHeight(this)));
		validate();
		repaint();
	
	}

}
