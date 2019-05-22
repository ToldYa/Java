/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest.instrument;

public abstract class StringedInstrument extends Instrument {
	
	private int numOfStrings;
	private double stringSize;
	
	protected StringedInstrument(int numOfStrings, double stringSize, String manufacturer) {
		super(manufacturer);
		this.numOfStrings = numOfStrings;
		this.stringSize = stringSize;
	}
	
	public int getNumOfStrings() {
		return numOfStrings;
	}
	
	public double getStringSize() {
		return stringSize;
	}
}
