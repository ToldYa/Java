import java.util.ArrayList;
/** Wilhelm Ericsson wier0584
Emil Vesa emve6881**/
public class DogList {
	
	private ArrayList<Dog> dogList = new ArrayList<>();
	
	//--------------Methods-----------------------------
	public void addDog(String nameOfNewDog, String breedOfNewDog, int ageOfNewDog, double weightOfNewDog){
		
		Dog newDog = new Dog(nameOfNewDog, breedOfNewDog, ageOfNewDog, weightOfNewDog);
		dogList.add(newDog);
		
		System.out.println("Dog added!");
		System.out.println(dogList.get(dogList.size()-1));
		System.out.println();
	}
	public void printDogList(){
		System.out.println("Print List of dogs!");
		if(!dogList.isEmpty())
			for(int i = 0; i < dogList.size(); i++){
			System.out.println(dogList.get(i));
		}
		else{
			System.out.println("The list is empty...");	
		}
		System.out.println();
	}
	public void listTailLength(double minTailLength){
		
		for(int i = 0; i < dogList.size(); i++){
			
			if(minTailLength == 0){
				printDogList();
			}
			else if(dogList.get(i).getTailLength() >= minTailLength && minTailLength == 10){
				System.out.println(dogList.get(i));
			}
			else if(minTailLength < dogList.get(i).getTailLength()){
				
				System.out.println(dogList.get(i)+ "\n");
			}
			
		}
	}
	public int searchNameInList(String name){
		int searchResult = -1;
		String nameInList;
		
		for(int i = 0; i < dogList.size(); i++){
			
			nameInList = dogList.get(i).getDogName().toLowerCase().trim();
			
			if(nameInList.equals(name)){
				searchResult = i;
				i = dogList.size();
			}
				
		}
		
		return searchResult;
	}
	public void changeDogAge(String name){
		
		int newAge ,dogInList = searchNameInList(name);
		
		if(dogInList >= 0){
			System.out.println("Dog found...");
			newAge = dogList.get(dogInList).getDogAge() + 1;
			dogList.get(dogInList).setDogAge(newAge);
			dogList.get(dogInList).setTailLegnth();
			System.out.println("The age of the dog has been updated.");
		}
		else{
			System.out.println("Unable to update age...");
			System.out.println("A dog with the entered name was not found!");
		}
		
		System.out.println();
	}
	public void removeDog(String name){
		
		int dogInList = searchNameInList(name);
		
		if(dogInList >= 0){
			dogList.remove(dogInList);
			System.out.println("Dog has been removed!");
		}
		else{
			System.out.println("Unable to remove dog...");
			System.out.println("A dog with that name was not found!");
		}
		System.out.println();
	}
}
