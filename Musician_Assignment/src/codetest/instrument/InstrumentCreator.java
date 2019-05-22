package codetest.instrument;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InstrumentCreator {
	static Scanner scanner = new Scanner(System.in);
	
	public static Instrument constructInstrument(String instrumentName) {
		Instrument instrument = null;
		
		switch(instrumentName) {
		case "guitar":
			instrument = createGuitar();
			break;
		case "banjo":
			instrument = createBanjo();
			break;
		case "drums":
			instrument = createDrums();
			break;
		case "piano":
			instrument = createPiano();
			break;
		case "synthesizer":
			instrument = createSynthesizer();
			break;
		case "ukelele":
			instrument = createUkelele();
			break;
		default:
			System.out.println("Invalid instrument name: " + instrumentName);
			break;
		}
		
		return instrument;
	}
	
	private static Drums createDrums() {
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
	
	private static Piano createPiano() {
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
	
	private static Synthesizer createSynthesizer() {
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
	
	private static Banjo createBanjo() {
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
	
	private static Ukelele createUkelele() {
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
	
	private static Guitar createGuitar() {
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
}
