/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public class Synthesizer extends KeyedInstrument {
	
	boolean hasLED;
	
	public Synthesizer(int numberOfKeys, boolean hasLED, String manufacturer) {
		super(numberOfKeys, manufacturer);
		this.hasLED = hasLED;
	}
	
	public boolean getHasLED() {
		return hasLED;
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Synthesizer: ");
		output.append("NumOfKeys: " + getNumberOfKeys() + ", ");
		output.append("Manufacturer: " + getManufacturer() + ", ");
		output.append("Has LED: " + hasLED);
		
		return output.toString();
	}
	
	@Override
	public boolean equals(Instrument other) {
		boolean equals = false;
		if(other instanceof Synthesizer) {
			Synthesizer temp = (Synthesizer) other;
			equals = getNumberOfKeys() == temp.getNumberOfKeys() &&
					hasLED == temp.hasLED && getManufacturer().equals(temp.getManufacturer());
		}
		return equals;
	}
}
