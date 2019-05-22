/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.bandPack;

import codetest.instrument.Instrument;

public class Musician {
	
	private String name;
	private Instrument instrument;
	
	public Musician(String name, Instrument instrument) {
		this.name = name;
		this.instrument = instrument;
	}
	
	public String getName() {
		return name;
	}
	
	public Instrument getInstrument() {
		return instrument;
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(name);
		output.append(" - ");
		output.append(instrument.toString());
		return output.toString();
	}
	
}
