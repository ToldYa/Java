import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.*;

public class InfoPanel extends JPanel{
	private JLabel placeNameAndPos = new JLabel();
	private JTextArea description;
	private JScrollPane descScroll;
	private GridBagConstraints layoutConst = new GridBagConstraints();
	
	
	//--------------Constructor------------------
	public InfoPanel(String placeName, Position placePos){
		super();
		
		placeNameAndPos.setText(placeName + " {"+ placePos +"}");
		createPanelLayout();
		
	}
	public InfoPanel(String placeName, Position placePos, String desc){
		super();
		
		placeNameAndPos.setText(placeName + " {"+ placePos +"}");
		description = new JTextArea(5, 15);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		description.setEditable(false);
		description.setText(desc);
		descScroll = new JScrollPane(description);
		createExtendedPanelLayout();
	}
	//--------------Methods------------------
	private void assignGridValues(int x, int y, int orientation){
		layoutConst.gridx = x;
		layoutConst.gridy = y;
		layoutConst.anchor = orientation;
	}
	private void createPanelLayout(){
		setLayout(new GridBagLayout());
		layoutConst.insets = new Insets(2,2,2,2);
		assignGridValues(0, 0, layoutConst.SOUTHEAST);
		add(new JLabel("Name: "), layoutConst);
		
		assignGridValues(1, 0, layoutConst.SOUTHWEST);
		add(placeNameAndPos, layoutConst);
	}
	private void createExtendedPanelLayout(){
		createPanelLayout();
		
		assignGridValues(0, 1, layoutConst.NORTHEAST);
		add(new JLabel("Description: "), layoutConst);
		
		assignGridValues(1, 1, layoutConst.NORTHEAST);
		add(descScroll, layoutConst);
		
	}
}
