import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

public class NorthPanel extends JPanel{
	private boolean isNewPressed = false;
	private ArrayList<JComponent> panelComponents = new ArrayList<>();
	private ButtonGroup placeType = new ButtonGroup();
	private JTextField searchField = new JTextField("Search...", 10);
	private JRadioButton named, described ;
	private ActionListener buttonAction;
	
	//------------------------Constructor----------------------------
	public NorthPanel (ActionListener buttonAction){
		searchField.addMouseListener(new SearchFieldMouseLis());
		this.buttonAction = buttonAction;
		createComponents();
		createPanelLayout();
	}
	
	//------------------------Methods--------------------------------
	public boolean isNewPressed(){
		return isNewPressed;
	}
	public void setIsNewPressed(boolean newState){
		isNewPressed = newState;
	}
	public String getSelectedRadioButton(){
		String tempSelection = named.getText();
		if(described.isSelected()){
			tempSelection = described.getText();
		}
		return tempSelection;
	}

	private void createPanelLayout(){
		for(Component comp : panelComponents){
			add(comp);
		}
	}
	
	public void enableNewPlaceButton(){
		if(panelComponents.get(0) instanceof JButton){
			JButton temp = (JButton)panelComponents.get(0);
			temp.setEnabled(true);
		}
	}
	private void createComponents(){
		createNewPlaceButton();
		createRadioButtons();
		addSearchField();
		createSearchPlaceButton();
		createHidePlaceButton();
		createRemovePlace();
		createCoordinatesButton();
	}
	
	public String getPanelComponentText(Object obj){
		boolean compFound = false;
		String compText = "";
		int index = 0;
		do{
			if(obj != panelComponents.get(index)){
				index++;
			}else{
				compText = ((JButton)panelComponents.get(index)).getText();
				compFound = true;
			}
			
		}while(!compFound);
		
		return compText;
	}
	
	public String getSearchFieldText(){
		return searchField.getText();
	}
	public void resetSearchFieldText(){
		searchField.setText("Search...");
	}
	
	private void createNewPlaceButton(){
		JButton newPlace = new JButton("New");
		newPlace.setEnabled(false);
		newPlace.addActionListener(new NewPlaceLis());
		panelComponents.add(newPlace);
		
	}
	private void createSearchPlaceButton(){
		JButton searchPlace = new JButton("Search");
		searchPlace.addActionListener(buttonAction);
		panelComponents.add(searchPlace);
		
	}
	private void createHidePlaceButton(){
		JButton hidePlace = new JButton("Hide");
		hidePlace.addActionListener(buttonAction);
		panelComponents.add(hidePlace);
	}
	private void createRemovePlace(){
		JButton removePlace = new JButton("Remove");
		removePlace.addActionListener(buttonAction);
		
		panelComponents.add(removePlace);
		
	}
	private void createCoordinatesButton(){
		JButton coordinates = new JButton("Coordinates");
		coordinates.addActionListener(buttonAction);
		panelComponents.add(coordinates);
	}
	private void addSearchField(){
		panelComponents.add(searchField);
	}
	private void createRadioButtons(){//bryt ut
		JPanel radioButtonFrame = new JPanel();
		radioButtonFrame.setLayout(new BoxLayout(radioButtonFrame, BoxLayout.Y_AXIS));
		named = new JRadioButton("Named");
		described = new JRadioButton("Described");
		defineRadioButtonsGroup();
		radioButtonFrame.add(named);
		radioButtonFrame.add(described);
		panelComponents.add(radioButtonFrame);
	}
	private void defineRadioButtonsGroup(){
		placeType.add(named);
		placeType.add(described);
		placeType.setSelected(named.getModel(),true);
	}
	
	
	//---------------------INNER CLASS----------------------------
	
	private class SearchFieldMouseLis implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			searchField.setText("");
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
				
			
		}
		
	}

	private class NewPlaceLis implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			isNewPressed = true;
			
		}
	}

}
