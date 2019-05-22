/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import codetest.bandPack.*;
import codetest.instrument.*;

public class Program {
	
	private Scanner scanner;
	private Random rand;
	
	private LinkedList<Band> bands;
	private HashMap<Integer, LinkedList<Band>> neededInstruments;	//Key: Instruments represented by Integers Value: List of Bands in need of specified Instrument
	private HashMap<String, Integer> instrumentNumbers; 			//Key: instrument.getClass().toString() Value: The Integer corresponding to each instrument
	private LinkedList<Musician> bandlessMusicians;					//Holds musicians that are not members of a band
	private LinkedList<Musician> musiciansThatFoundBands;			//Holds musicians that have found a band and have to be removed from the bandlessMusicians list
	private final int NUM_OF_INSTRUMENTS = 6;
	
	/*
	 * banjo = 			0
	 * guitar = 		1
	 * ukelele = 		2
	 * 
	 * piano = 			3
	 * synthesizer = 	4
	 * 
	 * drums = 			5
	 */
	
	private Program() {
		scanner = new Scanner(System.in);
		rand = new Random();
		
		bands = new LinkedList<Band>();
		neededInstruments = new HashMap<Integer, LinkedList<Band>>();
		instrumentNumbers = new HashMap<String, Integer>();
		bandlessMusicians = new LinkedList<Musician>();
		musiciansThatFoundBands = new LinkedList<Musician>();
		
		Band tweek = new Band("Tweek and Skaters");
		Band fray = new Band("Frayrats");
		
		bands.add(tweek);
		bands.add(fray);
		
		Iterator<Band> ite = bands.iterator();
		Band band;
		while(ite.hasNext()) {
			band = ite.next();
			for(int i = 0; i < NUM_OF_INSTRUMENTS; i++) {
				if(neededInstruments.get(i) == null)
					neededInstruments.put(i, new LinkedList<>());
				neededInstruments.get(i).add(band);
			}
		}
		
		Musician hazy = new Musician("Hazy Baar", new Guitar(5, 0.4, "Bender"));
		Musician beat = new Musician("Beatwin", new Piano(51, "McFaren", true));
		Musician theVoid = new Musician("The Void", new Synthesizer(47, true, "Lorg"));
		Musician spring = new Musician("Stringsteen", new Banjo(4, 0.8, "Gibson"));
		Musician xind = new Musician("Xindea", new Drums(true, "Toyota"));
		Musician capt = new Musician("Captor", new Synthesizer(72, false, "Suzuki"));
		Musician rita = new Musician("Rita Linter", new Ukelele(7, 1.2, "Brick"));
		
		instrumentNumbers.put(hazy.getInstrument().getClass().toString(), 1);
		instrumentNumbers.put(beat.getInstrument().getClass().toString(), 3);
		instrumentNumbers.put(theVoid.getInstrument().getClass().toString(), 4);
		instrumentNumbers.put(spring.getInstrument().getClass().toString(), 0);
		instrumentNumbers.put(xind.getInstrument().getClass().toString(), 5);
		instrumentNumbers.put(rita.getInstrument().getClass().toString(), 2);
		
		tweek.addMember(hazy);
		neededInstruments.get(1).remove(tweek);
		tweek.addMember(beat);
		neededInstruments.get(3).remove(tweek);
		tweek.addMember(theVoid);
		neededInstruments.get(4).remove(tweek);
		fray.addMember(spring);
		neededInstruments.get(0).remove(fray);
		fray.addMember(xind);
		neededInstruments.get(5).remove(fray);
		fray.addMember(capt);
		neededInstruments.get(4).remove(fray);
		
		bandlessMusicians.add(rita);
		
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
			bandlessMusicians.add(newMusician);
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
		String instrumentName = scanner.nextLine().trim().toLowerCase();
		Instrument instrument = InstrumentCreator.constructInstrument(instrumentName);
		
		return instrument;
	}
	
	
	
	private void addNewBand() {
		Band newBand = retrieveBandInfo();
		bands.add(newBand);
		
		for(int i = 0; i < NUM_OF_INSTRUMENTS; i++) {
			neededInstruments.get(i).add(newBand);
		}
		
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
		Iterator<Musician> ite = bandlessMusicians.iterator();
		Musician musician;
		ArrayList<Band> bandsToRemove = new ArrayList<>(bands.size());
		Band temp;
		while(ite.hasNext()) {
			musician = ite.next();
			temp = attemptToJoinRandomBand(musician);
			if(temp != null)
				bandsToRemove.add(temp);
		}
		
		if(!musiciansThatFoundBands.isEmpty()) {
			for(int i = 0; i < musiciansThatFoundBands.size(); i++) {
				musician = musiciansThatFoundBands.get(i);
				int instrumentNum = instrumentNumbers.get(musician.getInstrument().getClass().toString());
				neededInstruments.get(instrumentNum).remove(bandsToRemove.get(i));
				bandlessMusicians.remove(musiciansThatFoundBands.get(i));
			}
		}
		
		musiciansThatFoundBands.clear();
	}
	
	/**
	 * Attempts to add musician parameter to randomized Band, no changes are made if
	 * randomized Band contains Musician with Instrument of same sub-class
	 * @param musician to add into members LinkedList of random band
	 */
	private Band attemptToJoinRandomBand(Musician musician) {
		int randInt = rand.nextInt(bands.size());
		Band band = bands.get(randInt);
		int instrumentNum = instrumentNumbers.get(musician.getInstrument().getClass().toString());
		
		if(neededInstruments.get(instrumentNum).contains(band)) {
			band.addMember(musician);
			musiciansThatFoundBands.add(musician);
			System.out.println("Musician " + musician.getName() + " joined " + band.getName());
		}else {
			return null;
		}
		
		return band;
	}
	
	/**
	 * Removes one random Musician from each band present in LinkedList bands
	 */
	private void removeRandMusicians() {
		Iterator<Band> ite = bands.iterator();
		Band band;
		
		while(ite.hasNext()) {
			band = ite.next();
			if(band.size() > 0) {
				int randInt = rand.nextInt(band.size());
				Musician removedMember = band.removeMember(randInt);
				bandlessMusicians.add(removedMember);
				neededInstruments.get(instrumentNumbers.get(removedMember.getInstrument().getClass().toString())).add(band);
				System.out.println("Musician " + removedMember.getName() + " left " + band.getName());
			}
		}
	}
	
	/**
	 * Prints details of each Musician present in each band held by LinkedList bands
	 */
	private void printBandDetails() {
		StringBuilder output = new StringBuilder();
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
		System.out.print("Enter command\n\"1\" - List bands\n\"2\" - Play concert\n\"3\" - Exit\n\"4\" - Add new band\n\"5\" - Add new musician: ");
		cmd = scanner.nextLine();
		return cmd.trim();
	}
	
	public static void main(String args[]) {
		Program prog = new Program();
		prog.run();
	}
	
}
