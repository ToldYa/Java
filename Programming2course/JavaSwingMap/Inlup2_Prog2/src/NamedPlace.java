
public class NamedPlace extends Place{
	
	
	//-----------------------Constructor---------------------

	public NamedPlace(String name, Position pos) {
		super(name, pos);
	}
	NamedPlace(String name, Position pos, Category category){
		super(name, pos, category);
		
	}

	//------------------------Methods------------------------
	@Override
	public String prepareForFile() {
		String categoryName = getCategory().getCategoryName();
		String position = getPosition().prepareForFile();
		return "Named,"+categoryName+","+position+","+getName();
	}
	
}
