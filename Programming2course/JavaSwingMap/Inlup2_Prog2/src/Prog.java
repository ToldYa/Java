
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;



import java.awt.*;
import java.io.File;

public class Prog {
	private boolean isSaved;
	private HashMap<Position, MapMark> markOnMap = new HashMap<>();
	private HashSet<MapMark> selectedPlaces = new HashSet<>();
	private HashSet<Place> places = new HashSet<>();
	private Map<String, HashSet<Place>> nameSorted = new TreeMap<>();
	private Map<String, HashMap<Position, Place>> categorySorted = new HashMap<>();
	private CategoryListModel categoryList = new CategoryListModel();
	private ProgFileManager fileManager = new ProgFileManager();
//-------------------Construct----------------------

	public Prog(){
		isSaved = true;
		createStandardCategories();
		
	}
//-------------------Methods------------------------
	private void createStandardCategories(){
		addCategories("Bus", Color.RED);	
		addCategories("Train",Color.GREEN);
		addCategories("Underground", Color.BLUE );
	}
 	public void addCategories(String categoryName, Color categoryColor){
		categoryList.addSorted(new Category(categoryName, categoryColor));
	}
	public void addSelectedPlace(MapMark selectedPlace){
		if(!selectedPlaces.contains(selectedPlace)){
			selectedPlaces.add(selectedPlace);
			
		}
		
	}
	public NamedPlace addNewNamedPlace(String name, Position pos, Category category){
		
		NamedPlace tempPlace;
		if(category != null){
			tempPlace = new NamedPlace(name,pos,category);
			places.add(tempPlace);
		}else{
			tempPlace = new NamedPlace(name,pos);
			places.add(tempPlace);
		}
		addPlaceToNameSorted(tempPlace);
		addPlaceToCategorySorted(tempPlace);
		markOnMap.put(tempPlace.getPosition(), new MapMark(tempPlace));
		isSaved = false;
		return tempPlace;	
	}
	public DescribedPlace addNewDescribedPlace(String name, Position pos, Category category, String description){
		
		DescribedPlace tempPlace;
		if(category != null){
			tempPlace = new DescribedPlace(name,pos,category, description);
			places.add(tempPlace);
			
		}else{
			tempPlace = new DescribedPlace(name,pos,description);
			places.add(tempPlace);
			
		}
		addPlaceToNameSorted(tempPlace);
		addPlaceToCategorySorted(tempPlace);
		markOnMap.put(tempPlace.getPosition(), new MapMark(tempPlace));
		isSaved = false;
		return tempPlace;
	}
	
	private void addPlaceToCategorySorted(Place newPlace){
		String categoryName = newPlace.getCategory().getCategoryName();
		boolean exists = categorySorted.containsKey(categoryName);
		
		if(exists){
			categorySorted.get(categoryName).put(newPlace.getPosition(), newPlace); 
		}else{
			HashMap<Position,Place> newMap = new HashMap<>();
			newMap.put(newPlace.getPosition(),newPlace);
			categorySorted.put(categoryName, newMap);
		}
	}
	private void addPlaceToNameSorted(Place newPlace){
		boolean exists = nameSorted.containsKey(newPlace.getName());
		if(exists){
			nameSorted.get(newPlace.getName()).add(newPlace); 
		}else{
			HashSet<Place> newSet = new HashSet<>();
			newSet.add(newPlace);
			nameSorted.put(newPlace.getName(), newSet);
		}
	}
	public void clearSelection(){
		selectedPlaces.clear();
	
	}
	public void deselectMark(MapMark deselectedPlace){
		selectedPlaces.remove(deselectedPlace);
	}
	public CategoryListModel getCategories(){
		return categoryList;
	}
	public Category getCategory(int index){
		return categoryList.get(index);
	}
	public int getCategorySize(){
		return categoryList.size();
	}

	public MapMark getMapMark(Position pos){
		
		return markOnMap.get(pos);
		
		
	}
	public boolean isCategoryEmpty(String categoryName){
		boolean isEmpty;
		try{
			isEmpty = !categorySorted.containsKey(categoryName);
		}catch(NullPointerException e){
			isEmpty = true;
		}
		
		return isEmpty;
	}
	public Iterator<MapMark> getCategorySortedMap(String categoryName){
		HashSet<MapMark> tempMarks = new HashSet<>(); 
		for(Map.Entry<Position, Place> entry : categorySorted.get(categoryName).entrySet()){
			 MapMark mark = markOnMap.get(entry.getKey());
			 tempMarks.add(mark);
		}
		return tempMarks.iterator();
	}
	public Iterator<MapMark> getNameSortedSet(String placeName){
		Iterator<Place> tempNameSorted = nameSorted.get(placeName).iterator();
		HashSet<MapMark> tempMarks = new HashSet<>(); 
		while(tempNameSorted.hasNext()){
			 MapMark mark = markOnMap.get(tempNameSorted.next().getPosition());
			 tempMarks.add(mark);
		}
		return tempMarks.iterator();
	}
	public Iterator<MapMark> getAllMapMark(){
		HashSet<MapMark> tempMapMarks = new HashSet<>();
		for(Map.Entry<Position, MapMark> mapMark : markOnMap.entrySet()){
			tempMapMarks.add(mapMark.getValue());
		}
		
		
		return tempMapMarks.iterator();
	}

	public void selectCategory(String categoryName){
		Iterator<MapMark> tempCategory = getCategorySortedMap(categoryName);
		while(tempCategory.hasNext()){
			MapMark tempMark = tempCategory.next();
			tempMark.setIsMarked();
			addSelectedPlace(tempMark);
		}
		
	}
	public HashSet<MapMark> getSelectedPlace(){
		
		return selectedPlaces;
	}
	public boolean isNameAPlace(String placeName){
		return nameSorted.containsKey(placeName);
	}
	public boolean isSaved(){
		return isSaved;
	}
	public void removeSelectedPlaces(){
		
		Iterator<MapMark> tempSelected = selectedPlaces.iterator();
		while(tempSelected.hasNext()){
			Place tempPlace = tempSelected.next().getMarkedPlace();
			places.remove(tempPlace);
			markOnMap.remove(tempPlace.getPosition());
			removePlaceFromNameSorted(tempPlace);
			removePlaceFromCategorySorted(tempPlace);
		}
		selectedPlaces.clear();
	}
	private void removePlaceFromNameSorted(Place removePlace){
		String nameOfPlace = removePlace.getName();
		nameSorted.get(nameOfPlace).remove(removePlace);
		if(nameSorted.get(nameOfPlace).isEmpty()){
			nameSorted.remove(nameOfPlace);
		}
	}
	private void removePlaceFromCategorySorted(Place removePlace){
		String nameOfCategory = removePlace.getCategory().getCategoryName();
		categorySorted.get(nameOfCategory).remove(removePlace.getPosition());
		if(categorySorted.get(nameOfCategory).isEmpty()){
			categorySorted.remove(nameOfCategory);
		}
	}
	public boolean saveToFile(File file){//kanske ska ta emot ett Fil-objekt istället
		if(isNewFile(file)){
			isSaved = fileManager.saveToNewFile(file, places.iterator());
		}else{

			isSaved = fileManager.saveToFile(places.iterator());
			
		}
		return isSaved;
	}
	private boolean isNewFile(File file){
		return !file.equals(fileManager.getTargetFile());
	}
	public void setIsSaved(boolean newState){
		isSaved = newState;
	}
	public boolean searchForPlace(int x, int y){
		Position tempPos = new Position(x, y);
		Place tempPlace = new NamedPlace("temp",tempPos);
		return places.contains(tempPlace);
	}
	public void clearData(){
		Iterator<Place> tempPlaces = places.iterator();
		while(tempPlaces.hasNext()){
			Place tempPlace = tempPlaces.next();
			markOnMap.remove(tempPlace.getPosition());
			removePlaceFromNameSorted(tempPlace);
			removePlaceFromCategorySorted(tempPlace);
			tempPlaces.remove();
			
		}
	}

	
	
	//Se över hela denna del kan innehålla väldigt mycket fel och konstigheter.

	public boolean loadFile(File file){//se över booleans då dessa inte är kopplade hela vägen.
		boolean isSuccessful = false;
		if(isNewFile(file)){
			restoreData(fileManager.loadFromNewFile(file));
			isSuccessful = true;
		}else{
			restoreData(fileManager.loadFromFile());
			isSuccessful = true;
		}

		return isSuccessful;
	}
	private void restoreData(Map<String,HashSet<String[]>> tempData){
		if(tempData.containsKey("Named")){
			Iterator<String[]> tempNamedPlaces = tempData.get("Named").iterator();
			restoreNamedPlaces(tempNamedPlaces);
		}
		if(tempData.containsKey("Described")){
			Iterator<String[]> tempDescribedPlaces = tempData.get("Described").iterator();
			restoreDescribedPlaces(tempDescribedPlaces);
		}
	}
	
	private void restoreDescribedPlaces(Iterator<String[]> dataDescribedPlaces){
		while(dataDescribedPlaces.hasNext()){
			String[] tempLine = dataDescribedPlaces.next();
			Position tempPos = loadPosition(tempLine[2], tempLine[3]);
			if(!searchForPlace(tempPos.getXCoordinate(), tempPos.getYCoordinate())){
				Category tempCategory = determineCategory(tempLine[1]);
				String placeName = tempLine[4];
				String tempDescription = tempLine[5];
				addNewDescribedPlace(placeName, tempPos, tempCategory, tempDescription);
			}
		}
	}
	
	private void restoreNamedPlaces(Iterator<String[]> dataNamedPlaces){
		while(dataNamedPlaces.hasNext()){
			
			String[] tempLine = dataNamedPlaces.next();
			Position tempPos = loadPosition(tempLine[2], tempLine[3]);
			if(!searchForPlace(tempPos.getXCoordinate(), tempPos.getYCoordinate())){
				Category tempCategory = determineCategory(tempLine[1]);
				String placeName = tempLine[4];
				addNewNamedPlace(placeName, tempPos, tempCategory);
			}
			
		}
	}
	
	private Category determineCategory(String dataLineCategory){
		Category tempCategory; 
		int index = categoryList.binGetIndexOf(dataLineCategory);
		if(index > -1){
			tempCategory = categoryList.get(index);
		}else{
			tempCategory = null;
		}
	
		return tempCategory;
	}
	private Position loadPosition(String xPos, String yPos){
		Position tempPos = null;
		try{
			int x = Integer.parseInt(xPos);
			int y = Integer.parseInt(yPos);
			tempPos = new Position(x, y);
		}catch(NumberFormatException nFE){
			System.err.println("---Error---\nWhile Loading Position Values");
		
		}
		return tempPos;
	}

}