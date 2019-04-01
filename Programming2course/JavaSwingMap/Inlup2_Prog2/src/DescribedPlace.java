
public class DescribedPlace extends Place{
	private String description;
		
	//-----------------------Constructor---------------------
	public DescribedPlace(String name, Position pos, String description) {
		super(name, pos);
		this.description = description;
	}
	public DescribedPlace(String name, Position pos, Category category, String description) {
		super(name, pos, category);
		this.description = description;
	}
	//------------------------Methods------------------------
	public String getDescription(){
		return description;
	}
	@Override
	public String prepareForFile() {
		String categoryName = getCategory().getCategoryName();
		String position = getPosition().prepareForFile();
		return "Described,"+categoryName+","+position+","+getName()+","+description;
	}

}
