
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;




public class GUI extends JFrame{
	private JMenuItem newMap, loadPlaces, save, exit; 
	private JMenuBar menuBar = new JMenuBar();
	private JMenu archive = new JMenu("Archive");
	private Prog prog = new Prog();
	private NorthPanel northPanel = new NorthPanel(new NorthPanelLis());
	private EastPanel eastPanel = new EastPanel(new EastPanelLis());
	private JPanel mapDisplayPanel = new JPanel();
	private MapDisplay mapDisplay = new MapDisplay();
	private JScrollPane mapScroll = new JScrollPane(mapDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
	private JFileChooser saveToFile,fileChooser;
	
	//---------------------Constructor----------------------------
	public GUI(){
		super("Inlupp 2");
		eastPanelSetup();
		createMenu();
		createMapDisplay();
		addGUIComponents();
		setGUISettings();
		setupFileChoosers();
	}
	//---------------------Methods--------------------------------	
	private void setGUISettings(){
		addWindowListener(new GUIFrameWinLis());
		addWindowStateListener(new GUIFrameWinLis());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Selected Coordiantes: ");
		setVisible(true);
		setLocationRelativeTo(null);
		pack();
	}
	private void addGUIComponents(){
		add(mapDisplayPanel, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		add(eastPanel, BorderLayout.EAST);
	}
	
	private void addMenuAlt(){
		archive.add(newMap);
		archive.add(loadPlaces);
		archive.add(save);
		archive.add(exit);
	}

	private void createMenu(){
		menuBar.add(archive);
		createMenuAlt();
		addMenuAlt();
		setJMenuBar(menuBar);
	}
	private void createMenuAlt(){
		createMenuNewMapAlt();
		createMenuLoadAlt();
		createMenuSaveAlt();
		createMenuExitAlt();
		}
	private void createMenuNewMapAlt(){
		newMap = new JMenuItem("New Map");
		newMap.addActionListener(new LoadMapLis());
	}
	private void createMenuLoadAlt(){
		loadPlaces= new JMenuItem("Load Places");
		loadPlaces.addActionListener(new LoadFileLis());
	}
	private void createMenuSaveAlt(){
		save = new JMenuItem("Save");
		save.addActionListener(new SaveLis());
	}
	private void createMenuExitAlt(){
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ExitLis());
	}
	private void createMapDisplay(){
		mapDisplayPanel.setLayout(new FlowLayout());
		mapDisplayPanel.add(mapScroll);
		mapDisplayPanel.addComponentListener(new MapDisplayCompLis());
		
		mapScroll.setVisible(false);
		mapDisplay.addMouseListener(new MouseCoordinateLis());
	}
	private void setupFileChoosers(){
		saveToFile = new JFileChooser(".");
		fileChooser = new JFileChooser(".");
		setSaveToFileFilter();
	}
	private void setSaveToFileFilter(){
		FileNameExtensionFilter saveFilter = new FileNameExtensionFilter("Places", "places");
		saveToFile.addChoosableFileFilter(saveFilter);
		saveToFile.setFileFilter(saveFilter);
	}
	public void showNewlyLoadedPlaces(){// bör ses över
		Iterator<MapMark> tempMarks = prog.getAllMapMark();
		while(tempMarks.hasNext()){
			MapMark tempMark = tempMarks.next();
			tempMark.addMouseListener(new MarkLis());
			mapDisplay.add(tempMark);
		}	
		mapDispValRepaint();
	}	
	public void deselectMarks(){
		Iterator<MapMark> tempSelected = prog.getSelectedPlace().iterator();
		while(tempSelected.hasNext()){
			tempSelected.next().setIsMarked();
		}
		prog.clearSelection();
		mapDispValRepaint();
	}
	public void hideSelectedMarks(){
		HashSet<MapMark> tempSelections = prog.getSelectedPlace();
		Iterator<MapMark> tempIt = tempSelections.iterator();
		while(tempIt.hasNext()){
			MapMark tempMark = tempIt.next();
			
			tempMark.setVisible(false);
			tempMark.setIsMarked();

		}
		prog.clearSelection();
		mapDispValRepaint();
	}
	 


	public void resizeMapDisplay(){
		
		int w = mapDisplayPanel.getSize().width;
		int h = mapDisplayPanel.getSize().height;
		if(w > mapDisplay.getSize().width){
			w = mapDisplay.getSize().width;
		}
		if(h > mapDisplay.getSize().height){
			h = mapDisplay.getSize().height;
		}
		mapScroll.setPreferredSize( new Dimension(w,h));
			
		validate();
		repaint();
	}
	public void eastPanelSetup(){
		eastPanel.setCategoryList(prog.getCategories());
		eastPanel.setListListener(new EastPanelListLis());
	}
	public void setMapDisplaySize(int mapWidth,int mapHeight){
		mapScroll.setMinimumSize(new Dimension((mapWidth/2), (mapHeight/2)));
		mapScroll.setMaximumSize(new Dimension(mapWidth, mapHeight));
		mapScroll.setPreferredSize(new Dimension((mapWidth*2/3), (mapHeight*2/3)));
		mapScroll.setVisible(true);
		pack();
	}
	public void saveToFileErrorMess(boolean progressSaved){
		if(progressSaved){
			JOptionPane.showMessageDialog(GUI.this, "Progress Saved!");
		}else{
			JOptionPane.showMessageDialog(GUI.this, "---Error---\nUnable To Save");
		}
		
	}
	public void saveToFile(){
		int answer = saveToFile.showSaveDialog(null);
		if(answer == JFileChooser.APPROVE_OPTION){
			File tempFile = saveToFile.getSelectedFile();
			tempFile = checkSuffix(tempFile);
			saveToFileErrorMess(prog.saveToFile(tempFile));
		}
		
	}
	private File checkSuffix(File tempFile){
		if(!tempFile.getName().contains(".places")){
			tempFile = new File(tempFile.toString()+".places");
		}
		return tempFile;
	}
	public int saveToFile(String warningMessage){
		int answer = JOptionPane.showConfirmDialog(GUI.this, warningMessage);
		if(answer == JOptionPane.OK_OPTION){
			saveToFile();
		}
		return answer;
	}
	
	public void showSelectedCategory(){
		String categoryName = eastPanel.getSelectedCategory().getCategoryName();
		if(!prog.isCategoryEmpty(categoryName)){
			deselectMarks();
			Iterator<MapMark> tempCategoryMarks = prog.getCategorySortedMap(categoryName);
			while(tempCategoryMarks.hasNext()){
				tempCategoryMarks.next().setVisible(true);
			}
		}
	}
	
	
	public void restoreFileChooserFilter(){
		fileChooser.resetChoosableFileFilters();
		fileChooser.setAcceptAllFileFilterUsed(true);
	} 

	public void clearData(){// tar bort all data används vid laddning av ny fil när befintlig fil redan finns
		Iterator<MapMark> tempMarks = prog.getAllMapMark();
		while(tempMarks.hasNext()){
			mapDisplay.remove(tempMarks.next());
		}
		prog.clearData();
	}
	public void mapDispValRepaint(){
		mapDisplay.validate();
		repaint();
	}

	//--------------------Inner Class----------------------------

	private class ExitLis implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ave) {
			if(prog.isSaved()){
				System.exit(0);
			}else{
				int answer = saveToFile("Progress Not Saved! \nWould You Like To Save before Exiting?");
				if(answer == JFileChooser.APPROVE_OPTION || answer == JOptionPane.NO_OPTION){
					System.exit(0); 
				}
			}
		}
				
			
			
		}

		
	
	
	private class SaveLis implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveToFile();
		}
		
	}
	
	private class LoadFileLis implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
				if(prog.isSaved()){
					loadFileAction();
				}else{
					saveToFile("Progress Not Saved! \nWould You Like To Save before Loading New File?");
					loadFileAction();
				}
				prog.setIsSaved(true);
			}
		private void loadFileAction(){
	
			int answer = chooseFileToLoad();
			if(answer == fileChooser.APPROVE_OPTION){
				clearData();
				File tempFile = fileChooser.getSelectedFile();
				if(prog.loadFile(tempFile)){
					showNewlyLoadedPlaces();
				}else
					JOptionPane.showMessageDialog(GUI.this, "---Error---\nUnable To Load File");
				}
		}
		private int chooseFileToLoad(){
			int answer;
			setLoadFileFilter();
			answer = fileChooser.showOpenDialog(null);
			restoreFileChooserFilter();
			return answer;
		}
		private void setLoadFileFilter(){
			FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("Places", "places");
			fileChooser.addChoosableFileFilter(mapFilter);
			fileChooser.setFileFilter(mapFilter);
		}
		
	}
	
		
	
	private class MouseCoordinateLis extends MouseAdapter{
		private JPanel describedPlaceInputPanel = new JPanel(new GridBagLayout());
		private JTextField nameField = new JTextField(15);
		private JTextArea descriptionArea = new JTextArea(5, 15);
		private JScrollPane descScroll = new JScrollPane(descriptionArea);
		private GridBagConstraints cons = new GridBagConstraints();

		//---------------------Constructor----------------------------

 		public MouseCoordinateLis() {
			descriptionArea.setWrapStyleWord(true);
			descriptionArea.setLineWrap(true);
			createDescriptionPanel();
		}
		
		//---------------------Methods--------------------------------	
		@Override
		public void mouseClicked(MouseEvent e) {
			if(northPanel.isNewPressed() && !prog.searchForPlace(e.getX(), e.getY())){
				chooseAction(northPanel.getSelectedRadioButton(), e);
				String tempTitle = getTitle().substring(0, 22 );
				setTitle(tempTitle + "---" + e.getX() +"."+ e.getY() +"---");
			}else if(prog.searchForPlace(e.getX(), e.getY())){
				JOptionPane.showMessageDialog(null, "Unable To Add Place At: " + e.getX()+"." + e.getY() + "\n--- Already A Place At Location ---");
			}
			
		}

		private void chooseAction(String actionName, MouseEvent e){
			if(actionName.equals("Named")){
				addNamedPlace(e);
			}else{
				addDescribedPlace(e);
			}
		}
		private void addNamedPlace(MouseEvent e){
			Position tempPos = new Position(e.getX(),e.getY());
			String name = JOptionPane.showInputDialog(null, "Enter Name: ");
			if(name != null && !name.isEmpty()){
				NamedPlace newPlace = prog.addNewNamedPlace(name, tempPos, eastPanel.getSelectedCategory());
				addMarkOnMap(prog.getMapMark(newPlace.getPosition()));
				clearForNewSelection();
			}else if(name != null){
				JOptionPane.showMessageDialog(null, "--- ERROR --- \nNo Name Entered!");
			}	
		}
		
		private void addDescribedPlace(MouseEvent e){
			Position tempPos = new Position(e.getX(),e.getY());
			
			int svar = JOptionPane.showConfirmDialog(null, describedPlaceInputPanel, "New Descrined Place", JOptionPane.OK_CANCEL_OPTION);
			
			
			if(validValuesEntered(svar)){
				DescribedPlace newPlace = prog.addNewDescribedPlace(nameField.getText(), tempPos, eastPanel.getSelectedCategory(), descriptionArea.getText());
				addMarkOnMap(prog.getMapMark(newPlace.getPosition()));
				clearForNewSelection();
			}else if(svar != JOptionPane.CANCEL_OPTION){
				JOptionPane.showMessageDialog(null, "--- ERROR --- \nInvalid Entry!");
			}
			clearDescriptionInputAreas();
		}
		private void addMarkOnMap(MapMark newMarkOnMap){// lï¿½gger till markeringar pï¿½ kartan
			newMarkOnMap.addMouseListener(new MarkLis());
			mapDisplay.add(newMarkOnMap);
			mapDisplay.repaint();
		}
		private void clearDescriptionInputAreas(){
			nameField.setText("");
			descriptionArea.setText("");
		}
		private void clearForNewSelection(){
			eastPanel.clearSelection();
			northPanel.setIsNewPressed(false);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		private void createDescriptionPanel(){
			cons.insets = new Insets(2,2,2,2);
			defineRowOne();
			defineRowTwo();	
		}
		private void assignGridValues(int x, int y, int orientation){
			cons.gridx = x;
			cons.gridy = y;
			cons.anchor = orientation;
		}
		private void defineRowOne(){
			assignGridValues(0, 0, cons.SOUTHEAST);
			describedPlaceInputPanel.add(new JLabel("Name: "), cons);
			
			assignGridValues(1, 0, cons.SOUTHEAST);
			describedPlaceInputPanel.add(nameField, cons);
		}
		private void defineRowTwo(){
			assignGridValues(0, 1, cons.NORTHEAST);
			describedPlaceInputPanel.add(new JLabel("Description: "), cons);
			
			assignGridValues(1, 1, cons.NORTHEAST);
			describedPlaceInputPanel.add(descScroll, cons);
		}


		private boolean validValuesEntered(int svar){
			return svar == JOptionPane.OK_OPTION && !nameField.getText().isEmpty() && !descriptionArea.getText().isEmpty();
		}
		@Override

		public void mouseEntered(MouseEvent e) {
			if(northPanel.isNewPressed()){
				setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(northPanel.isNewPressed()){
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
		}
			
	}
	
	private class MapDisplayCompLis extends ComponentAdapter{
		
		
		
		@Override
		public void componentResized(ComponentEvent e) {
			super.componentResized(e);
			
			resizeMapDisplay();
		}
		
		
		
	}
	
	
	private class GUIFrameWinLis extends WindowAdapter{
		
		
		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			
			if(prog.isSaved()){
				System.exit(0);
				
			}else{
				
				int answer = saveToFile("Progress Not Saved! \nWould You Like To Save before Exiting?");
				if(answer == JFileChooser.APPROVE_OPTION || answer == JOptionPane.NO_OPTION){
						System.exit(0); 
				}
				
				
			}
			
			
		}
		@Override
		public void windowStateChanged(WindowEvent e) {
			// TODO Auto-generated method stub
			super.windowStateChanged(e);
			if(e.getNewState() == MAXIMIZED_BOTH){
				mapScroll.setPreferredSize(new Dimension(mapDisplay.getSize().width, mapDisplay.getSize().height));
				
			}
			else{
				
				resizeMapDisplay();
			}
		}
		
			
	}
	private class LoadMapLis implements ActionListener{
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(prog.isSaved()){
				newMapAction();
			}else{
				saveToFile("Progress Not Saved! \nWould You Like To Save before Loading New File?");
				newMapAction();
			}
			
			
		}
		
		private void newMapAction(){
			int answer = chooseMapFile();
			if(answer == JFileChooser.APPROVE_OPTION){
				clearData();
				ImageIcon tempMap = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
				mapDisplay.setMapDisplay(tempMap);
				setMapDisplaySize(tempMap.getIconWidth(),tempMap.getIconHeight());
				northPanel.enableNewPlaceButton();
				
			}
			
		}
		private int chooseMapFile(){
			int answer;
			FileNameExtensionFilter mapFilter = new FileNameExtensionFilter("Map Image", "PNG","JPEG","BMP","GIF");
			fileChooser.addChoosableFileFilter(mapFilter);
			fileChooser.setFileFilter(mapFilter);
			fileChooser.setAcceptAllFileFilterUsed(false);
			answer = fileChooser.showOpenDialog(null);
			restoreFileChooserFilter();
			return answer;
		}
		
	}
	private class MarkLis extends MouseAdapter{
		JDialog placeInfo = new JDialog();	
		JButton okButt = new JButton("OK");
		InfoPanel info;
		
		public MarkLis(){
			
			placeInfo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			placeInfo.setResizable(false);
			placeInfo.setTitle("Place Info");
			placeInfo.addWindowListener(new InfoPlaceWinLis());
			okButt.addActionListener(new OkLis());
			JPanel buttPanel = new JPanel();
			buttPanel.add(okButt);
			placeInfo.add(buttPanel, BorderLayout.SOUTH);
		}
		@Override
		public void mouseClicked(MouseEvent e){//bryt ut metoder ---> detta har nu gjorts (actionLeftClick(..), actionRightClick(..)), dessa bÃ¶r dock kanske ocksÃ¥ ses Ã¶ver!
			
			if(e.getSource() instanceof MapMark && e.getButton() == e.BUTTON1){
				actionLeftClick(e);
			}
			else if(e.getSource() instanceof MapMark && e.getButton() == e.BUTTON3 && !placeInfo.isVisible() ){      
				actionRightClick(e);
			}		
		}
		private void actionLeftClick(MouseEvent e){
			MapMark tempMark = (MapMark)e.getSource();
			tempMark.setIsMarked();
			tempMark.repaint();
			if(tempMark.isMarked()){
				prog.addSelectedPlace(tempMark);
			}else{
				prog.deselectMark(tempMark);
			}
		}
		private void actionRightClick(MouseEvent e){
			placeInfo.setLocationRelativeTo(e.getComponent());
			MapMark tempMark = (MapMark)e.getSource();
			
			if(tempMark.getMarkedPlace() instanceof NamedPlace){
				namedPlaceInfo(tempMark.getMarkedPlace());
			}else if (tempMark.getMarkedPlace() instanceof DescribedPlace){
				describedPlaceInfo(tempMark.getMarkedPlace());
			}
			
			setInfoPanelSize();
			placeInfo.setVisible(true);
		}
	
 		private void setInfoPanelSize(){
			placeInfo.pack();
			placeInfo.setSize(new Dimension(placeInfo.getWidth()+10, placeInfo.getHeight() + 10));
			
		}
		
		private void namedPlaceInfo(Place place){
			info = new InfoPanel(place.getName(), place.getPosition());
			placeInfo.add(info, BorderLayout.CENTER);
			
		}
		private void describedPlaceInfo(Place place){
			DescribedPlace descPlace = (DescribedPlace)place;
			info = new InfoPanel(descPlace.getName(), descPlace.getPosition(),descPlace.getDescription());
			placeInfo.add(info, BorderLayout.CENTER);
			
		}
		

		
		
						//--------------Inner Class in MarkLis --------------------------
		
		private class OkLis implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				
				placeInfo.remove(info);
				placeInfo.dispose();	
			}
			
		}
		
		private class InfoPlaceWinLis extends WindowAdapter{
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				placeInfo.remove(info);
				super.windowClosing(e);
				
				
			}
		}
	}
	
	private class EastPanelListLis implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(!e.getValueIsAdjusting() && eastPanel.getSelectedCategory() != null){
				showSelectedCategory();
			}
			
		}
		
	}
	private class EastPanelLis implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource().equals(eastPanel.getHideButton())){
				hideCategories();
			}
		}
		
		private void hideCategories(){
			
			try{
				String categoryName = eastPanel.getSelectedCategory().getCategoryName();
				if(!prog.isCategoryEmpty(categoryName)){
					deselectMarks();
					prog.selectCategory(categoryName);
					hideSelectedMarks();
					eastPanel.clearSelection();
				}else{
					JOptionPane.showMessageDialog(null, "There Are No Places Available In Category!");
				}
					
			}catch(NullPointerException err){
				JOptionPane.showMessageDialog(null, "No Category Selected!");
				
			}
			
		}
		
		
	}
	private class NorthPanelLis implements ActionListener{
		JPanel coordinateInputPanel = new JPanel();
		JTextField xInput = new JTextField(8);
		JTextField yInput = new JTextField(8);
		
				//-----------Constructor------
		public NorthPanelLis(){
			createCoordinateInput();
		}
		
				//-----------Methods----------
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String cmd =  northPanel.getPanelComponentText(e.getSource());
			switch(cmd){
			case "Remove":
				removeAction();
				break;
			case "Hide": 
				hideAction();
				break;
			case "Search":
				searchAction();
				break;
			case "Coordinates": 
				coordinatesAction();
				break;
			default:
				System.out.println("Something Went Wrong! " + cmd);
			}
			
		}
		
		
		
		
				//...........RemoveAction...........
		private void removeAction(){//kanske en varning i form av JOPTIONPANE 
			removeMapDisplayComponents();
			prog.removeSelectedPlaces();
			mapDispValRepaint();
		}
		private void removeMapDisplayComponents(){
			Iterator<MapMark> selections = prog.getSelectedPlace().iterator();
			while(selections.hasNext()){
				mapDisplay.remove(selections.next());
			}
		}
		
		
				//...........HideAction...........
		private void hideAction(){
			hideSelectedMarks();
		}
		
		
		
				//...........SearchNameAction...........
		private void searchAction(){
			deselectMarks();
			String placeName = northPanel.getSearchFieldText();
			if(!placeName.isEmpty() && prog.isNameAPlace(placeName)){
				
				setNewSelections(prog.getNameSortedSet(placeName));
				northPanel.resetSearchFieldText();
				
			}else{
				JOptionPane.showMessageDialog(null, "There is no place with the name "+ placeName + "!" );
				northPanel.resetSearchFieldText();
			}
		}
		private void setNewSelections(Iterator<MapMark> newSelect){
			MapMark tempMark;
			while(newSelect.hasNext()){
				tempMark = newSelect.next();
				tempMark.setVisible(true);
				prog.addSelectedPlace(tempMark);
				tempMark.setIsMarked();
			}
			mapDispValRepaint();
		}
		
				//...........CoordinatesAction...........
		private void coordinatesAction(){
			int svar = JOptionPane.showConfirmDialog(null, coordinateInputPanel, "Input coordinates", JOptionPane.OK_CANCEL_OPTION);
			if(svar == JOptionPane.OK_OPTION){
				try{
					int x = Integer.parseInt(xInput.getText());
					int y = Integer.parseInt(yInput.getText());
					if(prog.searchForPlace(x, y)){
						JOptionPane.showMessageDialog(null, "A Place Was Found At: " + x + "."+ y);
						MapMark tempMark = prog.getMapMark(new Position(x,y));
						tempMark.setVisible(true);
						tempMark.setIsMarked();
						mapDispValRepaint();
					
					}
					else {
						JOptionPane.showMessageDialog(null, "No Place Was Found At: " + x + "."+ y);
					}
				}catch(NumberFormatException en){
					JOptionPane.showMessageDialog(null, "--- Error --- \n Invalid Input!");
					
				}
			clearTextFields();
			}
		}
		private void createCoordinateInput(){
			coordinateInputPanel.add(new JLabel("x: "));
			coordinateInputPanel.add(xInput);
			coordinateInputPanel.add(new JLabel("y: "));
			coordinateInputPanel.add(yInput);
			
		}
		private void clearTextFields(){
			xInput.setText("");
			yInput.setText("");
		}
		
		
		
		
		
		
	}
	
	
	
	//---------------------MAIN-----------------------------------


	public static void main(String[] args){
		new GUI();
	}


}
