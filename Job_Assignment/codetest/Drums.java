/**
 * Author: Emil Vesa
 * Mail: emil.vesa@gmail.com
 */

package codetest;

public class Drums extends PercussionInstrument {
	
	public Drums(boolean hasHiHat, String manufacturer) {
		super(hasHiHat, manufacturer);
	}
	
	@Override
	public String toString() {
		StringBuffer output = new StringBuffer();
		output.append("Drums: ");
		output.append("Has Hi-Hat: " + getHasHiHat() + ", ");
		output.append("Manufacturer: " + getManufacturer());
		
		return output.toString();
	}
	
	@Override
	public boolean equals(Instrument other) {
		boolean equals = false;
		if(other instanceof Drums) {
			Drums temp = (Drums) other;
			equals = getHasHiHat() == temp.getHasHiHat() && getManufacturer().equals(temp.getManufacturer());
		}
		return equals;
	}
}
