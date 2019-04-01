
import java.util.Scanner;
/** Wilhelm Ericsson wier0584
Emil Vesa emve6881**/

public class DogListProgram{
	
	private DogList dogList = new DogList();
	private Scanner keyboard = new Scanner(System.in);
	
	
	//------------------------Methods--------------
	public void addDogToList(){
		String name, breed;
		int age;
		double weight;
		System.out.println("Add new dog!");
		System.out.print("Enter name: ");
		name = readLine();
		System.out.print("Enter dog breed: ");
		breed = readLine();
		System.out.print("Enter age: ");
		age = readInt();
		System.out.print("Enter weight: ");
		weight = readDouble();
				
		
		dogList.addDog(name, breed, age, weight);
		
	}
	public void listTailLength(){
		System.out.println("Minimal tail length!");
		double minTailLength;
		System.out.print("Enter minimal tail length: ");
		minTailLength = readDouble();
		dogList.listTailLength(minTailLength);
	
	}
	public void changeDogAge(){
		System.out.println("Change dog age!");
		System.out.print("What is the name of the dog? ");
		String name = readLine().toLowerCase().trim();
		dogList.changeDogAge(name);
	}
	public void removeDogFromList(){
		System.out.println("Remove dog from list!");
		System.out.print("What is the name of the dog? ");
		String name = readLine().toLowerCase().trim();
		dogList.removeDog(name);
	}
	public String enterCommand(){
		String menuChoice;
		System.out.println("1: Add new dog \t\t2: Change age of a dog  \t3: Minimal tail length \n" +
				 "4: Print List \t\t5: Remove dog \t\t\t6: Quit ");
		System.out.print("Enter Command: ");
		menuChoice = readLine();
		return menuChoice;
	}
	
	public void startUp(){
		char name = 'a';
		char breed = 'A';
		int age = 2;
		double weight = 8;
		
		for(int i = 0; i < 5; i++){
							
			String newName = "" + name;
			String newBreed = "" + breed;
			dogList.addDog(newName, newBreed, age, weight);
			name++;
			breed++;
			age*= age;
			weight++;
		}
		dogList.addDog("taxi1", "Basset", 9, 35);
		dogList.addDog("taxi2", "Sausage dog", 57, 45);
		dogList.addDog("taxi3", "Dachshund", 3, 15);
		System.out.println("Dogs created!");
	}
	public void runCommandLine(){
		
		String menuChoice;
		boolean run = true;
		while(run){
			
			menuChoice = enterCommand();
			switch(menuChoice){
				case "1": 
					addDogToList();
					break;
				case "2": 
					changeDogAge();
					break;
				case "3": 
					listTailLength();
					break;
				case "4": 
					dogList.printDogList();
					break;
				case "5": 
					removeDogFromList();
					break;
				case "6": 
					System.out.println("Shutting down...");
					run = false;
					break;			
				default:
					System.out.println("Invaild command!");
			}
			
		}
		
	}
	public void exit(){
		keyboard.close();
		System.out.println("Done");
		
	}
	
	public String readLine(){
		String input = keyboard.nextLine();
		return input;
	}
	public int readInt(){
		int input = keyboard.nextInt();
		keyboard.nextLine();
		return input;
	}
	public double readDouble(){
		double input = keyboard.nextDouble();
		keyboard.nextLine();
		return input;
	}
	
	
	//-------------------Main--------------------------
	public static void main(String[] args){
		
		DogListProgram DogListProg = new DogListProgram();
		
		DogListProg.startUp();
			
		DogListProg.runCommandLine();
		
		DogListProg.exit();
		
	
	}
}
