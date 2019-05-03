/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Program {
	
	private Scanner scanner;
	private MyLinkedList<Band> bands;
	private MyLinkedList<Musician> musicians;
	
	/**
	* Constructor used to populate linked lists of musicians and bands
	*/
	private Program() {
		scanner = new Scanner(System.in);
		bands = new MyLinkedList<>();
		musicians = new MyLinkedList<>();
		
		Band tweek = new Band("Tweek and Skaters");
		Band fray = new Band("Frayrats");
		
		bands.add(tweek);
		bands.add(fray);
		
		Musician hazy = new Musician("Hazy Baar", new Guitar(5, 0.4, "Bender"));
		Musician beat = new Musician("Beatwin", new Piano(51, "McFaren", true));
		Musician theVoid = new Musician("The Void", new Synthesizer(47, true, "Lorg"));
		Musician spring = new Musician("Stringsteen", new Banjo(4, 0.8, "Gibson"));
		Musician xind = new Musician("Xindea", new Drums(true, "Toyota"));
		Musician capt = new Musician("Captor", new Synthesizer(72, false, "Suzuki"));
		Musician rita = new Musician("Rita Linter", new Ukelele(7, 1.2, "Brick"));
		
		musicians.add(hazy);
		musicians.add(beat);
		musicians.add(theVoid);
		musicians.add(spring);
		musicians.add(xind);
		musicians.add(capt);
		musicians.add(rita);
		
		tweek.addMember(hazy);
		tweek.addMember(beat);
		tweek.addMember(theVoid);
		fray.addMember(spring);
		fray.addMember(xind);
		fray.addMember(capt);
		
	}
	
	/**
	 * Responsible for the branching of the application through console inputs
	 */
	private void run(){
		String cmd = "";
		boolean running = true;
		while (running) {
			cmd = retrieveCommand(cmd);
			switch(cmd) {
				case "1": //list all bands with members and their instruments
					printBandDetails();
					break;
				case "2": //play one night, bands lose 1 random member and musicians without band try join a random band, print all changes to console
					playConcert();
					break;
				case "3": //quit application
					System.out.println("Goodbye!");
					scanner.close();
					running = false;
					break;
				case "4":
					addNewBand();
					break;
				case "5":
					addNewMusician();
					break;
				default:
					System.out.println("Invalid command given!");
					break;
			}
		}
		
	}
	

	private void addNewMusician() {
		Musician newMusician = retrieveMusicianInfo();
		if(newMusician != null) {
			musicians.add(newMusician);
			System.out.println("New musician: " + newMusician.getName() + " added");
		}
	}
	
	private Musician retrieveMusicianInfo() {
		System.out.print("Provide musician name: ");
		String musicianName = scanner.nextLine();
		Instrument instrument = decideMusicianInstrument();
		if(instrument != null)
			return new Musician(musicianName, instrument);
		else
			return null;
	}
	
	private Instrument decideMusicianInstrument() {
		System.out.print("Provide instrument name {\"Guitar\", \"Banjo\", \"Drums\", \"Piano\", \"Synthesizer\", \"Ukelele\"}: ");
		String instrumentName = scanner.nextLine();
		Instrument instrument = null;
		switch(instrumentName) {
		case "Guitar":
			instrument = createGuitar();
			break;
		case "Banjo":
			instrument = createBanjo();
			break;
		case "Drums":
			instrument = createDrums();
			break;
		case "Piano":
			instrument = createPiano();
			break;
		case "Synthesizer":
			instrument = createSynthesizer();
			break;
		case "Ukelele":
			instrument = createUkelele();
			break;
		default:
			System.out.println("Invalid instrument name: " + instrumentName);
			break;
		}
		return instrument;
	}
	
	private Drums createDrums() {
		boolean hasHiHat = false;
		String manufacturer = "";
		
		try {
			System.out.print("Has Hi-Hat {true/false}: ");
			hasHiHat = scanner.nextBoolean();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Drums(hasHiHat, manufacturer);
	}
	
	private Piano createPiano() {
		int numOfKeys = 0;
		boolean isAcoustic = false;
		String manufacturer = "";
		
		try {
			System.out.print("Number of keys {integer}: ");
			numOfKeys = scanner.nextInt();
			System.out.print("Is acoustic {true/false}: ");
			isAcoustic = scanner.nextBoolean();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Piano(numOfKeys, manufacturer, isAcoustic);
	}
	
	private Synthesizer createSynthesizer() {
		int numOfKeys = 0;
		boolean hasLED = false;
		String manufacturer = "";
		
		try {
			System.out.print("Number of keys {integer}: ");
			numOfKeys = scanner.nextInt();
			System.out.print("has LED {true/false}: ");
			hasLED = scanner.nextBoolean();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Synthesizer(numOfKeys, hasLED, manufacturer);
	}
	
	private Banjo createBanjo() {
		int numOfStrings = 0;
		double thickness = 0.0;
		String manufacturer = "";
		
		try {
			System.out.print("Number of strings {integer}: ");
			numOfStrings = scanner.nextInt();
			System.out.print("String thickness {double}: ");
			thickness = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Banjo(numOfStrings, thickness, manufacturer);
	}
	
	private Ukelele createUkelele() {
		int numOfStrings = 0;
		double thickness = 0.0;
		String manufacturer = "";
		
		try {
			System.out.print("Number of strings {integer}: ");
			numOfStrings = scanner.nextInt();
			System.out.print("String thickness {double}: ");
			thickness = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Ukelele(numOfStrings, thickness, manufacturer);
	}
	
	private Guitar createGuitar() {
		int numOfStrings = 0;
		double thickness = 0.0;
		String manufacturer = "";
		
		try {
			System.out.print("Number of strings {integer}: ");
			numOfStrings = scanner.nextInt();
			System.out.print("String thickness {double}: ");
			thickness = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Manufacturer: ");
			manufacturer = scanner.nextLine();
		}catch(InputMismatchException e) {
			System.out.println("Wrong input type given");
			if(scanner.hasNext())
				scanner.nextLine();
			return null;
		}
		
		return new Guitar(numOfStrings, thickness, manufacturer);
	}
	
	private void addNewBand() {
		Band newBand = retrieveBandInfo();
		bands.add(newBand);
		System.out.println("New band: " + newBand.getName() + " added");
	}
	
	private Band retrieveBandInfo() {
		System.out.print("Provide band name: ");
		String bandName = scanner.nextLine();
		return new Band(bandName);
	}
	
	private void playConcert() {
		removeRandMusicians();
		assignNewMusiciansToBands();
	}
	
	/**
	 * Iterates over available Musician objects in musicians LinkedList and attempts to
	 * add those that are not currently members of a Band to randomized Band
	 */
	private void assignNewMusiciansToBands() {
		Iterator<Musician> ite = musicians.iterator();
		Musician musician;
		
		while(ite.hasNext()) {
			musician = ite.next();
			
			if(!musician.getIsInBand())
				attemptToJoinRandomBand(musician);
		}
		
	}
	
	/**
	 * Attempts to add musician parameter to randomized Band, no changes are made if
	 * randomized Band contains Musician with Instrument of same sub-class
	 * @param musician to add into members LinkedList of random band
	 */
	private void attemptToJoinRandomBand(Musician musician) {
		boolean instrumentAlreadyExists = false;
		Random rand = new Random();
		int randInt = rand.nextInt(bands.size());
		Band band = bands.get(randInt);
		Iterator<Musician> ite = band.getMemberList().iterator();
		
		while(ite.hasNext())
			if(ite.next().getInstrument().getClass().equals(musician.getInstrument().getClass()))
				instrumentAlreadyExists = true;
		
		if(!instrumentAlreadyExists) {
			band.addMember(musician);
			musician.setIsInBand(true);
			System.out.println("Musician " + musician.getName() + " joined " + band.getName());
		}
	}
	
	/**
	 * Removes one random Musician from each band present in LinkedList bands
	 */
	private void removeRandMusicians() {
		Random rand = new Random();
		Iterator<Band> ite = bands.iterator();
		Band band;
		while(ite.hasNext()) {
			band = ite.next();
			if(band.getMemberList().size() > 0) {
				int randInt = rand.nextInt(band.getMemberList().size());
				Musician removedMember = band.getMemberList().remove(randInt);
				removedMember.setIsInBand(false);
				System.out.println("Musician " + removedMember.getName() + " left " + band.getName());
			}
		}
	}
	
	/**
	 * Prints details of each Musician present in each band held by LinkedList bands
	 */
	private void printBandDetails() {
		StringBuffer output = new StringBuffer();
		Iterator<Band> ite = bands.iterator();
		Band temp;
		
		while(ite.hasNext()) {
			temp = ite.next();
			output.append(temp.toString());
			if(ite.hasNext())
				output.append("\n");
		}
		
		System.out.println(output.toString());
	}
	
	private String retrieveCommand(String cmd) {
		System.out.print("Enter command {\"1\", \"2\", \"3\", \"4\", \"5\"}: ");
		cmd = scanner.nextLine();
		return cmd.trim();
	}
	
	public static void main(String args[]) {
		Program prog = new Program();
		prog.run();
	}
	
}
