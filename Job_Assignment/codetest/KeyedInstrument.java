/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public abstract class KeyedInstrument extends Instrument{
	
	private int numberOfKeys;
	
	protected KeyedInstrument(int numberOfKeys, String manufacturer) {
		super(manufacturer);
		this.numberOfKeys = numberOfKeys;
	}
	
	public int getNumberOfKeys() {
		return numberOfKeys;
	}
}
