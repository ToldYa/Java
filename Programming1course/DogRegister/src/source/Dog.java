/** Wilhelm Ericsson wier0584
Emil Vesa emve6881**/
public class Dog {
	private final double BASSET_TAIL_LENGHT = 3.7; 
	private String dogName,dogBreed;
	private int dogAge;
	private double dogWeight, tailLength; 
	
	
	//-------------------Constructor----------------------------
	public Dog(String name, String breed, int age, double weight){

		dogName = name;
		dogBreed = breed;
		dogAge = age;
		dogWeight = weight;
		setTailLegnth();
	}
	
	//-------------------Methods----------------------------

	
	public String toString(){
		String output = "Name: " + dogName  + ", Dog breed: "+ dogBreed + ", Age: "+ dogAge + " years" + 
				", Weight: " + dogWeight + " kg "+ ", Tail length: " + tailLength + " cm";
		return output;
	}
	
	public String getDogName(){
		return dogName;
	}
	public String getDogBreed(){
		return dogBreed;
	}
	public int getDogAge(){
		return dogAge;
	}
	public double getDogWeight(){
		return dogWeight;
	}
	public double getTailLength(){
		return tailLength;
	}
	
	public void setDogName(String newName){
		dogName = newName;
	}
	public void setDogBreed(String breed){
		dogBreed = breed;
	}
	public void setDogAge(int age){
		dogAge = age;
	}
	public void setDogWeight(double weight){
		dogWeight = weight;
	}
	public void setTailLegnth(){
		String whatBreed = dogBreed.toLowerCase().trim();
		boolean isBasset = whatBreed.equals("basset") ||  whatBreed.equals("sausage dog") || whatBreed.equals("dachshund");
		if(isBasset){
			tailLength = BASSET_TAIL_LENGHT;
		}else{
			tailLength = (dogAge*dogWeight)/10;
		}
	}		
	
		

}
