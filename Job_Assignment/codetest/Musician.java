/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public class Musician {
	
	private String name;
	private boolean isInBand;
	private Instrument instrument;
	
	public Musician(String name, Instrument instrument) {
		this.name = name;
		this.instrument = instrument;
		this.isInBand = false;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getIsInBand() {
		return isInBand;
	}
	
	public void setIsInBand(boolean inBand) {
		this.isInBand = inBand;
	}
	
	public Instrument getInstrument() {
		return instrument;
	}
	
	public String toString() {
		return name + " - " + instrument.toString();
	}
	
}
